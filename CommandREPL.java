/*
Dilen Govin
Jordan
Section D

Assignment 12 - CommandREPL.java, CSC 210, Fall 2017
REPL which handles user commands as they are passed in. Reads from stdin using
a Scanner Object to read line by line. Those lines are parsed based on their
first word and evaluated into a valid command or invalid command. The only time
the Thread stops if the user signifies they want to quit by calling the "quit"
command.
*/

import java.io.*;
import java.util.*;

public class CommandREPL extends Thread{

  private Player player;
  private HashMap<String,Room> rooms;
  private HashMap<String,Item> items;
  private HashMap<String,Mob> mobs;
  private Room stash;
  private Room currRoom;

  // initializes the REPL object with data from the Game class
  public CommandREPL(Player p, HashMap<String,Room> r, HashMap<String,Item> i, HashMap<String,Mob> m, Room s){
    player = p;
    rooms = r;
    items = i;
    mobs = m;
    stash = s;
  }

  @Override
  public void run(){
    Scanner in = new Scanner(System.in);

    System.out.println("Welcome to the adventure!");
    System.out.println("What class would you like to play as (Warrior, Dwarf, "
                       + "or Ranger)?");

    getRole(in); // gets the role

    System.out.println("Starting the adventure...");

    currRoom = stash;

    while (true){
      System.out.print("> ");
      String line = in.nextLine();
      String[] command = line.split("\\s+");

      /* look command
      *  returns details of the current room
      */
      if (command[0].toLowerCase().equals("look")){
        currRoom.look();

      /* examine command
      *  prints out the details of an item or mob the user wants to examine
      */
      } else if (command[0].toLowerCase().equals("examine")){
        examine(command[1], line);

      /* move command
      *  moves from current room to the connecting room in the user specified
      *  direction. If no connection or bad direction returns invalid.
      */
      } else if (command[0].toLowerCase().equals("move")){
        moveRoom(command[1]);

      /* pickup command
      *  Pickup an item that is currently in the room. If the item doesn't exist
      *  or there is another error, returns and invalid command or item. There must
      *  be no mob in the room for an item to be picked up.
      */
      } else if (command[0].toLowerCase().equals("pickup")){
        pickupItem(command[0], line);

      /* drop command
      *  Command takes an item from inventory and drops it on the floor, The
      *  dropped item will be placed in the Room. If item is not in inventory
      *  returns an invalid item.
      */
      } else if (command[0].toLowerCase().equals("drop")){
        String itemName = line.split(command[0])[1].trim().toLowerCase();
        dropItem(itemName);


      /* stash commands
      *  stores an item in the stash room. Cannot be accessed anymore. Stashed
      *  items will count towards scores.
      */
      } else if (command[0].toLowerCase().equals("stash")){
        String itemName = line.split(command[0])[1].trim().toLowerCase();
        stashItem(itemName);


      /* fight command
      *  Fight commands takes a current weapon argument to fight the current mob
      *  in the room.
      */
      } else if (command[0].toLowerCase().equals("fight")){
        fight(command[0], line);

      /* inventory command
      *  Prints out an alphabetically sorted examine list of the items in the
      *  inventory. If no items exist nothing is printed.
      */
      } else if (command[0].toLowerCase().equals("inventory")){

        for (String itemName : player.getItems().keySet()){
          player.getItems().get(itemName).examine();
          System.out.println();
        }

      /* quit command
      *  Exits the program and prints the user's score. Exits with a status of
      *  0.
      */
      } else if (command[0].toLowerCase().equals("quit")){
        System.out.println("Finishing game...");
        System.out.println("Final score: " + player.calcScore());
        System.exit(0);

      // catch for any command spelt wrong or non-exstent command
      } else {
        System.out.println("Invalid command.");
      }
    }
  }

  /*
  getRole method
  Purpose - used to acquire the role of the player. If the player doesn't give a
    valid class, the function continues until the user inputs a correct class.

  Arguments
    Scanner in - Scanner object to continue reading input if class is invalid

  Returns
    None
  */

  public void getRole(Scanner in){
    String pClass = "";
    while (pClass == ""){
      pClass = in.nextLine();

      pClass = pClass.trim().toLowerCase(); // removes case sensitivity

      if (pClass.equals("warrior")){
        player = new Warrior();
      } else if (pClass.equals("ranger")){
        player = new Ranger();
      } else if (pClass.equals("dwarf")){
        player = new Dwarf();
      } else {
        System.out.println("Invalid choice.");
        pClass = "";
      }
    }
  }

  public void examine(String identifier, String line){
    // examine for mob
    if (identifier.toLowerCase().equals("mob")){
      String mobName = line.split(identifier)[1].trim();
      examineMob(mobName);

    // examine for items
  } else if (identifier.toLowerCase().equals("item")){
      String itemName = line.split(identifier)[1].trim();
      examineItem(itemName);

    // catch for not putting item or mob
    } else {
      System.out.println("Invalid command.");
    }
  }

  public void examineMob(String mobName){
    if (mobs.containsKey(mobName.toLowerCase())){
      if (currRoom.getMob() != null){
        if (currRoom.getMob().getName().toLowerCase().equals(mobName.toLowerCase())){
          mobs.get(mobName.toLowerCase()).examine();
        } else {
          System.out.println("Invalid command.");
        }
      } else {
        System.out.println("Invalid command.");
      }
    } else {
      System.out.println("Invalid command.");
    }
  }

  public void examineItem(String itemName){
    if (items.containsKey(itemName.toLowerCase())){
      if (currRoom.getItem(itemName.toLowerCase()) != null){
        // exmaine the item
        items.get(itemName.toLowerCase()).examine();
      } else {
        System.out.println("Invalid command.");
      }
    } else {
      System.out.println("Invalid command.");
    }
  }

  public void fight(String command, String line){
    if (currRoom.getMob() != null){
      Mob mob = currRoom.getMob();
      String itemName = line.split(command)[1].trim().toLowerCase();

      if (player.getItems().containsKey(itemName)){
        Item item = items.get(itemName);
        if (item instanceof Weapon){
          if (player.getPower() + item.getPower() > mob.getPower()){
            currRoom.setMob(null); // removes mob
            System.out.println("You destroyed " + mob.getName() + " with power level " +
                               mob.getPower() + "!");
          } else {
            System.out.println(mob.getName() + " defeated you! Try a different weapon.");
          }
          player.addMove();
        } else {
          System.out.println("Invalid weapon.");
        }
      } else {
        System.out.println("Invalid weapon.");
      }
    } else {
      System.out.println("No mob found.");
    }
  }

  public void moveRoom(String command){
    if (currRoom.getConnection(command.toUpperCase()) == null){
      System.out.println("Invalid direction.");
    } else {
      // sets current move to connected room
      currRoom = currRoom.getConnection(command.toUpperCase());
      player.addMove();
    }
  }

  public void pickupItem(String command, String line){
    if (currRoom.getMob() != null){
      System.out.println("You must destroy the mob first.");
    } else {
      String itemName = line.split(command)[1].trim().toLowerCase();

      if (items.containsKey(itemName)){
        if (currRoom.getItem(itemName) != null){
          if (player.addItem(itemName, items.get(itemName)) == 0){
            currRoom.removeItem(itemName); // removes item from name
          }
        } else {
          System.out.println("Invalid item.");
        }
      } else {
        System.out.println("Invalid item.");
      }
    }
  }

  public void dropItem(String itemName){
    if (player.getItems().containsKey(itemName)){
      player.removeItem(itemName);
      currRoom.addItem(items.get(itemName));
    } else {
      System.out.println("Invalid item.");
    }
  }

  public void stashItem(String itemName){
    if (currRoom == stash){
      if (player.getItems().containsKey(itemName)){
        player.stash(items.get(itemName));
        player.removeItem(itemName);
      } else {
        System.out.println("Invalid item.");
      }
    } else {
      System.out.println("Invalid command.");
    }
  }
}

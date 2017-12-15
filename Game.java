/*
Dilen Govin
Jordan
Section D

Assignment 12 - Game.java, CSC 210, Fall 2017
Game class which creates and oversees the operation of a text based dungeon crawler
that parses a game file and user input. The game allows the player to choose one
three classes, Ranger, Warrior, and Dwarf, and traverse the map and collect items.
The purpose of this class is to process the game file and create the "game board"
and the HashMap storage of rooms, items, and mobs.
*/

import java.util.*;
import java.io.*;

public class Game{

  private static Player player; // variable for player Object
  private static HashMap<String,Room> rooms;  // maps room names to Room Object
  private static HashMap<String,Item> items;  // maps item names to Item Object
  private static HashMap<String,Mob> mobs;    // maps mob names to Mob Object
  private static Room stash;  // declares stash room pointer

  public static void main(String[] args){
    player = new Player();
    rooms = new HashMap<String,Room>();
    items = new HashMap<String,Item>();
    mobs = new HashMap<String,Mob>();
    stash = null;
    // initializes the rooms, items, mobs, stash, and player Objects/Data

    fillDungeon(args[0]);

    // creats REPL object and starts it on a new thread
    CommandREPL repl = new CommandREPL(player, rooms, items, mobs, stash);
    repl.start();
  }

  /*
  fillDungeon method
  Purpose - this method is repsonsible for reading in the name of the game file
    and parsing it into the game board as well as items and mobs. Uses a BufferedReader
    Object to read form file line by line.

  Arguments
    String file - name of the game file. Passed in from args[0] as a command line
      argument.

  Returns
    None
  */

  public static void fillDungeon(String file){
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String line = null;

      while((line = br.readLine()) != null) { // reads file by line
        String[] parse = line.split("\\s+");

        if (parse[0].equals("define")){
          if (parse[1].equals("Room")){
            defineRoom(br);

          } else if (parse[1].equals("Treasure")){
            defineTreasure(br);

          } else if (parse[1].equals("Weapon")){
            defineWeapon(br);

          } else if (parse[1].equals("Mob")){
            defineMob(br);

          } else {
            System.out.println("Invalid define command");
          }
        } else if (parse[0].equals("end")){
          continue;

        } else {
          connectRooms(line); // creates room connections
        }
      }
      br.close();

    } catch(FileNotFoundException ex) { // catch for files not found
        System.err.println("Error: Unable to open file '" + file + "'");
        System.exit(1);

    } catch(IOException ex) { // catch for error opening file
        System.err.println("Error: Error reading file '" + file + "'");
        System.exit(1);
    }
  }

  /*
  defineRoom, defineTreasure, defineWeapon, defineMob methods
  Purpose - methods for parsing sections of the game file declared by "define"
    into their respective game Objects. The Objects are created case sensitive,
    but storing the information is case insensitive.

  Arguments
    BufferedReader br - BufferedReader to retreive information from the file

  Returns
    None
  */

  public static void defineRoom(BufferedReader br){
    try {
      // sets name of room
      String name = br.readLine().split("name:")[1].trim();
      Room currRoom = new Room(name);

      // sets description for the room
      String description = br.readLine().split("description:")[1].trim();
      currRoom.setDescription(description.trim());

      // sets items found in the room, if not none
      String[] itemArray = br.readLine().split("items: ")[1].split(", ");
      if (!itemArray[0].trim().toLowerCase().equals("none")){
        for (String itemName : itemArray){
          if (!items.containsKey(itemName.trim().toLowerCase())){
            System.err.println("Item is not defined earlier in file");
            System.exit(1);
          }
          if (items.get(itemName.trim().toLowerCase()).setLocation(currRoom) == 0){
            currRoom.addItem(items.get(itemName.trim().toLowerCase()));
          } else {
            System.err.println("Item already placed");
            System.exit(1);
          }
        }
      }

      // sets the mob in the room
      String mob = br.readLine().split("mob:")[1].trim();
      if (!mob.equals("none")){
        currRoom.setMob(mobs.get(mob.toLowerCase()));
      }

      // adds the room to the Room HashMap
      rooms.put(name.toLowerCase(), currRoom);

      // if first room, then sets it as the stash room.
      if (stash == null){
        stash = rooms.get(name.toLowerCase());
      }
    } catch(FileNotFoundException ex) { // catch for files not found
        System.err.println("Error: Unable to open file");
        System.exit(1);
    } catch(IOException ex) { // catch for error opening file
        System.err.println("Error: Error reading file");
        System.exit(1);
    }
  }

  // see above block comment
  public static void defineTreasure(BufferedReader br){
    try {
      Item treasure = new Treasure();

      // parse into desired values by splitting on category
      String name = br.readLine().split("name:")[1].trim();
      Float weight = Float.parseFloat(br.readLine().split("weight:")[1].trim());
      int value = Integer.parseInt(br.readLine().split("value:")[1].trim());

      treasure.setName(name);
      treasure.setWeight(weight);
      treasure.setValue(value);

      items.put(name.toLowerCase(), treasure);

    } catch(FileNotFoundException ex) { // catch for files not found
        System.err.println("Error: Unable to open file");
        System.exit(1);
    } catch(IOException ex) { // catch for error opening file
        System.err.println("Error: Error reading file");
        System.exit(1);
    }
  }

  // see above block comment
  public static void defineWeapon(BufferedReader br){
    try{
      Item weapon = new Weapon();

      // parse into desired values by splitting on category
      String name = br.readLine().split("name:")[1].trim();
      Float weight = Float.parseFloat(br.readLine().split("weight:")[1].trim());
      int damage = Integer.parseInt(br.readLine().split("damage:")[1].trim());

      weapon.setName(name);
      weapon.setWeight(weight);
      weapon.setPower(damage);

      items.put(name.toLowerCase(), weapon);

    } catch(FileNotFoundException ex) { // catch for files not found
        System.err.println("Error: Unable to open file");
        System.exit(1);
    } catch(IOException ex) { // catch for error opening file
        System.err.println("Error: Error reading file");
        System.exit(1);
    }
  }

  // see above block comment
  public static void defineMob(BufferedReader br){
    try {
      Mob mob = new Mob();

      // parse into desired values by splitting on category
      String name = br.readLine().split("name:")[1].trim();
      String description = br.readLine().split("description:")[1].trim();
      int power = Integer.parseInt(br.readLine().split("power:")[1].trim());

      mob.setName(name);
      mob.setDescription(description);
      mob.setPower(power);

      mobs.put(name.toLowerCase(), mob);

    } catch(FileNotFoundException ex) { // catch for files not found
        System.err.println("Error: Unable to open file");
        System.exit(1);
    } catch(IOException ex) { // catch for error opening file
        System.err.println("Error: Error reading file");
        System.exit(1);
    }
  }

  /*
  connectRooms method
  Purpose - parse a String line passed in as input and connects the Room Objects
    if the connection is valid. If not the fuinction displays an invalid file Error
    and exits with a status of 1.

  Arguments
    String line - String that delcares the connection, with the following form,
      room1 DIRECTION room2

  Returns
    None
  */

  public static void connectRooms(String line){
    String direction = ""; // sets the key for Room connection
    String[] room = null;

    if (line.contains("NORTH")){
      room = line.split("NORTH ");
      direction = "NORTH";
    } else if (line.contains("SOUTH")){
      room = line.split("SOUTH ");
      direction = "SOUTH";
    } else if (line.contains("EAST")){
      room = line.split("EAST ");
      direction = "EAST";
    } else if (line.contains("WEST")){
      room = line.split("WEST ");
      direction = "WEST";
    }

    if (room != null){
      if (rooms.containsKey(room[0].trim().toLowerCase())){

        if (rooms.containsKey(room[1].trim().toLowerCase())){
          String room1 = room[0].trim().toLowerCase(); // name of room 1
          String room2 = room[1].trim().toLowerCase(); // name of room 2

          rooms.get(room1).setConnection(direction, rooms.get(room2));
          // sets connection
        } else {
          System.err.println(room[1].trim() + " does not exist");
          System.exit(1);
        }
      } else {
        System.err.println(room[0].trim() + " does not exist");
        System.exit(1);
      }
    }
  }
}

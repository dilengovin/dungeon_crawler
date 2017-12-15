/*
Dilen Govin
Jordan
Section D

Assignment 12 - Player.java, CSC 210, Fall 2017
PLayer object that keeps track of the player's inventory, power, moves, and max
carryweight. This class is the paretn of the roles the user can select when playing
the game, extended in Warrior, Ranger, and Dwarf.
*/

import java.util.*;

public class Player{

  // variables to stroe data in the game.
  private int power;
  private int moves;
  private float carryWeight;
  private float currWeight;
  private TreeMap<String,Item> items;
  private ArrayList<Item> stash;

  public Player(){
    moves = 0;
    power = 0;
    carryWeight = 20.0f;
    currWeight = 0.0f;
    items = new TreeMap<String,Item>();
    stash = new ArrayList<Item>();
  }

  /* Getters and Setters for private vairables
  *  sets or returns value associated with those vairables
  */

  public int getMoves(){
    return moves;
  }

  public void addMove(){
    moves++;
  }

  public int getPower(){
    return power;
  }

  public void setPower(int p){
    power = p;
  }

  public void setCarryWeight(float w){
    carryWeight = w;
  }

  public float getCurrWeight(){
    return currWeight;
  }

  public ArrayList<Item> getStash(){
    return stash;
  }

  public void stash(Item item){
    stash.add(item);
  }

  /* Inventory methods
  *  methods that manipulate the inventory of the player
  */

  // adds and item to inventory
  public int addItem(String itemName, Item item){
    if ((item.getWeight() + currWeight) > carryWeight){
      System.out.println("I cannot carry this item.");
      return 1;
    } else {
      items.put(itemName, item);
      currWeight += item.getWeight();
    }
    return 0;
  }

  // removes an item form inventory
  public void removeItem(String itemName){
    currWeight -= items.get(itemName).getWeight();
    items.remove(itemName);
  }

  // returns the TreeMap of the inventory
  public TreeMap<String,Item> getItems(){
    return items;
  }

  // returnsm the floor int of the user's score
  public int calcScore(){
    if (moves > 0){
      int sum = 0;
      for (Item i : stash){
        if (i instanceof Treasure){ // only adds value from Treasure Objects
          sum += i.getValue();
        }
      }
      return (sum / moves);
    } else {
      return 0;
    }
  }
}

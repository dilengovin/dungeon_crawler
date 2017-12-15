/*
Dilen Govin
Jordan
Section D

Assignment 12 - Room.java, CSC 210, Fall 2017
Following is the code for a Room object to be utalized in the
Game.java and CommandREPL.java. Stores name, description, mob,
items, and connections to other rooms to create a sort of map.
*/

import java.util.*;
import java.io.*;

public class Room{

  private String name;
  private String description;

  private Mob mob; // pointer to mob in room
  private TreeMap<String,Item> items;
  private TreeMap<String,Room> connections;

  public Room(String n){
    name = n;
    description = "";

    mob = null; // no mob, unless stated in game file
    items = new TreeMap<String,Item>();
    connections = new TreeMap<String,Room>();
  }

  /* Getters and Setters for private vairables
  *  sets or returns value associated with those vairables
  */

  public void setName(String n){
    name = n;
  }

  public String getName(){
    return name;
  }

  public void setDescription(String d){
    description = d;
  }

  public String getDescription(){
    return description;
  }

  public void setMob(Mob m){
    mob = m;
  }

  public Mob getMob(){
    return mob;
  }

  // adds item to the item TreeMap
  public void addItem(Item item){
    items.put(item.getName().toLowerCase(), item);
  }

  // removes an item from TreeMap
  public void removeItem(String itemName){
    items.remove(itemName);
  }

  // returns the keySet for items
  public Set<String> getItems(){
    return items.keySet();
  }

  // returns an item based on key
  public Item getItem(String i){
    if (items.containsKey(i)){
      return items.get(i);
    }
    return null;
  }

  // prints the items in a list
  public String itemString(){
    String str = "Items: ";
    if (getItems().isEmpty()){
      return str + "none";
    }
    for (String s : getItems()){
      str += items.get(s).getName() + ", ";
    }
    return str.substring(0, str.length() - 2); // removes ending comma
  }

  // sets a room connection
  public void setConnection(String d, Room room){
    if (!connections.containsKey(d)){
      connections.put(d, room);
    }
  }

  // returns the room connected by the direction
  public Room getConnection(String d){
    if (connections.containsKey(d)){
      return connections.get(d);
    }
    return null;
  }

  // returns all connections
  public Set<String> getConnections(){
    return connections.keySet();
  }

  // returns a list of the directions
  public String getDirections(){
    String str = "There are connections in the following directions: ";
    if (getConnections().isEmpty()){
      return str + "none";
    }
    for (String s : getConnections()){
      str += s + ", ";
    }
    return str.substring(0, str.length() - 2); // removes ending comma
  }

  // prints out the room in the spec detialed form
  public void look(){
    System.out.println("Name: " + name);
    System.out.println("Description: " + description);
    System.out.println(getDirections());
    System.out.println(itemString());

    if (mob == null){
      System.out.println("Mob: none");
    } else {
      System.out.println("Mob: " + mob.getName());
    }
  }
}

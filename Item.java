/*
Dilen Govin
Jordan
Section D

Assignment 12 - Item.java, CSC 210, Fall 2017
Item class that is abstract. Is the basis for Treasure and Weapon objects in the
game. Makes stroage of the Treasure and Weapons easier.
*/

public abstract class Item{

  private String name;
  private float weight;
  private Room location;

  public Item(){
    weight = 0.0f;
    location = null;
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

  public void setWeight(float w){
    weight = w;
  }

  public float getWeight(){
    return weight;
  }

  // sets location of the item if not already in a room
  public int setLocation(Room room){
    if (location == null){ // if not already placed
      location = room;
      return 0;
    }
    return 1;
  }

  public Room getLocation(){
    return location;
  }

  // abstract class for Item and Treasure

  public abstract int getValue();
  public abstract void setValue(int v);

  public abstract int getPower();
  public abstract void setPower(int p);

  public abstract void examine();
}

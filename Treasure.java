/*
Dilen Govin
Jordan
Section D

Assignment 12 - Treasure.java, CSC 210, Fall 2017
Treasure object that is used to calculate score. Stores a value which is an int
that correllates to its worth. The class extends Item, and implements its
abstract classes.
*/


public class Treasure extends Item{

  private int value;

  public Treasure(){
    value = 0;
  }

  /* Getters and Setters for private vairables
  *  sets or returns value associated with those vairables
  */

  public void setValue(int v){
    value = v;
  }

  public int getValue(){
    return value;
  }

  public int getPower(){
    return 0;
  }

  public void setPower(int p){
    ;
  }

  // Prints out the examine command
  public void examine(){
    System.out.println("Name: " + getName());
    System.out.println("Weight: " + getWeight());
    System.out.println("Value: " + value);
  }
}

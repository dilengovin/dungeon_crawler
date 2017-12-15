/*
Dilen Govin
Jordan
Section D

Assignment 12 - Weapon.java, CSC 210, Fall 2017
Weapon object that is used to calculate score. Stores aits power which is an int
that correllates to its damage. The class extends Item, and implements its
abstract classes.
*/

public class Weapon extends Item{

  private int power;

  public Weapon(){
    power = 0;
  }

  /* Getters and Setters for private vairables
  *  sets or returns value associated with those vairables
  */

  public void setPower(int p){
    power = p;
  }

  public int getPower(){
    return power;
  }

  public int getValue(){
    return 0;
  }

  public void setValue(int v){
    ;
  }

  // Prints out the examine command
  public void examine(){
    System.out.println("Name: " + getName());
    System.out.println("Weight: " + getWeight());
    System.out.println("Power: " + power);
  }
}

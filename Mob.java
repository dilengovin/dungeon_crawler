/*
Dilen Govin
Jordan
Section D

Assignment 12 - Mob.java, CSC 210, Fall 2017
Mob object for the player to fight in the game. Mob's have a power level, name,
and a description. Initiliazed to 0 power, until changed. 
*/

public class Mob{

  private String name;
  private String description;
  private int power;

  public Mob(){
    power = 0;
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

  public void setPower(int p){
    power = p;
  }

  public int getPower(){
    return power;
  }

  // Prints out the examine command for a mob
  public void examine(){
    System.out.println("Name: " + name);
    System.out.println("Description: " + description);
  }
}

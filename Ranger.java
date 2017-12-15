/*
Dilen Govin
Jordan
Section D

Assignment 12 - Dwarf.java, CSC 210, Fall 2017
Extends player but overrides the player's calcScoire class. The score is now
2 times what it was due to Ranger's special ability.
*/

public class Ranger extends Player{

  public Ranger(){
    super();
  }

  // recalculates the score to be 2x that of the warrior or dwarf
  public int calcScore(){
    if (getMoves() > 0){
      int sum = 0;
      for (Item i : getStash()){
        if (i instanceof Treasure){
          sum += i.getValue();
        }
      }
      return (sum / (int) Math.floor(getMoves() / 2.0));
    } else {
      return 0;
    }
  }
}

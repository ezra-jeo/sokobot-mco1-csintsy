package solver;

import java.util.HashSet;
import java.util.Set;

public class SokoBot {

  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
    /*
     * YOU NEED TO REWRITE THE IMPLEMENTATION OF THIS METHOD TO MAKE THE BOT SMARTER
     */
    /*
     * Default stupid behavior: Think (sleep) for 3 seconds, and then return a
     * sequence
     * that just moves left and right repeatedly.
     */
    try {
      Thread.sleep(3000);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return "ududududududududududud";
  }

  public boolean isValidAction(Position nextPosition, char[][] mapData) {
      boolean isValid;

      if (mapData[nextPosition.getX()][nextPosition.getY()] != '#')
          isValid = true;
      else  
          isValid = false;

        return isValid;
  }

  public Position getPlayerPosition(char[][] itemsData) {
      int rowLength = itemsData.length;
      int columnLength = itemsData[0].length;
      int i = 0;
      int j = 0;
      boolean found = false;

      while (!found && i < rowLength) {
          while (!found && j < columnLength) {
              if (itemsData[i][j] == '@')
                  found = true;              
              j++;
          }
          i++;
      }
      
      Position playerPosition = new Position(i - 1, j - 1);
      return playerPosition;
  }

  public Set<Position> getCratePositions(char[][] itemsData) {
      int rowLength = itemsData.length;
      int columnLength = itemsData[0].length;
      Position cratePosition = null;
      Set<Position> crateList = new HashSet<>();

      for (int i = 0; i < rowLength; i++) {
          for (int j = 0; j < columnLength; j++) {
              if (itemsData[i][j] == '$') {
                  cratePosition = new Position(i, j);
                  crateList.add(cratePosition);
              }
          }
      }
      
      return crateList;
  }

  public Set<Position> getGoalCratePositions(char[][] mapData) {
      int rowLength = mapData.length;
      int columnLength = mapData[0].length;
      Position goalCratePosition = null;
      Set<Position> goalCrateList = new HashSet<>();

      for (int i = 0; i < rowLength; i++) {
          for (int j = 0; j < columnLength; j++) {
              if (mapData[i][j] == '.') {
                  goalCratePosition = new Position(i, j);
                  goalCrateList.add(goalCratePosition);
              }
          }
      }
      
      return goalCrateList;
  }

}

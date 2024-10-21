package solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
      String actionSequence = "";

      try {
          Thread.sleep(15000);

          Queue<Node> queue = new LinkedList<Node>();
          Set<State> visitedStates = new HashSet<>();
          Position playerPosition = getPlayerPosition(itemsData);
          Set<Position> cratePositions = getCratePositions(itemsData);
          Set<Position> goalCratePositions = getGoalCratePositions(mapData);
          State state = new State(playerPosition, cratePositions);
          List<String> possibleActions = null;
          Node node = new Node(null, null, state);
          boolean isGoal = false;

          queue.offer(node);

          while (!isGoal && !queue.isEmpty()) {
              node = queue.poll();

              if (node.getState().isGoal(goalCratePositions)) {
                  isGoal = true;
                  actionSequence = getActionSequence(node);
              }
              else {
                  visitedStates.add(node.getState());
                  possibleActions = getPossibleActions(node.getState(), mapData, itemsData);

                  for (String action : possibleActions) {
                      state = getNextState(node.getState(), actionSequence, itemsData);
                        
                      if (!visitedStates.contains(state) && !state.isDeadlock(mapData)) {
                          node = new Node(node, action, state);
                          queue.offer(node);
                      }
                  }
              }
          }
      } catch (Exception ex) {
          ex.printStackTrace();
      }

      return actionSequence;
  }

  public String getActionSequence(Node node) {
      String actionSequence = "";
      StringBuilder actionSequenceReverse = new StringBuilder();

      while (node != null) {
          actionSequence = actionSequence + node.getAction();
          node = node.getPreviousNode();
      }

      actionSequenceReverse.append(actionSequence);
      actionSequenceReverse.reverse();
      actionSequence = actionSequenceReverse.toString();
      return actionSequence;

  }

  public List<String> getPossibleActions(State state, char[][] mapData, char[][] itemsData) {
      String[] actualActions = {"u", "d", "l", "r"};
      List<String> possibleActions = new ArrayList<>();

      for (int i = 0; i < 4; i++) {
          if (isValidAction(actualActions[i], state.getPlayerPosition(), mapData, itemsData)) {
              possibleActions.add(actualActions[i]);
          }
      }

      return possibleActions;
  }

  public boolean isValidAction(String action, Position playerPosition, char[][] mapData, char[][] itemsData) {  
      boolean isValid;
        
      if (action.equals("u")) {
          if (mapData[playerPosition.getX() - 1][playerPosition.getY()] == '#') {
              isValid = false;
          }
          else if (itemsData[playerPosition.getX() - 1][playerPosition.getY()] == '$' &&
                  (itemsData[playerPosition.getX() - 2][playerPosition.getY()] == '$' || 
                   mapData[playerPosition.getX() - 2][playerPosition.getY()] == '#')) {
              isValid = false;
          }
          else {
              isValid = true;
          }
      }
      else if (action.equals("d")) {
          if (mapData[playerPosition.getX() + 1][playerPosition.getY()] == '#') {
              isValid = false;
          }
          else if (itemsData[playerPosition.getX() + 1][playerPosition.getY()] == '$' &&
                  (itemsData[playerPosition.getX() + 2][playerPosition.getY()] == '$' || 
                  mapData[playerPosition.getX() + 2][playerPosition.getY()] == '#')) {
              isValid = false;
          }
          else {
            isValid = true;
          }
      }
      else if (action.equals("l")) {
          if (mapData[playerPosition.getX()][playerPosition.getY() - 1] == '#') {
              isValid = false;
          }
          else if (itemsData[playerPosition.getX()][playerPosition.getY() - 1] == '$' &&
                  (itemsData[playerPosition.getX()][playerPosition.getY() - 2] == '$' || 
                  mapData[playerPosition.getX()][playerPosition.getY() - 2] == '#')) {
              isValid = false;
          }
          else {
            isValid = true;
          }
      }
      else {
          if (mapData[playerPosition.getX()][playerPosition.getY() + 1] == '#') {
              isValid = false;
          }
          else if (itemsData[playerPosition.getX()][playerPosition.getY() + 1] == '$' &&
                  (itemsData[playerPosition.getX()][playerPosition.getY() + 2] == '$' || 
                  mapData[playerPosition.getX()][playerPosition.getY() + 2] == '#')) {
              isValid = false;
          }
          else {
            isValid = true;
          }
      }

      return isValid;
  }

  public State getNextState(State state, String action, char[][] itemsData) {
      Position currentPlayerPosition = state.getPlayerPosition();
      Position nextPlayerPosition;
      Set<Position> nextCratePositions;

      if (action.equals("u")) {
          itemsData[currentPlayerPosition.getX() - 2][currentPlayerPosition.getY()] = itemsData[currentPlayerPosition.getX() - 1][currentPlayerPosition.getY()];
          itemsData[currentPlayerPosition.getX() - 1][currentPlayerPosition.getY()] = itemsData[currentPlayerPosition.getX()][currentPlayerPosition.getY()];
      }
      else if (action.equals("d")) {
          itemsData[currentPlayerPosition.getX() + 2][currentPlayerPosition.getY()] = itemsData[currentPlayerPosition.getX() + 1][currentPlayerPosition.getY()];
          itemsData[currentPlayerPosition.getX() + 1][currentPlayerPosition.getY()] = itemsData[currentPlayerPosition.getX()][currentPlayerPosition.getY()];
      }
      else if (action.equals("l")) {
          itemsData[currentPlayerPosition.getX()][currentPlayerPosition.getY() - 2] = itemsData[currentPlayerPosition.getX()][currentPlayerPosition.getY() - 1];
          itemsData[currentPlayerPosition.getX()][currentPlayerPosition.getY() - 1] = itemsData[currentPlayerPosition.getX()][currentPlayerPosition.getY()];
      }
      else {
          itemsData[currentPlayerPosition.getX()][currentPlayerPosition.getY() + 2] = itemsData[currentPlayerPosition.getX()][currentPlayerPosition.getY() + 1];
          itemsData[currentPlayerPosition.getX()][currentPlayerPosition.getY() + 1] = itemsData[currentPlayerPosition.getX()][currentPlayerPosition.getY()];
      }
      
      nextPlayerPosition = getPlayerPosition(itemsData);
      nextCratePositions = getCratePositions(itemsData);

      State nextState = new State(nextPlayerPosition, nextCratePositions);
      return nextState;
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

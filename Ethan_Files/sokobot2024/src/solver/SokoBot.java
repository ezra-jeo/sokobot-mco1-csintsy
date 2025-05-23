package solver;

import java.util.HashSet;
import java.util.Set;

public class SokoBot {

    public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
    /*
        * YOU NEED TO REWRITE THE IMPLEMENTATION OF THIS METHOD TO MAKE THE BOT SMARTER
    */
        Position initPlayer = getPlayerPosition(itemsData);
        Set<Position> crates = getCratePositions(itemsData);
        Set<Position> walls = getWallPositions(mapData);
        Set<Position> targets = getGoalCratePositions(mapData);
        State initState = new State(initPlayer, crates);
        Heuristics heuristics = new Heuristics(targets);
        Search search = new Search(initState, walls, targets, mapData, heuristics);
        
        return search.astar();
    }

    public boolean isValidAction(Position nextPosition, char[][] mapData) {

        return mapData[nextPosition.getX()][nextPosition.getY()] != '#';
    }

    public Position getPlayerPosition(char[][] itemsData) {
        int rowLength = itemsData.length;
        int columnLength = itemsData[0].length;
        Position playerPosition = new Position(0, 0);

        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < columnLength; j++) {
                if (itemsData[i][j] == '@') {
                    playerPosition = new Position(i, j);
                }
            }
        }

        return playerPosition;
    }

    public HashSet<Position> getCratePositions(char[][] itemsData) {
        int rowLength = itemsData.length;
        int columnLength = itemsData[0].length;
        Position cratePosition = null;
        HashSet<Position> crateList = new HashSet<>();

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

    public Set<Position> getWallPositions(char[][] mapData) {
        int rowLength = mapData.length;
        int columnLength = mapData[0].length;
        Position wallPosition = null;
        Set<Position> wallList = new HashSet<>();

        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < columnLength; j++) {
                if (mapData[i][j] == '#') {
                    wallPosition = new Position(i, j);
                    wallList.add(wallPosition);
                }
            }
        }
        
        return wallList;
    }

}

package solver;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;


public class Search {
    // BFS
    private State initState;
    private Set<Position> walls;
    private Set<Position> targets;

    public Search(State initState, Set<Position> walls, Set<Position> targets) {
        this.initState = initState;
        this.walls = walls;
        this.targets = targets;
    }

    // public String bfs() {


    // }

    public SearchNode getChild(SearchNode node, char action) {
        
        State curState = node.getState();
        Set<Position> newCrates = curState.getCratePositions();
        Position newPlayer = curState.getPlayerPosition();
        

        switch(action) {
            case 'u':
                newPlayer = getNewPosition(curState.getPlayerPosition(), action);

                if (newCrates.contains(newPlayer)) {
                    Position newCrate = getNewPosition(newPlayer, action);
                    newCrates.remove(newPlayer);
                    newCrates.add(newCrate);
                }
                break;
            case 'd':
                newPlayer = getNewPosition(curState.getPlayerPosition(), action);

                if (newCrates.contains(newPlayer)) {
                    Position newCrate = getNewPosition(newPlayer, action);
                    newCrates.remove(newPlayer);
                    newCrates.add(newCrate);
                }

                break;
            case 'l':
                newPlayer = getNewPosition(curState.getPlayerPosition(), action);

                if (newCrates.contains(newPlayer)) {
                    Position newCrate = getNewPosition(newPlayer, action);
                    newCrates.remove(newPlayer);
                    newCrates.add(newCrate);
                }

                break;
            case 'r':
                newPlayer = getNewPosition(curState.getPlayerPosition(), action);

                if (newCrates.contains(newPlayer)) {
                    Position newCrate = getNewPosition(newPlayer, action);
                    newCrates.remove(newPlayer);
                    newCrates.add(newCrate);
                }

                break;
        }
        
        State newState = new State(newPlayer, newCrates);
        SearchNode newNode = new SearchNode(node, newState, 0, action);

        return newNode;
    }

    public Position getNewPosition(Position entity, char move) {
        Position newPos;
        int x = entity.getX();
        int y = entity.getY();

        switch(move) {
            case 'u':
                newPos = new Position(x+1, y);
            case 'd':
                newPos = new Position(x-1, y);
            case 'l':
                newPos = new Position(x, y-1);
            case 'r':
                newPos = new Position(x, y+1);
            default:
                newPos = new Position(x, y);
        }

        return newPos;
    }



    public ArrayList<String> getActionList(State state) {

        ArrayList<String> actions = new ArrayList<String>();

        Set<Position> crates = state.getCratePositions();
        Position newPlayer;
        Position newBox;
        
        // u
        newPlayer = getNewPosition(state.getPlayerPosition(), 'u');
        newBox = getNewPosition(newPlayer, 'u');
        
        if (!this.walls.contains(newPlayer)) {
            // if there's no box on top of the player or there is a box but there's no box or wall stopping it.

            if (!crates.contains(newPlayer) || (crates.contains(newPlayer) && (!crates.contains(newBox) || !walls.contains(newBox)))) {
                actions.add("u");
            }
        } 

        // d
        newPlayer = getNewPosition(state.getPlayerPosition(), 'd');
        newBox = getNewPosition(newPlayer, 'd');
        
        if (!this.walls.contains(newPlayer)) {
            // if there's no box below the player or there is a box but there's no box or wall stopping it.

            if (!crates.contains(newPlayer) || (crates.contains(newPlayer) && (!crates.contains(newBox) || !walls.contains(newBox)))) {
                actions.add("d");
            }
        } 

        // l
        newPlayer = getNewPosition(state.getPlayerPosition(), 'l');
        newBox = getNewPosition(newPlayer, 'l');
        
        if (!this.walls.contains(newPlayer)) {
            // if there's no box to the left of the player or there is a box but there's no box or wall stopping it.

            if (!crates.contains(newPlayer) || (crates.contains(newPlayer) && (!crates.contains(newBox) || !walls.contains(newBox)))) {
                actions.add("l");
            }
        } 

        // r
        newPlayer = getNewPosition(state.getPlayerPosition(), 'r');
        newBox = getNewPosition(newPlayer, 'r');
        
        if (!this.walls.contains(newPlayer)) {
            // if there's no box to the right of the player or there is a box but there's no box or wall stopping it.

            if (!crates.contains(newPlayer) || (crates.contains(newPlayer) && (!crates.contains(newBox) || !walls.contains(newBox)))) {
                actions.add("r");
            }
        } 

        return actions;
    }


}
 
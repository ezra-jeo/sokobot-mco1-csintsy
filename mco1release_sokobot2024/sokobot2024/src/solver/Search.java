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
    private char[][] mapData;

    public Search(State initState, Set<Position> walls, Set<Position> targets, char[][] mapData) {
        this.initState = initState;
        this.walls = walls;
        this.targets = targets;
        this.mapData = mapData;
    }

    public String bfs() {
        Set<State> explored = new HashSet<State>();
        Queue<SearchNode> frontier = new LinkedList<SearchNode>();
        SearchNode curNode = new SearchNode(null, this.initState, 0, '\0');
        SearchNode childNode;
        ArrayList<String> actions;
        String path = "";
        boolean goal = false;
        frontier.offer(curNode);
        while (!frontier.isEmpty() && !goal) {
            curNode = frontier.poll();
            explored.add(curNode.getState());

            if (curNode.getState().isGoal(this.targets)) {
                path = buildSolution(curNode);
                System.out.println("goal");
                goal = true;
            }

            actions = getActionList(curNode.getState());

            for (String action : actions) {
                childNode = getChild(curNode, action.charAt(0));

                if (childNode != null && childNode.getState() != null) {
                    System.out.println("Is " + childNode.getState().hashCode() + " not seen? " + (!explored.contains(childNode.getState()) && !frontier.contains(childNode)));
                    System.out.println("Is it not a deadlock state " + !childNode.getState().isDeadlock(mapData));
                    if (!explored.contains(childNode.getState()) && !frontier.contains(childNode) && !childNode.getState().isDeadlock(mapData)) {
                        System.out.println("Add node " + childNode.getState().getPlayerPosition().getX() + " " + childNode.getState().getPlayerPosition().getY());

                        frontier.offer(childNode);
                    }
                }
            }
        }
        path = buildSolution(curNode);
        return path;
    }

    public String buildSolution(SearchNode node) {
        String actionSequence = "";
        StringBuilder actionSequenceReverse = new StringBuilder();
  
        while (node != null) {
            actionSequence = actionSequence + node.getAction();
            node = node.getPrevNode();
        }
  
        actionSequenceReverse.append(actionSequence);
        actionSequenceReverse.reverse();
        actionSequence = actionSequenceReverse.toString();
        return actionSequence;
    } 

    public SearchNode getChild(SearchNode node, char action) {
        
        State curState = node.getState();
        HashSet<Position> newCrates = new HashSet<Position>(curState.getCratePositions());
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
                newPos = new Position(x-1, y);
                break;
            case 'd':
                newPos = new Position(x+1, y);
                break;
            case 'l':
                newPos = new Position(x, y-1);
                break;
            case 'r':
                newPos = new Position(x, y+1);
                break;
            default:
                newPos = new Position(x, y);
                break;
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

            if (!crates.contains(newPlayer) || (crates.contains(newPlayer) && (!crates.contains(newBox) && !walls.contains(newBox)))) {
                actions.add("u");
            }
        } 

        // d
        newPlayer = getNewPosition(state.getPlayerPosition(), 'd');
        newBox = getNewPosition(newPlayer, 'd');
        
        if (!this.walls.contains(newPlayer)) {
            // if there's no box below the player or there is a box but there's no box or wall stopping it.

            if (!crates.contains(newPlayer) || (crates.contains(newPlayer) && (!crates.contains(newBox) && !walls.contains(newBox)))) {
                actions.add("d");
            }
        } 

        // l
        newPlayer = getNewPosition(state.getPlayerPosition(), 'l');
        newBox = getNewPosition(newPlayer, 'l');
        
        if (!this.walls.contains(newPlayer)) {
            // if there's no box to the left of the player or there is a box but there's no box or wall stopping it.
 
            if (!crates.contains(newPlayer) || (crates.contains(newPlayer) && (!crates.contains(newBox) && !walls.contains(newBox)))) {
                actions.add("l");
            }
        } 

        // r
        newPlayer = getNewPosition(state.getPlayerPosition(), 'r');
        newBox = getNewPosition(newPlayer, 'r');
        
        if (!this.walls.contains(newPlayer)) {
            // if there's no box to the right of the player or there is a box but there's no box or wall stopping it.

            if (!crates.contains(newPlayer) || (crates.contains(newPlayer) && (!crates.contains(newBox) && !walls.contains(newBox)))) {
                actions.add("r");
            }
        } 

        return actions;
    }


}
 
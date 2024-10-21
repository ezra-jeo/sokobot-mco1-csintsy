package solver;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;


public class Search {
    // BFS
    private State initState;
    private Set<Position> walls;
    private Set<Position> targets;
    private char[][] mapData;
    private static Heuristics heuristics; 

    public Search(State initState, Set<Position> walls, Set<Position> targets, char[][] mapData, Heuristics heuristics) {
        this.initState = initState;
        this.walls = walls;
        this.targets = targets;
        this.mapData = mapData;
        Search.heuristics = heuristics;
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
            else {
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
        }

        return path;
    }

    public String astar() {
        Set<State> explored = new HashSet<State>();
        Queue<SearchNode> frontier = new PriorityQueue<SearchNode>(11, astarComparator);
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
            else if (!curNode.getState().isDeadlock(mapData)){
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
                        else {
                            // Check if cost is lesser than the one in the frontier if it exists

                            for (SearchNode node : frontier) {
                                if (childNode == node && childNode.getCost() < node.getCost())
                                    node = childNode;
                            }
                        }

                    }
                }
            }
        }

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
        Set<Position> newCrates = new HashSet<Position>(curState.getCratePositions());
        Position newPlayer = curState.getPlayerPosition();
        int cost = node.getCost()+1; 

        switch(action) {
            case 'u':
                newPlayer = getNewPosition(curState.getPlayerPosition(), action);

                if (newCrates.contains(newPlayer)) {
                    Position newCrate = getNewPosition(newPlayer, action);
                    newCrates.remove(newPlayer);
                    newCrates.add(newCrate);
                    cost++;
                }
                break;
            case 'd':
                newPlayer = getNewPosition(curState.getPlayerPosition(), action);

                if (newCrates.contains(newPlayer)) {
                    Position newCrate = getNewPosition(newPlayer, action);
                    newCrates.remove(newPlayer);
                    newCrates.add(newCrate);
                    cost++;
                }
                break;
            case 'l':
                newPlayer = getNewPosition(curState.getPlayerPosition(), action);

                if (newCrates.contains(newPlayer)) {
                    Position newCrate = getNewPosition(newPlayer, action);
                    newCrates.remove(newPlayer);
                    newCrates.add(newCrate);
                    cost++;
                }
                break;
            case 'r':
                newPlayer = getNewPosition(curState.getPlayerPosition(), action);

                if (newCrates.contains(newPlayer)) {
                    Position newCrate = getNewPosition(newPlayer, action);
                    newCrates.remove(newPlayer);
                    newCrates.add(newCrate);
                    cost++;
                }
                break;
        }
        
        State newState = new State(newPlayer, newCrates);
        SearchNode newNode = new SearchNode(node, newState, cost, action);

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
        String[] possibleMoves = {"u", "d", "l", "r"};

        for (String move : possibleMoves) {
            
            newPlayer = getNewPosition(state.getPlayerPosition(), move.charAt(0));
            newBox = getNewPosition(newPlayer, move.charAt(0));

            if (!this.walls.contains(newPlayer)) {
                if (!crates.contains(newPlayer) || (crates.contains(newPlayer) && (!crates.contains(newBox) && !this.walls.contains(newBox)))) {
                    actions.add(move);
                }
            }
        }

        return actions;
    }

    // Comparators

    public static Comparator<SearchNode> astarComparator = new Comparator<SearchNode>() {
        @Override
        public int compare(SearchNode node1, SearchNode node2) {
            return (int) ((node1.getCost() + heuristics.getHeuristics(node1.getState())) - (node2.getCost() + heuristics.getHeuristics(node2.getState()))); 
        }
    };

}
 
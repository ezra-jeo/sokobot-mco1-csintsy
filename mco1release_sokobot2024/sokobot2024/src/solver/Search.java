package solver;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;


public class Search {
    // BFS
    private State initState;
    private Set<Position> walls;
    private Set<Position> targets;
    private Set<Position> deadlocks;
    private char[][] mapData;
    private static Heuristics heuristics; 

    public Search(State initState, Set<Position> walls, Set<Position> targets, char[][] mapData, Heuristics heuristics) {
        this.initState = initState;
        this.walls = walls;
        this.targets = targets;
        this.mapData = mapData;
        this.deadlocks = getDeadlocks(this.mapData);
        Search.heuristics = heuristics;
    }

    public String astar() {
        Set<State> explored = new HashSet<State>();
        Queue<SearchNode> frontier = new PriorityQueue<SearchNode>(11, astarComparator);
        Set<SearchNode> children;
        SearchNode curNode = new SearchNode(null, this.initState, 0, '\0');
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
                children = getChild(curNode);
                for (SearchNode childNode : children) {
                    if (childNode != null && childNode.getState() != null) {
                        if (!explored.contains(childNode.getState()) && !frontier.contains(childNode)) {
                            frontier.offer(childNode);
                        }
                        else {
                            // Check if cost of node is lesser than the one in the frontier (if it exists)
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

    public Set<SearchNode> getChild(SearchNode node) {

        Set<SearchNode> children = new HashSet<SearchNode>();
        State state = node.getState();
        Set<Position> crates = state.getCratePositions();
        String[] possibleMoves = {"u", "d", "l", "r"};

        for (String move : possibleMoves) {
            
            Position newPlayer = getNewPosition(state.getPlayerPosition(), move.charAt(0));
            Position newCrate = getNewPosition(newPlayer, move.charAt(0));
            Set<Position> newCrates = new HashSet<Position>(state.getCratePositions());
            int cost = node.getCost()+1; 

            if (!this.walls.contains(newPlayer)) {
                if (!crates.contains(newPlayer) || (crates.contains(newPlayer) && (!crates.contains(newCrate) && !this.walls.contains(newCrate)) && !this.deadlocks.contains(newCrate))) {
                    if (newCrates.contains(newPlayer)) {
                        newCrates.remove(newPlayer);
                        newCrates.add(newCrate);
                        cost -= 151;
                    }
                    State newState = new State(newPlayer, newCrates);
                    SearchNode newNode = new SearchNode(node, newState, cost, move.charAt(0));
                    children.add(newNode);
                }
            }
        }
        return children;
    }

    public boolean isOpposite(char oldDirection, char newDirection) {
        boolean isOpposite = false;
        switch(oldDirection) {
            case 'u':
                if (newDirection == 'd')
                    isOpposite = true;
                break;
            case 'd':
                if (newDirection == 'u')
                    isOpposite = true;
                break;
            case 'l':
                if (newDirection == 'r')
                    isOpposite = true;
                break;
            case 'r':
                if (newDirection == 'l')
                    isOpposite = true;
                break;
        }

        return isOpposite;
    }

    public Set<Position> getDeadlocks(char[][] mapData) {
        Set<Position> deadlocks = new HashSet<Position>();
        int rowLength = mapData.length;
        int colLength = mapData[0].length;

        for (int i = 1; i < rowLength-1; i++) {
            for (int j = 1; j < colLength-1; j++) {
                if ((mapData[i - 1][j] == '#' || mapData[i + 1][j] == '#') &&
                    (mapData[i][j + 1] == '#' || mapData[i][j - 1] == '#') &&
                    (mapData[i][j] != '.')) {
                    Position deadlock = new Position(i, j);
                    deadlocks.add(deadlock);
                }
            }
        }
    
        return deadlocks;
    }

    // Comparator
    public static Comparator<SearchNode> astarComparator = new Comparator<SearchNode>() {
        @Override
        public int compare(SearchNode node1, SearchNode node2) {
            int n1Cost = node1.getCost();
            int n2Cost = node2.getCost();

            return (int) ((n1Cost + heuristics.getHeuristics(node1.getState())) - (n2Cost + heuristics.getHeuristics(node2.getState()))); 
        }
    };

    public static Comparator<SearchNode> gbfsComparator = new Comparator<SearchNode>() {
        @Override
        public int compare(SearchNode node1, SearchNode node2) {
            return (int) ((heuristics.getHeuristics(node1.getState())) - (heuristics.getHeuristics(node2.getState()))); 
        }
    };
}
 
package solver;

public class SearchNode {
    private SearchNode prevNode;
    private State state;
    private int cost;
    private String move;

    public SearchNode(SearchNode prevNode, State state, int cost, String move) {
        this.prevNode = prevNode;
        this.state = state;
        this.cost = cost; // incurred cost
        this.move = move;
    }

    public SearchNode getPrevNode() {
        return this.prevNode;
    }

    public State getState() {
        return this.state;
    }
    
    public int getCost() {
        return this.cost;
    }
    
    public String getMove() {
        return this.move;
    }

    @Override
    public boolean equals(Object obj) {
        return this.state == ((SearchNode) obj).state;
    }
    

}

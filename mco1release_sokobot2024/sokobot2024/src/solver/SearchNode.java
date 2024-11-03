package solver;

public class SearchNode {
    private SearchNode prevNode;
    private State state;
    private int cost;
    private char action;
    private char lastPush;

    public SearchNode(SearchNode prevNode, State state, int cost, char action, char lastPush) {
        this.prevNode = prevNode;
        this.state = state;
        this.cost = cost; // incurred cost
        this.action = action;
        this.lastPush = lastPush;
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
    
    public char getAction() {
        return this.action;
    }

    public char getLastPush() {
        return this.lastPush;
    }

    public void setLastPush(char direction) {
        this.lastPush = direction;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object obj) {
        return this.state == ((SearchNode) obj).state;
    }
    

}

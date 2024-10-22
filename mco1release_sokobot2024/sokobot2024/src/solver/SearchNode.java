package solver;

public class SearchNode {
    private SearchNode prevNode;
    private State state;
    private int cost;
    private int pushCost;
    private char action;

    public SearchNode(SearchNode prevNode, State state, int cost, char action) {
        this.prevNode = prevNode;
        this.state = state;
        this.cost = cost; // incurred cost
        this.action = action;
        this.pushCost = 0;
    }

    public SearchNode(SearchNode prevNode, State state, int cost, char action, int pushCost) {
        this.prevNode = prevNode;
        this.state = state;
        this.cost = cost; // incurred cost
        this.action = action;
        this.pushCost = pushCost;
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

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getPushCost() {
        return this.pushCost;
    }

    public void setPushCost(int cost) {
        this.pushCost = cost;
    }


    @Override
    public boolean equals(Object obj) {
        return this.state == ((SearchNode) obj).state;
    }
    

}

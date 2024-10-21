package solver;

// The Node class represents the node in the search tree.
public class Node {
    private Node previousNode;
    private String action;
    private State state;

    public Node(Node previousNode, String action, State currenState) {
        this.previousNode = previousNode;
        this.action = action;
        this.state = currenState;
    }

    public Node getPreviousNode() {
        return this.previousNode;
    }

    public String getAction() {
        return this.action;
    }

    public State getState() {
        return this.state;
    }
    
}

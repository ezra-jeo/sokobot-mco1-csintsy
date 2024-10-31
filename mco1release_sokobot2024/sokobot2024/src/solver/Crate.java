package solver;

public class Crate {
    private Position position;
    private Position prevPosition;

    public Crate(Position position, Position prevPosition) {
        this.position = position;
        this.prevPosition = prevPosition;
    }

    
    public Crate(Position position) {
        this.position = position;
        this.prevPosition = null;
    }

    public Position getPosition() {
        return this.position;
    }

    public Position getPrevPosition() {
        return this.prevPosition;
    }
}

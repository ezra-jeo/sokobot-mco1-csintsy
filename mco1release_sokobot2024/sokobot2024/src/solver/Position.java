package solver;

import java.util.Objects;

// The Position class represents the coordinates of entities with respect to the level.
public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        Position position = (Position) obj;

        if (this.x == position.getX() && this.y == position.getY())
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
    
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}

package solver;

import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// The State class represents the position of the player and crates with respect to the level.
public class State {
    private Position playerPosition;
    private Set<Position> cratePositions;

    public State(Position playerPosition, Set<Position> cratePositions) {
        this.playerPosition = playerPosition;
        this.cratePositions = cratePositions;
    }

    public boolean isGoal(Set<Position> goalCratePositions) {
        return this.cratePositions.equals(goalCratePositions);
    }

    public boolean isDeadlock(char[][] mapData) {
        boolean isDeadlock = false;
        List<Position> cratePositionsList = new ArrayList<>(this.cratePositions);
        int length = cratePositions.size();
        int i = 0;

        while (!isDeadlock && i < length) {
            Position cratePosition = cratePositionsList.get(i);

            if ((mapData[cratePosition.getX() - 1][cratePosition.getY()] == '#' ||
                 mapData[cratePosition.getX() + 1][cratePosition.getY()] == '#') &&
                (mapData[cratePosition.getX()][cratePosition.getY() + 1] == '#' ||
                 mapData[cratePosition.getX()][cratePosition.getY() - 1] == '#') &&
                 (mapData[cratePosition.getX()][cratePosition.getY()] != '.'))
                isDeadlock = true;
            i++;
        }

        return isDeadlock;
    }

    @Override
    public boolean equals(Object object) {
        State s = (State)object;
	    if (object == this || this.hashCode()== s.hashCode()) 
            return true;
	    if (object == null || this.getClass() != object.getClass()) 
            return false;

	    return Objects.equals(this.playerPosition, s.getPlayerPosition()) && Objects.equals(this.cratePositions, s.getCratePositions());
    }

    @Override
    public int hashCode() {
		int result = 31; 
        for (Position crate : cratePositions) {
            result = 53 * result + crate.hashCode();
        }
        result = 53 * result + playerPosition.hashCode();
        return result;
	}

    public Position getPlayerPosition() {
        return this.playerPosition;
    }

    public Set<Position> getCratePositions() {
        return this.cratePositions;
    }
}


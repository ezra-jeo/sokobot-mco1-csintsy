package solver;

import java.util.Set;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

// The State class represents the position of the player and crates with respect to the level.
public class State {
    private Position playerPosition;
    private Set<Position> cratePositions;

    public State(Position playerPosition, Set<Position> createPositions) {
        this.playerPosition = playerPosition;
        this.cratePositions = createPositions;
    }

    public boolean isGoal(Set<Position> goalCratePositions) {
        boolean isGoal;

        if (this.cratePositions.equals(goalCratePositions))
            isGoal = true;
        else
            isGoal = false;

        return isGoal;
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
    public boolean equals(Object obj) {
        State state = (State) obj;

        if (this.playerPosition.equals(state.getPlayerPosition()) && this.cratePositions.equals(state.getCratePositions()))
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerPosition, cratePositions);
    }

    public Position getPlayerPosition() {
        return this.playerPosition;
    }

    public Set<Position> getCratePositions() {
        return this.cratePositions;
    }
}


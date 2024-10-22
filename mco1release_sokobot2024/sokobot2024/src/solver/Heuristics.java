package solver;

import java.util.Set;

public class Heuristics {

    private Set<Position> targets;

    public Heuristics(Set<Position> targets) {
        this.targets = targets;
    }
    
    public int manhattan(Position a, Position b) {
        int x1 = a.getX();
        int y1 = a.getY();
        int x2 = b.getX();
        int y2 = b.getY();

        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public double getDist(Position entity, Set<Position> objSet) {
        double minDist = -1; // as initial value

        // gets the closest object to entity given an object set;
        for (Position objPos : objSet) {
            double dist = euclidean(entity, objPos);

            if (minDist < 0 || dist < minDist) 
                minDist = dist;
        }

        return minDist;
    }

    public double getHeuristics(State state) {
        Set<Position> crates = state.getCratePositions();
        double heuristic = 0;

        // Player to crates
        Position player = state.getPlayerPosition();
        heuristic += getDist(player, crates);

        // Crates to targets
        for (Position crate : crates) {
            heuristic += getDist(crate, this.targets);
        }

        return heuristic;
    }

    public double euclidean(Position a, Position b) {
        int x1 = a.getX();
        int y1 = a.getX();
        int x2 = b.getX();
        int y2 = b.getY();

        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }


}

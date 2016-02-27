package model.units;

import java.awt.Point;

/**
 * Possible movement directions and their opposites.
 */
public enum Direction {
    UP(new Point(0, -1)),
    DOWN(new Point(0, 1)),
    LEFT(new Point(-1, 0)),
    RIGHT(new Point(1, 0));

    static {
        UP.opposite = DOWN;
        DOWN.opposite = UP;
        LEFT.opposite = RIGHT;
        RIGHT.opposite = LEFT;
    }

    private Point p;
    private Direction opposite;
    
    private Direction(final Point p) {
        this.p = p;
    }

    public Point getPoint(final int index) {
        return Direction.values()[index].p;
    }
    
    public Direction getOppositeDirection() {
        return this.opposite;
    }
}

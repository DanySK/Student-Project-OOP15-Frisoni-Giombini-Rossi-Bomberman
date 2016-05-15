package model.units;

import java.awt.Point;

/**
 * Possible movement directions and their opposites.
 * Each direction is associated to a point that is
 * used to calculate the new position.
 */
public enum Direction {
    
    /**
     * Upward movement.
     */
    UP(new Point(0, -1)),
    
    /**
     * Downward movement.
     */
    DOWN(new Point(0, 1)),
    
    /**
     * Shifting left.
     */
    LEFT(new Point(-1, 0)),
    
    /**
     * Shifting right.
     */
    RIGHT(new Point(1, 0));

    static {
        UP.opposite = DOWN;
        DOWN.opposite = UP;
        LEFT.opposite = RIGHT;
        RIGHT.opposite = LEFT;
    }

    private Point translation;
    private Direction opposite;
    
    Direction(final Point pos) {
        this.translation = pos;
    }

    /**
     * This method returns the point associated to the
     * Direction.
     * 
     * @return
     *          the relative Point
     */
    public Point getTranslation() {
        return this.translation;
    }
    
    /**
     * This method returns the opposite direction.
     * 
     * @return
     *          the opposite direction
     */
    public Direction getOppositeDirection() {
        return this.opposite;
    }
}

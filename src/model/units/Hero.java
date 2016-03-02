package model.units;

/**
 * This class models a Hero.
 *
 */
public interface Hero extends Entity {

    /**
     * This method set the hero in movement or not.
     * @param b
     *          true if he's in movements, false otherwise
     */
    void setMoving(boolean b);

    /**
     * Check if the hero is in movement.
     * @return
     *          true if he's in movement, false otherwise
     */
    boolean isMoving();
}
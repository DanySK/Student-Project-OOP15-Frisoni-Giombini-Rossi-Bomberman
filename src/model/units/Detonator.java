package model.units;

import java.awt.Point;
import java.util.Set;

/**
 * This class models a Detonator.
 */
public interface Detonator {
    
    /**
     * It adds a bomb to the List.
     * 
     * @param pos
     *          the bomb's position
     */
    void addBomb(final Point pos);

    /**
     * It increases the range of a bomb.
     */
    void increaseRange();

    /**
     * It increases the number of bombs that can be planted.
     */
    void increaseBombs();

    /**
     * This method returns the bomb to be planted.
     * 
     * @param pos
     *          the new bomb's position
     */
    void plantBomb(final Point pos);

    /**
     * Reactivates a bomb that has already exploded.
     */
    void reactivateBomb();

    /**
     * It returns the bomb to reactivate.
     * 
     * @return a bomb that has to be reactivated
     */
    Bomb getBombToReactivate();

    /**
     * Gets bomb's delay.
     * 
     * @return bomb's delay
     */
    long getBombDelay();

    /**
     * Checks if there are bombs to plant.
     *  
     * @return true if there's at least a bomb to plant.
     */
    boolean hasBombs();

    /**
     * Gets the list of planted bombs.
     * 
     * @return the list of planted bombs
     */
    Set<Bomb> getPlantedBombs();

    /**
     * Gets the actual range of a bomb.
     * 
     * @return the actual range of a bomb
     */
    int getActualRange();
    
    /**
     * Gets the actual number of bombs.
     * 
     * @return the actual number of bombs
     */
    int getActualBombs();

}

package model.units;

import java.awt.Point;
import java.util.Set;
import java.util.function.Predicate;

/**
 * This class models a Detonator.
 */
public interface Detonator {

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
     * It returns a bomb that satisfy the specified 
     * predicate.
     * 
     * @param pred
     *          the predicate to satisfy 
     * @return a bomb
     */
    Bomb getBomb(final Predicate<Bomb> pred);

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

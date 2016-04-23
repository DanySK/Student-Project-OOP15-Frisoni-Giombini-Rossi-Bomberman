package model.units;

/**
 * This interface shapes a Bomb.
 */
public interface Bomb extends LevelElement{
    
    /**
     * Gets boms's range.
     * 
     * @return bomb's range
     */
    int getRange();
    
    /**
     * Increases the range of the bomb.
     * 
     * @param range
     *          the new range
     */
    void setRange(final int range);
    
    /**
     * Set the bomb to be planted or not.
     * 
     * @param bool
     *          the boolean describing if it is planted or not
     */
    void setPlanted(final boolean bool);

    /**
     * Verifies if the bomb is planted.
     * 
     * @return true if it is planted, otherwise false
     */
    boolean isPositioned();
    
}

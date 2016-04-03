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
     */
    void setPlanted();

    /**
     * Verifies if the bomb is planted.
     * 
     * @return true if it is planted, otherwise false
     */
    boolean isPositioned();
    
    /**
     * Bomb's toString.
     * 
     * @return the string describing the bomb
     */
    String toString();
    
}

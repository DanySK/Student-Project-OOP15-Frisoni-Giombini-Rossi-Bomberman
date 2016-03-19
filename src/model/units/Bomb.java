package model.units;


public interface Bomb extends LevelElement{
    
    /**
     * Increases the range of the bomb.
     */
    void increaseRange();
    
    /**
     * Set the bomb to be planted or not.
     * 
     * @param b
     *          true if it has to be planted, otherwise false
     */
    void setPlanted(final boolean b);
    
    /**
     * Gets boms's range.
     * 
     * @return bomb's range
     */
    int getRange();
    
    /**
     * Verifies if the bomb is planted.
     * 
     * @return true if it is planted, otherwise false
     */
    boolean isPlanted();
    
}

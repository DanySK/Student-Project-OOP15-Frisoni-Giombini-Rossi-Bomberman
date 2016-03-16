package model.units;

public interface Bomb extends LevelElement{
    
    /**
     * Increases the range of the bomb.
     */
    void increaseRange();
    
    int getRange();

}

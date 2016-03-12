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
    
    /**
     * Increase hero's attack level.
     */
    void increaseAttack();
            
    /**
     * Increase the number of bombs.
     */
    void increaseBomb();
    
    /**
     * Sets the flamepass.
     */
    void setFlamepass();
    
    /**
     * Set the hero to be confused.
     */
    void setConfusion();

    /**
     * This method return the attack level.
     * 
     * @return the hero's attack level
     */
    int getAttack();
    
    /**
     * This method return the number of lives.
     * 
     * @return the number of remaining lives 
     */
    int getRemainingLives();
    
    /**
     * Checks if the hero's got the flamepass.
     * 
     * @return true if he's got this powerup, false otherwise
     */
    boolean checkFlamepass();
    
    /**
     * This methods return how many bombs the hero's got.
     * 
     * @return the number of bombs
     */
    //int getBombs();
}
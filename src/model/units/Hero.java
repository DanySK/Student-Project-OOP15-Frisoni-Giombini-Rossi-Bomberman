package model.units;

import java.util.Set;

import model.Tile;

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
     * This method increase the range of a hero's bomb.
     */
    void increaseRange();

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
     * 
     */
    Bomb plantBomb(final int nTiles);
    
    
    long getBombDelay();
    
    boolean checkFlameCollision(final Set<Tile> afflictedTiles);
    
    boolean hasBomb();
    
    Detonator getDetonator();
    
    void setKey();
}
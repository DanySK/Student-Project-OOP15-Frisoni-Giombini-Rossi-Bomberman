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
     * 
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
     * Checks if the hero's got the flamepass.
     * 
     * @return true if he's got this powerup, false otherwise
     */
    boolean checkFlamepass();  
    
    /**
     * Plants a bomb.
     * 
     * @return the bomb to plant
     */
    Bomb plantBomb(final int nTiles);
    
    /**
     * Gets bomb's delay.
     * 
     * @return bomb's delay
     */
    long getBombDelay();
    
    /**
     * Checks collisions with flames.
     * 
     * @param afflictedTiles
     *          set of afflicted tiles
     * @return true if there's a collision, false otherwise
     */
    boolean checkFlameCollision(final Set<Tile> afflictedTiles);
    
    /**
     * Checks if he has bombs.
     * 
     * @return true if there's at least a bomb, false otherwise
     */
    boolean hasBomb();
    
    /**
     * Returns hero's deonator.
     * 
     * @return hero's detonator
     */
    Detonator getDetonator();
    
    /**
     * Set the key.
     */
    void setKey();
}
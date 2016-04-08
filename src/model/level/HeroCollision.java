package model.level;

import java.awt.Rectangle;
import java.util.Set;

import model.Tile;

/**
 * This class models all the hero's collision
 * with other game elements.
 */
public interface HeroCollision extends Collision{
    
    /**
     * Checks if there's a collision with a powerup.
     * 
     * @param powerUpSet
     *          the set of powerups
     * @return false
     */
    boolean powerUpCollision(final Set<Tile> powerUpSet);
    
    /**
     * Cheks the collision with the open door.
     * 
     *@param doorOpened
     *          the open door
     * @return true if there's a collision, false otherwise
     */
    boolean openDoorCollision(final Rectangle doorOpened);

}

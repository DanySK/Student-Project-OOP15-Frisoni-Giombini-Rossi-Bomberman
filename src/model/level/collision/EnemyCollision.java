package model.level.collision;

import model.units.Entity;

/**
 * This class models all the enemy's collision
 * with other game elements.
 */
public interface EnemyCollision extends Collision {
    
    /**
     * Checks if there's a collision between enemy and Hero.
     * @param heroEntity 
     *                  the Hero entity
     * @return false if they collide, true otherwise
     */
    boolean heroCollision(final Entity heroEntity);
    
}

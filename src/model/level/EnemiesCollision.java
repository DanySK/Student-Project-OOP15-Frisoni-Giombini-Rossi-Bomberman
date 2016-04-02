package model.level;

import model.units.Entity;

public interface EnemiesCollision extends Collision {
    
    /**
     * Checks if there's a collision between enemy and Hero.
     * 
     * @param heroEntity 
     *                  the Hero entity
     * @return false if they collide, true otherwise
     */
    boolean heroCollision(final Entity heroEntity);
    
}

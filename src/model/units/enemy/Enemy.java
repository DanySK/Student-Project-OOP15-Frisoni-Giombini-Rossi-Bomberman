package model.units.enemy;

import java.awt.Rectangle;
import java.util.Set;

import model.units.Direction;
import model.units.Entity;
import model.units.Hero;

public interface Enemy extends Entity{
    
    /**
     * Enemies' movement.
     * @param dir
     *          the direction that enemies want to go
     * @param blockSet
     *          the set of tiles
     * @param hero
     *          the Hero entity
     * @param bombSet
     *          the set of bomb planted
     */
    void move(final Direction dir, final Set<Rectangle> blockSet, final Hero hero, 
            final Set<Rectangle> bombSet);
    
    /**
     * This method chooses a random direction.
     * @return the random direction
     */
    Direction getRandomDirection();
    
    /**
     * This method updates the enemies movement.
     * @param blockSet
     *          the set of block          
     * @param hero
     *          the Hero entity
     * @param dir
     *          the direction where to go to the enemy 
     * @param bombSet
     *          the set of bomb planted
     */
    void updateMove(final Set<Rectangle> blockSet, final Hero hero, final Direction dir, 
            final Set<Rectangle> bombSet);
    
    /**
     * This method return the type of enemy.
     * @return enemy's type
     */
    EnemyType getEnemyType();
    
    /**
     * This method serves to increase the life and the attack of the enemy at the passage of 
     * a new level.
     */
    void potentiateEnemy();
    
    /**
     * toString of enemy.
     * @return the string describing enemy
     */
    String toString();
}

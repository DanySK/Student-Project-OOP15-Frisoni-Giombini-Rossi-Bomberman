package model.units.enemies;

import java.awt.Rectangle;
import java.util.Set;

import model.units.Direction;
import model.units.Hero;

public interface Enemies {
    
    /**
     * Enemies' movement.
     * @param dir
     *          the direction that enemies want to go
     * @param blockSet
     *          the set of tiles
     * @param hero
     *          the Hero entity
     */
    void move(final Direction dir, final Set<Rectangle> blockSet, final Hero hero);
    
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
     */
    void updateMove(final Set<Rectangle> blockSet, final Hero hero, final Direction dir);
    
}

package model.level.collision;

import java.awt.Rectangle;
import java.util.Set;
import java.util.function.Predicate;

import model.Tile;
import model.units.Direction;

/**
 * This class models a generic collision whith
 * different game elements.
 */

public interface Collision {
    
    /**
     * Checks if there's a collision with blocks.
     * 
     * @param blockSet
     *          the set of blocks
     * @return true if there's a collision, false otherwise
     */
    boolean blockCollision(final Set<Rectangle> blockSet);
    
    /**
    * Cheks if there's a collision with a bomb.
    * 
    * @param bombSet
    *          the set of planted bombs
    * @return true if there's a collision, false otherwise
    */
    boolean bombCollision(final Set<Rectangle> bombSet);
    
    /**
     * Checks if there's a collision with fire.
     * 
     * @param afflictedTiles
     *          the set of tiles on fire
     * @return true if there's a collision, false otherwise
     */
    boolean fireCollision(final Set<Tile> afflictedTiles);
    
    /**
     * Checks if there's a collision.
     * 
     * @param set
     *          the set of elements 
     * @param pred
     *          the predicate
     * @return true if the entity can move, false otherwise
     */
    <X> boolean elementCollision(final Set<X> set, final Predicate<X> pred);
    
    /**
     * Checks if there's a collision with an explosion.
     * 
     * @param rec
     *          the element hitbox
     * @return true if there's a collision, false otherwise
     */
    boolean explosionIntersection(final Rectangle rec);
    
    /**
     * Updates entity's hitbox.
     * 
     * @param dir
     *          the direction where he would move
     */
    void updateEntityRec(final Direction dir);

}

package model.units;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Set;

import model.level.Collision;

public interface Entity extends LevelElement{
    
    /**
     * This methods check if the entity collide
     * with other game elements.
     * 
     * @return true if there's a collision, false otherwise
     */
    boolean checkCollision(final Direction dir, final Set<Rectangle> blockSet, final Set<Rectangle> bombSet);
    
    /**
     * This method allow the entity to move.
     * 
     * @param dir
     *          the direction where to move
     */
    void move(final Direction dir, final Set<Rectangle> blockSet, final Set<Rectangle> bombSet);
    
    /**
     * Method that modify the current value of lives.
     * 
     * @param change
     *          life to be added
     */
    void modifyLife(final int change);
    
    /**
     * This method update the entity's direction.
     * 
     * @param dir
     *          the new direction
     */
    void updateDirection(Direction dir);
    
    /**
     * This method returns the position where the hero would be if there's no collision.
     * 
     * @param p
     *          the point whose coordinates are to be added
     * @return the position where the hero would move
     */
    Point getPossiblePos(Point p);
    
    /**
     * This method is used to know the actual direction of the entity.
     * 
     * @return the entity's direction
     */
    Direction getDirection();
    
    Collision getCollision();
    
    /**
     * Checks if the entity is dead.
     * 
     * @return
     */
    boolean isDead();
    
    /**
     * Check if the hero is in movement.
     * @return
     *          true if he's in movement, false otherwise
     */
    boolean isMoving();
}

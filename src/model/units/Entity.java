package model.units;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Set;

public interface Entity extends LevelElement{
    
    /**
     * This methods check if the entity collide
     * with other game elements.
     * 
     * @return true if there's a collision, false otherwise
     */
    boolean checkCollision(final Direction dir, final Set<Rectangle> blockSet);
    
    /**
     * This method allow the entity to move.
     * 
     * @param dir
     *          the direction where to move
     */
    void move(Direction dir, Set<Rectangle> set);
    
    /**
     * Method that modify the current value of lives.
     * 
     * @param change
     *          life to be added
     */
    void modifyLife(final int change);
    
    /**
     * This method update the entity's position.
     * 
     * @param p
     *          the point whose coordinates are to be added
     */
    void updatePosition(Point p);
    
    /**
     * This method update the entity's direction.
     * 
     * @param dir
     *          the new direction
     */
    void updateDirection(Direction dir);
    
    /**
     * This method update the parameters in the hitBox.
     */
    void updateHitbox();    
    
    /**
     * This method returns the position where the hero would be if there's no collision.
     * 
     * @param p
     *          the point whose coordinates are to be added
     * @return the position where the hero would move
     */
    Point getPossiblePos(Point p);
    
    /**
     * This method is used to know the actual speed of the entity.
     * 
     * @return the actual speed
     */
    int getSpeed();
    
    /**
     * This method is used to know the actual direction of the entity.
     * 
     * @return the entity's direction
     */
    Direction getDirection();
    
    /**
     * Checks if the entity is dead.
     * 
     * @return
     */
    boolean isDead();
}

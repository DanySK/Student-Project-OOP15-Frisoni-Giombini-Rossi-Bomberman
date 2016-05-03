package model.units;

import java.awt.Point;

/**
 * This class models a game entity.
 */
public interface Entity extends LevelElement {
    
    /**
     * This method allow the entity to move.
     * 
     * @param dir
     *          the direction where to move
     */
    void move(final Direction dir);
    
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
     * Increase hero's attack level.
     * 
     * @param attackToAdd
     *          the attack to add to the previous one
     */
    void increaseAttack(final int attackToAdd);
    
    /**
     * This method returns the position where the hero would be if there's no collision.
     * 
     * @param pos
     *          the point whose coordinates are to be added
     * @return the position where the hero would move
     */
    Point getPossiblePos(Point pos);
    
    /**
     * This method is used to know the actual direction of the entity.
     * 
     * @return the entity's direction
     */
    Direction getDirection();
    
    /**
     * This method return the attack level.
     * 
     * @return the hero's attack level
     */
    int getAttack();
    
    /**
     * Gets the entity's score.
     * 
     * @return entity's score
     */
    int getScore();
    
    /**
     * Gets remaining lives.
     * 
     * @return remaining lives
     */
    int getRemainingLives();
    
    /**
     * Checks if the entity is dead.
     * 
     * @return true if the entity is dead
     */
    boolean isDead();
    
    /**
     * Check if the hero is in movement.
     * 
     * @return true if he's in movement, false otherwise
     */
    boolean isMoving();
    
}

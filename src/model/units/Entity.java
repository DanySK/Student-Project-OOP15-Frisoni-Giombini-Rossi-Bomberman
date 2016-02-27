package model.units;

import java.awt.Point;
import java.awt.Rectangle;

public interface Entity {
    
    /**
     * This method allow the entity to move.
     * 
     * @param dir
     *          the direction where to move
     */
    void move(Direction dir);
    
    /**
     * This method returns the steps that the entity make.
     * @return the steps that the entity make
     */
    int numberOfSteps();
    
    /**
     * This method update the entity's position.
     * @param p
     *          the point whose coordinates are to be added
     */
    void updatePosition(Point p);
    
    /**
     * This method update the entity's direction.
     * @param dir
     *          the new direction
     */
    void updateDirection(Direction dir);
    
    /**
     * This method update the parameters in the hitBox.
     */
    void updateHitbox();
    
    /**
     * This method allows to set the entity's step.
     */
    void setStep();
    
    /**
     * This method is used to know the entity's position.
     * @return the current position of the entity
     */
    Point getPosition();
    
    /**
     * This method is used to know the geometrical figure of the entity.
     * @return the geometrical figure of the entity
     */
    Rectangle getHitbox();
    
    /**
     * This method is used to know the x coordinate of the entity's position.
     * @return the x coordinate
     */
    int getX();
    
    
    /**
     * This methods is used to know the y coordinate of the entity's position.
     * @return the y coordinate
     */
    int getY();
    
    
    /**
     * This method is used to know the actual speed of the entity.
     * @return the actual speed
     */
    int getSpeed();
    
    /**
     * This method is used to know the actual direction of the entity.
     * @return the entity's direction
     */
    Direction getDirection();
    
}

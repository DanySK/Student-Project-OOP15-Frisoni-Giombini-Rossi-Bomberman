package model.units;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * This interface models a game element.
 */
public interface LevelElement {
    
    /**
     * This method is used to know the element's position.
     * 
     * @return the current position of the entity
     */
    Point getPosition();
    
    /**
     * This method is used to know the geometric figure of the element.
     * 
     * @return the geometric figure of the entity
     */
    Rectangle getHitbox();
    
    /**
     * This method is used to know the x coordinate of the element's position.
     * 
     * @return the x coordinate
     */
    int getX();
    
    /**
     * This methods is used to know the y coordinate of the element's position.
     * 
     * @return the y coordinate
     */
    int getY();
}

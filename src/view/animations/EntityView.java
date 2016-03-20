package view.animations;

import java.awt.Image;

import model.units.Entity;

/**
 * This interface manages the visual representation of a {@link Entity}.
 *
 */
public interface EntityView {
    
    /**
     * Gets the sprite associated to the current state of the entity.
     * 
     * @return the entity's sprite image
     */
    Image getImage();
    
    /**
     * @return the x coordinate for the entity's representation.
     */
    int getX();
    
    /**
     * @return the y coordinate for the entity's representation.
     */
    int getY();
    
    /**
     * Updates the frame for the current animation of the entity.
     */
    void updateFrame();
}

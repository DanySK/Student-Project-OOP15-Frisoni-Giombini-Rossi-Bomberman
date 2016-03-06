package view.animations;

import java.awt.Image;
import java.awt.Point;

import model.units.Hero;

/**
 * This interface manages the visual representation of a {@link Hero}.
 *
 */
public interface HeroView {
    
    /**
     * Gets the sprite associated to the current state of the hero.
     * 
     * @return the hero's sprite image
     */
    Image getImage();
    
    /**
     * @return the x coordinate for the hero's representation.
     */
    int getX();
    
    /**
     * @return the y coordinate for the hero's representation.
     */
    int getY();
    
    /**
     * Updates the frame for the current animation of the hero.
     */
    void updateFrame();
    
    /**
     * @return the center point of the sprite associated to the hero.
     */
    Point getCenterPoint();
}

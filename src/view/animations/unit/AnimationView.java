package view.animations.unit;

import java.awt.Image;

/**
 * This interface manages the visual representation of a frame-based animation.
 *
 */
public interface AnimationView {
    
    /**
     * Gets the frame associated to the current state of the animation.
     * 
     * @return the current frame to display
     */
    Image getImage();
    
    /**
     * @return the x coordinate for the frame.
     */
    int getX();
    
    /**
     * @return the y coordinate for the frame.
     */
    int getY();
    
    /**
     * Updates the frame for the current animation.
     */
    void updateFrame();
}

package view.animations.unit;

import java.awt.Image;

/**
 * This interface manages the visual representation of a frame-based animation.
 * These methods are needed for each type of animation.
 *
 */
public interface AnimationView {
    
    /**
     * Gets the frame associated to the current state of the animation.
     * 
     * @return the frame to display, properly sized
     */
    Image getImage();
    
    /**
     * @return the x coordinate of the frame.
     */
    int getX();
    
    /**
     * @return the y coordinate of the frame.
     */
    int getY();
    
    /**
     * Updates the frame for the current animation.
     */
    void updateFrame();
}

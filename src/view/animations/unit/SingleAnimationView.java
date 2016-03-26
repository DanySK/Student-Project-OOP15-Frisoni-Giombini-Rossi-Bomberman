package view.animations.unit;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * This interface manages the representation of an element with a single animation.
 *
 */
public interface SingleAnimationView extends AnimationView {
    
    /**
     * @return the frames of the single animation.
     */
    public List<BufferedImage> animationFrames();
}

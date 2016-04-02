package view.animations.unit;

import java.awt.image.BufferedImage;
import java.util.List;

import model.units.LevelElement;

/**
 * An implementation of {@link SingleAnimationView}.
 * The animation starts immediately and lasts the indicated time.
 * It calculates the frame to display based on the total number of frames that make the animation
 * in the specified time, at the current fps.
 *
 */
public abstract class AbstractSingleAnimationView extends AbstractAnimationView implements SingleAnimationView {

    private static final int TIME_FACTOR = 1000;
    
    private final Animation animation;

    /**
     * Constructs a new representation with a single animation.
     * 
     * @param element
     *          the element to represent
     * @param fps
     *          the number of frame-per-second
     * @param duration
     *          the duration of the animation (in milliseconds).
     *          It is used to define the delay between each frame.
     */
    public AbstractSingleAnimationView(final LevelElement element, final int fps, final long duration) {
        super(element);
        this.animation = new Animation(animationFrames(), (int) ((fps / animationFrames().size() * duration) / TIME_FACTOR), false);
        this.animation.start();
    }
    
    @Override
    public abstract List<BufferedImage> animationFrames();
    
    @Override
    public Animation getAnimation() {
        return this.animation;
    }
}

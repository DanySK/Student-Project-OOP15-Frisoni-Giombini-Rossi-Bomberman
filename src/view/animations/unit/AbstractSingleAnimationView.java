package view.animations.unit;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * An implementation of {@link SingleAnimationView}.
 * It calculates the frame to display based on the total number of frames that make the animation
 * in the specified time, at the current fps.
 *
 */
public abstract class AbstractSingleAnimationView implements SingleAnimationView {

    private static final int TIME_FACTOR = 1000;
    
    private final Animation animation;
    private final int size;

    /**
     * Constructs a new representation with a single animation.
     * 
     * @param size
     *          the size of the sprite
     * @param fps
     *          the number of frame-per-second
     * @param duration
     *          the duration of the animation (in milliseconds).
     *          It is used to define the delay between each frame.
     */
    public AbstractSingleAnimationView(final int size, final int fps, final long duration) {
        this.size = size;
        this.animation = new Animation(animationFrames(), (int) ((fps / animationFrames().size() * duration) / TIME_FACTOR), false);
        this.animation.start();
    }
    
    /**
     * @return the size of the represented element.
     */
    protected int getSize() {
        return this.size;
    }
    
    @Override
    public abstract List<BufferedImage> animationFrames();
    
    @Override
    public Image getImage() {
        return this.animation.getCurrentFrame()
                .getScaledInstance(this.size, (this.size * Sprite.getSpriteHeight()) / Sprite.getSpriteWidth(), Image.SCALE_DEFAULT);
    }
    
    @Override
    public void updateFrame() {
        this.animation.update();
    }
}

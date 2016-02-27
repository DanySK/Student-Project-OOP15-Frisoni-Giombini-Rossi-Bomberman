package view.animations;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class handles an animation based on a {@link BufferedImage} sequence.
 * 
 */
public class Animation {

    private int frameCount;
    private final int frameDelay;
    private int currentFrame;
    private final int totalFrames;

    private boolean stopped;

    private final List<BufferedImage> frames = new ArrayList<>();

    /**
     * Creates an animation.
     * 
     * @param frames
     *          the frames to loop in the animation
     * @param frameDelay
     *          the delay between each frame
     */
    public Animation(final List<BufferedImage> frames, final int frameDelay) {
        Objects.requireNonNull(frames);
        if (frameDelay <= 0) {
            throw new IllegalArgumentException("Invalid duration: " + frameDelay);
        }
        this.frames.addAll(frames);
        this.frameCount = 0;
        this.frameDelay = frameDelay;
        this.currentFrame = 0;
        this.totalFrames = this.frames.size();
        this.stopped = true;
    }

    /**
     * Starts the animation.
     */
    public void start() {
        if (this.stopped) {
            this.stopped = false;
        }
    }

    /**
     * Stops the animation.
     */
    public void stop() {
        if (!this.stopped) {
            this.stopped = true;
        }
    }

    /**
     * Restarts the animation.
     */
    public void restart() {
        this.stopped = false;
        this.currentFrame = 0;
    }

    /**
     * Resets the animation.
     */
    public void reset() {
        this.stopped = true;
        this.frameCount = 0;
        this.currentFrame = 0;
    }

    /**
     * @return the current frame of the animation
     */
    public BufferedImage getCurrentFrame() {
        return this.frames.get(this.currentFrame);
    }

    /**
     * Updates the current frame of the animation.
     */
    public void update() {
        if (!this.stopped) {
            this.frameCount++;
            if (this.frameCount > this.frameDelay) {
                this.frameCount = 0;
                this.currentFrame++;
                if (this.currentFrame > this.totalFrames - 1) {
                    this.currentFrame = 0;
                }
            }
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((frames == null) ? 0 : frames.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Animation && this.frames.equals(((Animation) obj).frames);
    }
}
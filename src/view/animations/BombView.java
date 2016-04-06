package view.animations;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

import model.units.Bomb;
import view.animations.unit.AbstractSingleAnimationView;
import view.animations.unit.Sprite;

/**
 * An implementation of {@link BombView}.
 *
 */
public class BombView extends AbstractSingleAnimationView {

    private static final List<BufferedImage> BOMB_TRIGGER = Sprite.getSprites(new Point(10, 0), new Point(11, 0), new Point(12, 0));

    /**
     * Constructs a new view for the bomb.
     * 
     * @param bomb
     *          the bomb to represent
     * @param fps
     *          the number of frame-per-second
     * @param duration
     *          the duration (in milliseconds) of the animation
     */
    public BombView(final Bomb bomb, final int fps, final long duration) {
        super(bomb, fps, duration);
    }
    
    @Override
    public List<BufferedImage> animationFrames() {
        return BOMB_TRIGGER;
    }
}
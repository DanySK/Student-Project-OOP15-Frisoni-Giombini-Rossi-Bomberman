package view.animations;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;

import model.units.Bomb;

/**
 * An implementation of {@link BombView}.
 *
 */
public class BombViewImpl implements BombView {

    // Sprites
    private static final int ANIMATION_DELAY = 10;
    private static final List<BufferedImage> BOMB_TRIGGER = Sprite.getSprites(new Point(10, 0), new Point(11, 0), new Point(12, 0));

    // Animation state
    private final Animation bombTrigger;

    private final Bomb bomb;
    private final int size;

    /**
     * Constructs a new view for the bomb.
     * 
     * @param bomb
     *          the bomb to represent
     * @param size
     *          the size of a tile
     */
    public BombViewImpl(final Bomb bomb, final int size) {
        this.bomb = Objects.requireNonNull(bomb);
        this.size = size;
        this.bombTrigger = new Animation(BOMB_TRIGGER, ANIMATION_DELAY);
    }

    @Override
    public Image getImage() {
        return this.bombTrigger.getCurrentFrame()
                .getScaledInstance(this.size, (this.size * Sprite.getSpriteHeight()) / Sprite.getSpriteWidth(), Image.SCALE_DEFAULT);
    }

    @Override
    public int getX() {
        return this.bomb.getX();
    }

    @Override
    public int getY() {
        return this.bomb.getY() - this.size * Sprite.getSpriteHeight() / Sprite.getSpriteWidth() + this.size;
    }

    @Override
    public void updateFrame() {
        this.bombTrigger.update();
    }

    @Override
    public int getNumberFrameAnimation() {
        return BOMB_TRIGGER.size();
    }
    
    @Override
    public Bomb getBomb() {
        return this.bomb;
    }
}
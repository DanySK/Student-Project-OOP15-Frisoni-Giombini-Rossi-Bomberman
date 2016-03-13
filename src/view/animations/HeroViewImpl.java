package view.animations;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import model.units.Hero;

/**
 * An implementation of {@link HeroView}.
 * It calculates the frame to display based on the hero's status and manages
 * the speed of the animations.
 *
 */
public class HeroViewImpl implements HeroView {

    // Sprites
    private static final int ANIMATION_DELAY = 10;
    private static final List<BufferedImage> WALK_DOWN = Sprite.getSprites(new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0), new Point(4, 0));
    private static final List<BufferedImage> WALK_RIGHT = Sprite.getSprites(new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1), new Point(4, 1));
    private static final List<BufferedImage> WALK_UP = Sprite.getSprites(new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(3, 2), new Point(4, 2));
    private static final List<BufferedImage> WALK_LEFT = Sprite.getSprites(new Point(0, 3), new Point(1, 3), new Point(2, 3), new Point(3, 3), new Point(4, 3));
    private static final List<BufferedImage> STAND_DOWN = Sprite.getSprites(new Point(6, 0), new Point(7, 0), new Point(8, 0));
    private static final List<BufferedImage> STAND_RIGHT = Sprite.getSprites(new Point(6, 1), new Point(7, 1), new Point(8, 1));
    private static final List<BufferedImage> STAND_UP = Sprite.getSprites(new Point(6, 2), new Point(7, 2), new Point(8, 2));
    private static final List<BufferedImage> STAND_LEFT = Sprite.getSprites(new Point(6, 3), new Point(7, 3), new Point(8, 3));
    
    // Animation states
    private Animation walkDown, walkRight, walkUp, walkLeft;
    private Animation standDown, standRight, standUp, standLeft;

    private final Hero hero;
    private final int size;
    private Optional<Animation> currAnimation;

    /**
     * Constructs a new view for the Hero.
     * 
     * @param hero
     *          the hero to represent
     * @param size
     *          the size of the sprite
     */
    public HeroViewImpl(final Hero hero, final int size) {
        this.hero = Objects.requireNonNull(hero);
        this.size = size;
        this.currAnimation = Optional.empty();
        loadAnimations(ANIMATION_DELAY);
        updateAnimation();
    }

    @Override
    public Image getImage() {
        updateAnimation();
        return this.currAnimation.get().getCurrentFrame()
                .getScaledInstance(this.size, (this.size * Sprite.getSpriteHeight()) / Sprite.getSpriteWidth(), Image.SCALE_DEFAULT);
    }

    @Override
    public int getX() {
        return this.hero.getX();
    }

    @Override
    public int getY() {
        return this.hero.getY() - this.size * Sprite.getSpriteHeight() / Sprite.getSpriteWidth() + this.size;
    }

    @Override
    public void updateFrame() {
        this.currAnimation.get().update();
    }
    
    @Override
    public Point getCenterPoint() {
        return new Point(getX() + this.size / 2,
                getY() + (this.size * Sprite.getSpriteHeight()) / (Sprite.getSpriteWidth() * 2));
      
    }

    private void loadAnimations(final int delay) {
        walkDown = new Animation(WALK_DOWN, delay);
        walkRight = new Animation(WALK_RIGHT, delay);
        walkUp = new Animation(WALK_UP, delay);
        walkLeft = new Animation(WALK_LEFT, delay);
        standDown = new Animation(STAND_DOWN, delay);
        standRight = new Animation(STAND_RIGHT, delay);
        standUp = new Animation(STAND_UP, delay);
        standLeft = new Animation(STAND_LEFT, delay);
    }

    private void updateAnimation() {
        Animation nextAnimation;
        switch (this.hero.getDirection()) {
        case DOWN:
            nextAnimation = this.hero.isMoving() ? walkDown : standDown;
            break;
        case RIGHT:
            nextAnimation = this.hero.isMoving() ? walkRight : standRight;
            break;
        case UP:
            nextAnimation = this.hero.isMoving() ? walkUp : standUp;
            break;
        case LEFT:
            nextAnimation = this.hero.isMoving() ? walkLeft : standLeft;
            break;
        default:
            throw new IllegalArgumentException("Invalid direction");
        }
        if (!this.currAnimation.isPresent() || !this.currAnimation.get().equals(nextAnimation)) {
            this.currAnimation = Optional.of(nextAnimation);
            this.currAnimation.get().start();
        }
    }
}

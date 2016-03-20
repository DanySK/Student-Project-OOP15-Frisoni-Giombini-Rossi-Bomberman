package view.animations;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import model.units.Direction;
import model.units.Entity;

/**
 * An implementation of {@link EntityView}.
 * It manages the animations of an entity that can move in the game.
 * It calculates the frame to display based on the entity's status.
 *
 */
public abstract class AbstractEntityView implements EntityView {

    private static final int ANIMATION_DELAY = 10;

    // Animation states
    private final EnumMap<Direction, Animation> movementAnimations = new EnumMap<>(Direction.class);
    private final EnumMap<Direction, Animation> standingAnimations = new EnumMap<>(Direction.class);;

    private final Entity entity;
    protected final int size;
    private Optional<Animation> currAnimation;

    /**
     * Constructs a new view for the entity.
     * 
     * @param entity
     *          the entity to represent
     * @param size
     *          the size of the sprite
     */
    public AbstractEntityView(final Entity entity, final int size) {
        this.entity = Objects.requireNonNull(entity);
        this.size = size;
        this.currAnimation = Optional.empty();
        loadAnimations(ANIMATION_DELAY);
        updateAnimation();
    }

    /**
     * @return the frames of the animations associated to the movement of the entity.
     */
    public abstract EnumMap<Direction, List<BufferedImage>> movementFrames();

    /**
     * 
     * @return the frames of the animations associated to the standing-position of the entity.
     */
    public abstract EnumMap<Direction, List<BufferedImage>> standingFrames();

    @Override
    public Image getImage() {
        updateAnimation();
        return this.currAnimation.get().getCurrentFrame()
                .getScaledInstance(this.size, (this.size * Sprite.getSpriteHeight()) / Sprite.getSpriteWidth(), Image.SCALE_DEFAULT);
    }

    @Override
    public int getX() {
        return this.entity.getX();
    }

    @Override
    public int getY() {
        return this.entity.getY() - this.size * Sprite.getSpriteHeight() / Sprite.getSpriteWidth() + this.size;
    }

    @Override
    public void updateFrame() {
        this.currAnimation.get().update();
    }

    private void loadAnimations(final int delay) {
        movementFrames().entrySet().stream().forEach(e -> movementAnimations.put(e.getKey(), new Animation(e.getValue(), delay)));
        standingFrames().entrySet().stream().forEach(e -> standingAnimations.put(e.getKey(), new Animation(e.getValue(), delay)));
    }

    private void updateAnimation() {
        Animation nextAnimation;
        nextAnimation = this.entity.isMoving() ? movementAnimations.get(this.entity.getDirection()) : standingAnimations.get(this.entity.getDirection());
        if (!this.currAnimation.isPresent() || !this.currAnimation.get().equals(nextAnimation)) {
            this.currAnimation = Optional.of(nextAnimation);
            this.currAnimation.get().start();
        }
    }
}

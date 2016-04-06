package view.animations.unit;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

import model.units.Direction;
import model.units.Entity;

/**
 * An implementation of {@link EntityAnimationView}.
 * It manages the animations of an {@link Entity} that can move in the game.
 * It calculates the frame to display based on the entity's status.
 * The animation of an entity is updated periodically, at the specified frequency.
 *
 */
public abstract class AbstractEntityView extends AbstractAnimationView implements EntityAnimationView {

    // The delay in seconds between each frame update of the animations
    private static final double UPDATE_FRAME_DELAY = 0.1;

    // Animation states
    private final EnumMap<Direction, Animation> movementAnimations = new EnumMap<>(Direction.class);
    private final EnumMap<Direction, Animation> standingAnimations = new EnumMap<>(Direction.class);

    private final Entity entity;
    private Optional<Animation> currAnimation;

    /**
     * Constructs a new view for the entity.
     * 
     * @param entity
     *          the entity to represent
     * @param fps
     *          the number of frame-per-second
     */
    public AbstractEntityView(final Entity entity, final int fps) {
        super(entity);
        this.entity = entity;
        this.currAnimation = Optional.empty();
        loadAnimations((int) (fps * UPDATE_FRAME_DELAY));
        updateAnimation();
    }

    @Override
    public abstract EnumMap<Direction, List<BufferedImage>> movementFrames();

    @Override
    public abstract EnumMap<Direction, List<BufferedImage>> standingFrames();
    
    @Override
    public Animation getAnimation() {
        return this.currAnimation.get();
    }
    
    @Override
    public Image getImage() {
        updateAnimation();
        return super.getImage();
    }

    private void loadAnimations(final int delay) {
        movementFrames().entrySet().stream().forEach(e -> movementAnimations.put(e.getKey(), new Animation(e.getValue(), delay, true)));
        standingFrames().entrySet().stream().forEach(e -> standingAnimations.put(e.getKey(), new Animation(e.getValue(), delay, true)));
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

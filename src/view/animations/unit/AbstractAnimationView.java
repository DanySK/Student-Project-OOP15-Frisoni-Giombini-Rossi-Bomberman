package view.animations.unit;

import java.awt.Image;
import java.util.Objects;

import model.units.LevelElement;

/**
 * An implementation of {@link AnimationView}.
 * This class handles the basic operations that affect the rendering of a {@link LevelElement} animation.
 * Note: for a 3D visual effect, the game's frames generally have a vertical rectangular form factor.
 *
 */
public abstract class AbstractAnimationView implements AnimationView {

    private final LevelElement element;
    
    /**
     * Constructs a new abstract AnimationView.
     * 
     * @param element
     *          the level element associated to the animation
     */
    public AbstractAnimationView(final LevelElement element) {
        this.element = Objects.requireNonNull(element);
    }
    
    /**
     * @return the current animation to manage.
     */
    protected abstract Animation getAnimation();
    
    @Override
    public Image getImage() {
        return getAnimation().getCurrentFrame()
                .getScaledInstance(this.element.getHitbox().width,
                        (this.element.getHitbox().height * Sprite.getSpriteHeight()) / Sprite.getSpriteWidth(), Image.SCALE_DEFAULT);
    }

    @Override
    public int getX() {
        return this.element.getX();
    }

    @Override
    public int getY() {
        return this.element.getY() - this.element.getHitbox().height * Sprite.getSpriteHeight() / Sprite.getSpriteWidth() + this.element.getHitbox().height;
    }
    
    @Override
    public void updateFrame() {
        getAnimation().update();
    }
}

package view.animations;

import java.awt.Point;

import model.units.Hero;
import view.animations.unit.EntityAnimationView;

/**
 * This interface manages the visual representation of a {@link Hero}.
 *
 */
public interface HeroView extends EntityAnimationView {
    
    /**
     * @return the center point of the sprite associated to the hero.
     */
    Point getCenterPoint();
}

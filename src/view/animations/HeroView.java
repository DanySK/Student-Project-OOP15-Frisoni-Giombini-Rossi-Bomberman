package view.animations;

import java.awt.Point;

import model.units.Hero;

/**
 * This interface manages the visual representation of a {@link Hero}.
 *
 */
public interface HeroView extends EntityView {
    
    /**
     * @return the center point of the sprite associated to the hero.
     */
    Point getCenterPoint();
}

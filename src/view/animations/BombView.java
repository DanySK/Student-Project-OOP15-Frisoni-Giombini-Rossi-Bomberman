package view.animations;

import model.units.Bomb;
import model.units.LevelElement;
import view.animations.unit.SingleAnimationView;

/**
 * This interface manages the visual representation of a {@link Bomb}.
 *
 */
public interface BombView extends SingleAnimationView {
    
    /**
     * @return the bomb represented.
     */
    LevelElement getBomb();
}
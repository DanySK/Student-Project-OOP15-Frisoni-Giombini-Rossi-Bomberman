package view.animations;

import model.units.Bomb;

/**
 * This interface manages the visual representation of a {@link Bomb}.
 *
 */
public interface BombView extends EntityView {
    
    /**
     * @return the number of frames that realize the animation.
     */
    int getNumberFrameAnimation();
    
    /**
     * @return the bomb represented.
     */
    Bomb getBomb();
}
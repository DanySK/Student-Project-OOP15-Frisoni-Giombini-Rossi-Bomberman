package view.animations.unit;

import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.List;

import model.units.Direction;

/**
 * This interface manage the visual representation of an entity that can moves
 * on the screen.
 *
 */
public interface EntityAnimationView extends AnimationView {
    
    /**
     * @return the frames of the animations associated to the movement of the entity.
     */
    EnumMap<Direction, List<BufferedImage>> movementFrames();

    /**
     * 
     * @return the frames of the animations associated to the standing-position of the entity.
     */
    EnumMap<Direction, List<BufferedImage>> standingFrames();
}

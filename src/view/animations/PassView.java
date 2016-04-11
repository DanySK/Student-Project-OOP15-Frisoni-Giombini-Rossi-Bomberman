package view.animations;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.List;

import model.units.Direction;
import model.units.enemy.Enemy;
import view.animations.unit.AbstractEnemyView;
import view.animations.unit.AbstractEntityView;
import view.animations.unit.Sprite;

/**
 * An implementation of {@link AbstractEntityView}.
 * It represents a pass enemy.
 *
 */
public class PassView extends AbstractEnemyView {
    
    private static final EnumMap<Direction, List<BufferedImage>> MOVEMENT_MAP = new EnumMap<>(Direction.class);
    private static final EnumMap<Direction, List<BufferedImage>> STANDING_MAP = new EnumMap<>(Direction.class);
    static {
        MOVEMENT_MAP.put(Direction.DOWN, Sprite.getSprites(new Point(6, 5), new Point(7, 5), new Point(8, 5), new Point(9, 5)));
        MOVEMENT_MAP.put(Direction.RIGHT, Sprite.getSprites(new Point(6, 6), new Point(7, 6), new Point(8, 6), new Point(9, 6)));
        MOVEMENT_MAP.put(Direction.UP, Sprite.getSprites(new Point(6, 7), new Point(7, 7), new Point(8, 7), new Point(9, 7)));
        MOVEMENT_MAP.put(Direction.LEFT, Sprite.getSprites(new Point(6, 8), new Point(7, 8), new Point(8, 8), new Point(9, 8)));
        STANDING_MAP.put(Direction.DOWN, MOVEMENT_MAP.get(Direction.DOWN));
        STANDING_MAP.put(Direction.RIGHT, MOVEMENT_MAP.get(Direction.RIGHT));
        STANDING_MAP.put(Direction.UP, MOVEMENT_MAP.get(Direction.UP));
        STANDING_MAP.put(Direction.LEFT, MOVEMENT_MAP.get(Direction.LEFT));
    }
    
    /**
     * Constructs a new view for the kondoria.
     * 
     * @param enemy
     *          the entity to represent
     * @param fps
     *          the number of frame-per-second
     */
    public PassView(final Enemy enemy, final int fps) {
        super(enemy, fps);
    }
    
    @Override
    public EnumMap<Direction, List<BufferedImage>> movementFrames() {
        return MOVEMENT_MAP;
    }

    @Override
    public EnumMap<Direction, List<BufferedImage>> standingFrames() {
        return STANDING_MAP;
    }
}

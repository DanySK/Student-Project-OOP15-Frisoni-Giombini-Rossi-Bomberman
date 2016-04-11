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
 * It represents a minvo enemy.
 *
 */
public class MinvoView extends AbstractEnemyView {

    private static final EnumMap<Direction, List<BufferedImage>> MOVEMENT_MAP = new EnumMap<>(Direction.class);
    private static final EnumMap<Direction, List<BufferedImage>> STANDING_MAP = new EnumMap<>(Direction.class);
    static {
        MOVEMENT_MAP.put(Direction.DOWN, Sprite.getSprites(new Point(12, 5), new Point(13, 5), new Point(14, 5), new Point(15, 5),
                new Point(16, 5), new Point(17, 5), new Point(18, 5), new Point(19, 5), new Point(20, 5), new Point(21, 5)));
        MOVEMENT_MAP.put(Direction.RIGHT, Sprite.getSprites(new Point(12, 6), new Point(13, 6), new Point(14, 6), new Point(15, 6),
                new Point(16, 6), new Point(17, 6)));
        MOVEMENT_MAP.put(Direction.UP, Sprite.getSprites(new Point(12, 7), new Point(13, 7), new Point(14, 7), new Point(15, 7)));
        MOVEMENT_MAP.put(Direction.LEFT, Sprite.getSprites(new Point(12, 8), new Point(13, 8), new Point(14, 8), new Point(15, 8),
                new Point(16, 8), new Point(17, 8)));
        STANDING_MAP.put(Direction.DOWN, MOVEMENT_MAP.get(Direction.DOWN));
        STANDING_MAP.put(Direction.RIGHT, MOVEMENT_MAP.get(Direction.RIGHT));
        STANDING_MAP.put(Direction.UP, MOVEMENT_MAP.get(Direction.UP));
        STANDING_MAP.put(Direction.LEFT, MOVEMENT_MAP.get(Direction.LEFT));
    }
    
    /**
     * Constructs a new view for the minvo.
     * 
     * @param enemy
     *          the entity to represent
     * @param fps
     *          the number of frame-per-second
     */
    public MinvoView(final Enemy enemy, final int fps) {
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

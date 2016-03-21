package view.animations;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.List;

import model.units.Direction;
import model.units.Hero;

/**
 * An implementation of {@link HeroView}.
 * It calculates the frame to display based on the hero's status and manages
 * the speed of the animations.
 *
 */
public class HeroViewImpl extends AbstractEntityView implements HeroView {

    private static final EnumMap<Direction, List<BufferedImage>> MOVEMENT_MAP = new EnumMap<>(Direction.class);
    private static final EnumMap<Direction, List<BufferedImage>> STANDING_MAP = new EnumMap<>(Direction.class);
    static {
        MOVEMENT_MAP.put(Direction.DOWN, Sprite.getSprites(new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0), new Point(4, 0)));
        MOVEMENT_MAP.put(Direction.RIGHT, Sprite.getSprites(new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1), new Point(4, 1)));
        MOVEMENT_MAP.put(Direction.UP, Sprite.getSprites(new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(3, 2), new Point(4, 2)));
        MOVEMENT_MAP.put(Direction.LEFT, Sprite.getSprites(new Point(0, 3), new Point(1, 3), new Point(2, 3), new Point(3, 3), new Point(4, 3)));
        STANDING_MAP.put(Direction.DOWN, Sprite.getSprites(new Point(6, 0), new Point(7, 0), new Point(8, 0)));
        STANDING_MAP.put(Direction.RIGHT, Sprite.getSprites(new Point(6, 1), new Point(7, 1), new Point(8, 1)));
        STANDING_MAP.put(Direction.UP, Sprite.getSprites(new Point(6, 2), new Point(7, 2), new Point(8, 2)));
        STANDING_MAP.put(Direction.LEFT, Sprite.getSprites(new Point(6, 3), new Point(7, 3), new Point(8, 3)));
    }
    
    /**
     * Constructs a new view for the hero.
     * 
     * @param hero
     *          the hero to represent
     * @param size
     *          the size of a tile
     */
    public HeroViewImpl(final Hero hero, final int size) {
        super(hero, size);
    }
    
    @Override
    public Point getCenterPoint() {
        return new Point(getX() + getSize() / 2,
                getY() + (getSize() * Sprite.getSpriteHeight()) / (Sprite.getSpriteWidth() * 2));
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

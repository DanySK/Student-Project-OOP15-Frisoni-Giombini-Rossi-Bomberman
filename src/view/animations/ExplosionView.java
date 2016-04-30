package view.animations;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

import model.units.Tile;
import model.units.TileImpl;
import view.animations.unit.AbstractSingleAnimationView;
import view.animations.unit.Sprite;

/**
 * This class handles the representation of a {@link TileImpl} involved in a bomb's explosion.
 *
 */
public class ExplosionView extends AbstractSingleAnimationView {

    private static final List<BufferedImage> EXPLOSION = Sprite.getSprites(new Point(10, 1), new Point(11, 1), new Point(12, 1),
            new Point(13, 1), new Point(14, 1), new Point(15, 1), new Point(16, 1), new Point(17, 1));

    private final Tile tile;
    
    /**
     * Constructs a new view for the exploded tile.
     * 
     * @param tile
     *          the tile where the explosion occurred
     * @param fps
     *          the number of frame-per-second
     * @param duration
     *          the duration (in milliseconds) of the animation         
     */
    public ExplosionView(final Tile tile, final int fps, final long duration) {
        super(tile, fps, duration);
        this.tile = tile;
    }

    @Override
    public List<BufferedImage> animationFrames() {
        return EXPLOSION;
    }
    
    @Override
    public Tile getLevelElement() {
        return this.tile;
    }
}

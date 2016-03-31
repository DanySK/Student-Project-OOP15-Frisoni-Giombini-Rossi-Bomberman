package view.animations;

/**
 * This class handles the representation of {@link Tile} involved in a bomb's explosion.
 *
 *//*
public class ExplosionView extends AbstractSingleAnimationView {

    private static final List<BufferedImage> EXPLOSION = Sprite.getSprites(new Point(10, 1), new Point(11, 1), new Point(12, 1),
            new Point(13, 1), new Point(14, 1), new Point(15, 1), new Point(16, 1), new Point(17, 1));

    private final Tile tile;
*/
    /**
     * Constructs a new view for the exploded tile.
     * 
     * @param tile
     *          the tile where the explosion occurred
     * @param size
     *          the size of a tile
     * @param fps
     *          the number of frame-per-second
     * @param duration
     *          the duration (in milliseconds) of the animation         
     *//*
    public ExplosionView(final Tile tile, final int size, final int fps, final long duration) {
        super(size, fps, duration);
        this.tile = Objects.requireNonNull(tile);
    }

    @Override
    public int getX() {
        return this.tile.getRow() * getSize();
    }

    @Override
    public int getY() {
        return this.tile.getCol() * getSize() - getSize() * Sprite.getSpriteHeight() / Sprite.getSpriteWidth() + getSize();
    }

    @Override
    public List<BufferedImage> animationFrames() {
        return EXPLOSION;
    }
}*/

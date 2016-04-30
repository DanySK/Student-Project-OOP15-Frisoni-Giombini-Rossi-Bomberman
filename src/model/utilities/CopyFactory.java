package model.utilities;

import java.awt.Dimension;
import java.awt.Point;

import model.units.Bomb;
import model.units.BombImpl;
import model.units.Tile;
import model.units.TileImpl;

/**
 * This class is used to get a copy
 * of a static game element.
 */
public final class CopyFactory {

    private CopyFactory() { }
    
    /**
     * Gets a copy of the tile.
     * 
     * @param tile
     *          the tile to copy
     * @return the copy of the tile
     */
    public static Tile getCopy(final Tile tile) {
        return new TileImpl(new Point(tile.getPosition()), 
                new Dimension(tile.getHitbox().width, tile.getHitbox().height), 
                tile.getType(), tile.getPowerup());
    }
    
    /**
     * Gets a copy of a bomb.
     * 
     * @param bomb
     *          the bomb to copy
     * @return the copy of the bomb
     */
    public static Bomb getCopy(final Bomb bomb) {
        final Bomb bombCopy = new BombImpl(new Point(bomb.getPosition()), 
                new Dimension(bomb.getHitbox().width, bomb.getHitbox().height), 
                bomb.getRange());
        bombCopy.setPlanted(bomb.isPositioned());
        return bombCopy;
    }
}

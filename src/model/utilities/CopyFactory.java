package model.utilities;

import java.awt.Dimension;
import java.awt.Point;

import model.Tile;
import model.units.Bomb;
import model.units.BombImpl;

/**
 * This class is used to get a copy
 * of a static game element.
 */
public final class CopyFactory {

    private CopyFactory(){ }
    
    /**
     * Gets a copy of the tile.
     * 
     * @param t
     *          the tile to copy
     * @return the copy of the tile
     */
    public static Tile getCopy(final Tile t) {
        return new Tile(new Point(t.getPosition()), 
                new Dimension(t.getHitbox().width, t.getHitbox().height), 
                t.getType(), t.getPowerup());
    }
    
    /**
     * Gets a copy of a bomb.
     * 
     * @param b
     *          the bomb to copy
     * @return the copy of the bomb
     */
    public static Bomb getCopy(final Bomb b) {
        final Bomb bombCopy = new BombImpl(new Point(b.getPosition()), 
                new Dimension(b.getHitbox().width, b.getHitbox().height), 
                b.getRange());
        bombCopy.setPlanted(b.isPositioned());
        return bombCopy;
    }
}

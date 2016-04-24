
package model.test;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Optional;

import org.junit.Test;

import org.junit.Assert;
import model.Tile;
import model.TileType;
import model.units.Bomb;
import model.units.BombImpl;
import model.utilities.CopyFactory;
import model.utilities.MapPoint;

/**
 * This class is used to verify the correct operation of the copies.
 */
public class TestCopyFactory {
    
    private static final int TILE_DIMENSION = 10;
    
    /**
     * This test verifies the correct copy of the tiles.
     */
    @Test
    public void testTile() {
        final Tile tile = new Tile(new Point(MapPoint.getCoordinate(0, TILE_DIMENSION),
                MapPoint.getCoordinate(0, TILE_DIMENSION)), 
                new Dimension(TILE_DIMENSION, TILE_DIMENSION),
                TileType.CONCRETE, Optional.empty());
        Tile copyTile = CopyFactory.getCopy(tile);
        Assert.assertEquals(tile.getPosition(), copyTile.getPosition());
        Assert.assertEquals(tile.getPowerup(), copyTile.getPowerup());
        Assert.assertEquals(tile.getType(), copyTile.getType());
        Assert.assertEquals(tile.getX(), copyTile.getX());
        Assert.assertEquals(tile.getY(), copyTile.getY());
        Assert.assertTrue(tile.equals(copyTile));
        copyTile.setType(TileType.WALKABLE);
        Assert.assertFalse(tile.equals(copyTile));
        Assert.assertEquals(tile.getPosition(), copyTile.getPosition());
        Assert.assertEquals(tile.getPowerup(), copyTile.getPowerup());
        Assert.assertNotEquals(tile.getType(), copyTile.getType());
        Assert.assertEquals(tile.getX(), copyTile.getX());
        Assert.assertEquals(tile.getY(), copyTile.getY());
        copyTile = CopyFactory.getCopy(tile);
        Assert.assertEquals(tile.getPosition(), copyTile.getPosition());
        Assert.assertEquals(tile.getPowerup(), copyTile.getPowerup());
        Assert.assertEquals(tile.getType(), copyTile.getType());
        Assert.assertEquals(tile.getX(), copyTile.getX());
        Assert.assertEquals(tile.getY(), copyTile.getY());
        Assert.assertTrue(tile.equals(copyTile));
    }
    
    /**
     * This test verifies the correct copy of the bombs.
     */
    @Test
    public void testBomb() {
        final Bomb bomb = new BombImpl(new Point(1, 1), new Dimension(TILE_DIMENSION, TILE_DIMENSION), 2);
        Bomb copyBomb = CopyFactory.getCopy(bomb);
        Assert.assertEquals(bomb.getPosition(), copyBomb.getPosition());
        Assert.assertEquals(bomb.getRange(), copyBomb.getRange());
        copyBomb.setRange(3);
        Assert.assertEquals(bomb.getPosition(), copyBomb.getPosition());
        Assert.assertNotEquals(bomb.getRange(), copyBomb.getRange());
        copyBomb = CopyFactory.getCopy(bomb);
        Assert.assertEquals(bomb.getPosition(), copyBomb.getPosition());
        Assert.assertEquals(bomb.getRange(), copyBomb.getRange());
    }
}

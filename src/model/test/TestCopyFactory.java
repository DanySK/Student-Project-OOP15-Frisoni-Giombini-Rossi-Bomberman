package model.test;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Optional;

import org.junit.Test;

import org.junit.Assert;
import model.Tile;
import model.TileType;
import model.utilities.CopyFactory;
import model.utilities.MapPoint;

public class TestCopyFactory {
    
    @Test
    public void test() {
        final int tileDimension = 10;
        final Tile tile = new Tile(new Point(MapPoint.getCoordinate(0, tileDimension),
                MapPoint.getCoordinate(0, tileDimension)), 
                new Dimension(tileDimension, tileDimension),
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
}

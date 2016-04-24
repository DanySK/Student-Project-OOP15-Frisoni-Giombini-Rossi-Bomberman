package model.test;

import java.awt.Dimension;
import java.awt.Point;
import org.junit.Test;

import org.junit.Assert;

import model.units.Detonator;
import model.utilities.MapPoint;

/**
 * This class is used to verify the correct placement of the bombs.
 */
public class TestBomb {
    
    private static final int TILE_DIMENSION = 10;
    private static final int N_TILES = 5;

    /**
     * This test verifies the correct placement of the bombs than the dell'hero position.
     */
    @Test
    public void test() {
        final Detonator detonator = new Detonator(new Dimension(TILE_DIMENSION, TILE_DIMENSION));
        Assert.assertTrue(detonator.hasBombs());
        Assert.assertEquals(detonator.getActualBombs(), 1);
        Assert.assertEquals(detonator.getPlantedBombs().size(), 0);
        detonator.plantBomb(new Point(MapPoint.getCorrectPos(10, N_TILES, TILE_DIMENSION), 
                MapPoint.getCorrectPos(0, N_TILES, TILE_DIMENSION)));
        Assert.assertEquals(detonator.getBombToReactivate().getX(), 10);
        Assert.assertEquals(detonator.getBombToReactivate().getY(), 0);
        Assert.assertEquals(detonator.getPlantedBombs().size(), 1);
        Assert.assertFalse(detonator.hasBombs());
        detonator.reactivateBomb();
        Assert.assertEquals(detonator.getPlantedBombs().size(), 0);
        Assert.assertTrue(detonator.hasBombs());
        detonator.increaseBombs();
        Assert.assertEquals(detonator.getActualBombs(), 2);
        Assert.assertTrue(detonator.hasBombs());
        detonator.plantBomb(new Point(MapPoint.getCorrectPos(25, N_TILES, TILE_DIMENSION), 
                MapPoint.getCorrectPos(10, N_TILES, TILE_DIMENSION)));
        Assert.assertEquals(detonator.getBombToReactivate().getX(), 20);
        Assert.assertEquals(detonator.getBombToReactivate().getY(), 10);
        Assert.assertEquals(detonator.getPlantedBombs().size(), 1);
        Assert.assertTrue(detonator.hasBombs());
        detonator.plantBomb(new Point(MapPoint.getCorrectPos(10, N_TILES, TILE_DIMENSION), 
                MapPoint.getCorrectPos(23, N_TILES, TILE_DIMENSION)));
        Assert.assertEquals(detonator.getPlantedBombs().size(), 2);
        Assert.assertFalse(detonator.hasBombs());
        detonator.reactivateBomb();
        Assert.assertEquals(detonator.getBombToReactivate().getX(), 10);
        Assert.assertEquals(detonator.getBombToReactivate().getY(), 20);
        Assert.assertTrue(detonator.hasBombs());
        Assert.assertEquals(detonator.getPlantedBombs().size(), 1);
        detonator.reactivateBomb();
        Assert.assertEquals(detonator.getPlantedBombs().size(), 0);
        Assert.assertTrue(detonator.hasBombs());
        detonator.plantBomb(new Point(MapPoint.getCorrectPos(30, N_TILES, TILE_DIMENSION), 
                MapPoint.getCorrectPos(18, N_TILES, TILE_DIMENSION)));
        Assert.assertEquals(detonator.getBombToReactivate().getX(), 30);
        Assert.assertEquals(detonator.getBombToReactivate().getY(), 20);
        Assert.assertEquals(detonator.getPlantedBombs().size(), 1);
    }
}

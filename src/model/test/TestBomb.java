package model.test;

import java.awt.Dimension;
import java.awt.Point;
import org.junit.Test;

import org.junit.Assert;

import model.units.Detonator;
import model.utilities.MapPoint;

public class TestBomb {

    @Test
    public void test1() {
        final int nTiles = 5;
        final int dimension = 10;
        final Detonator detonator = new Detonator(new Dimension(dimension, dimension));
        Assert.assertTrue(detonator.hasBombs());
        Assert.assertEquals(detonator.getActualBombs(), 1);
        Assert.assertEquals(detonator.getPlantedBombs().size(), 0);
        detonator.plantBomb(new Point(MapPoint.getCorrectPos(10, nTiles, dimension), 
                MapPoint.getCorrectPos(0, nTiles, dimension)));
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
        detonator.plantBomb(new Point(MapPoint.getCorrectPos(25, nTiles, dimension), 
                MapPoint.getCorrectPos(10, nTiles, dimension)));
        Assert.assertEquals(detonator.getBombToReactivate().getX(), 20);
        Assert.assertEquals(detonator.getBombToReactivate().getY(), 10);
        Assert.assertEquals(detonator.getPlantedBombs().size(), 1);
        Assert.assertTrue(detonator.hasBombs());
        detonator.plantBomb(new Point(MapPoint.getCorrectPos(10, nTiles, dimension), 
                MapPoint.getCorrectPos(23, nTiles, dimension)));
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
        detonator.plantBomb(new Point(MapPoint.getCorrectPos(30, nTiles, dimension), 
                MapPoint.getCorrectPos(18, nTiles, dimension)));
        Assert.assertEquals(detonator.getBombToReactivate().getX(), 30);
        Assert.assertEquals(detonator.getBombToReactivate().getY(), 20);
        Assert.assertEquals(detonator.getPlantedBombs().size(), 1);
    }
}

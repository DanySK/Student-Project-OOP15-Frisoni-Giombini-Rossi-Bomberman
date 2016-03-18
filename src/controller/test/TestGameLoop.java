package controller.test;


import org.junit.Assert;
import org.junit.Test;

import controller.AbstractGameLoop;

/**
 * This class is used to test the correct operation of the game loop.
 */
public class TestGameLoop {
    
    private static final int TEST_FPS = 60;
    private static final int RANGE = TEST_FPS + 10;
    private static final int MILLIS = 1000;
    private volatile int countModel;
    private volatile int countView; 
    
    /**
     * This test verifies the correct start and stop operation of AbstractGameLoop.
     */
    @Test
    public void test1() {
        final AbstractGameLoop game = new GameLoop();
        game.start();
        try {
            Thread.sleep(MILLIS);
        } catch (final InterruptedException e) {
            Assert.assertTrue("Interrupted", false);
        }
        Assert.assertTrue(this.countModel > 0 && this.countView > 0);
        System.out.print("Test1: " + this.countView + " and " + this.countModel + " before stopped, ");
        game.stopLoop();
        final int n = this.countView;
        try {
            Thread.sleep(MILLIS);
        } catch (final InterruptedException e) {
            Assert.assertTrue("Interrupted", false);
        }
        System.out.println(this.countView + " and " + this.countModel + " after stopped.");
        Assert.assertTrue(this.countView <= n + 1);
    }
    
    /**
     * This test verifies the correct pause and un-pause operation of AbstractGameLoop.
     */
    @Test
    public void test2() {
        final AbstractGameLoop game1 = new GameLoop();
        game1.start();
        try {
            Thread.sleep(MILLIS);
        } catch (final InterruptedException e) {
            Assert.assertTrue("Interrupted", false);
        }
        System.out.print("Test2: " + this.countView + " and " + this.countModel + " before pause, ");
        game1.pause();
        try {
            Thread.sleep(MILLIS);
        } catch (final InterruptedException e) {
            Assert.assertTrue("Interrupted", false);
        }
        System.out.print(this.countView + " and " + this.countModel + " after pause, ");
        Assert.assertTrue(this.countModel < RANGE);
        game1.unPause();
        try {
            Thread.sleep(MILLIS);
        } catch (final InterruptedException e) {
            Assert.assertTrue("Interrupted", false);
        }
        game1.stopLoop();
        System.out.println(this.countView + " and " + this.countModel + " after stopped.");
        Assert.assertTrue(this.countModel >= TEST_FPS);
    }

    /**
     * This test verifies the correct framerate of AbstractGameLoop.
     */
    @Test
    public void test3() {
        final AbstractGameLoop game2 = new GameLoop();
        game2.start();
        try {
            Thread.sleep(MILLIS);
        } catch (final InterruptedException e) {
            Assert.assertTrue("Interrupted", false);
        }
        game2.stopLoop();
        System.out.println("Test3: the result is " + this.countView + " for View and " + this.countModel + " for Model, the result expected is " + TEST_FPS + ".");
    }
    
    /**
     * This class implements the abstract methods of AbstractGameLoop.
     */
    private class GameLoop extends AbstractGameLoop{
        
        /**
         * Constructor for GameLoop.
         */
        public GameLoop() {
            super(TEST_FPS);
            countModel = 0;
            countView = 0;
        } 

        @Override
        public void updateModel() {
            countModel++;
        }

        @Override
        public void updateView() {
            countView++;
        }

    }
    
}

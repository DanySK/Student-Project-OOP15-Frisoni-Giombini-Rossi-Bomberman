package controller.test;


import org.junit.Assert;
import org.junit.Test;

import controller.AbstractGameLoop;

/**
 * This class is used to test the correct operation of the game loop.
 */
public class TestGameLoop {
    
    private static final String INTERRUPTED = "Interrupted";
    private static final int TEST_FPS = 60;
    private static final int RANGE = TEST_FPS + 10;
    private static final int MILLIS = 1000;
    private volatile int nUpdatesModel;
    private volatile int nUpdatesView; 
    
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
            Assert.assertTrue(INTERRUPTED, false);
        }
        Assert.assertTrue(this.nUpdatesModel > 0 && this.nUpdatesView > 0);
        System.out.println("Test1: ");
        System.out.print("      " + this.nUpdatesView + " - " + this.nUpdatesModel + " before stopped, ");
        game.stopLoop();
        final int n = this.nUpdatesView;
        try {
            Thread.sleep(MILLIS);
        } catch (final InterruptedException e) {
            Assert.assertTrue("INTERRUPTED", false);
        }
        System.out.println(this.nUpdatesView + " - " + this.nUpdatesModel + " after stopped.");
        Assert.assertTrue(this.nUpdatesView <= n + 1);
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
            Assert.assertTrue(INTERRUPTED, false);
        }
        System.out.println("Test2: ");
        System.out.print("      " + this.nUpdatesView + "/"
                + this.nUpdatesModel + " before pause, ");
        game1.pauseLoop();
        try {
            Thread.sleep(MILLIS);
        } catch (final InterruptedException e) {
            Assert.assertTrue(INTERRUPTED, false);
        }
        System.out.print(this.nUpdatesView + "/" + this.nUpdatesModel + " after pause, ");
        Assert.assertTrue(this.nUpdatesModel < RANGE);
        game1.unPauseLoop();
        try {
            Thread.sleep(MILLIS);
        } catch (final InterruptedException e) {
            Assert.assertTrue(INTERRUPTED, false);
        }
        game1.stopLoop();
        System.out.println(this.nUpdatesView + "/" + this.nUpdatesModel + " after stopped.");
        Assert.assertTrue(this.nUpdatesModel >= TEST_FPS);
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
            Assert.assertTrue(INTERRUPTED, false);
        }
        game2.stopLoop();
        System.out.println("Test3: ");
        System.out.println("      the result is " + this.nUpdatesView + " for View and "
                + this.nUpdatesModel + " for Model, the result expected is " + TEST_FPS + ".");
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
            nUpdatesModel = 0;
            nUpdatesView = 0;
        } 

        @Override
        public void updateModel() {
            nUpdatesModel++;
        }

        @Override
        public void updateView() {
            nUpdatesView++;
        }

        @Override
        public void updateGameState() {
            //do nothing
        }

        @Override
        public void updateEnemies() {
            //do nothing
        }

        @Override
        public void updateTime() {
            //do nothing
        }
    }
}

package controller;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

/**
 *  Implementation of {@link GameLoop}.
 *  A "clock" for the game. 
 *  The GameLoop synchronizes model and view every frame.
 */
public abstract class AbstractGameLoop extends Thread implements GameLoop {

    private static final double TIME_FACTOR = 1000000000.0;
    private static final double SLEEP_FACTOR = 0.0000001;
    private static final long MILLI = 0001;

    private final int gameSpeed;
    private volatile boolean running;
    private volatile boolean paused;
    private volatile boolean wasPaused;

    /**
     * Constructor for AbstractGameLoop.
     */
    public AbstractGameLoop(final int gameSpeed) {
        this.gameSpeed = gameSpeed;
        this.running = false;
        this.paused = false;
        this.wasPaused = false;
    }

    /**
     * This method is called as soon as the GameLoop is started.
     */
    @Override
    public void run() {
        double nextTime = System.nanoTime();
        double lastSecondTime = (int) (nextTime / TIME_FACTOR);
        this.running = true;
        while (this.running) {
            if (!this.paused) {
                if (this.wasPaused) {
                    nextTime = System.nanoTime();
                    this.wasPaused = false;
                }
                final double currTime = System.nanoTime();
                if (currTime >= nextTime) {
                    nextTime += TIME_FACTOR / this.gameSpeed;
                    this.updateModel();
                    this.updateView();
                } else {
                    final long sleepTime = (long) (SLEEP_FACTOR * (nextTime - currTime));
                    if (sleepTime > 0) {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            // do nothing
                        }
                    }
                }
                final int thisSecond = (int) (nextTime / TIME_FACTOR);
                if (thisSecond > lastSecondTime) {
                    //System.out.println(lastSecondTime);
                    lastSecondTime = thisSecond;
                    this.updateTime();
                    this.updateEnemies();
                }
            } else {
                this.wasPaused = true;
            }
            this.updateGameState();
        }
    }

    @Override
    public void stopLoop() {
        this.running = false;
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    /**
     * @return true if the game is paused, otherwise false.
     */
    protected boolean isPaused() {
        return this.paused;
    }

    @Override
    public void unPause() {
        if (this.isPaused()) {
            this.paused = false;
        }
    }

    @Override
    public void pause() {
        if (this.isRunning()) {
            this.paused = true;
        }
    }

    /**
     * This method took a long time and an action type runnable,
     * creates a new thread to control the time.
     * @param delay is the time after which must take some action
     * @param run is the action to take
     */
    protected void doOperationAfterDelay(final long delay, final Runnable action) {
        new Agent(delay, action).start();
    }

    /**
     * This class performs a certain action after a certain time.
     */
    private class Agent extends Thread {

        private final Runnable action;
        private final long delay;
        private long count;
        private volatile boolean stop;

        public Agent(final long delay, final Runnable action) {
            this.action = action;
            this.delay = delay;
            this.count = 0;
            this.stop = false;
        }

        @Override
        public void run() {
            while (!this.stop && running) {
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                            if (!paused) {
                                try {
                                    Agent.sleep(MILLI);
                                } catch (InterruptedException e) {
                                    System.out.println(e);
                                }
                                count += MILLI;
                                if (count >= delay) {
                                    action.run();
                                    stop = true;
                                }
                            }
                        }
                    });
                } catch (InvocationTargetException | InterruptedException e1) {
                    System.out.println(e1);
                }
            }
        }
    }

    /**
     * This method is used to update the position of the Hero.
     */
    public abstract void updateModel();

    /**
     * This method is used to update the graphics of game.
     */
    public abstract void updateView();

    /**
     * This method is used to update the state of game.
     */
    public abstract void updateGameState();

    /**
     * This method is used to update the enemies.
     */
    public abstract void updateEnemies();
    
    /**
     * This method is used to update the time of game.
     */
    public abstract void updateTime();
}

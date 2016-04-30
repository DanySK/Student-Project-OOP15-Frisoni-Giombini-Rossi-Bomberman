package controller;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.swing.SwingUtilities;

/**
 *  Implementation of {@link GameLoop}.
 *  A "clock" for the game. 
 *  The GameLoop synchronizes model and view every frame.
 */
public abstract class AbstractGameLoop extends Thread implements GameLoop {

    private static final double TIME_FACTOR = 1000000000.0;
    private static final double SLEEP_FACTOR = 0.0000001;

    private final int gameSpeed;
    private volatile boolean running;
    private volatile boolean paused;
    private volatile boolean wasPaused;
    private Optional<Set<Agent>> threads = Optional.empty();

    /**
     * Constructor for AbstractGameLoop.
     * @param gameSpeed
     *          the speed of the game
     */
    public AbstractGameLoop(final int gameSpeed) {
        this.gameSpeed = gameSpeed;
        this.running = false;
        this.paused = false;
        this.wasPaused = false;
        if (!this.threads.isPresent()) {
            this.threads = Optional.of(new HashSet<>());
        }
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
                            System.err.println(e);
                        }
                    }
                }
                final int thisSecond = (int) (nextTime / TIME_FACTOR);
                if (thisSecond > lastSecondTime) {
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
    public boolean isRunningLoop() {
        return this.running;
    }

    /**
     * @return true if the game is paused, otherwise false.
     */
    protected boolean isPaused() {
        return this.paused;
    }

    @Override
    public void unPauseLoop() {
        if (this.isPaused()) {
            this.paused = false;
        }
    }

    @Override
    public void pauseLoop() {
        if (this.isRunningLoop()) {
            this.paused = true;
        }
    }
    
    /**
     * This method kills all agents.
     */
    protected void stopThreads() {
        this.threads.get().forEach(thread -> thread.kill());
    }

    /**
     * This method took a long time and an action type runnable,
     * creates a new thread to control the time.
     * @param delay 
     *          is the time after which must take some action
     * @param action 
     *          is the action to take
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

        /**
         * Constructor for Agent.
         * @param delay
         *              is the time after which must take some action
         * @param action
         *              is the action to take
         */
        public Agent(final long delay, final Runnable action) {
            this.action = action;
            this.delay = delay;
            this.count = 0;
            this.stop = false;
            threads.get().add(this);
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
                                    Agent.sleep(1);
                                } catch (InterruptedException e) {
                                    System.err.println(e);
                                }
                                count += 1;
                                if (count >= delay) {
                                    action.run();
                                    kill();
                                }
                            }
                        }
                    });
                } catch (InvocationTargetException | InterruptedException e1) {
                    System.err.println(e1);
                }
            }
            threads.get().remove(this);
        }
        
        /**
         * This method kills this agent.
         */
        public void kill() {
            this.stop = true;
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

package controller;


/**
 *  A "clock" for the game. 
 *  The GameLoop synchronizes model and view every frame.
 *
 */
public abstract class AbstractGameLoop extends Thread {

    private final static double TIME_FACTOR = 1000000000.0;
    private final static int MAX_SKIPPED_FRAMES = 5;

    private final int game_speed;
    private boolean running;
    private boolean paused;

    /**
     * Constructor for AbstractGameLoop.
     */
    public AbstractGameLoop(final int game_speed) {
        this.game_speed = game_speed;
        this.running = false;
        this.paused = false;
    }

    /**
     * This method is called as soon as the GameLoop is started.
     */
    public void run() {
        double nextTime = System.nanoTime();
        double lastSecondTime = (int) (nextTime / TIME_FACTOR);
        int skippedFrames = 0;
        this.running = true;

        while(running) {
            if(!this.isPaused()){
                final double currTime = System.nanoTime();
                while(skippedFrames < MAX_SKIPPED_FRAMES && currTime > nextTime) {
                    updateModel();
                    nextTime += TIME_FACTOR / this.game_speed;
                    skippedFrames++;
                }
                skippedFrames = 0;
                final int thisSecond = (int) (nextTime / TIME_FACTOR);
                if (thisSecond > lastSecondTime) {
                    lastSecondTime = thisSecond;
                }
                updateView();
            }
        }
    }

    /**
     * When the Hero dies, the game is stopped.
     */
    public void stopped() {
        this.running = false;
    }

    /**
     * 
     * @return true if the game is running, otherwise false.
     */
    public boolean isRunning() {
        return this.running;
    }

    /**
     * @return true if the game is paused, otherwise false.
     */
    public boolean isPaused() {
        return this.paused;
    }

    /**
     * Change the state of game in stop pause
     */
    public void stopPause() {
        if (this.isRunning()) {
            this.paused = false;
        }
    }

    /**
     * Change the state of game in pause
     */
    public void pause() {
        if (this.isRunning()) {
            this.paused = true;
        }
    }

    public abstract void updateModel();

    public abstract void updateView();
}

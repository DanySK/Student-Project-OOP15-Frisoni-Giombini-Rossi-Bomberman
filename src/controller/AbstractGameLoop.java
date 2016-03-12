package controller;

/**
 *  Implementation of {@link GameLoop}.
 *  A "clock" for the game. 
 *  The GameLoop synchronizes model and view every frame.
 *
 */
public abstract class AbstractGameLoop extends Thread implements GameLoop {

    private final static double TIME_FACTOR = 1000000000.0;
    private final static double SLEEP_FACTOR = 0.0000001;

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
        this.running = true;

        while(this.running) {
            if(!this.paused){
                final double currTime = System.nanoTime();
                if(currTime >= nextTime) {
                    nextTime += TIME_FACTOR / this.game_speed;
                    updateModel();
                    updateView();
                }
                else{
                    long sleepTime = (long)(SLEEP_FACTOR * (nextTime - currTime));
                    if(sleepTime > 0) {
                        try {
                            Thread.sleep(sleepTime);
                        }
                        catch(InterruptedException e) {
                            // do nothing
                        }
                    }
                }
            }
        }
    }

    @Override
    public void stopped() {
        this.running = false;
    }

    /**
     * @return true if the game is running, otherwise false.
     */
    private boolean isRunning() {
        return this.running;
    }

    /**
     * @return true if the game is paused, otherwise false.
     */
    private boolean isPaused() {
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
     * This method is used to update the position of the Hero.
     */
    public abstract void updateModel();

    /**
     * This method is used to update the graphics of the game.
     */
    public abstract void updateView();
}
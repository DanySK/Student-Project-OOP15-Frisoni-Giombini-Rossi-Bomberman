package controller;

public interface GameLoop {
    
    /**
     * @return true if the game is running, false.
     */
    boolean isRunning();
    
    /**
     * Change the state of game in pause.
     */
    void pause();
    
    /**
     * Change the state of game in stop pause.
     */
    void unPause();
    
    /**
     * Stops the game.
     */
    void stopLoop();
    
}

package controller;

public interface GameLoop {
    
    /**
     * When the Hero dies, the game is stopped.
     */
    void stopped();
    
    /**
     * Change the state of game in stop pause.
     */
    void unPause();
    
    /**
     * Change the state of game in pause.
     */
    void pause();
    
}

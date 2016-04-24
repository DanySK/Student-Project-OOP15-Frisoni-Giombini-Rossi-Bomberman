package view.game;

import java.awt.event.KeyListener;
import java.util.Set;

import controller.GameController;
import controller.GameLoop;
import model.Tile;
import view.game.DrawableFrame.GameMessage;
import view.game.GameOverPanel.GameOverObserver;

/**
 * This interface models the visual representation of the game.
 * The controller can call only the functions of this interface.
 * 
 */
public interface GameFrame {
    
    /**
     * Sets an observer of the game frame.
     * 
     * @param observer
     *          the controller to attach
     */
    void setObserver(GameController observer);
    
    /**
     * Sets the game loop to be controlled.
     * 
     * @param gameLoop
     *          the loop that handles the game
     */
    void setGameLoop(GameLoop gameLoop);
    
    /**
     * Adds a new KeyListener to the view.
     * 
     * @param listener
     *          the keyListener to add
     */
    void setKeyListener(KeyListener listener);
    
    /**
     * This method is called before the UI is used.
     * It creates and initializes the view.
     */
    void initView();
    
    /**
     * @return the size of a tile.
     */
    int getTileSize();
    
    /**
     * @return the duration of an explosion's animation.
     */
    long getExplosionDuration();
    
    /**
     * Shows the user interface on the screen.
     */
    void showView();

    /**
     * Updates the view and repaints the game panel.
     */
    void update();
    
    /**
     * Updates the view for a new stage.
     */
    void updateStage();
    
    /**
     * Updates the rendering of the elapsed time.
     * 
     * @param seconds
     *          the number of seconds since the game's start
     */
    void updateTime(long seconds);
    
    /**
     * Renders an explosion on the screen.
     * 
     * @param tiles
     *          the tiles involved in the explosion
     */
    void renderExplosion(Set<Tile> tiles);
    
    /**
     * Stops the oldest explosion rendering.
     */
    void removeExplosion();
    
    /**
     * Shows a game message.
     * 
     * @param gameMessage
     *          the game message to render
     */
    void showMessage(GameMessage gameMessage);
    
    /**
     * Removes the pause message.
     */
    void removeMessage();
    
    /**
     * Shows a panel associated to the end of the game.
     * 
     * @param score
     *          the score obtained by the player
     * @param time
     *          the time reached by the player
     * @param isRecord
     *          true if the score represents a new record, false otherwise
     * @param observer
     *          the observer of the GameOver panel
     */
    void showGameOverPanel(int score, int time, boolean isRecord, GameOverObserver observer);
    
    /**
     * Closes the game frame.
     */
    void closeView();
}

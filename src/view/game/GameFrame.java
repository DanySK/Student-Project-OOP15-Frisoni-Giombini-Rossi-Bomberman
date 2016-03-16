package view.game;

import java.awt.event.KeyListener;

import controller.GameController;
import controller.GameLoop;

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
     * This method adds a new KeyListener to the view.
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
     * This method shows the user interface on the screen.
     */
    void showView();
    
    /**
     * @return the size of a tile.
     */
    int getTileSize();

    /**
     * This method updates the view and repaints the game panel.
     */
    void update();
}

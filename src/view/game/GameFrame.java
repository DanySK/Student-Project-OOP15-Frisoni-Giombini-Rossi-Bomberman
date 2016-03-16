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
     * This method adds a new KeyListener to the view.
     * 
     * @param listener
     *          the keyListener to add
     */
    void setKeyListener(KeyListener listener);
    
    void setObserver(GameController observer);
    
    void setGameLoop(GameLoop gameLoop);
    
    /**
     * This method is called before the UI is used.
     * It shows the user interface on the screen.
     */
    void initView();
    
    /**
     * @return the size of a tile.
     */
    int getTileSize();

    /**
     * This method updates the view and repaints the game panel.
     */
    void update();
}

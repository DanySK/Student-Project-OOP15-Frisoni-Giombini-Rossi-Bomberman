package view.game;

import java.awt.event.KeyListener;

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
     * This method repaints the game panel.
     */
    void repaintGamePanel();
}

package view.game;

import view.game.DrawableFrameImpl.GameMessage;

/**
 * This interface models the actions that can be performed on
 * a frame that supports the rendering of messages.
 *
 */
public interface DrawableFrame {

    /**
     * Initializes the drawable frame.
     */
    void initDrawable();
    
    /**
     * Draws the specified message.
     * 
     * @param gameMessage
     *          the game message to render
     */
    void drawMessage(GameMessage gameMessage);
    
    /**
     * Clears the message.
     */
    void clearMessage();
}

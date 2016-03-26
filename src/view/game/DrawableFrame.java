package view.game;

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
     * @param msg
     *          the message to display
     */
    void drawMessage(String msg, float opacity);
    
    /**
     * Clears the message.
     */
    void clearMessage();
}

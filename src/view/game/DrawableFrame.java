package view.game;

import view.LanguageHandler;
import view.SoundEffect;

/**
 * This interface models the actions that can be performed on
 * a frame that supports the rendering of messages.
 *
 */
public interface DrawableFrame {

    /**
     * The possible messages that can be displayed.
     */
    enum GameMessage {
        /**
         * The message displayed when the game is paused.
         */
        PAUSE(LanguageHandler.getHandler().getLocaleResource().getString("pause"), 0.7f, SoundEffect.ADVICE),
        /**
         * The message displayed when the game frame has not focus.
         */
        FOCUS(LanguageHandler.getHandler().getLocaleResource().getString("focusWarning"), 0.7f, SoundEffect.ADVICE),
        /**
         * The message displayed when a stage is completed.
         */
        STAGE(LanguageHandler.getHandler().getLocaleResource().getString("stageClear"), 1.0f, SoundEffect.NEXT_LEVEL);

        private final String message;
        private final float opacity;
        private final SoundEffect sound;

        /**
         * Creates a new game message.
         * 
         * @param message
         *      the message to display
         * @param opacity
         *      the background opacity
         * @param sound
         *      the sound to play
         */
        GameMessage(final String message, final float opacity, final SoundEffect sound) {
            this.message = message;
            this.opacity = opacity;
            this.sound = sound;
        }

        /**
         * @return the message.
         */
        public String getMessage() {
            return this.message;
        }

        /**
         * @return the background opacity.
         */
        public float getOpacity() {
            return this.opacity;
        }

        /**
         * @return the sound to reproduce at the notification.
         */
        public SoundEffect getSound() {
            return this.sound;
        }
    }

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

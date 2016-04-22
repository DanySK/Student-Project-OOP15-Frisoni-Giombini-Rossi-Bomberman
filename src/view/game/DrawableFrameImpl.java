package view.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import view.GUIFactory;
import view.LanguageHandler;
import view.SoundEffect;

/**
 * This class is an implementation of {@link DrawableFrame}.
 * It handles a custom {@link JFrame} that support the displaying of messages and their cleaning.
 * It uses Graphics2D to work.
 *
 */
public class DrawableFrameImpl extends JFrame implements DrawableFrame {

    /**
     * Auto-generated UID.
     */
    private static final long serialVersionUID = -4905480110288926483L;

    /**
     * Enumeration for all the game messages.
     *
     */
    public enum GameMessage {
        
        /**
         * The message displayed when the game is in pause.
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
         * @return the sound to play at the notification.
         */
        public SoundEffect getSound() {
            return this.sound;
        }
    }

    private final JPanel overlayPanel;
    
    /**
     * Constructs a new DrawableFrameImpl.
     */
    public DrawableFrameImpl() {
        this.overlayPanel = new JPanel();
        this.overlayPanel.setOpaque(false);
        this.setGlassPane(this.overlayPanel);
    }

    @Override
    public void initDrawable() {
        this.overlayPanel.setPreferredSize(this.getPreferredSize());
    }

    @Override
    public void drawMessage(final GameMessage gameMessage) {
        if (gameMessage.getOpacity() < 0 || gameMessage.getOpacity() > 1) {
            throw new IllegalArgumentException("Opacity parameter outside of expected range");
        }

        this.overlayPanel.setVisible(true);
        final Dimension d = this.overlayPanel.getPreferredSize();
        final BufferedImage image = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);

        final Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(0.0f, 0.0f, 0.0f, gameMessage.getOpacity()));
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.WHITE);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

        final Font font = new GUIFactory.Standard().getFullFrameFont();
        final FontRenderContext frc = new FontRenderContext(null, true, true);
        final Rectangle2D r = font.getStringBounds(gameMessage.getMessage(), frc);

        final int rWidth = (int) Math.round(r.getWidth());
        final int rHeight = (int) Math.round(r.getHeight());
        final int rX = (int) Math.round(r.getX());
        final int rY = (int) Math.round(r.getY());
        final int x = d.width / 2 - rWidth / 2 - rX;
        final int y = d.height / 2 - rHeight / 2 - rY;

        g.setFont(font);
        g.drawString(gameMessage.getMessage(), x, y);
        g.dispose();
        if (this.overlayPanel.getGraphics() != null) {
            this.overlayPanel.repaint();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    DrawableFrameImpl.this.overlayPanel.getGraphics().drawImage(image, 0, 0, null);
                    gameMessage.getSound().playOnce();
                }
            });
        }
    }

    @Override
    public void clearMessage() {
        this.overlayPanel.repaint();
        this.overlayPanel.setVisible(false);
    }
}

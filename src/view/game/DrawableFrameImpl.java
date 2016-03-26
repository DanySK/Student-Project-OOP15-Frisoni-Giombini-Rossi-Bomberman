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
    public void drawMessage(final String msg, final float opacity) {
        this.overlayPanel.setVisible(true);
        final Dimension d = this.overlayPanel.getPreferredSize();
        final BufferedImage image = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);

        final Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(0.0f, 0.0f, 0.0f, opacity));
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.WHITE);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

        final Font font = new GUIFactory.Standard().getFullFrameFont();
        final FontRenderContext frc = new FontRenderContext(null, true, true);
        final Rectangle2D r = font.getStringBounds(msg, frc);

        final int rWidth = (int) Math.round(r.getWidth());
        final int rHeight = (int) Math.round(r.getHeight());
        final int rX = (int) Math.round(r.getX());
        final int rY = (int) Math.round(r.getY());
        final int x = d.width / 2 - rWidth / 2 - rX;
        final int y = d.height / 2 - rHeight / 2 - rY;

        g.setFont(font);
        g.drawString(msg, x, y);
        g.dispose();
        if (this.overlayPanel.getGraphics() != null) {
            this.overlayPanel.repaint();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    DrawableFrameImpl.this.overlayPanel.getGraphics().drawImage(image, 0, 0, null);
                    SoundEffect.ADVICE.playOnce();
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

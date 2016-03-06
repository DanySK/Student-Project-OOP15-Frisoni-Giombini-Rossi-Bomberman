package view.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import view.GUIFactory;
import view.LanguageHandler;

/**
 * This class generates a {@link BufferedImage} to display at the end of the game.
 * 
 */
public class GameOverImage implements Runnable {
    
    private static final float BG_OPACITY = 0.7f;
    
    private BufferedImage image;
 
    private final JPanel panel;
    
    /**
     * Creates a new GameOverImage.
     * 
     * @param panel
     *          the panel on which draw, with the size to use for the image
     */
    public GameOverImage(final JPanel panel) {
        this.panel = panel;
    }
 
    @Override
    public void run() {
        final String title = LanguageHandler.getHandler().getLocaleResource().getString("gameOver");
        final Dimension d = panel.getPreferredSize();
        image = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
        
        final Graphics2D g = image.createGraphics();
        g.setColor(new Color(0.0f, 0.0f, 0.0f, BG_OPACITY));
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.WHITE);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        
        final Font font = new GUIFactory.Standard().getFullFrameFont();
        final FontRenderContext frc = new FontRenderContext(null, true, true);
        final Rectangle2D r = font.getStringBounds(title, frc);
        
        final int rWidth = (int) Math.round(r.getWidth());
        final int rHeight = (int) Math.round(r.getHeight());
        final int rX = (int) Math.round(r.getX());
        final int rY = (int) Math.round(r.getY());
         
        final int x = d.width / 2 - rWidth / 2 - rX;
        final int y = d.height / 2 - rHeight / 2 - rY;
         
        g.setFont(font);
        g.drawString(title, x, y);
        g.dispose();
    }
 
    /**
     * @return the buffered image to display at the end of the game.
     */
    public BufferedImage getImage() {
        return this.image;
    }
}

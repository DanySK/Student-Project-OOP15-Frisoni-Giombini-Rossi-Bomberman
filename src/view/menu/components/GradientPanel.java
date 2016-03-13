package view.menu.components;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * A custom JPanel with a gradient background.
 *
 */
public class GradientPanel extends JPanel {

    /**
     * Auto-generated UID.
     */
    private static final long serialVersionUID = 307088997388816338L;

    // Starting Gradient Color.
    private final Color startColor;

    // Ending Gradient Color.
    private final Color endColor;

    /**
     * Constructor supplying a color.
     * 
     * @param startColor
     *          the first color of the gradient
     * @param endColor
     *          the second color of the gradient
     */
    public GradientPanel(final Color startColor, final Color endColor) {
        super();
        this.startColor = startColor;
        this.endColor = endColor;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        final int w = getWidth();
        final int h = getHeight();
        final GradientPaint gp = new GradientPaint(0, 0, startColor, 0, h, endColor);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}

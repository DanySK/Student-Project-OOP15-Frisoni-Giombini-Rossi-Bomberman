package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * This class handles the representation of an animated text on the specified
 * {@link Graphics}.
 *
 */
public class TextParticle {

    /*
     * These constants are used to define the shift factor of the text.
     */
    private static final double START_TEXT_HEIGHT = 2;
    private static final double START_X_FACTOR = 0.6;
    private static final double START_Y_FACTOR = 0.4;
    private static final double START_Z_FACTOR = 1.5;
    private static final double MOVEMENT_FACTOR = 0.3;
    private static final double FALL_FACTOR = 0.1;

    private static final int RGB = 256;

    private final Random seed;
    private final String msg;
    private int x, y;
    private double xa, ya, za;
    private double xx, yy, zz;

    /**
     * Creates a new text particle.
     * 
     * @param msg
     *              the message to show
     * @param x
     *              the coordinate on the x-axis
     * @param y
     *              the coordinate on the y-axis
     */
    public TextParticle(final String msg, final int x, final int y) {
        this.seed = new Random();
        this.msg = msg;
        this.x = x;
        this.y = y;

        // Initializes the text position
        this.xx = x;
        this.yy = y;
        this.zz = START_TEXT_HEIGHT;

        // Calculates the movement factor for the random animation
        this.xa = seed.nextGaussian() * START_X_FACTOR;
        this.ya = seed.nextGaussian() * START_Y_FACTOR;
        this.za = seed.nextGaussian() * START_Z_FACTOR;
    }

    /**
     * Updates the coordinates for the representation of the message.
     */
    public void tick() {
        this.xx += this.xa;
        this.yy += this.ya;
        this.zz += this.za;
        if (this.zz < 0) {
            this.zz = 0;
            this.za *= -MOVEMENT_FACTOR;
            this.xa *= MOVEMENT_FACTOR;
            this.ya *= MOVEMENT_FACTOR;
        }
        this.za -= FALL_FACTOR;
        this.x = (int) this.xx;
        this.y = (int) this.yy;
    }

    /**
     * Draws the message on the specified graphic.
     * It uses each time a random color for the rendering.
     * 
     * @param g
     *              the graphic where to draw
     */
    public void render(final Graphics g) {
        g.setColor(new Color(seed.nextInt(RGB), seed.nextInt(RGB), seed.nextInt(RGB)));
        g.setFont(new GUIFactory.Standard().getDescriptionFont());
        g.drawString(this.msg, this.x - this.msg.length(), y - (int) zz);
    }
}

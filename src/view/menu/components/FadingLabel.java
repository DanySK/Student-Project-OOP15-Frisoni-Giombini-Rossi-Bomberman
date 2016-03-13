package view.menu.components;

import java.awt.AlphaComposite;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * This is a custom {@link JLabel} with a Fade-In effect.
 * Idea from:
 * http://stackoverflow.com/questions/24159510/multiple-jlabel-fade-in-effect-one-at-a-time
 *
 */
public class FadingLabel extends JLabel {
    
    /**
     * Auto-generated UID.
     */
    private static final long serialVersionUID = -8470473554286674946L;
    
    private static final int TIME = 1000;
    private static final float MAX_ALPHA = 1f;
    private static final int DELAY = 40;
    
    private final Object fadeLock = new Object();

    private float targetAlpha;
    private float alpha;
    private Timer timer;
    private long startTime;
    private float fromAlpha;

    /**
     * Constructs a new fading label.
     */
    public FadingLabel() {
        init();
    }

    /**
     * Creates a new fading label.
     * 
     * @param text
     *          the text to display in the label
     * @param icon
     *          the icon to use in the label
     * @param horizontalAlignment
     *          the horizontal alignment of the label's content
     */
    public FadingLabel(final String text, final Icon icon, final int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        init();
    }

    /**
     * Creates a new fading label.
     * 
     * @param text
     *          the text to display in the label
     * @param horizontalAlignment
     *          the horizontal alignment of the label's content
     */
    public FadingLabel(final String text, final int horizontalAlignment) {
        super(text, horizontalAlignment);
        init();
    }

    /**
     * Creates a new fading label.
     * 
     * @param text
     *          the text to display in the label
     */
    public FadingLabel(final String text) {
        super(text);
        init();
    }

    /**
     * Creates a new fading label.
     * 
     * @param image
     *          the icon image to use in the label
     * @param horizontalAlignment
     *          the horizontal alignment of the label's content
     */
    public FadingLabel(final Icon image, final int horizontalAlignment) {
        super(image, horizontalAlignment);
        init();
    }

    /**
     * Creates a new fading label.
     * 
     * @param image
     *          the icon image to use in the label
     */
    public FadingLabel(final Icon image) {
        super(image);
        init();
    }

    /**
     * This method initializes the timer to perform the animation.
     */
    protected final void init() {
        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (alpha < MAX_ALPHA) {
                    final long now = System.currentTimeMillis();
                    final long diff = now - startTime;
                    final float progress = (float) diff / (float) TIME;
                    final float distance = targetAlpha - fromAlpha;
                    alpha = (float) (distance * progress);
                    alpha += fromAlpha;
                    if (alpha > MAX_ALPHA) {
                        timer.stop();
                        alpha = MAX_ALPHA;
                    }
                } else {
                    alpha = MAX_ALPHA;
                    timer.stop();
                }
                repaint();
                if (!timer.isRunning()) {
                    synchronized (fadeLock) {
                        fadeLock.notifyAll();
                    }
                }
            }
        });
        timer.setInitialDelay(0);
    }

    /**
     * This method performs the fade effect to the specified alpha target.
     * 
     * @param target
     *          the alpha value to reach
     */
    protected void fadeTo(final float target) {
        final Runnable run = new Runnable() {
            @Override
            public void run() {
                timer.stop();
                fromAlpha = alpha;
                targetAlpha = target;
                if (targetAlpha == alpha) {
                    repaint();
                } else {
                    startTime = System.currentTimeMillis();
                    timer.start();
                }
            }
        };
        if (EventQueue.isDispatchThread()) {
            run.run();
        } else {
            EventQueue.invokeLater(run);
        }
    }

    /**
     * This method performs the fade-in effect.
     */
    public void fadeIn() {
        fadeTo(MAX_ALPHA);
    }

    /**
     * This method performs the fade-out effect.
     */
    public void fadeOut() {
        fadeTo(0f);
    }
    
    /**
     * Resets the fading effect.
     */
    public void resetFading() {
        this.timer.stop();
        this.alpha = 0f;
    }

    /**
     * This method suspends the fading animation of the label.
     */
    public void waitFor() {
        if (EventQueue.isDispatchThread()) {
            throw new IllegalStateException("Calling waitFor while within the EDT!");
        }
        synchronized (fadeLock) {
            try {
                fadeLock.wait();
            } catch (InterruptedException ex) {
            }
        }
    }

    @Override
    public void paint(final Graphics g) {
        final Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));
        super.paint(g2d);
        g2d.dispose();
    }
}

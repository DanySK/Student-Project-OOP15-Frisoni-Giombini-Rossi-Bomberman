package view.game;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.plaf.LayerUI;

import model.level.Level;
import view.ImageLoader;
import view.ImageLoader.GameImage;

/**
 * A view for the rendering of the main game screen.
 *
 */
public class GameFrameImpl implements GameFrame {

    private static final String FRAME_NAME = "Game";

    private JFrame frame;

    private final Level model;

    private StatisticPanel statisticPanel;
    private GamePanel gamePanel;
    private LoggerPanel loggerPanel;
    
    private final boolean darkMode;
    private SpotlightLayerUI layerUI;
    private JLayer<?> jlayer;

    /**
     * Creates a new frame for the game rendering.
     * @param model
     *          the level data
     * @param darkMode
     *          true if the dark mode is active, false otherwise
     */
    public GameFrameImpl(final Level model, final boolean darkMode) {
        this.model = Objects.requireNonNull(model);
        this.darkMode = darkMode;
        createView();
    }

    private void createView() {
        // Sets panels
        this.gamePanel = new GamePanel(this.model);
        this.statisticPanel = new StatisticPanel(this.model);
        this.loggerPanel = new LoggerPanel(this.model);

        // Sets the frame
        this.frame = new JFrame();
        this.frame.setTitle(FRAME_NAME);
        this.frame.setIconImage(ImageLoader.getLoader().createImage(GameImage.ICON));
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent event) {
                exitProcedure();
            }
        });

        // Sets the layout
        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(this.statisticPanel.getPanel(), BorderLayout.NORTH);
        mainPanel.add(this.loggerPanel.getPanel(), BorderLayout.SOUTH);
        if (this.darkMode) {
            this.layerUI = new SpotlightLayerUI(this.gamePanel.getTileSize());
            this.jlayer = new JLayer<JPanel>(this.gamePanel, layerUI);
            mainPanel.add(this.jlayer, BorderLayout.CENTER);
        } else {
            mainPanel.add(this.gamePanel, BorderLayout.CENTER);
        }

        this.frame.add(mainPanel);
        this.frame.setLocationByPlatform(true);
        this.frame.pack();
        this.frame.setFocusable(true);
    }

    /**
     * Custom exit procedure to save the score before closing.
     */
    public void exitProcedure() {
        // model.setHighScores();
        // highScoreProperties.saveProperties();
        this.frame.dispose();
        // System.exit(0);
    }

    @Override
    public void initView() {
        this.gamePanel.initGamePanel();
        repaintGamePanel();
        this.frame.setVisible(true);
    }

    @Override
    public int getTileSize() {
        return this.gamePanel.getTileSize();
    }
    
    @Override
    public void repaintGamePanel() {
        this.gamePanel.repaint();
        if (this.darkMode) {
            this.layerUI.moveLight(this.gamePanel.getHeroViewCenterPoint(), this.jlayer);
        }
    }
    
    @Override
    public void setKeyListener(final KeyListener listener) {
        this.frame.addKeyListener(Objects.requireNonNull(listener));
    }


    /**
     * This class overlays a radial gradient (for a spotlight effect) to a panel.
     * It is used to implement a dark mode for the game.
     * Idea by:
     * http://docs.oracle.com/javase/tutorial/uiswing/misc/jlayer.html
     *
     */
    class SpotlightLayerUI extends LayerUI<JPanel> {
        
        /**
         * Auto-generated UID.
         */
        private static final long serialVersionUID = 2444824246299506113L;
        
        private static final float RADIUS_FACTOR = 1.7f;
        private static final float ALFA = 1f;

        private final float radius;
        private int mX, mY;
        
        /**
         * Constructs a new spotlight layer.
         * 
         * @param tileSize
         *      the size of a tile map, used for set the spotlight dimension.
         */
        SpotlightLayerUI(final int tileSize) {
            this.radius = tileSize * RADIUS_FACTOR;
        }

        @Override
        public void paint(final Graphics g, final JComponent c) {
            final Graphics2D g2 = (Graphics2D) g.create();

            // Paint the view.
            super.paint(g2, c);

            // Create a radial gradient, transparent in the middle.
            final Point2D center = new Point2D.Float(mX, mY);
            final float[] dist = {0.0f, 1.0f};
            final Color[] colors = {new Color(0.0f, 0.0f, 0.0f, 0.0f), Color.BLACK};
            final RadialGradientPaint p =
                    new RadialGradientPaint(center, this.radius, dist, colors);
            g2.setPaint(p);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ALFA));
            g2.fillRect(0, 0, c.getWidth(), c.getHeight());
            g2.dispose();
        }

        /**
         * Moves the light in the specified position.
         * 
         * @param position
         *      the position of the spotlight
         * @param layer
         *      the layer on which to draw
         */
        public void moveLight(final Point point, final JLayer<?> layer) {
            this.mX = point.x;
            this.mY = point.y;
            layer.repaint();
        }
    }
}

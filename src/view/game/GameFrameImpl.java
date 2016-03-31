package view.game;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.util.Objects;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.plaf.LayerUI;

import controller.GameController;
import controller.GameLoop;
import model.Tile;
import view.ImageLoader;
import view.ImageLoader.GameImage;
import view.LanguageHandler;
import view.SoundEffect;
import view.game.GameOverPanel.GameOverObserver;

/**
 * A view for the rendering of the main game screen.
 *
 */
public class GameFrameImpl implements GameFrame {

    private static final String FRAME_NAME = "Game";
    private static final float MESSAGE_OPACITY = 0.7f;
    private static final float NEXT_LEVEL_OPACITY = 1f;

    private DrawableFrameImpl frame;

    private GameController observer;
    private GameLoop gameLoop;

    private StatisticPanel statisticPanel;
    private GamePanel gamePanel;

    private final boolean darkMode;
    private SpotlightLayerUI layerUI;
    private JLayer<?> jlayer;

    /**
     * Creates a new frame for the game rendering.
     * 
     * @param darkMode
     *          true if the dark mode is active, false otherwise
     */
    public GameFrameImpl(final boolean darkMode) {
        this.darkMode = darkMode;
    }

    @Override
    public void initView() {
        // Sets the frame
        this.frame = new DrawableFrameImpl();
        this.frame.setTitle(FRAME_NAME);
        this.frame.setIconImage(ImageLoader.getLoader().createImage(GameImage.ICON));
        this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent event) {
                exitProcedure();
            }
        });
        this.frame.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(final FocusEvent e) {
                if (GameFrameImpl.this.gameLoop.isRunning()) {
                    GameFrameImpl.this.gameLoop.unPause();
                    GameFrameImpl.this.frame.clearMessage();
                }
            }
            @Override
            public void focusLost(final FocusEvent e) {
                if (GameFrameImpl.this.gameLoop.isRunning()) {
                    GameFrameImpl.this.gameLoop.pause();
                    GameFrameImpl.this.frame.drawMessage(LanguageHandler.getHandler().getLocaleResource().getString("focusWarning"),
                            MESSAGE_OPACITY);
                }
            }
        });
        //this.frame.setResizable(false);

        // Sets the panels
        this.gamePanel = new GamePanel(this.observer);
        this.statisticPanel = new StatisticPanel(this.observer);

        // Sets the layout
        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(this.statisticPanel.getPanel(), BorderLayout.NORTH);
        if (this.darkMode) {
            this.layerUI = new SpotlightLayerUI(this.gamePanel.getTileSize());
            this.jlayer = new JLayer<JPanel>(this.gamePanel, layerUI);
            mainPanel.add(this.jlayer, BorderLayout.CENTER);
        } else {
            mainPanel.add(this.gamePanel, BorderLayout.CENTER);
        }

        this.frame.add(mainPanel);
        this.frame.setLocationByPlatform(true);
        this.frame.setFocusable(true);
        this.frame.pack();
    }

    /**
     * Custom exit procedure to save the score before closing.
     */
    private void exitProcedure() {
        this.gameLoop.stopLoop();
        SoundEffect.GAME_THEME.stop();
        this.frame.dispose();
    }

    @Override
    public void setObserver(final GameController observer) {
        this.observer = Objects.requireNonNull(observer);
    }

    @Override
    public void setGameLoop(final GameLoop gameLoop) {
        this.gameLoop = Objects.requireNonNull(gameLoop);
    }

    @Override
    public void setKeyListener(final KeyListener listener) {
        this.frame.addKeyListener(Objects.requireNonNull(listener));
    }

    @Override
    public void showView() {
        this.gamePanel.initGamePanel();
        update();
        this.frame.initDrawable();
        this.frame.setVisible(true);
        if (SoundEffect.isMusicOn()) {
            SoundEffect.GAME_THEME.playLoop();
        }
    }

    @Override
    public int getTileSize() {
        return this.gamePanel.getTileSize();
    }

    @Override
    public long getExplosionDuration() {
        return this.gamePanel.getExplosionDuration();
    }
    
    @Override
    public void update() {
        this.gamePanel.repaint();
        if (this.darkMode) {
            this.layerUI.moveLight(this.gamePanel.getHeroViewCenterPoint(), this.jlayer);
        }
    }
    
    @Override
    public void renderExplosions(final Set<Tile> tiles) {
        //this.gamePanel.addExplosions(tiles);
        SoundEffect.EXPLOSION.playOnce();
    }

    @Override
    public void removeExplosion() {
        //this.gamePanel.removeExpolosions();
    }

    @Override
    public void showPauseMessage() {
        this.frame.drawMessage(LanguageHandler.getHandler().getLocaleResource().getString("pause"),
                MESSAGE_OPACITY);
    }

    //TODO: change to removeMessage() in future
    @Override
    public void removePauseMessage() {
        this.frame.clearMessage();
    }

    @Override
    public void showNextLevelMessage() {
        this.frame.drawMessage(LanguageHandler.getHandler().getLocaleResource().getString("pause"),
                NEXT_LEVEL_OPACITY);
    }

    @Override
    public void showGameOverPanel(final GameOverObserver observer) {
        final GameOverPanel panel = new GameOverPanel();
        panel.setObserver(Objects.requireNonNull(observer));
        this.frame.getContentPane().removeAll();
        this.frame.getContentPane().invalidate();
        this.frame.getContentPane().add(panel);
        this.frame.getContentPane().revalidate();
    }
    
    @Override
    public void closeView() {
        exitProcedure();
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

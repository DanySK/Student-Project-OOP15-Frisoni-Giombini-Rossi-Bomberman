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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.LayerUI;

import controller.GameController;
import controller.GameLoop;
import model.units.Tile;
import view.ImageLoader;
import view.ImageLoader.GameImage;
import view.LanguageHandler;
import view.SoundEffect;
import view.game.DrawableFrame.GameMessage;
import view.game.GameOverPanel.GameOverObserver;

/**
 * A view for the rendering of the main game screen.
 *
 */
public class GameFrameImpl implements GameFrame {

    private static final String FRAME_NAME = "Game";

    private DrawableFrameImpl frame;
    private boolean initialized;

    private GameController controller;
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
        this.initialized = false;
    }

    @Override
    public void initView() {
        // Sets the frame
        this.frame = new DrawableFrameImpl();
        this.frame.setTitle(FRAME_NAME);
        this.frame.setIconImage(ImageLoader.createImage(GameImage.ICON));
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
                if (GameFrameImpl.this.gameLoop.isRunningLoop()) {
                    GameFrameImpl.this.gameLoop.unPauseLoop();
                    GameFrameImpl.this.frame.clearMessage();
                }
            }
            @Override
            public void focusLost(final FocusEvent e) {
                if (GameFrameImpl.this.gameLoop.isRunningLoop()) {
                    GameFrameImpl.this.gameLoop.pauseLoop();
                    GameFrameImpl.this.frame.drawMessage(GameMessage.FOCUS);
                }
            }
        });
        this.frame.setResizable(false);

        // Sets the panels
        this.gamePanel = new GamePanel(this.controller);
        this.statisticPanel = new StatisticPanel(this.controller);

        // Sets the layout
        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(this.statisticPanel, BorderLayout.NORTH);
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
        this.initialized = true;
    }

    /**
     * Custom exit procedure to execute before the frame's closing.
     */
    private void exitProcedure() {
        if (this.gameLoop.isRunningLoop()) {
            this.gameLoop.pauseLoop();
            if (JOptionPane.showConfirmDialog(frame,
                    LanguageHandler.getHandler().getLocaleResource().getString("exitConfirm"),
                    LanguageHandler.getHandler().getLocaleResource().getString("exit"),
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                this.gameLoop.stopLoop();
                closeView();
            } else {
                this.gameLoop.unPauseLoop();
            }
        } else {
            closeView();
        }
    }

    @Override
    public void setObserver(final GameController observer) {
        this.controller = Objects.requireNonNull(observer);
    }

    @Override
    public void setGameLoop(final GameLoop gameLoop) {
        this.gameLoop = Objects.requireNonNull(gameLoop);
    }

    @Override
    public void setKeyListener(final KeyListener listener) {
        checkInitialization();
        this.frame.addKeyListener(Objects.requireNonNull(listener));
    }

    @Override
    public void showView() {
        checkInitialization();
        update();
        this.frame.initDrawable();
        this.frame.setVisible(true);
        if (SoundEffect.isMusicOn()) {
            SoundEffect.GAME_THEME.playLoop();
        }
    }

    @Override
    public int getTileSize() {
        checkInitialization();
        return this.gamePanel.getTileSize();
    }

    @Override
    public long getExplosionDuration() {
        checkInitialization();
        return this.gamePanel.getExplosionDuration();
    }

    @Override
    public void update() {
        checkInitialization();
        this.gamePanel.repaint();
        this.statisticPanel.updateStats();
        this.statisticPanel.updateScore(this.controller.getHero().getScore());
        if (this.darkMode) {
            this.gamePanel.getHeroViewCenterPoint().ifPresent(p -> this.layerUI.moveLight(p, this.jlayer));
        }
    }

    @Override
    public void updateTime(final long seconds) {
        checkInitialization();
        this.statisticPanel.updateTime(seconds);
    }
    
    @Override
    public void updateStage() {
        checkInitialization();
        this.gamePanel.initialize();
    }
    
    @Override
    public void renderExplosion(final Set<Tile> tiles) {
        checkInitialization();
        this.gamePanel.addExplosion(tiles);
    }

    @Override
    public void removeExplosion() {
        checkInitialization();
        this.gamePanel.removeExplosion();
    }
    
    @Override
    public void showMessage(final GameMessage gameMessage) {
        checkInitialization();
        this.frame.drawMessage(gameMessage);
    }
    
    @Override
    public void removeMessage() {
        checkInitialization();
        this.frame.clearMessage();
    }

    @Override
    public void showGameOverPanel(final int score, final int time, final boolean isRecord, final GameOverObserver observer) {
        checkInitialization();
        final GameOverPanel panel = new GameOverPanel(score, time, isRecord);
        panel.setObserver(Objects.requireNonNull(observer));
        this.frame.getContentPane().removeAll();
        this.frame.getContentPane().invalidate();
        this.frame.getContentPane().add(panel);
        this.frame.getContentPane().revalidate();
        SoundEffect.DEAD.playOnce();
    }

    @Override
    public void closeView() {
        checkInitialization();
        SoundEffect.GAME_THEME.stop();
        this.frame.dispose();
    }
    
    /*
     * Throws a {link IllegalStateException} when a method is called without first initializing the frame.
     */
    private void checkInitialization() {
        if (!this.initialized) {
            throw new IllegalStateException("Game frame not initialized");
        }
    }

    /**
     * This class overlays a radial gradient (for a spotlight effect) to a panel.
     * It is used to implement the dark mode for the game.
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
         *      the size of a tile map, used for set the spotlight dimension
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

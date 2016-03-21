package view.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.Timer;

import controller.GameController;
import model.TileType;
import model.units.Bomb;
import model.units.PowerUpType;
import model.utilities.PowerUp;
import view.ImageLoader;
import view.ImageLoader.GameImage;
import view.animations.BombView;
import view.animations.BombViewImpl;
import view.animations.HeroView;
import view.animations.HeroViewImpl;

/**
 * A {@link JPanel} for the principal game's rendering.
 * It draws the BoardMap and all the entities of the game.
 *
 */
public class GamePanel extends JPanel implements ActionListener {

    /**
     * Auto-generated UID.
     */
    private static final long serialVersionUID = -6689261673710076779L;

    private static final double SCALE = 0.6;

    // The delay used for updating animations
    private static final int DELAY = 10;

    private final GameController controller;

    private final GameOverImage gameOverImage;

    private final int tileSize;
    private final Map<TileType, Image> tilesImages;
    private final Map<PowerUpType, Image> powerUpImages;
    private HeroView hero;

    /**
     * Creates a new GamePanel.
     * 
     * @param controller
     *          the controller of the game
     */
    public GamePanel(final GameController controller) {

        this.controller = controller;

        /*
         * Calculates the panel size according to the screen resolution
         * and the map's side (number of tiles in height/width).
         */
        final int nTiles = this.controller.getLevelSize();
        this.tileSize = calculateTileSize(SCALE, nTiles);
        this.setPreferredSize(new Dimension(nTiles * this.tileSize, nTiles * this.tileSize)); 

        /*
         * EnumMap for associating the tiles' types with images.
         * At the inclusion, it scales the images (one time).
         * Effectively, it is inefficient to load an image and scale it every time the component is asked to render itself.
         * So this is the best way to proceed. 
         */
        tilesImages = new EnumMap<>(TileType.class);
        tilesImages.put(TileType.WALKABLE, ImageLoader.getLoader().createImageOfSize(GameImage.WALKABLE, this.tileSize, this.tileSize));
        tilesImages.put(TileType.RUBBLE, ImageLoader.getLoader().createImageOfSize(GameImage.RUBBLE, this.tileSize, this.tileSize));
        tilesImages.put(TileType.CONCRETE, ImageLoader.getLoader().createImageOfSize(GameImage.CONCRETE, this.tileSize, this.tileSize));
        tilesImages.put(TileType.DOOR_OPENED, ImageLoader.getLoader().createImageOfSize(GameImage.DOOR_OPENED, this.tileSize, this.tileSize));
        tilesImages.put(TileType.DOOR_CLOSED, ImageLoader.getLoader().createImageOfSize(GameImage.DOOR_CLOSED, this.tileSize, this.tileSize));

        /*
         * EnumMap for associating the power-ups' types with images.
         */
        powerUpImages = new EnumMap<>(PowerUpType.class);
        powerUpImages.put(PowerUpType.ATTACK, ImageLoader.getLoader().createImageOfSize(GameImage.ATTACK_UP, this.tileSize, this.tileSize));
        powerUpImages.put(PowerUpType.LIFE, ImageLoader.getLoader().createImageOfSize(GameImage.LIFE_UP, this.tileSize, this.tileSize));
        powerUpImages.put(PowerUpType.BOMB, ImageLoader.getLoader().createImageOfSize(GameImage.BOMBS_UP, this.tileSize, this.tileSize));
        powerUpImages.put(PowerUpType.RANGE, ImageLoader.getLoader().createImageOfSize(GameImage.RANGE_UP, this.tileSize, this.tileSize));
        powerUpImages.put(PowerUpType.HURT, ImageLoader.getLoader().createImageOfSize(GameImage.LIFE_DOWN, this.tileSize, this.tileSize));
        powerUpImages.put(PowerUpType.MYSTERY, ImageLoader.getLoader().createImageOfSize(GameImage.MYSTERY, this.tileSize, this.tileSize));
        powerUpImages.put(PowerUpType.KEY, ImageLoader.getLoader().createImageOfSize(GameImage.KEY, this.tileSize, this.tileSize));

        this.gameOverImage = new GameOverImage(this);
        this.gameOverImage.run();
    }

    /**
     * Initializes the game panel.
     */
    public void initGamePanel() {
        this.hero = new HeroViewImpl(this.controller.getHero(), this.tileSize);
        final Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    /**
     * Draws all graphical components.
     */
    @Override
    public void paintComponent(final Graphics g) {
        // Draw the power-ups
        for (final PowerUp p : this.controller.getPowerUpInLevel()) {
            g.drawImage(this.powerUpImages.get(p.getType()), p.getX() * this.tileSize, p.getY() * this.tileSize, this);
        }
        // Draw the map
        final TileType[][] map = this.controller.getMap();
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                g.drawImage(this.tilesImages.get(map[x][y]), x * this.tileSize, y * this.tileSize, this);
            }
        }
        // Draw the bombs
        for (final Bomb b : this.controller.getPlantedBombs()) {
            final BombView bv = new BombViewImpl(b, this.tileSize);
            g.drawImage(bv.getImage(), bv.getX(), bv.getY(), null);
        }
        // Draw the hero
        g.drawImage(this.hero.getImage(), this.hero.getX(), this.hero.getY(), null);
        // Draw the GameOver screen
        if (this.controller.isGameOver()) {
            g.drawImage(this.gameOverImage.getImage(), 0, 0, null);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * @return the center point of the sprite associated to the hero.
     */
    public Point getHeroViewCenterPoint() {
        return this.hero.getCenterPoint();
    }

    /**
     * @return the size of a tile.
     */
    public int getTileSize() {
        return this.tileSize;
    }

    /**
     * Calculates the perfect size of a tile by desktop resolution.
     * 
     * @param scale
     *          the scale to apply to screen's dimension
     * @param nTiles
     *          the number of tiles in height/width to render in the frame
     * @return the size of a single tile
     */
    private static int calculateTileSize(final double scale, final int nTiles) {
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int height = (int) screen.getHeight();
        return Math.toIntExact(Math.round((height * scale) / nTiles));
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        updateSprite();
    }

    private void updateSprite() {
        this.hero.updateFrame();
    }
}

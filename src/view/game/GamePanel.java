package view.game;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.GameController;
import model.units.PowerUpType;
import model.units.Tile;
import model.units.TileType;
import view.ImageLoader;
import view.ImageLoader.GameImage;
import view.SoundEffect;
import view.TextParticle;
import view.animations.BombView;
import view.animations.ExplosionView;
import view.animations.HeroView;
import view.animations.HeroViewImpl;
import view.animations.factory.EnemyViewFactory;
import view.animations.unit.AbstractEnemyView;

/**
 * A {@link JPanel} for the principal game's rendering.
 * It draws the board map and all the elements of the game.
 *
 */
public class GamePanel extends JPanel {

    /**
     * Auto-generated UID.
     */
    private static final long serialVersionUID = -6689261673710076779L;

    private static final double SCALE = 0.6;
    private static final long EXPLOSION_DURATION = 300L;

    private final GameController controller;

    private int tileSize;
    private final Map<TileType, Image> tilesImages;
    private final Map<PowerUpType, Image> powerUpImages;

    private Optional<HeroView> hero;
    private final Set<BombView> bombs;
    private final Deque<Set<ExplosionView>> explosions;
    private final Set<AbstractEnemyView> enemies;
    private final Set<TextParticle> scores;
    
    private final EnemyViewFactory enemyFactory;

    /**
     * Creates a new GamePanel.
     * 
     * @param controller
     *          the controller of the game
     */
    public GamePanel(final GameController controller) {
        this.controller = controller;
        
        this.tilesImages = new HashMap<>();
        this.powerUpImages = new EnumMap<>(PowerUpType.class);
        this.hero = Optional.empty();
        this.bombs = new HashSet<>();
        this.explosions = new LinkedList<>();
        this.enemies = new HashSet<>();
        this.scores = new HashSet<>();
        this.enemyFactory = new EnemyViewFactory();
        initialize();
    }
    
    /**
     * Initializes the game panel.
     * Loads the resources according to the level's size and clears the collections
     * for the view-animations.
     */
    public final void initialize() {
        /*
         * Calculates the tile size according to the screen resolution
         * and the map's side (number of tiles in height/width).
         */
        this.tileSize = calculateTileSize(SCALE, this.controller.getLevelSize());

        /*
         * EnumMap for associating the tiles' types with images.
         * At the inclusion, it scales the images (one time).
         * Effectively, it is inefficient to load an image and scale it every time the component is asked to render itself.
         * So this is the best way to proceed.
         */
        tilesImages.put(TileType.WALKABLE, ImageLoader.createImageOfSize(GameImage.WALKABLE, this.tileSize, this.tileSize));
        tilesImages.put(TileType.RUBBLE, ImageLoader.createImageOfSize(GameImage.RUBBLE, this.tileSize, this.tileSize));
        tilesImages.put(TileType.CONCRETE, ImageLoader.createImageOfSize(GameImage.CONCRETE, this.tileSize, this.tileSize));
        tilesImages.put(TileType.DOOR_OPENED, ImageLoader.createImageOfSize(GameImage.DOOR_OPENED, this.tileSize, this.tileSize));
        tilesImages.put(TileType.DOOR_CLOSED, ImageLoader.createImageOfSize(GameImage.DOOR_CLOSED, this.tileSize, this.tileSize));

        /*
         * EnumMap for associating the power-ups' types with images.
         * It uses the same logic adopted for tiles' types rendering.
         */
        powerUpImages.put(PowerUpType.ATTACK, ImageLoader.createImageOfSize(GameImage.ATTACK_UP, this.tileSize, this.tileSize));
        powerUpImages.put(PowerUpType.LIFE, ImageLoader.createImageOfSize(GameImage.LIFE_UP, this.tileSize, this.tileSize));
        powerUpImages.put(PowerUpType.BOMB, ImageLoader.createImageOfSize(GameImage.BOMBS_UP, this.tileSize, this.tileSize));
        powerUpImages.put(PowerUpType.RANGE, ImageLoader.createImageOfSize(GameImage.RANGE_UP, this.tileSize, this.tileSize));
        powerUpImages.put(PowerUpType.HURT, ImageLoader.createImageOfSize(GameImage.LIFE_DOWN, this.tileSize, this.tileSize));
        powerUpImages.put(PowerUpType.CONFUSION_ON, ImageLoader.createImageOfSize(GameImage.CONFUSION_ON, this.tileSize, this.tileSize));
        powerUpImages.put(PowerUpType.CONFUSION_OFF, ImageLoader.createImageOfSize(GameImage.CONFUSION_OFF, this.tileSize, this.tileSize));
        powerUpImages.put(PowerUpType.MYSTERY, ImageLoader.createImageOfSize(GameImage.MYSTERY, this.tileSize, this.tileSize));
        powerUpImages.put(PowerUpType.KEY, ImageLoader.createImageOfSize(GameImage.KEY, this.tileSize, this.tileSize));

        this.hero = Optional.empty();
        this.bombs.clear();
        this.explosions.clear();
        this.enemies.clear();
        this.scores.clear();

        /*
         * Sets the preferred size of the panel. 
         */
        this.setPreferredSize(new Dimension(this.controller.getLevelSize() * this.tileSize, this.controller.getLevelSize() * this.tileSize));
        final Container c = this.getTopLevelAncestor();
        if (c instanceof JFrame) {
            final JFrame f = (JFrame) c;
            f.pack();
        }
    }

    /**
     * Draws all graphical components.
     */
    @Override
    public void paintComponent(final Graphics g) {
        // Updates sprites
        updateSprites();
        // Draws the power-ups
        for (final Tile p : this.controller.getPowerUp()) {
            g.drawImage(this.powerUpImages.get(p.getPowerup().get()), p.getX(), p.getY(), this);
        }
        // Draws the map
        for (final Tile p : this.controller.getTiles()) {
            g.drawImage(this.tilesImages.get(p.getType()), p.getX(), p.getY(), this);
        }
        // Draws the explosions
        synchronized (this.explosions) {
            if (!this.explosions.isEmpty()) {
                this.explosions.stream().forEach(s -> s.stream().forEach(e -> {
                    g.drawImage(e.getImage(), e.getX(), e.getY(), null);
                }));
            }
        }
        // Draws the bombs
        this.controller.getPlantedBombs().stream().filter(b -> !this.bombs.contains(b)).forEach(b -> {
            this.bombs.add(new BombView(b, this.controller.getFPS(), this.controller.getBombDelay()));
        });
        this.bombs.removeIf(b -> !this.controller.getPlantedBombs().contains(b.getLevelElement()));
        this.bombs.stream().forEach(b -> g.drawImage(b.getImage(), b.getX(), b.getY(), null));
        // Draws scores
        this.scores.removeIf(s -> s.isTerminated());
        this.scores.stream().forEach(s -> {
            s.tick();
            s.render(g);
        });
        // Draws the enemies
        this.controller.getEnemies().stream().filter(e -> !this.enemies.contains(e)).forEach(e -> {
            this.enemies.add(enemyFactory.createEnemyView(e, this.controller.getFPS()));
        });
        final Iterator<AbstractEnemyView> iterator = this.enemies.iterator();
        while (iterator.hasNext()) {
            final AbstractEnemyView enemy = iterator.next();
            if (!this.controller.getEnemies().contains(enemy.getLevelElement())) {
                this.scores.add(new TextParticle(String.valueOf(enemy.getLevelElement().getScore()),
                        enemy.getX(), enemy.getY(), this.controller.getFPS()));
                SoundEffect.HIT.playOnce();
                iterator.remove();
            }
        }
        this.enemies.stream().forEach(e -> g.drawImage(e.getImage(), e.getX(), e.getY(), null));
        // Draws the hero
        if (this.hero.isPresent()) {
            g.drawImage(this.hero.get().getImage(), this.hero.get().getX(), this.hero.get().getY(), null);
        } else {
            this.hero = Optional.of(new HeroViewImpl(this.controller.getHero(), this.controller.getFPS()));
        }
        // Ensures the synchronization of animations
        Toolkit.getDefaultToolkit().sync();
    }

    private void updateSprites() {
        this.hero.ifPresent(h -> h.updateFrame());
        this.bombs.stream().forEach(b -> b.updateFrame());
        synchronized (this.explosions) {
            this.explosions.stream().forEach(s -> s.forEach(e -> e.updateFrame()));
        }
        this.enemies.stream().forEach(e -> e.updateFrame());
    }

    /**
     * Returns the center point of the sprite associated to the hero.
     * @return the point if the hero view is present, an empty value otherwise.
     */
    public Optional<Point> getHeroViewCenterPoint() {
        return this.hero.isPresent() ? Optional.of(this.hero.get().getCenterPoint()) : Optional.empty();
    }

    /**
     * @return the size of a tile.
     */
    public int getTileSize() {
        return this.tileSize;
    }

    /**
     * @return the duration of an explosion's animation.
     */
    public long getExplosionDuration() {
        return EXPLOSION_DURATION;
    }

    /**
     * Adds a set of exploded tiles.
     * 
     * @param tiles
     *          the tiles involved in a bomb's explosion
     */
    public void addExplosion(final Set<Tile> tiles) {
        SoundEffect.EXPLOSION.playOnce();
        synchronized (this.explosions) {
            this.explosions.addLast(tiles.stream()
                           .map(t -> new ExplosionView(t, this.controller.getFPS(), EXPLOSION_DURATION))
                           .collect(Collectors.toSet()));
        }
    }

    /**
     * Removes the oldest set of exploded tiles.
     */
    public void removeExplosion() {
        synchronized (this.explosions) {
            if (!this.explosions.isEmpty()) {
                this.explosions.removeFirst();
            }
        }
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
        if (scale < 0 || nTiles < 0) {
            throw new IllegalArgumentException();
        }
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int height = (int) screen.getHeight();
        return Math.toIntExact(Math.round((height * scale) / nTiles));
    }
}
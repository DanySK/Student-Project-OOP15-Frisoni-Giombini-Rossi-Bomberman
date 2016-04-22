package model.level;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import model.Tile;
import model.TileType;
import model.TilesFactory;
import model.units.Bomb;
import model.units.Direction;
import model.units.Hero;
import model.units.HeroImpl;
import model.units.enemy.Enemy;
import model.units.enemy.EnemyImpl;
import model.units.enemy.EnemyType;
import model.utilities.CopyFactory;
import model.utilities.MapPoint;

/**
 * This class represent the Model, as it contains
 * all game elements.
 *
 */
public class LevelImpl implements Level {

    private static final Point START_HERO_POS = new Point(1, 1);
    private static final int MIN_TILES = 11;
    private static final int MAX_TILES = 19; 
    private static final int ENEMY_FACTOR = 8;

    private Tile[][] map;
    private Hero hero;
    private int tileDimension;
    private int nTiles;
    private int stage;
    private Set<Enemy> enemies;

    /**
     * The constructor is used to set the size of the map,
     * because it's the first thing to do to start the game.
     */
    public LevelImpl() {
        this.setTilesNumber();
    }

    /**
     * This method initialize all the elements inside this map.
     */
    @Override
    public void initLevel(final int tileDimension) {
        this.setTileDimension(tileDimension);
        this.createLevel();
        this.initEnemies();
        this.initHero();
    }
    /**
     * This method initialize correctly the hero.
     */
    private void initHero(){
        if (this.isFirstStage()) {
            this.createHero();            
        } else {
            final int lives = this.hero.getRemainingLives();
            final int attack = this.hero.getAttack();
            final int score = this.hero.getScore();
            this.createHero();
            this.hero.nextLevel(lives, attack, score);
        }
    }

    /**
     * This method creates the hero.
     */
    private void createHero(){
        this.hero = new HeroImpl(MapPoint.getPos(START_HERO_POS, this.tileDimension),
                new Dimension(this.tileDimension, this.tileDimension));
    }

    /**
     * This method initialized enemies.
     */
    private void initEnemies() {
        this.createEnemies();
        if (!this.isFirstStage()) {
            this.enemies.forEach(e -> e.potentiateEnemy());
        }
    }

    /**
     * Creates the enemies.
     */
    private void createEnemies() {
        final Set<Tile> set = this.getFreeTiles();
        this.enemies = Collections.synchronizedSet(new HashSet<>());
        final EnemyType[] vet = EnemyType.values();
        for (int i = 0; i < this.getFreeTiles().size() / ENEMY_FACTOR; i++) {
            final Tile t = set.stream().findAny().get();
            set.remove(t);
            this.enemies.add(new EnemyImpl(t.getPosition(), Direction.DOWN,
                    new Dimension(this.tileDimension, this.tileDimension), 
                    vet[new Random().nextInt(vet.length)]));
        }
    }

    /**
     * This method generates a random level 
     * with the specified size and block density.
     */
    private void createLevel() {
        final TilesFactory factory = new TilesFactory(this.nTiles, this.nTiles);
        this.map = new Tile[this.nTiles][this.nTiles];
        for (int i = 0; i < this.nTiles; i++) {
            for (int j = 0; j < this.nTiles; j++) {
                this.map[i][j] = factory.createForCoordinates(i, j, this.tileDimension);
            }
        }
        this.setDoor(factory);
        this.setKey(factory);
    }

    /**
     * Selects all the walkable tiles and set the door in a random way.
     * 
     * @param factory
     *          the TilesFactory object
     */
    private void setDoor(final TilesFactory factory) {
        factory.setDoor(this.getGenericSet(t -> t.getType().equals(TileType.WALKABLE)));
    }

    /**
     * Sets the key as a tile's powerup.
     * 
     * @param factory
     *          the TilesFactory object
     */
    private void setKey(final TilesFactory factory) {
        factory.setKey(this.getGenericSet(t -> t.getType().equals(TileType.RUBBLE)));
    }

    /**
     * This method allows the hero to move,
     * it checks also collisions with other elements.
     */
    @Override
    public void moveHero(final Direction dir) {
        this.hero.move(this.hero.getCorrectDirection(dir), this.getBlocks(), 
                this.hero.getDetonator().getPlantedBombs().stream().map(b -> b.getHitbox()).collect(Collectors.toSet()), 
                this.getPowerUpForMovement());
    }

    @Override
    public void moveEnemies() {
        synchronized (this.enemies) {
            this.enemies.forEach(e -> e.updateMove(this.getBlocks(), this.hero, e.getRandomDirection(),
                    this.hero.getDetonator().getPlantedBombs().stream().map(b -> b.getHitbox()).collect(Collectors.toSet())));
        }
    }

    @Override
    public void setDirectionEnemies() {
        synchronized (this.enemies) {
            this.enemies.stream().filter(e -> e.getEnemyType().equals(EnemyType.MINVO))
            .forEach(e -> e.setDirection(e.getRandomDirection()));
        }
    }

    /**
     * This method checks if the enemy collides with fire and if it reduces his life.
     * @param tiles involved
     */
    private void checkCollisionWithExplosionBomb(final Set<Tile> tiles) {
        synchronized (this.enemies) {
            final Iterator<Enemy> it = this.enemies.iterator();
            while (it.hasNext()) {
                final Enemy e = it.next();
                if (e.checkFlameCollision(tiles)) {
                    e.modifyLife(-this.hero.getAttack());
                }
                if (e.getRemainingLives() <= 0) {
                    this.getHero().increaseScore(e.getScore());
                    it.remove();
                }
            }
        }
    }

    /**
     * Detonates a bomb.
     * 
     * @return the set of afflicted tiles
     */
    @Override
    public Set<Tile> detonateBomb() {
        final Set<Tile> tiles = this.getAllAfflictedTiles(
                CopyFactory.getCopy(this.hero.getDetonator().getBombToReactivate()));
        if (this.hero.checkFlameCollision(tiles)) {
            this.hero.modifyLife(-this.hero.getAttack());
        }
        this.checkCollisionWithExplosionBomb(tiles);
        this.hero.getDetonator().reactivateBomb();
        return tiles;
    }

    /**
     * This method returns a set of all afflicted tiles.
     * 
     * @return the entire set of afflicted tiles
     */
    private Set<Tile> getAllAfflictedTiles(final Bomb b) {
        final Set<Tile> afflictedTiles = new HashSet<>();
        afflictedTiles.addAll(this.getAfflictedTiles(Direction.UP, b, 
                MapPoint.getInvCoordinate(b.getX(), this.tileDimension),
                MapPoint.checkBoundaries(MapPoint.getInvCoordinate(b.getY(), this.tileDimension),
                        -b.getRange(), this.nTiles)));
        afflictedTiles.addAll(this.getAfflictedTiles(Direction.RIGHT, b,
                MapPoint.checkBoundaries(MapPoint.getInvCoordinate(b.getX(), this.tileDimension), 
                        +b.getRange(), this.nTiles), 
                MapPoint.getInvCoordinate(b.getY(), this.tileDimension)));
        afflictedTiles.addAll(this.getAfflictedTiles(Direction.DOWN, b, 
                MapPoint.getInvCoordinate(b.getX(), this.tileDimension),
                MapPoint.checkBoundaries(MapPoint.getInvCoordinate(b.getY(), this.tileDimension), 
                        +b.getRange(), this.nTiles)));
        afflictedTiles.addAll(this.getAfflictedTiles(Direction.LEFT, b,
                MapPoint.checkBoundaries(MapPoint.getInvCoordinate(b.getX(), this.tileDimension), 
                        -b.getRange(), this.nTiles), 
                MapPoint.getInvCoordinate(b.getY(), this.tileDimension)));
        return afflictedTiles;
    }

    /**
     * This method return all the afflicted tiles
     * in a specified direction.
     * 
     * @param dir
     *         the direction 
     * @param b
     *         the bomb 
     * @param maxX
     *          the max x coordinate
     * @param maxY
     *          the max y coordinate
     * @return the set of afflicted tiles
     */
    private Set<Tile> getAfflictedTiles(final Direction dir, final Bomb b, final int maxX, final int maxY) {
        final Set<Tile> afflictedTiles = new HashSet<>();
        boolean stop = false;
        for (int i = MapPoint.getInvCoordinate(b.getX(), this.tileDimension); 
                MapPoint.stopCycle(i, maxX, dir) && !stop; i += MapPoint.continueCycle(dir)) {
            for (int j = MapPoint.getInvCoordinate(b.getY(), this.tileDimension); 
                    MapPoint.stopCycle(j, maxY, dir) && !stop; j += MapPoint.continueCycle(dir)) {
                if (this.map[i][j].getType().equals(TileType.CONCRETE)) {
                    stop = true;
                } else {
                    afflictedTiles.add(CopyFactory.getCopy(this.map[i][j]));
                    if (this.map[i][j].getType().equals(TileType.RUBBLE)) {
                        if (this.map[i][j].getPowerup().isPresent()) {
                            this.map[i][j].setType(TileType.POWERUP_STATUS);
                        } else {
                            this.map[i][j].setType(TileType.WALKABLE);
                        }
                        stop = true;
                    }
                }
            }
        }
        return afflictedTiles;
    }

    /**
     * This method return the size of the map.
     * 
     * @return the size of the map
     */
    @Override
    public int getSize() {
        return this.nTiles;
    }

    /**
     * Gets the tiles of the map that aren't in a powerup status.
     * 
     * @return the set of tiles
     */
    @Override
    public Set<Tile> getTiles(){
        return this.getGenericSet(t -> !t.getType().equals(TileType.POWERUP_STATUS))
                .stream().map(t -> CopyFactory.getCopy(t)).collect(Collectors.toSet());
    }

    /**
     * This method build a Set of all Tiles that have a PowerUp.
     * 
     * @return the PowerUp sets
     */
    @Override
    public Set<Tile> getPowerUp() {
        return this.getPowerUpForMovement().stream().map(t -> CopyFactory.getCopy(t)).collect(Collectors.toSet());
    }

    private Set<Tile> getPowerUpForMovement() {
        return this.getGenericSet(t -> t.getType().equals(TileType.POWERUP_STATUS) && t.getPowerup().isPresent());
    }

    /**
     * Gets the tile that represents the door.
     * 
     * @return the tile that represents the door
     */
    @Override
    public Tile getDoor() {
        return CopyFactory.getCopy(this.getDoorToOpen());
    }

    private Tile getDoorToOpen() {
        return this.getGenericSet(t -> t.getType().equals(TileType.DOOR_CLOSED) || t.getType().equals(TileType.DOOR_OPENED))
                .stream().findFirst().get();
    }

    /**
     * This method builds a Set of all indestructible blocks,
     * closed door included.
     * 
     * @return the set of blocks
     */
    private Set<Rectangle> getBlocks() {
        return this.getGenericSet(t -> t.getType().equals(TileType.RUBBLE) || t.getType().equals(TileType.CONCRETE))
                .stream().map(t -> CopyFactory.getCopy(t)).map(t -> t.getHitbox()).collect(Collectors.toSet());
    }

    /**
     * Gets the entire set of tiles that are available
     * to positionate enemies.
     * 
     * @return the set of free tiles
     */
    private Set<Tile> getFreeTiles() {
        return this.getGenericSet(t -> t.getType().equals(TileType.WALKABLE) 
                && !MapPoint.isEntryPoint(MapPoint.getInvCoordinate(t.getX(),tileDimension),
                        MapPoint.getInvCoordinate(t.getY(), tileDimension)))
                .stream().map(t -> CopyFactory.getCopy(t)).collect(Collectors.toSet());
    }

    /**
     * This method allows to get a generic set of elements.
     *  
     * @param func 
     *          the function
     * @return a set of elements
     */
    private Set<Tile> getGenericSet(final Predicate<Tile> pred) {
        final Set<Tile> set = new HashSet<>();
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map.length; j++) {
                if (pred.test(this.map[i][j])) {
                    set.add(this.map[i][j]);
                }
            }
        }
        return set;
    }

    /**
     * This method allows to get the hero.
     */
    @Override
    public Hero getHero() {
        return this.hero;
    }

    /**
     * This method generates a random value to set
     * as the size of the map.
     */
    @Override
    public final void setTilesNumber() {
        int tiles = 0;
        while (tiles % 2 == 0) {
            tiles = new Random().nextInt(MAX_TILES - MIN_TILES) + MIN_TILES;
        }
        this.nTiles = tiles;
    }

    /**
     * This method set the dimension of the tile.
     */
    @Override
    public void setTileDimension(final int dim) {
        this.tileDimension = dim;
    }

    /**
     * Set the open door.
     */
    @Override
    public void setOpenDoor() {
        this.getDoorToOpen().setType(TileType.DOOR_OPENED);
    }

    /**
     * Sets first stage.
     */
    @Override
    public void setFirstStage() {
        this.stage = 0;
    }

    /**
     * Sets next stage.
     */
    @Override
    public void setNextStage() {
        this.stage++;
    }

    /**
     * Checks if its the first stage.
     * 
     * @return true if it's the first stage, false otherwise
     */
    private boolean isFirstStage() {
        return this.stage == 0;
    }

    /**
     * This method check if the game is over.
     * 
     * @return true if the game is over, false otherwise
     */
    @Override
    public boolean isGameOver() {
        return this.hero.isDead();
    }

    @Override
    public Set<Enemy> getEnemies() {
        return Collections.unmodifiableSet(this.enemies);
    }

}
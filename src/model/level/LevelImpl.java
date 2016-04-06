package model.level;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashSet;
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
import model.units.LevelElement;
import model.units.enemy.Enemy;
import model.units.enemy.EnemyImpl;
import model.units.enemy.EnemyType;
import model.utilities.MapPoint;

/**
 * This class represent the Model, as it contains
 * all game elements.
 *
 */
public class LevelImpl implements Level {

    private static final Point START_HERO_POS = new Point(1, 1);
    private static final double BLOCK_DENSITY = 0.5;
    private static final double POWERUP_DENSITY = 0.75;
    private static final int MIN_TILES = 11;
    private static final int MAX_TILES = 19;

    private Tile[][] map;
    private Hero hero;
    private int tileDimension;
    private int nTiles;
    private Set<Enemy> enemies;

    /**
     * The constructor is used to set the size of the map,
     * because it's the first thing to do to start the game.
     */
    public LevelImpl() {
        this.setNumberTiles();
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
        if(this.hero != null && !this.hero.isDead()){
            final int lives = this.hero.getRemainingLives();
            final int attack = this.hero.getAttack();
            final int score = this.hero.getScore();
            this.createHero();
            this.hero.nextLevel(lives, attack, score);            
        } else {
            this.createHero();
        }
    }
    
    /**
     * This method creates the hero.
     */
    private void createHero(){
        this.hero = new HeroImpl(MapPoint.getPos(START_HERO_POS, this.tileDimension), 
                Direction.DOWN, 
                new Dimension(this.tileDimension, this.tileDimension));
    }
    
    private void initEnemies() {
        this.createEnemies();
        if (this.hero != null && !this.hero.isDead()) {
            for (Enemy e : this.enemies) {
                e.potentiateEnemy();
            }
        }
    }

    /**
     * Creates the enemies.
     */
    private void createEnemies() {
        final Set<Tile> set = this.getFreeTiles();
        this.enemies = new HashSet<>();
        final EnemyType[] vet = EnemyType.values();
        for(int i = 0; i < this.getFreeTiles().size()/6; i++) {
            final Tile t = set.stream().findAny().get();
            set.remove(t);
            this.enemies.add(new EnemyImpl(t.getPosition(), Direction.DOWN, 
                new Dimension(this.tileDimension, this.tileDimension), /*EnemyType.BALLOM*/vet[new Random().nextInt(vet.length)]));
        }
    }

    /**
     * This method generates a random level 
     * with the specified size and block density.
     */
    private void createLevel() {
        final TilesFactory factory = new TilesFactory(this.nTiles, this.nTiles, BLOCK_DENSITY,
                POWERUP_DENSITY);
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
    private void setDoor(final TilesFactory factory){
        factory.setDoor(this.getGenericSet(new Predicate<Tile>(){
            @Override
            public boolean test(Tile t) {
                return t.getType().equals(TileType.WALKABLE);
            } 
        }));
    }

    /**
     * Sets the key as a tile's powerup.
     * 
     * @param factory
     *          the TilesFactory object
     */
    private void setKey(final TilesFactory factory){
        factory.setKey(this.getGenericSet(new Predicate<Tile>(){
            @Override
            public boolean test(Tile t) {
                return t.getType().equals(TileType.RUBBLE);
            }        
        }));
    }

    /**
     * This method allows the hero to move,
     * it checks also collisions with other elements.
     */
    @Override
    public void moveHero(final Direction dir) {
        this.hero.move(this.hero.getCorrectDirection(dir), this.getBlocks(), this.getRectangles(this.getPlantedBombs()), this.getPowerUp());
    }

    @Override
    public void moveEnemies() {
        for (Enemy e : this.enemies) {
            e.updateMove(this.getBlocks(), this.hero, e.getRandomDirection(), this.getRectangles(this.getPlantedBombs()));
        }
    }

    @Override
    public void setDirectionEnemies() {
        for (Enemy e : this.enemies) {
            if (e.getEnemyType().equals(EnemyType.KONDORIA) || e.getEnemyType().equals(EnemyType.MINVO) || e.getEnemyType().equals(EnemyType.PASS)) {
                e.setDirection(e.getRandomDirection());
            }
        }
    }

    /**
     * This method checks if the enemy collides with fire and if it reduces his life.
     * @param tiles involved
     */
    private void checkCollisionWithExplosionBomb(final Set<Tile> tiles) {
        for (Enemy e : this.enemies) {
            if (e.checkFlameCollision(tiles)) {
                e.modifyLife(-this.hero.getAttack());
            }
            if (e.getRemainingLives() <= 0) {
                this.enemies.remove(e);
            }
        }
    }

    /**
     * Verifies if a bomb can be planted.
     * 
     * @return true if the bomb can be planted, false otherwise
     */
    public boolean canPlantBomb(){
        final Point point = new Point(MapPoint.getCorrectPos(this.hero.getX(), nTiles, this.tileDimension), 
                MapPoint.getCorrectPos(this.hero.getY(), nTiles, this.tileDimension));
        for(final Bomb b: this.getPlantedBombs()){
            if(b.getPosition().equals(point)){
                return false;
            }
        }
        return true;
    }

    /**
     * Plants a bomb.
     */
    @Override
    public void plantBomb() {
        this.hero.plantBomb();
    }

    /**
     * Detonates a bomb.
     * 
     * @return the set of afflicted tiles
     */
    @Override
    public Set<Tile> detonateBomb() {
        final Bomb b = this.hero.getDetonator().getBombToReactivate();
        final Set<Tile> tiles = this.getAllAfflictedTiles(b);
        if(this.hero.checkFlameCollision(tiles)){
            this.hero.modifyLife(-1);
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
                this.checkBoundaries(MapPoint.getInvCoordinate(b.getY(), this.tileDimension), -b.getRange())));
        afflictedTiles.addAll(this.getAfflictedTiles(Direction.RIGHT, b,
                this.checkBoundaries(MapPoint.getInvCoordinate(b.getX(), this.tileDimension), +b.getRange()), 
                MapPoint.getInvCoordinate(b.getY(), this.tileDimension)));
        afflictedTiles.addAll(this.getAfflictedTiles(Direction.DOWN, b, 
                MapPoint.getInvCoordinate(b.getX(), this.tileDimension),
                this.checkBoundaries(MapPoint.getInvCoordinate(b.getY(), this.tileDimension), +b.getRange())));
        afflictedTiles.addAll(this.getAfflictedTiles(Direction.LEFT, b,
                this.checkBoundaries(MapPoint.getInvCoordinate(b.getX(), this.tileDimension), -b.getRange()), 
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
    private Set<Tile> getAfflictedTiles(final Direction dir, final Bomb b, final int maxX, final int maxY){
        final Set<Tile> afflictedTiles = new HashSet<>();
        boolean stop = false;
        for(int i = MapPoint.getInvCoordinate(b.getX(), this.tileDimension); this.stop(i, maxX, dir) && !stop; i += this.next(dir) ){
            for(int j = MapPoint.getInvCoordinate(b.getY(), this.tileDimension); this.stop(j, maxY, dir) && !stop; j += this.next(dir) ){
                if(this.map[i][j].getType().equals(TileType.CONCRETE)){
                    stop = true;
                }
                else {
                    afflictedTiles.add(this.map[i][j]);
                    if(this.map[i][j].getType().equals(TileType.RUBBLE)){
                        if(this.map[i][j].getPowerup().isPresent()){
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
     * Checks boundaries.
     * 
     * @param coordinate
     *          the coordinate
     * @param range
     *          the bomb's range
     * @return the maximum possible coordinate
     */
    private int checkBoundaries(final int coordinate, final int range){
        if((coordinate + range) > this.nTiles - 1){
            return this.nTiles - 1;
        } else if ((coordinate + range) < 0){
            return 0;
        } else {
            return coordinate + range;
        }
    }

    /**
     * Checks if the cycle must be stopped or not.
     * 
     * @param coordinate
     *          the coordinate
     * @param max
     *          the max coordinate
     * @param dir
     *          the direction
     * @return true if the cycle can continue, false otherwise
     */
    private boolean stop(final int coordinate, final int max, final Direction dir){
        if(dir.equals(Direction.UP) || dir.equals(Direction.LEFT)){
            return coordinate >= max;
        } else {
            return coordinate <= max; 
        }
    }

    /**
     * Return the next value the coordinate has to add.
     * 
     * @param dir
     *          the direction
     * @return the integer to add to the coordinate
     */
    private int next(final Direction dir){
        if(dir.equals(Direction.UP) || dir.equals(Direction.LEFT)){
            return -1;
        } else {
            return 1;
        }
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
        return this.getGenericSet(new Predicate<Tile>(){
            @Override
            public boolean test(Tile t) {
                return !t.getType().equals(TileType.POWERUP_STATUS);
            }
        });
    }

    /**
     * This method build a Set of all Tiles that have a PowerUp.
     * 
     * @return the PowerUp sets
     */
    @Override
    public Set<Tile> getPowerUp(){
        return this.getGenericSet(new Predicate<Tile>(){
            @Override
            public boolean test(Tile t) {
                return t.getType().equals(TileType.POWERUP_STATUS) && t.getPowerup().isPresent();
            }  
        });
    }

    /**
     * This method returns the set of planted bombs.
     */
    @Override
    public Set<Bomb> getPlantedBombs() {
        return this.hero.getDetonator().getPlantedBombs().stream().collect(Collectors.toSet());
    }

    /**
     * Gets the tile that represents the door.
     * 
     * @return the tile that represents the door
     */
    @Override
    public Tile getDoor(){
        return this.getGenericSet(new Predicate<Tile>(){
            @Override
            public boolean test(Tile t) {
                return t.getType().equals(TileType.DOOR_CLOSED) || t.getType().equals(TileType.DOOR_OPENED);
            }
        }).stream().findFirst().get();
    }

    /**
     * This method builds a Set of all indestructible blocks,
     * closed door included.
     * 
     * @return the set of blocks
     */
    private Set<Rectangle> getBlocks(){
        return this.getGenericSet(new Predicate<Tile>(){
            @Override
            public boolean test(Tile t) {
                return t.getType().equals(TileType.RUBBLE) || t.getType().equals(TileType.CONCRETE);
            }
        }).stream().map(t -> t.getHitbox()).collect(Collectors.toSet());
    }

    /**
     * Gets the entire set of tiles that are available
     * to positionate enemies.
     * 
     * @return the set of free tiles
     */
    private Set<Tile> getFreeTiles(){
        return this.getGenericSet(new Predicate<Tile>(){
            @Override
            public boolean test(Tile t) {
                return t.getType().equals(TileType.WALKABLE) && 
                        !MapPoint.isEntryPoint(MapPoint.getInvCoordinate(t.getX(),tileDimension),
                        MapPoint.getInvCoordinate(t.getY(), tileDimension));
            }            
        });
    }

    /**
     * This method allows to get a generic set of elements.
     *  
     * @param func 
     *          the function
     * @return a set of elements
     */
    private Set<Tile> getGenericSet(final Predicate<Tile> pred){
        final Set<Tile> set = new HashSet<>();
        for(int i = 0; i < this.map.length; i++){
            for(int j = 0; j < this.map.length; j++){
                if(pred.test(this.map[i][j])){
                    set.add(this.map[i][j]);
                }
            }
        }
        return set;
    }

    /**
     * This method return a set of LevelElement's boundbox.
     * 
     * @param set
     * @return
     */
    private <X extends LevelElement> Set<Rectangle> getRectangles(final Set<X> set ){
        return set.stream().map(p -> p.getHitbox()).collect(Collectors.toSet());
    }

    /**
     * This method return the hero's position.
     */
    @Override
    public Point getHeroPosition() {
        return this.hero.getPosition();
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
    public void setNumberTiles() {
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
    public void setOpenDoor(){
        this.getDoor().setType(TileType.DOOR_OPENED);
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
        return this.enemies;
    }

}
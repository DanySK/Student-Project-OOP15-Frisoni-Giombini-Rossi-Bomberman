package model.level;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

import model.Tile;
import model.TileType;
import model.TilesFactory;
import model.units.Direction;
import model.units.Hero;
import model.units.HeroImpl;
import model.units.PowerUpType;
import model.utilities.MapPoint;
import model.utilities.PowerUp;

/**
 * This class represent the Model, as it contains
 * all game elements.
 *
 */
public class LevelImpl implements Level {

    private static final Point START_HERO_POS = new Point(1, 1);
    private static final double BLOCK_DENSITY = 0.5;
    private static final double POWERUP_DENSITY = 0.75;
    private static final int MIN_TILES = 9;
    private static final int MAX_TILES = 19;

    private Tile[][] map;
    private Hero hero;
    private int tileDimension;
    private int nTiles;
    private boolean inGame;

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
        this.hero = new HeroImpl(MapPoint.getPos(START_HERO_POS, this.tileDimension), 
                Direction.DOWN, 
                new Dimension(this.tileDimension, this.tileDimension));
    }

    /**
     * This method generates a random level 
     * with the specified size and block density.
     */
    private void createLevel() {
        if(this.map != null){
            throw new UnsupportedOperationException();
        } else {
            final TilesFactory factory = new TilesFactory(this.nTiles, this.nTiles, BLOCK_DENSITY,
                                                            POWERUP_DENSITY, this.tileDimension);
            this.map = new Tile[this.nTiles][this.nTiles];
            for (int i = 0; i < this.nTiles; i++) {
                for (int j = 0; j < this.nTiles; j++) {
                    this.map[i][j] = factory.createForCoordinates(i, j);
                }
            }
        }
    }

    /**
     * This method generates a random value to set
     * as the size of the map.
     */
    private void setNumberTiles() {
        int tiles = 0;
        while (tiles % 2 == 0) {
            tiles = new Random().nextInt(MAX_TILES - MIN_TILES) + MIN_TILES;
        }
        this.nTiles = tiles;
    }

    /**
     * This method allows the hero to move,
     * it checks also collisions with other elements.
     */
    @Override
    public void moveHero(final Direction dir) {
        this.hero.move(dir, this.getBlocks());
    }
    
    /**
     * This methods return a map that represents
     * tiles' types in the background.
     */
    @Override
    public TileType[][] getMap() {
        TileType[][] mapType = new TileType[this.nTiles][this.nTiles];
        for (int i = 0; i < this.nTiles; i++) {
            for (int j = 0; j < this.nTiles; j++) {
                mapType[i][j] = this.map[i][j].getType();
            }
        }
        return mapType;
    }

    /**
     * This method return a set that represents 
     * powerup's types in the map.
     */
    @Override
    public Set<PowerUp> getPowerupInLevel() {
        Set<PowerUp> powerupSet = new HashSet<>();
        for(int i = 0; i < this.nTiles; i++){
            for(int j = 0; j < this.nTiles; j++){
                if(this.map[i][j].getType().equals(TileType.WALKABLE) && this.map[i][j].getPowerup().isPresent()){
                    powerupSet.add(new PowerUp(i, j, this.map[i][j].getPowerup().get()));
                }
            }
        }
        return powerupSet;
    }
    
    /**
     * This method build a Set of all indestructible blocks.
     * 
     * @return the set of blocks
     */
    private Set<Rectangle> getBlocks(){
        return this.getGenericSet(new Predicate<Tile>(){
            @Override
            public boolean test(Tile tile) {
                return !tile.getType().equals(TileType.WALKABLE);
            }
        });
    }
    
    /**
     * This method build a Set of all PowerUp.
     * 
     * @return the PowerUp sets
     */
    private Map<Rectangle, PowerUpType> getPowerUp(){
        Map<Rectangle, PowerUpType> map = new HashMap<>();
        for(int i = 0; i < this.map.length; i++){
            for(int j = 0; j < this.map.length; j++){
                if(this.map[i][j].getPowerup().isPresent()){
                    map.put(this.map[i][j].getBoundBox(), this.map[i][j].getPowerup().get());
                }
            }
        }
        return map;
    }
    
    /**
     * This method allows to get a generic set of Rectangle.
     * 
     * @param pred 
     *          the predicate
     * 
     * @return a set of Rectangle
     */
    private Set<Rectangle> getGenericSet(Predicate<Tile> pred){
        Set<Rectangle> set = new HashSet<>();
        for(int i = 0; i < this.map.length; i++){
            for(int j = 0; j < this.map.length; j++){
                if(pred.test(this.map[i][j])){
                    set.add(this.map[i][j].getBoundBox());
                }
            }
        }
        return set;
        
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
        return this.hero; //TODO: copia difensiva?
    }

    /**
     * This method return the size of the map.
     */
    @Override
    public int getSize() {
        return this.nTiles;
    }

    /**
     * This method set the dimension of the tile.
     */
    @Override
    public void setTileDimension(final int dim) {
        this.tileDimension = dim;
    }

    /**
     * This method check if the game is over.
     */
    @Override
    public boolean isGameOver() {
        return this.inGame;
    }
}
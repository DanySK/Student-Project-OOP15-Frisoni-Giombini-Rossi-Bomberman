package model.level;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;

import model.Tile;
import model.TileType;
import model.TilesFactory;
import model.units.Direction;
import model.units.Hero;
import model.units.HeroImpl;
import model.utilities.MapPoint;

/**
 * This class represent the Model, as it contains
 * all game elements.
 *
 */
public class LevelImpl implements Level {

    private static final Point START_HERO_POS = new Point(1, 1);
    private static final double BLOCK_DENSITY = 0.5;
    private static final int MIN_TILES = 9;
    private static final int MAX_TILES = 11;

    private Tile[][] map;
    private Hero hero;
    private Collision collision;
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
        this.collision = new Collision(this.map, this.hero);
    }

    /**
     * This method generates a random level 
     * with the specified size and block density.
     */
    private void createLevel() {
        if(this.map != null){
            throw new UnsupportedOperationException();
        } else {
            final TilesFactory factory = new TilesFactory(this.nTiles, this.nTiles, BLOCK_DENSITY, this.tileDimension);
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
            tiles = new Random().nextInt(MAX_TILES) + MIN_TILES;
        }
        this.nTiles = tiles;
    }

    /**
     * This method allows the hero to move,
     * it checks also collisions with other elements.
     */
    @Override
    public void moveHero(final Direction dir) {
        this.collision.setDirection(dir);
        if(this.collision.blockCollision()){
            this.hero.setMoving(true);
            this.hero.move(dir);
        }
        else{
            this.hero.setMoving(false);
        }
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
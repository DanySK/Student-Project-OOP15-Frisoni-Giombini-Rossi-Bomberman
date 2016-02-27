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

/**
 * 
 *
 */
public class LevelImpl implements Level {

    private static final Point START_HERO_POS = new Point(1, 1);
    private static final double BLOCK_DENSITY = 0.5;
    private static final int MIN_TILES = 9;
    private static final int MAX_TILES = 20;

    private Tile[][] map;
    private Hero hero;
    private int tileDimension;
    private int nTiles;
    private boolean inGame;

    /**
     * Builds a new map.
     */
    public LevelImpl() {
        this.setNumberTiles();
    }

    @Override
    public void initLevel(final int tileDimension) {
        this.setTileDimension(tileDimension);
        this.createLevel();
        this.hero = new HeroImpl(new Point(START_HERO_POS.x * this.tileDimension, START_HERO_POS.y * this.tileDimension), 
                Direction.DOWN, 
                new Dimension(this.tileDimension, this.tileDimension));
    }

    /*
     * Generates a random level with the specified size and block density.
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

    private void setNumberTiles() {
        int tiles = 0;
        while (tiles % 2 == 0) {
            tiles = new Random().nextInt(MAX_TILES) + MIN_TILES;
        }
        this.nTiles = tiles;
    }

    @Override
    public void moveHero(final Direction dir) {
        this.hero.move(dir);
    }

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

    @Override
    public Point getHeroPosition() {
        return this.hero.getPosition();
    }

    @Override
    public Hero getHero() {
        return this.hero; //TODO: copia difensiva?
    }

    @Override
    public void setHeroMoving(final boolean b) {
        this.hero.setMoving(b);
    }

    @Override
    public int getSize() {
        return this.nTiles;
    }

    @Override
    public void setTileDimension(final int dim) {
        this.tileDimension = dim;
    }

    @Override
    public boolean isGameOver() {
        return this.inGame;
    }
}
package model.level;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import model.Tile;
import model.TileType;
import model.TilesFactory;
import model.units.Bomb;
import model.units.Direction;
import model.units.Hero;
import model.units.HeroImpl;
import model.units.LevelElement;
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
    private boolean gameOver;

    /**
     * The constructor is used to set the size of the map,
     * because it's the first thing to do to start the game.
     */
    public LevelImpl() {
        this.gameOver = false;
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
            this.getDoor(factory);
            this.setKey(factory);
            /*for (int i = 0; i < this.nTiles; i++) {
                for (int j = 0; j < this.nTiles; j++) {
                    if(this.map[i][j].getPowerup().isPresent()){
                        System.out.println("TYPE " + this.map[i][j].getType() + " ROW " + i + " COL " + j + " POWERUP " +this.map[i][j].getPowerup().get()); 
                    }
                    
                }
            }*/
        }
    }

    /**
     * Selects all the walkable tiles and set the door in a random way.
     * 
     * @param factory
     *          the TilesFactory object
     */
    private void getDoor(final TilesFactory factory){
        factory.setDoor(this.getGenericSet(new Function<Tile, Optional<Tile>>(){
            @Override
            public Optional<Tile> apply(final Tile t) {
                if(t.getType().equals(TileType.WALKABLE)){
                    return Optional.of(t);
                } else {
                    return Optional.empty();
                }
            }
        }).stream().filter(t -> t.isPresent()).map(t -> t.get()).collect(Collectors.toSet()));
    }

    private void setKey(final TilesFactory factory){
        factory.setKey(this.getGenericSet(new Function<Tile, Optional<Tile>>(){

            @Override
            public Optional<Tile> apply(Tile t) {
                if(t.getType().equals(TileType.RUBBLE)){
                    return Optional.of(t);
                } else {
                    return Optional.empty();
                }
            }

        }).stream().filter(t -> t.isPresent()).map(t -> t.get()).collect(Collectors.toSet()));
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
        this.hero.move(this.hero.getCorrectDirection(dir), this.getBlocks(), this.getRectangles(this.getPlantedBombs()), this.getPowerUp());
    }

    /**
     * Plants a bomb.
     */
    @Override
    public void plantBomb() {
        this.hero.plantBomb(this.nTiles);
    }

    /**
     * Detonates a bomb.
     */
    @Override
    public Set<Tile> detonateBomb() {
        final Bomb b = this.hero.getDetonator().getPlantedBombs().getFirst();
        //controlla nemici e in caso aggiungi punteggio all'hero
        if(this.hero.checkFlameCollision(this.getAllAfflictedTiles(b))){
            this.hero.modifyLife(-1);
            if(this.hero.isDead()){
                this.gameOver = true;
                System.out.println("Hero is dead!");
            }
        }
        this.hero.getDetonator().reactivateBomb();
        return this.getAllAfflictedTiles(b);
    }

    /**
     * Verifies if a bomb can be planted.
     */
    public boolean canPlantBomb(){
        /*final Point point = new Point(MapPoint.getInvCoordinate(this.hero.getX(), this.tileDimension), 
                MapPoint.getInvCoordinate(this.hero.getY(), this.tileDimension));*/
        final Point point = new Point(MapPoint.getPos(this.hero.getX(), this.nTiles, this.tileDimension) / this.tileDimension,
                MapPoint.getPos(this.hero.getY(), this.nTiles, this.tileDimension) / this.tileDimension);
        for(final Bomb b: this.getPlantedBombs()){
            if(new Point(MapPoint.getInvCoordinate(b.getX(), this.tileDimension), 
                    MapPoint.getInvCoordinate(b.getY(), this.tileDimension)).equals(point)){
                return false;
            }
        }
        return true;
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
     * powerups in the map.
     */
    @Override
    public Set<PowerUp> getPowerupInLevel() {
        return this.getGenericSet(new Function<Tile, Optional<PowerUp>>(){
            @Override
            public Optional<PowerUp> apply(Tile t) {
                if(t.getType().equals(TileType.POWERUP_STATUS) && t.getPowerup().isPresent()){
                    return Optional.of(new PowerUp(t.getRow(), t.getCol(), t.getPowerup().get()));
                } else {
                    return Optional.empty();
                }
            }
        }).stream().filter(p -> p.isPresent()).map(p -> p.get()).collect(Collectors.toSet());
    }

    /**
     * This method returns the set of planted bombs.
     */
    @Override
    public Set<Bomb> getPlantedBombs() {
        return this.hero.getDetonator().getPlantedBombs().stream().collect(Collectors.toSet());
    }

    /**
     * This method returns a set of all afflicted tiles.
     */
    private Set<Tile> getAllAfflictedTiles(final Bomb b) {
        final Set<Tile> afflictedTiles = new HashSet<>();
        afflictedTiles.addAll(this.getAfflictedTiles(b, 
                this.checkBoundaries(MapPoint.getInvCoordinate(b.getX(), this.tileDimension), -b.getRange()),
                MapPoint.getInvCoordinate(b.getY(), this.tileDimension),
                this.checkBoundaries(MapPoint.getInvCoordinate(b.getX(), this.tileDimension), +b.getRange()),
                MapPoint.getInvCoordinate(b.getY(), this.tileDimension)));
        afflictedTiles.addAll(this.getAfflictedTiles(b, MapPoint.getInvCoordinate(b.getX(), this.tileDimension),
                this.checkBoundaries(MapPoint.getInvCoordinate(b.getY(), this.tileDimension), -b.getRange()),
                MapPoint.getInvCoordinate(b.getX(), this.tileDimension),
                this.checkBoundaries(MapPoint.getInvCoordinate(b.getY(), this.tileDimension), +b.getRange())));
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
     * This method returns the set of afflicted tiles 
     * in a certain direction.
     * 
     * @param b
     *          the bomb
     * @param x
     *          the x coordinate
     * @param y
     *          the y coordinate
     * @param maxX
     *          the maximum x
     * @param maxY
     *          the maximum y
     * @return a set of afflicted tiles
     */
    private Set<Tile> getAfflictedTiles(final Bomb b, final int x, final int y, final int maxX, final int maxY){
        final Set<Tile> afflictedTiles = new HashSet<>();
        boolean stop = false;
        for(int i = x; i <= maxX && !stop; i++){
            for(int j = y; j <= maxY && !stop; j++){
                if(this.map[i][j].getType().equals(TileType.CONCRETE)){ //come fare con la chiave o la porta
                    if(i < MapPoint.getInvCoordinate(b.getX(), this.tileDimension) &&
                            j == MapPoint.getInvCoordinate(b.getY(), this.tileDimension) || 
                            i == MapPoint.getInvCoordinate(b.getX(), this.tileDimension) &&
                            j < MapPoint.getInvCoordinate(b.getY(), this.tileDimension)){
                        afflictedTiles.clear();
                    }
                    else{
                        stop = true;
                    }
                }
                else {
                    if(this.map[i][j].getType().equals(TileType.RUBBLE)){
                        if(this.map[i][j].getPowerup().isPresent()){
                            this.map[i][j].setType(TileType.POWERUP_STATUS);
                        } else {
                            this.map[i][j].setType(TileType.WALKABLE);
                        }
                    }
                    afflictedTiles.add(this.map[i][j]);
                }
            }
        }
        /*for(Tile t: afflictedTiles){
            System.out.println("Tile " + t.getRow() + " " + t.getCol());
        }
        System.out.println("stop");*/
        return afflictedTiles;
    }

    /**
     * This method builds a Set of all indestructible blocks,
     * closed door included.
     * 
     * @return the set of blocks
     */
    private Set<Rectangle> getBlocks(){
        return this.getGenericSet(new Function<Tile, Optional<Rectangle>>(){
            @Override
            public Optional<Rectangle> apply(final Tile t) {
                if (!t.getType().equals(TileType.WALKABLE) && !t.getType().equals(TileType.DOOR_CLOSED) &&
                        !t.getType().equals(TileType.POWERUP_STATUS)){
                    return Optional.of(t.getBoundBox());
                } else {
                    return Optional.empty();
                }
            }
        }).stream().filter(t -> t.isPresent()).map(t -> t.get()).collect(Collectors.toSet());
    }

    /**
     * This method build a Set of all Tiles that have a PowerUp.
     * 
     * @return the PowerUp sets
     */    
    private Set<Tile> getPowerUp(){
        return this.getGenericSet(new Function<Tile, Optional<Tile>>(){
            @Override
            public Optional<Tile> apply(final Tile t) {
                if(t.getType().equals(TileType.POWERUP_STATUS) && t.getPowerup().isPresent()){
                    return Optional.of(t);
                } else {
                    return Optional.empty();
                }
            }
        }).stream().filter(t -> t.isPresent()).map(t -> t.get()).collect(Collectors.toSet());
    }

    /**
     * This method allows to get a generic set of elements.
     *  
     * @param func 
     *          the function
     * @return a set of elements
     */
    private <X> Set<X> getGenericSet(final Function<Tile, X> func){
        final Set<X> set = new HashSet<>();
        for(int i = 0; i < this.map.length; i++){
            for(int j = 0; j < this.map.length; j++){
                set.add(func.apply(this.map[i][j]));
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
        return this.gameOver;
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
     * Set the open door.
     */
    public void setOpenDoor(){
        for(int i = 0; i < this.nTiles; i++){
            for(int j = 0; j < this.nTiles; j++){
                if(this.map[i][j].equals(TileType.DOOR_CLOSED)){
                    this.map[i][j].setType(TileType.DOOR_OPENED);
                }
            }
        }
    }

}
package model.level;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
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
            this.getDoor(factory);
        }
    }

    private void getDoor(TilesFactory factory){
        factory.setDoor(this.getGenericSetXX(new Function<Tile, Optional<Tile>>(){
            @Override
            public Optional<Tile> apply(Tile t) {
               if(t.getType().equals(TileType.WALKABLE)){
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
        this.hero.move(dir, this.getBlocks(), this.getRectangles(this.getPlantedBombs()));
    }

    @Override
    public void plantBomb() {
        this.hero.plantBomb(this.nTiles);
    }

    @Override
    public Set<Tile> detonateBomb() {
        Bomb b = this.hero.getDetonator().getPlantedBombs().getFirst();
        if(!this.hero.checkFlamepass()){
            if(this.hero.checkFlameCollision(this.getAllAfflictedTiles(b))){
                this.hero.modifyLife(-1);
                if(this.hero.isDead()){
                    this.inGame = false;
                    System.out.println("Hero is dead!");
                }
            }
        }
        this.hero.getDetonator().reactivateBomb();
        return this.getAllAfflictedTiles(b);
    }

    public boolean canPlantBomb(){
        final Point point = new Point(MapPoint.getInvCoordinate(this.hero.getX(), this.tileDimension), 
                MapPoint.getInvCoordinate(this.hero.getY(), this.tileDimension));
        for(Bomb b: this.getPlantedBombs()){
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
     * powerup's types in the map.
     */
    @Override
    public Set<PowerUp> getPowerupInLevel() {
        return this.getGenericSetXX(new Function<Tile, Optional<PowerUp>>(){
            @Override
            public Optional<PowerUp> apply(Tile t) {
                if(t.getType().equals(TileType.WALKABLE) && t.getPowerup().isPresent()){
                    return Optional.of(new PowerUp(t.getRow(), t.getCol(), t.getPowerup().get()));
                } else {
                    return Optional.empty();
                }
            }
        }).stream().filter(p -> p.isPresent()).map(p -> p.get()).collect(Collectors.toSet());
    }

    @Override
    public Set<Bomb> getPlantedBombs() {
        return this.hero.getDetonator().getPlantedBombs().stream().collect(Collectors.toSet());
    }

    @Override
    public Set<Tile> getAllAfflictedTiles(Bomb b) {
        Set<Tile> afflictedTiles = new HashSet<>();
        afflictedTiles.addAll(this.getAfflictedTiles(b, MapPoint.getInvCoordinate(b.getX(), this.tileDimension) - b.getRange(),
                MapPoint.getInvCoordinate(b.getY(), this.tileDimension),
                MapPoint.getInvCoordinate(b.getX(), this.tileDimension) + b.getRange(),
                MapPoint.getInvCoordinate(b.getY(), this.tileDimension)));
        afflictedTiles.addAll(this.getAfflictedTiles(b, MapPoint.getInvCoordinate(b.getX(), this.tileDimension),
                MapPoint.getInvCoordinate(b.getY(), this.tileDimension) - b.getRange(),
                MapPoint.getInvCoordinate(b.getX(), this.tileDimension),
                MapPoint.getInvCoordinate(b.getY(), this.tileDimension) + b.getRange()));
        return afflictedTiles;
    }

    private Set<Tile> getAfflictedTiles(Bomb b, int x, int y, int maxX, int maxY){
        Set<Tile> afflictedTiles = new HashSet<>();
        boolean stop = false;
        for(int i = x; i <= maxX && !stop; i++){
            for(int j = y; j <= maxY && !stop; j++){
                if(this.map[i][j].getType().equals(TileType.CONCRETE)){ //come fare con la chiave o la porta
                    if(i < MapPoint.getInvCoordinate(b.getX(), this.tileDimension) &&
                            j == MapPoint.getInvCoordinate(b.getY(), this.tileDimension) || 
                            (i == MapPoint.getInvCoordinate(b.getX(), this.tileDimension) &&
                            j < MapPoint.getInvCoordinate(b.getY(), this.tileDimension))){
                        afflictedTiles.clear();
                    }
                    else{
                        stop = true;
                    }
                }
                else {
                    if(this.map[i][j].getType().equals(TileType.RUBBLE)){
                        this.map[i][j].setType(TileType.WALKABLE);
                    }
                    afflictedTiles.add(this.map[i][j]);
                }
            }
        }
        return afflictedTiles;
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
        /*return this.getGenericSetXX(new Function<Tile, Tile>(){

            @Override
            public Tile apply(Tile t) {
                if ((!t.getType().equals(TileType.WALKABLE) && !t.getType().equals(TileType.DOOR_CLOSED) ||
                        t.getPowerup().isPresent())){
                    return t;
                }
            }
        });*/
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

    private <X> Set<X> getGenericSetXX (Function<Tile, X> func){
        Set<X> set = new HashSet<>();
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
        return this.inGame;
    }

    private <X extends LevelElement> Set<Rectangle> getRectangles(Set<X> set ){
        Set<Rectangle> rectangles = new HashSet<>();
        for(LevelElement x: set){
            rectangles.add(x.getHitbox());
        }
        return rectangles;
        //return set.stream().map(p -> p.getHitbox());
    }


}
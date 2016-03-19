package model.level;

import java.awt.Point;
import java.util.Set;

import model.Tile;
import model.TileType;
import model.units.Bomb;
import model.units.Direction;
import model.units.Hero;
import model.utilities.PowerUp;

/**
 * This interface handles a level of the game.
 *
 */
public interface Level {

    /**
     * Creates the level and all its objects.
     * 
     * @param tileDimension
     *          the tile's size
     */
    void initLevel(final int tileDimension);

    /**
     * Moves the Hero in the specified direction.
     * 
     * @param dir
     *          the movement direction
     */
    void moveHero(Direction dir);

    /**
     * Plants a bomb.
     */
    void plantBomb();
    
    Set<Tile> detonateBomb();
    
    boolean canPlantBomb();

    /**
     * This method return a TileType map, the background of the game.
     * 
     * @return the map
     *                  the map that represents tiles' types.
     */
    TileType[][] getMap();

    /**
     * Gets all the powerup: their coordinates and their type.
     * 
     * @return a map where are contained informations of all powerups
     */
    Set<PowerUp> getPowerupInLevel();

    /**
     * Gets all panted bombs.
     * 
     * @return the set of bombs in the map
     */
    Set<Bomb> getPlantedBombs();

    /**
     * Checks how what tiles are afflicted by the explosion.
     * 
     * @return the set of afflicted tiles
     */
    Set<Tile> getAllAfflictedTiles(final Bomb b);

    /**
     * This method return the hero position-
     * 
     * @return Hero's position.
     */
    Point getHeroPosition();

    /**
     * This method return the entity Hero.
     * 
     * @return the Hero.
     */
    Hero getHero();


    /**
     * This method allow to know the size of the map.
     * 
     * @return the side's size of the map.
     */
    int getSize();

    /**
     * Sets the dimension (weight/height) of a tile.
     * 
     * @param dim
     *          the dimension in pixel
     */
    void setTileDimension(final int dim);

    /**
     * This method is used to know whether the game is over or not. 
     * 
     * @return true if the game is over, otherwise false
     */
    boolean isGameOver();

}

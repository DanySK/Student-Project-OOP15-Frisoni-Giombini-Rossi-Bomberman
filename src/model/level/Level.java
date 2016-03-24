package model.level;

import java.awt.Point;
import java.util.Set;

import model.Tile;
import model.units.Bomb;
import model.units.Direction;
import model.units.Hero;

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

    /**
     * Detonates a bomb.
     * 
     * @return the set of afflicted tiles
     */
    Set<Tile> detonateBomb();

    /**
     * Verifies if there's already a bomb in that tile.
     * 
     * @return true if the bomb can be planted, false otherwise
     */
    boolean canPlantBomb();

    /**
     * 
     * @return
     */
    Set<Tile> getTiles();
    
    /**
     * 
     * @return
     */
    Set<Tile> getPowerUp();

    /**
     * Gets all panted bombs.
     * 
     * @return the set of bombs in the map
     */
    Set<Bomb> getPlantedBombs();

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

    /**
     * Set the tile type of the door to door_opened.
     */
    void setOpenDoor();

}

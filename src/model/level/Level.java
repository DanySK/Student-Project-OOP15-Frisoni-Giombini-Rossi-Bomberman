package model.level;

import java.awt.Point;

import model.TileType;
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
     * This method return a TileType map, the background of the game.
     * 
     * @return the map
     *                  the map that represents tiles' types.
     */
    TileType[][] getMap();

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

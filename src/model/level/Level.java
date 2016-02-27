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
     * @param dir
     *          the movement direction
     */
    void moveHero(Direction dir);
    
    /**
     * @return the map
     */
    TileType[][] getMap();

    /**
     * @return Hero's position.
     */
    Point getHeroPosition();
    
    /**
     * 
     * @return the Hero.
     */
    Hero getHero();
    
    /**
     * Sets the movement state of the hero.
     * 
     * @param b
     *          true if the hero is moving, false otherwise
     */
    void setHeroMoving(boolean b);

    /**
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
     * @return true if the game is over, otherwise false
     */
    boolean isGameOver();
   
}

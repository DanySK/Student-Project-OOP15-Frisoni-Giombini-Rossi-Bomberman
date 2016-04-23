package model.level;

import java.util.Set;

import model.Tile;
import model.units.Direction;
import model.units.Hero;
import model.units.enemy.Enemy;

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
     * Detonates a bomb.
     * 
     * @return the set of afflicted tiles
     */
    Set<Tile> detonateBomb();

    /**
     * This method allow to know the size of the map.
     * 
     * @return the side's size of the map.
     */
    int getSize();
    
    /**
     * Gets all the tiles where there isn't a powerup status.
     * 
     * @return the set of tiles
     */
    Set<Tile> getTiles();
    
    /**
     * Gets all the powerups that appears in the game.
     * 
     * @return the set of powerups
     */
    Set<Tile> getPowerUps();

    /**
     * Gets the door.
     * 
     * @return the tile where the door is positioned
     */
    Tile getDoor();

    /**
     * This method return the entity Hero.
     * 
     * @return the Hero.
     */
    Hero getHero();
    
    /**
     * This method generates a random value to set
     * as the size of the map.
     */
    void setTilesNumber();

    /**
     * Sets the dimension (weight/height) of a tile.
     * 
     * @param dim
     *          the dimension in pixel
     */
    void setTileDimension(final int dim);

    /**
     * Set the tile type of the door to door_opened.
     */
    void setOpenDoor();
    
    /**
     * Sets first stage.
     */
    void setFirstStage();
    
    /**
     * Sets the next stage.
     */
    void setNextStage();

    /**
     * This method is used to know whether the game is over or not. 
     * 
     * @return true if the game is over, otherwise false
     */
    boolean isGameOver();
  
    /**
     * Moves the Enemies in the specified direction.
     */
    void moveEnemies();
    
    /**
     * This method returns a set of enemy entities in the map.
     * 
     * @return the set of enemies.
     */
    Set<Enemy> getEnemies();
    
    /**
     * Set the enemies' direction. 
     */
    void setDirectionEnemies();
    
}

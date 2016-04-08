package controller;

import java.util.Set;

import model.Tile;
import model.units.Bomb;
import model.units.Hero;
import model.units.enemy.Enemy;

public interface GameController {
    
    /**
     * This method return the entity Hero.
     * @return the entity Hero.
     */
    Hero getHero();
    
    /**
     * Gets all the powerups.
     * 
     * @return the set of powerups
     */
    Set<Tile> getPowerUp();
    
    /**
     * Gets all the tiles where there isn't a powerup status.
     * 
     * @return the set of tiles
     */
    Set<Tile> getTiles();
    
    /**
     * @return true if the game is over, otherwise false.
     */
    boolean isGameOver();
    
    /**
     * This method return the side's size of the map.
     * @return the side's size of the map.
     */
    int getLevelSize();
    
    /**
     * This method return the set of bombs.
     * @return the set of bombs in the map
     */
    Set<Bomb> getPlantedBombs();
    
    /**
     * Get's FPS.
     * @return FPS
     */
    int getFPS();
    
    /**
     * Gets bomb's delay.
     * 
     * @return bomb's delay
     */
    long getBombDelay();
    
    /**
     * This method returns a set of enemy entities in the map.
     * @return the set of enemies.
     */
    Set<Enemy> getEnemies();
    
    /**
     * This method return the time elapsed since the start of the game.
     * @return the time of game
     */
    int getTime();
}

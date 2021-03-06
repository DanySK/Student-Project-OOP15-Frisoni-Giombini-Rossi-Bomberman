package controller;

import java.util.List;
import java.util.Set;

import controller.utilities.Pair;
import model.units.Bomb;
import model.units.Hero;
import model.units.Tile;
import model.units.enemy.Enemy;

/**
 * This class models a GameController.
 */
public interface GameController {
    
    /**
     * This method return the entity Hero.
     * @return the entity Hero.
     */
    Hero getHero();
    
    /**
     * Gets all the power up.
     * 
     * @return the set of power up
     */
    Set<Tile> getPowerUp();
    
    /**
     * Gets all the tiles where there isn't a power up status.
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
     * This method return bomb's delay.
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
    
    /**
     * This method return a pair of score-time of the best score.
     * @return the best score.
     */
    Pair<Integer, Integer> getRecord();
    
    /**
     * This method return a list of pairs score-time of the ten last scores.
     * @return a list of the ten last scores.
     */
    List<Pair<Integer, Integer>> getLastScores();
    
    /**
     * This method check if the queue of scores is empty.
     * @return true if is empty, false otherwise
     */
    boolean isScoreEmpty();
    
}

package controller;

import java.util.Set;

import model.Tile;
import model.units.Bomb;
import model.units.Hero;

public interface GameController {
    
    /**
     * This method return the entity Hero.
     * @return the entity Hero.
     */
    Hero getHero();
    
    /**
     * @return 
     */
    Set<Tile> getPowerUp();
    
    /**
     * @return 
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
}

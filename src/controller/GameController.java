package controller;

import java.util.Set;

import model.units.Bomb;
import model.units.Hero;

public interface GameController {
    
    /**
     * This method return the entity Hero.
     * @return the entity Hero.
     */
    Hero getHero();
    
    /**
     * @return a map where are contained informations of all powerups.
     */
    //Set<PowerUp> getPowerUpInLevel();*/
    
    /**
     * This method return the map.
     * @return the map that represents tiles' types.
     */
    //TileType[][] getMap();
    
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
    
}

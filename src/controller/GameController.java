package controller;

import java.util.Set;

import model.TileType;
import model.units.Hero;
import model.utilities.PowerUp;

public interface GameController {
    
    Hero getHero();
    
    Set<PowerUp> getPowerUpInLevel();
    
    TileType[][] getMap();
    
    boolean isGameOver();
    
    //Set<Bomb> getPlantedBombs();
}

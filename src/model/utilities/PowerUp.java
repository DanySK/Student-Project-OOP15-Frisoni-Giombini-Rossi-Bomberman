package model.utilities;

import model.units.PowerUpType;

public class PowerUp {

    private final int x;
    private final int y;
    private final PowerUpType type;

    public PowerUp(final int x, final int y, final PowerUpType type){
        this.x = x;   
        this.y = y;
        this.type = type;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public PowerUpType getType(){
        return this.type;
    }
}

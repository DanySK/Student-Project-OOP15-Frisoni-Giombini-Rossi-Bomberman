package model.utilities;

import model.units.PowerUpType;

/**
 * This class identify the principals information
 * of a powerup.
 */
public class PowerUp {

    private final int x;
    private final int y;
    private final PowerUpType type;

    /**
     * Creates a Powerup.
     * 
     * @param x
     *          the x coordinate
     * @param y
     *          the y coordinate
     * @param type
     *          the powerup's type
     */
    public PowerUp(final int x, final int y, final PowerUpType type){
        this.x = x;   
        this.y = y;
        this.type = type;
    }
    
    /**
     * Gets the x coordinate.
     * 
     * @return the x coordinate
     */
    public int getX(){
        return this.x;
    }
    
    /**
     * Gets the y coordinate.
     * 
     * @return the y coordinate
     */
    public int getY(){
        return this.y;
    }
    
    /**
     * Gets the powerup's type.
     * @return
     */
    public PowerUpType getType(){
        return this.type;
    }
    
}

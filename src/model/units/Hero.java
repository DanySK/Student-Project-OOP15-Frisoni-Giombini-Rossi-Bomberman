package model.units;

/**
 * This class models a Hero.
 *
 */
public interface Hero extends Entity {
    
    void setMoving(boolean b);
    
    boolean isMoving();
}
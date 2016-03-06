package model.units;

import java.awt.Dimension;
import java.awt.Point;

/**
 * Implementation of {@link Hero}.
 *
 */
public class HeroImpl extends AbstractEntity implements Hero {

    private static final int INITIAL_ATTACK = 2;
    private static final int INITIAL_LIFES = 5;

    private boolean inMovement;
    private int attack;
    private int lives;
    private boolean flamepass;

    /**
     * This allow to create a Hero.
     * 
     * @param pos
     *          the initial position
     * @param dir
     *          the initial direction
     * @param dim
     *          the dimension of the hitBox
     */
    public HeroImpl(final Point pos, final Direction dir, final Dimension dim) {
        super(pos, dir, dim);
        this.inMovement = false;
        this.attack = INITIAL_ATTACK;
        this.lives = INITIAL_LIFES;
        this.flamepass = false;
    }

    /*@Override
    public boolean checkCollision(Direction dir, Set<Rectangle> blockSet) {
        super.getCollision().updateEntityRec(dir);
        return super.getCollision().blockCollision(blockSet);
    }*/
    
    @Override
    public void move(Direction d) {
        super.updatePosition(super.getPossiblePos(d.getPoint()));
        super.updateDirection(d);
    } 
    
    @Override
    public void setMoving(boolean b) {
        this.inMovement = b;        
    }

    @Override
    public boolean isMoving() {
        return this.inMovement;
    }

    /**
     * Increase the attack level.
     */
    @Override
    public void increaseAttack() {
        this.attack++;        
    }

    /**
     * Increase the number of remaining lives;
     */
    @Override
    public void increaseLife() {
        this.lives++;        
    }

    /**
     * Sets the flamepass.
     */
    @Override
    public void setFlamepass() {
        this.flamepass = true;        
    }

    @Override
    public void increaseBomb() {
        //DA FARE DOPO LE BOMBE!        
    }

    @Override
    public int getAttack() {
        return this.attack;
    }

    @Override
    public int getRemainingLives() {
        return this.lives;
    }

    @Override
    public boolean checkFlamepass() {
        return this.flamepass;
    }
}

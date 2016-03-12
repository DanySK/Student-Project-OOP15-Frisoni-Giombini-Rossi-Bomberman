package model.units;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

/**
 * Implementation of {@link Hero}.
 *
 */
public class HeroImpl extends AbstractEntity implements Hero {

    private static final int INITIAL_ATTACK = 2;
    private static final int TIMER_DELAY = 5;

    private boolean inMovement;
    private int attack;
    private int lives;
    private boolean flamepass;
    private boolean isConfused;

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
        this.flamepass = false;
        this.isConfused = false;
    }

    @Override
    public boolean checkCollision(Direction dir, Set<Rectangle> blockSet) {
        super.getCollision().updateEntityRec(this.getCorrectDirection(dir));
        if(super.getCollision().blockCollision(blockSet)){
            this.setMoving(true);
            return true;
        }
        else{
            return false;
        }
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
     * Sets the flamepass.
     */
    @Override
    public void setFlamepass() {
        this.flamepass = true; 
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        HeroImpl.this.flamepass = false;
                    }
                });
            }
        }, TIMER_DELAY, 1);
    }

    @Override
    public void setConfusion() {
        this.isConfused = true; 
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        HeroImpl.this.isConfused = false;
                    }
                });
            }
        }, TIMER_DELAY, 1);
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

    private Direction getCorrectDirection(Direction dir) {
        return this.isConfused ? dir.getOppositeDirection() : dir;
    }

   

    
}

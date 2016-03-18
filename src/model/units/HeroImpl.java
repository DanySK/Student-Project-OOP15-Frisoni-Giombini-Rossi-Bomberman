package model.units;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import model.Tile;
import model.utilities.MapPoint;

/**
 * Implementation of {@link Hero}.
 *
 */
public class HeroImpl extends AbstractEntity implements Hero {

    private static final int INITIAL_ATTACK = 2;
    private static final long TIMER_DELAY = 5000L;

    private Detonator detonator;
    private boolean inMovement;
    private int attack;
    private int lives;
    private boolean flamepass;
    private boolean isConfused;
    private boolean key;

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
        this.detonator = new Detonator(dim);
        this.inMovement = false;
        this.attack = INITIAL_ATTACK;
        this.flamepass = false;
        this.isConfused = false;
        this.key = false;
    }

    @Override
    public boolean checkCollision(Direction dir, Set<Rectangle> blockSet, Set<Rectangle> bombSet) {
        super.getCollision().updateEntityRec(this.getCorrectDirection(dir));
        if(super.getCollision().blockCollision(blockSet) && super.getCollision().bombCollision(bombSet, this.getHitbox())){
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
        this.detonator.addBomb();       
    }

    @Override
    public void increaseRange() {
        if(this.detonator.hasBombs()){
            this.detonator.increaseRange(); 
        }
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

    @Override
    public Bomb plantBomb(int nTiles, Set<Bomb> plantedBombs) {
        if(this.detonator.hasBombs()){
            Bomb b = this.detonator.plantBomb(new Point(MapPoint.getPos(this.getX(), nTiles, this.getHitbox().width),
                    MapPoint.getPos(this.getY(), nTiles, this.getHitbox().width)));
           this.detonator.removeBomb(b);
           return b;
        }
        throw new IllegalStateException();
    }

    @Override
    public void detonateBomb(Bomb b, Set<Bomb> plantedBombs) {
        this.detonator.detonate(b, plantedBombs);        
    }

    @Override
    public long getBombDelay() {
        return this.detonator.getBombDelay();        
    }

    @Override
    public boolean checkFlameCollision(Set<Tile> afflictedTiles) {
        return this.getCollision().fireCollision(afflictedTiles, this.getHitbox());
    }

    @Override
    public void setKey() {
        this.key = true;
    }
}

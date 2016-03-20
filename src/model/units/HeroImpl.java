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
 */
public class HeroImpl extends AbstractEntity implements Hero {

    private static final int INITIAL_ATTACK = 2;
    private static final long TIMER_DELAY = 5000L;

    private final Detonator detonator;
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
        this.attack = INITIAL_ATTACK;
        this.flamepass = false;
        this.isConfused = false;
        this.key = false;
    }
    
    /**
     * Checks if hero's got any collision.
     * 
     * @return true if there's a collision, false otherwise
     */
    @Override
    public boolean checkCollision(final Direction dir, final Set<Rectangle> blockSet, final Set<Rectangle> bombSet) {
        super.getCollision().updateEntityRec(this.getCorrectDirection(dir));
        if(super.getCollision().blockCollision(blockSet) && super.getCollision().bombCollision(bombSet, this.getHitbox())){
            this.setMoving(true);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Set the hero to be in movement or not.
     */
    @Override
    public void setMoving(final boolean b) {
        super.inMovement = b;
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

    /**
     * Sets confusion.
     */
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

    /**
     * Adds a bomb to his detonator.
     */
    @Override
    public void increaseBomb() {
        this.detonator.addBomb();       
    }

    /**
     * Increases the range of a bomb.
     */
    @Override
    public void increaseRange() {
        if(this.detonator.hasBombs()){
            this.detonator.increaseRange(); 
        }
    } 

    /**
     * Gets hero's attack.
     * 
     * @return hero's attack
     */
    @Override
    public int getAttack() {
        return this.attack;
    } 
    
    /**
     * Checks if he has flamepass.
     */
    @Override
    public boolean checkFlamepass() {
        return this.flamepass;
    }

    /**
     * Gets the correct direction depending on the boolean confusion.
     * 
     * @param dir
     *          the direction where he would move
     * @return the direction where he will move
     */
    private Direction getCorrectDirection(final Direction dir) {
        return this.isConfused ? dir.getOppositeDirection() : dir;
    }

    /**
     * Gets the bomb to plant.
     * 
     * @return the bomb to plant
     */
    @Override
    public Bomb plantBomb(final int nTiles) {
        return this.detonator.plantBomb(new Point(MapPoint.getPos(this.getX(), nTiles, this.getHitbox().width),
                MapPoint.getPos(this.getY(), nTiles, this.getHitbox().width)));
    }

    /**
     * Gets bomb delay.
     * 
     * @return bomb delay
     */
    @Override
    public long getBombDelay() {
        return this.detonator.getBombDelay();        
    }

    /**
     * Checks flame collisions.
     * 
     * @return true if there's a collision, false otherwise
     */
    @Override
    public boolean checkFlameCollision(final Set<Tile> afflictedTiles) {
        return this.getCollision().fireCollision(afflictedTiles, this.getHitbox());
    }

    /**
     * Set the hero to own the key.
     */
    @Override
    public void setKey() {
        this.key = true;
    }

    /**
     * Checks if he has bomb to plant.
     * 
     * @return true if there's a bomb, false otherwise
     */
    @Override
    public boolean hasBomb() {
        return this.detonator.hasBombs();
    }

    /**
     * Gets hero's detonator.
     * 
     * @return hero's detonator
     */
    @Override
    public Detonator getDetonator() {
        return this.detonator;
    }
}

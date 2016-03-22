package model.units;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Set;

import model.Tile;
import model.utilities.MapPoint;

/**
 * Implementation of {@link Hero}.
 */
public class HeroImpl extends AbstractEntity implements Hero {

    private static final int INITIAL_ATTACK = 2;

    private final Detonator detonator;
    private int attack;
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
        this.isConfused = false;
        this.key = false;
    }
    
    /**
     * Checks if hero's got any collision.
     * 
     * @return true if there's a collision, false otherwise
     */
    @Override
    public boolean checkCollision(final Direction dir, final Set<Rectangle> blockSet, final Set<Rectangle> bombSet,
            final Set<Tile> powerUpSet) {
        super.getCollision().updateEntityRec(this.getCorrectDirection(dir));
        if(super.getCollision().blockCollision(blockSet) && super.getCollision().bombCollision(bombSet, this.getHitbox()) &&
                super.getCollision().powerUpCollision(powerUpSet)){
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
        System.out.println("attack");
        this.attack++;        
    }

    /**
     * Sets confusion.
     */
    @Override
    public void setConfusion(final boolean b) {
        System.out.println("confusion");
        this.isConfused = b;
    }

    /**
     * Adds a bomb to his detonator.
     */
    @Override
    public void increaseBomb() {
        System.out.println("increase bomb");
        this.detonator.addBomb();       
    }

    /**
     * Increases the range of a bomb.
     */
    @Override
    public void increaseRange() {
        System.out.println("increase range");
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
        /*return this.detonator.plantBomb(new Point(MapPoint.getCorrectPos(this.getX(), nTiles, this.getHitbox().width),
                MapPoint.getCorrectPos(this.getY(), nTiles, this.getHitbox().height)));*/
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
        System.out.println("key");
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

    /**
     * Cheks if hero's got the key.
     * 
     * @return true if he's got it, false otherwise
     */
    @Override
    public boolean hasKey() {
        return this.key;
    }
    
}

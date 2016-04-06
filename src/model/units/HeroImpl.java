package model.units;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Set;

import model.Tile;
import model.level.Collision;
import model.level.HeroCollision;
import model.level.HeroCollisionImpl;
import model.utilities.MapPoint;

/**
 * Implementation of {@link Hero}.
 */
public class HeroImpl extends AbstractEntity implements Hero {

    private Detonator detonator;
    private final HeroCollision heroCollision;
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
        this.heroCollision = new HeroCollisionImpl(this);
        this.isConfused = false;
        this.key = false;
    }
    
    /**
     * Hero's movement.
     */
    public void move(final Direction dir, final Set<Rectangle> blockSet, final Set<Rectangle> bombSet,
            final Set<Tile> powerUpSet){
        this.heroCollision.updateEntityRec(dir);
        if(this.heroCollision.blockCollision(blockSet) && this.heroCollision.bombCollision(bombSet) &&
                this.heroCollision.powerUpCollision(powerUpSet)){
            this.setMoving(true);
            super.move(dir);
        }
    }
    
    /**
     * Gets the bomb to plant.
     * 
     * @return the bomb to plant
     */
    @Override
    public Bomb plantBomb() {
        return this.detonator.plantBomb();
    }
       
    /**
     * Adds a bomb to his detonator.
     */
    @Override
    public void increaseBomb() {
        System.out.println("increase bomb");
        this.detonator.increaseBombs();       
    }

    /**
     * Increases the range of a bomb.
     */
    @Override
    public void increaseRange() {
        System.out.println("increase range");
        this.detonator.increaseRange(); 
    } 
    
    /**
     * Increases hero's score.
     */
    @Override
    public void increaseScore(int enemyScore) {
        super.score += enemyScore; 
    }

    /**
     * Checks the collision with the open door.
     * 
     * @return true if there's a collision, false otherwise
     */
    @Override
    public boolean checkOpenDoorCollision(final Tile door) {
        return this.heroCollision.openDoorCollision(door);
    }
    
    /**
     * Sets correctly hero for next game level.
     * 
     * @param lives
     *          the lives
     * @param attack
     *          the attack
     * @param score
     *          the score
     */
    @Override
    public void nextLevel(int lives, int attack, int score) {
        this.modifyLife(lives - 1);
        this.increaseAttack(attack - 1);
        this.increaseScore(score); 
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
     * Gets the correct direction depending on the boolean confusion.
     * 
     * @param dir
     *          the direction where he would move
     * @return the direction where he will move
     */
    public Direction getCorrectDirection(final Direction dir) {
        return this.isConfused ? dir.getOppositeDirection() : dir;
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
     * Gets the bomb's range.
     * 
     * @return bomb's range
     */
    @Override
    public int getBombRange() {
        return this.detonator.getActualRange();
    }
    
    /**
     * Gets hero's collision.
     * 
     * @return hero's collision
     */
    @Override
    public Collision getCollision() {
        return this.heroCollision;
    }
    
    /**
     * Set the hero to be in movement or not.
     */
    @Override
    public void setMoving(final boolean b) {
        super.inMovement = b;
    }

    /**
     * Sets confusion.
     */
    @Override
    public void setConfusion(final boolean b) {
        if(b){
            System.out.println("confusion_on"); 
        } else {
            System.out.println("confusion_off");
        }

        this.isConfused = b;
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
     * Cheks if hero's got the key.
     * 
     * @return true if he's got it, false otherwise
     */
    @Override
    public boolean hasKey() {
        return this.key;
    }

    /**
     * Checks if he has bomb to plant.
     * 
     * @return true if there's a bomb, false otherwise
     */
    @Override
    public boolean hasBomb(final int nTiles) {
        return this.detonator.hasBombs(new Point(MapPoint.getCorrectPos(this.getX(), nTiles, this.getHitbox().width), 
                MapPoint.getCorrectPos(this.getY(), nTiles, this.getHitbox().height)));
    }
    
    /**
     * Hero's toString.
     * 
     * @return hero's description
     */
    @Override
    public String toString(){
        return new StringBuilder().append("HERO -  ")
                .append(super.toString())
                .toString();
    }
    
}

package model.units;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Set;

import model.level.Collision;

/**
 * This class represent the entity that is 
 * the foundation of every entity in the game.
 *
 */
public abstract class AbstractEntity extends LevelElementImpl implements Entity {

    private static final int SPEED = 1;
    private static final int INITIAL_LIVES = 1;

    private Direction curDir;
    private Collision collision;
    private final int curSpeed;
    private int lives;


    /**
     * Constructs a new Entity.
     */
    public AbstractEntity(final Point pos, final Direction dir, final Dimension dim) {
        super(pos, dim);
        this.collision = new Collision(this);
        this.curDir = dir;
        this.curSpeed = SPEED;   
        this.lives = INITIAL_LIVES;
    }

    public abstract boolean checkCollision(Direction dir, Set<Rectangle> blockSet);

    /**
     * Abstract method, the implementation is 
     * different depending on the type of the entity.
     */
    @Override
    public void move(Direction d, Set<Rectangle> blockSet){
        if(this.checkCollision(d, blockSet)){
            this.updatePosition(this.getPossiblePos(d.getPoint()));
            this.updateDirection(d);
        }
    }

    @Override
    public void modifyLife(int change) {
        this.lives += change;        
    }
    
    /**
     * This method is used to calculate the
     * possible future position of the entity.
     */
    @Override
    public Point getPossiblePos(final Point pos){
        return new Point(super.getX() + pos.x * this.curSpeed,
                super.getY() + pos.y * this.curSpeed);
    }

    /**
     * Updates the position to the one received.
     */
    @Override
    public void updatePosition(final Point pos) {
        super.curPos.setLocation(pos);
        this.updateHitbox();
    }

    /**
     * Updates the direction.
     */
    @Override
    public void updateDirection(final Direction dir) {
        this.curDir = dir;
    }

    /**
     * Updates the hitBox.
     */
    @Override
    public void updateHitbox() {
        super.hitBox.setLocation(this.curPos);        
    }

    /**
     * Return the current speed.
     */
    @Override
    public int getSpeed() {
        return this.curSpeed;
    }

    /**
     * Return the current direction.
     */
    @Override
    public Direction getDirection() {
        return this.curDir;
    }

    public Collision getCollision(){
        return this.collision;
    }

    @Override
    public boolean isDead() {
        return this.lives == 0;
    }

}

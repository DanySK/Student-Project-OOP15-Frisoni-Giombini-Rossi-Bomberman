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

    private static final int INITIAL_LIVES = 1;

    private Direction curDir;
    private final Collision collision;
    protected boolean inMovement;
    private int lives;


    /**
     * Constructs a new Entity.
     */
    public AbstractEntity(final Point pos, final Direction dir, final Dimension dim) {
        super(pos, dim);
        this.collision = new Collision(this);
        this.curDir = dir; 
        this.inMovement = false;
        this.lives = INITIAL_LIVES;
    }

    public abstract boolean checkCollision(Direction dir, Set<Rectangle> blockSet, Set<Rectangle> plantedBombs);

    /**
     * Abstract method, the implementation is 
     * different depending on the type of the entity.
     */
    @Override
    public void move(final Direction d, final Set<Rectangle> blockSet, final Set<Rectangle> bombSet){
        if(this.checkCollision(d, blockSet, bombSet)){
            this.updatePosition(this.getPossiblePos(d.getPoint()));
            this.updateDirection(d);
        }
    }

    /**
     * Increases or decreases life.
     */
    @Override
    public void modifyLife(final int change) {
        this.lives += change;        
    }
    
    /**
     * This method is used to calculate the
     * possible future position of the entity.
     */
    @Override
    public Point getPossiblePos(final Point pos){
        return new Point(super.getX() + pos.x, super.getY() + pos.y);
    }

    /**
     * Updates the direction.
     */
    @Override
    public void updateDirection(final Direction dir) {
        this.curDir = dir;
    }

    /**
     * Return the current direction.
     */
    @Override
    public Direction getDirection() {
        return this.curDir;
    }

    /**
     * Return the collision.
     */
    @Override
    public Collision getCollision(){
        return this.collision;
    }

    /**
     * Gets remaining lives.
     * 
     * @return remaining lives
     */
    @Override
    public int getRemainingLives() {
        return this.lives;
    }
    
    /**
     * Verifies if the entity is dead.
     */
    @Override
    public boolean isDead() {
        return this.lives == 0;
    }
    
    /**
     * Verifies if the entity is in movement.
     */
    @Override
    public boolean isMoving() {
        return this.inMovement;
    }

}

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
    private Collision collision;
    private int lives;


    /**
     * Constructs a new Entity.
     */
    public AbstractEntity(final Point pos, final Direction dir, final Dimension dim) {
        super(pos, dim);
        this.collision = new Collision(this);
        this.curDir = dir; 
        this.lives = INITIAL_LIVES;
    }

    public abstract boolean checkCollision(Direction dir, Set<Rectangle> blockSet, Set<Rectangle> plantedBombs);

    /**
     * Abstract method, the implementation is 
     * different depending on the type of the entity.
     */
    @Override
    public void move(Direction d, Set<Rectangle> blockSet, Set<Rectangle> bombSet){
        if(this.checkCollision(d, blockSet, bombSet)){
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

    @Override
    public Collision getCollision(){
        return this.collision;
    }

    @Override
    public boolean isDead() {
        return this.lives == 0;
    }

}

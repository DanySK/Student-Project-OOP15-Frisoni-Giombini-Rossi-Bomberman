package model.units;

import java.awt.Dimension;
import java.awt.Point;

import model.level.Collision;

/**
 * This class represent the entity that is 
 * the foundation of every entity in the game.
 *
 */
public abstract class AbstractEntity extends LevelElementImpl implements Entity {

    private static final int START_SPEED = 1;
    private static final int DIVISION_FACTOR = 10;
    private static final int UNDEFINED = 0;

    private Direction curDir;
    private Collision collision;
    private final int curSpeed;
    private int step;
    

    /**
     * Constructs a new Entity.
     */
    public AbstractEntity(final Point pos, final Direction dir, final Dimension dim) {
        super(pos, dim);
        this.collision = new Collision(this);
        this.curDir = dir;
        this.curSpeed = START_SPEED;
        this.setStep();
    }
    
    //public abstract boolean checkCollision(Direction dir, Set<Rectangle> blockSet);

    /**
     * Abstract method, the implementation is 
     * different depending on the type of the entity.
     */
    @Override
    public abstract void move(Direction d);

    /**
     * This method calculates the number of
     * steps that the entity does at a time.
     */
    @Override
    public int numberOfSteps() {
        return this.curSpeed * this.step;
    }
    
    /**
     * This method is used to calculate the
     * possible future position of the entity.
     */
    @Override
    public Point getPossiblePos(final Point pos){
        return new Point(super.getX() + pos.x * this.numberOfSteps(),
                         super.getY() + pos.y * this.numberOfSteps());
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
     * This method initialize the step that the entity does.
     */
    @Override
    public final void setStep() {
        this.step = UNDEFINED;
        for(int i = 2; i <= DIVISION_FACTOR; i++){
            if(super.hitBox.width % i == 0){
                this.step = i;
                break;
            }
        }
        if(this.step == UNDEFINED){
            this.step = START_SPEED;
        }
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

    
}

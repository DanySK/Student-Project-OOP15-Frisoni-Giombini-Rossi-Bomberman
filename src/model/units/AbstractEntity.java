package model.units;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Set;

import model.Tile;
import model.level.Collision;

/**
 * This class represent the entity that is 
 * the foundation of every entity in the game.
 *
 */
public abstract class AbstractEntity extends DynamicLevelElement implements Entity {

    private static final int INITIAL_LIVES = 1;
    private static final int INITIAL_ATTACK = 1;
    private static final int INITIAL_SCORE = 0;

    private Direction curDir;
    protected boolean inMovement;
    private int lives;
    private int attack;
    protected int score;


    /**
     * Constructs a new Entity.
     */
    public AbstractEntity(final Point pos, final Direction dir, final Dimension dim) {
        super(pos, dim);
        this.curDir = dir; 
        this.inMovement = false;
        this.lives = INITIAL_LIVES;
        this.attack = INITIAL_ATTACK;
        this.score = INITIAL_SCORE;
    }

    /**
     * Abstract method, the implementation is 
     * different depending on the type of the entity.
     */
    @Override
    public void move(final Direction d){
        this.update(this.getPossiblePos(d.getPoint()));
        this.updateDirection(d);

    }
    
    /**
     * Checks flame collisions.
     * 
     * @return true if there's a collision, false otherwise
     */
    @Override
    public boolean checkFlameCollision(final Set<Tile> afflictedTiles){
        return this.getCollision().fireCollision(afflictedTiles);
    }

    /**
     * Increases or decreases life.
     */
    @Override
    public void modifyLife(final int change) {
        System.out.println("life");
        this.lives += change;        
    }
    
    /**
     * Updates the direction.
     */
    @Override
    public void updateDirection(final Direction dir) {
        this.curDir = dir;
    }
    
    /**
     * Increase attack level.
     */
    @Override
    public void increaseAttack(final int attackToAdd) {
        System.out.println("attack");
        this.attack += attackToAdd;        
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
     * Return the current direction.
     */
    @Override
    public Direction getDirection() {
        return this.curDir;
    }
    
    /**
     * Gets entiry's attack.
     * 
     * @return entity's attack
     */
    @Override
    public int getAttack() {
        return this.attack;
    }

    /**
     * Gets entity's score.
     * 
     * @return entity's score
     */
    @Override
    public int getScore(){
        return this.score;
    }

    /**
     * Gets entity's collision.
     * 
     * @return entity's collision
     */
    @Override
    public abstract Collision getCollision();

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

package model.units;

import java.awt.Dimension;
import java.awt.Point;

/**
 * This class represent the entity that is 
 * the foundation of every entity in the game.
 *
 */
public class EntityImpl extends DynamicLevelElement implements Entity {

    private static final int INITIAL_LIVES = 1;
    private static final int INITIAL_ATTACK = 1;
    private static final int INITIAL_SCORE = 0;

    protected Direction curDir;
    protected boolean inMovement;
    private int lives;
    private int attack;
    protected int score;


    /**
     * Constructs a new Entity.
     */
    public EntityImpl(final Point pos, final Dimension dim) {
        super(pos, dim);
        this.curDir = Direction.DOWN; 
        this.inMovement = false;
        this.lives = INITIAL_LIVES;
        this.attack = INITIAL_ATTACK;
        this.score = INITIAL_SCORE;
    }
    
    @Override
    public void move(final Direction dir) {
        this.update(this.getPossiblePos(dir.getPoint()));
        this.updateDirection(dir);
    }
    
    @Override
    public void modifyLife(final int change) {
        this.lives += change;        
    }

    @Override
    public void updateDirection(final Direction dir) {
        this.curDir = dir;
    }

    @Override
    public void increaseAttack(final int attackToAdd) {
        this.attack += attackToAdd;        
    }

    @Override
    public Point getPossiblePos(final Point pos) {
        return new Point(super.getX() + pos.x, super.getY() + pos.y);
    }

    @Override
    public Direction getDirection() {
        return this.curDir;
    }

    @Override
    public int getAttack() {
        return this.attack;
    }

    @Override
    public int getScore() {
        return this.score;
    }
    
    @Override
    public int getRemainingLives() {
        return this.lives;
    }
    
    @Override
    public void setDirection(final Direction dir) {
        this.curDir = dir;
    }
    
    @Override
    public boolean isDead() {
        return this.lives <= 0;
    }

    @Override
    public boolean isMoving() {
        return this.inMovement;
    }
    
    /**
     * Entity's toString.
     * 
     * @return entity's description
     */
    @Override
    public String toString() {
        return new StringBuilder().append("Direction is: ")
                .append(this.getDirection())
                .append(";\n")
                .append("\tLife/lives is/are: ")
                .append(this.getRemainingLives())
                .append(";\n")
                .append("\tAttack is: ")
                .append(this.getAttack())
                .append(";\n")
                .append(super.toString())
                .toString();
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + attack;
        result = prime * result + ((curDir == null) ? 0 : curDir.hashCode());
        result = prime * result + (inMovement ? 1231 : 1237);
        result = prime * result + lives;
        result = prime * result + score;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof EntityImpl && this.attack == ((EntityImpl) obj).attack
                && this.curDir.equals(((EntityImpl) obj).curDir) 
                && this.inMovement == ((EntityImpl) obj).inMovement
                && this.lives == ((EntityImpl) obj).lives 
                && this.score == ((EntityImpl) obj).score;
    }

}

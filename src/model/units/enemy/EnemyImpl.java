package model.units.enemy;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;
import java.util.Set;

import model.level.collision.Collision;
import model.level.collision.EnemyCollision;
import model.level.collision.EnemyCollisionImpl;
import model.units.AbstractEntity;
import model.units.Direction;
import model.units.Hero;

public class EnemyImpl extends AbstractEntity implements Enemy {
    
    private final EnemyCollision enemyCollision;
    private final EnemyType enemyType;
    
    /**
     * Constructor of EnemyImpl.
     * @param pos
     *          the initial position
     * @param dir
     *          the initial direction
     * @param dim
     *          the dimension of the hitBox
     * @param enemyType
     *          the type of enemy
     */
    public EnemyImpl(final Point pos, final Dimension dim, 
            final EnemyType enemyType) {
        super(pos, dim);
        this.enemyCollision = new EnemyCollisionImpl(this);
        this.enemyType = enemyType;
        super.modifyLife(this.enemyType.getEnemyLives() - 1);
        super.score = this.enemyType.getEnemyScore();
        super.increaseAttack(this.enemyType.getEnemyAttack() - 1);
    }

    @Override
    public void move(final Direction dir, final Set<Rectangle> blockSet, final Hero hero, 
            final Set<Rectangle> bombSet) {
        this.enemyCollision.updateEntityRec(dir);
        if (this.enemyCollision.blockCollision(blockSet) 
                && this.enemyCollision.bombCollision(bombSet) 
                && this.enemyCollision.heroCollision(hero)) {
            super.move(dir);
        }
    }
    
    /**
     * This method checks if the enemy collides with blocks or with the hero or with planted bombs.
     * @param dir
     *          the direction where the enemy wants to go
     * @param blockSet
     *          the set of blocks that are in the map
     * @param hero
     *          the hero's entity
     * @param bombSet
     *          the set of planted bombs
     * @return false if it collides, true otherwise
     */
    private boolean checkCollision(final Set<Rectangle> blockSet, final Hero hero, 
            final Set<Rectangle> bombSet) {
        return this.enemyCollision.blockCollision(blockSet) 
                && this.enemyCollision.bombCollision(bombSet) 
                && this.enemyCollision.heroCollision(hero) ? false : true;
    }
    
    @Override
    public Direction getRandomDirection() {
        final Direction[] vet = Direction.values();
        return vet[new Random().nextInt(vet.length)];
    }
    
    /**
     * This method returns a different direction from the current direction if it collides.
     * @param blockSet
     *          the set of blocks that are in the map
     * @param hero
     *          the hero's entity
     * @param dir
     *          the direction where the enemy wants to go
     * @return the direction where the enemy 
     *          must go
     */
    private Direction getNewDirection(final Set<Rectangle> blockSet, final Hero hero, 
            final Direction dir, final Set<Rectangle> bombSet) {
        if (this.checkCollision(blockSet, hero, bombSet)) {
            return dir;
        }
        return super.getDirection();
    }
    
    @Override
    public Collision getCollision() {
        return this.enemyCollision;
    }
   
    @Override
    public void updateMove(final Set<Rectangle> blockSet, final Hero hero, 
            final Direction dir, final Set<Rectangle> bombSet) {
        this.move(this.getNewDirection(blockSet, hero, dir, bombSet), blockSet, hero, bombSet);
    }

    @Override
    public EnemyType getEnemyType() {
        return this.enemyType;
    }

    @Override
    public void potentiateEnemy() {
        this.modifyLife(+1);
        this.increaseAttack(+1);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((enemyCollision == null) ? 0 : enemyCollision.hashCode());
        result = prime * result + ((enemyType == null) ? 0 : enemyType.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof EnemyImpl && this.enemyType.equals(((EnemyImpl) obj).getEnemyType());
    }

    @Override
    public String toString() {
        return new StringBuilder().append("ENEMY -  EnemyType is: ")
                .append(this.enemyType)
                .append(super.toString())
                .toString();
    }

}

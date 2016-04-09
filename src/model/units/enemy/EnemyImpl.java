package model.units.enemy;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;
import java.util.Set;

import model.level.Collision;
import model.level.EnemyCollision;
import model.level.EnemyCollisionImpl;
import model.units.AbstractEntity;
import model.units.Direction;
import model.units.Hero;

public class EnemyImpl extends AbstractEntity implements Enemy {
    
    private final EnemyCollision enemyCollision;
    private final EnemyType enemyType;
    
    public EnemyImpl(final Point pos, final Direction dir, final Dimension dim, final EnemyType enemyType) {
        super(pos, dir, dim);
        this.enemyCollision = new EnemyCollisionImpl(this);
        this.enemyType = enemyType;
        super.modifyLife(this.enemyType.getEnemyLives() - 1);
        super.score = this.enemyType.getEnemyScore();
        super.increaseAttack(this.enemyType.getEnemyAttack() - 1);
    }

    @Override
    public void move(final Direction dir, final Set<Rectangle> blockSet, final Hero hero, final Set<Rectangle> bombSet) {
        this.enemyCollision.updateEntityRec(dir);
        if (this.enemyCollision.blockCollision(blockSet) && this.enemyCollision.bombCollision(bombSet) && this.enemyCollision.heroCollision(hero)) {
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
    private boolean checkCollision(final Direction dir, final Set<Rectangle> blockSet, final Hero hero, final Set<Rectangle> bombSet) {
        if (this.enemyCollision.blockCollision(blockSet) && this.enemyCollision.bombCollision(bombSet) && this.enemyCollision.heroCollision(hero)) {
            return false;
        }
        return true;
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
     * @return the direction where the enemy must go
     */
    private Direction getNewDirection(final Set<Rectangle> blockSet, final Hero hero, final Direction dir, final Set<Rectangle> bombSet) {
        if (this.checkCollision(super.getDirection(), blockSet, hero, bombSet)) {
            return dir;
        }
        return super.getDirection();
    }
    
    @Override
    public Collision getCollision() {
        return this.enemyCollision;
    }
   
    @Override
    public void updateMove(final Set<Rectangle> blockSet, final Hero hero, final Direction dir, final Set<Rectangle> bombSet) {
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
    public void copy(final int lives, final int attack, final int score, final Direction dir) {
        super.copy(lives, attack, score, dir);
    }

}

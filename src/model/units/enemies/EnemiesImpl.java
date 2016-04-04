package model.units.enemies;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;
import java.util.Set;

import model.level.Collision;
import model.level.EnemiesCollision;
import model.level.EnemiesCollisionImpl;
import model.units.AbstractEntity;
import model.units.Direction;
import model.units.Hero;

public class EnemiesImpl extends AbstractEntity implements Enemies {
    
    private final EnemiesCollision enemyCollision;
    private final EnemiesType enemyType;
    
    public EnemiesImpl(final Point pos, final Direction dir, final Dimension dim, final int lives, final int score, final int attack, final EnemiesType enemyType) {
        super(pos, dir, dim);
        this.enemyCollision = new EnemiesCollisionImpl(this);
        super.modifyLife(lives - 1);
        super.score = score;
        super.increaseAttack(attack - 1);
        //this.setMoving(true);
        this.enemyType = enemyType;
    }

    @Override
    public void move(final Direction dir, final Set<Rectangle> blockSet, final Hero hero, final Set<Rectangle> bombSet) {
        this.enemyCollision.updateEntityRec(dir);
        if (this.enemyCollision.blockCollision(blockSet) && this.enemyCollision.heroCollision(hero) && this.enemyCollision.bombCollision(bombSet) /*&& this.inMovement*/) {
            super.move(dir);
        }
    }
    
    /**
     * This method check if enemy 
     * @param dir
     * @param blockSet
     * @param hero
     * @param bombSet
     * @return
     */
    private boolean checkCollision(final Direction dir, final Set<Rectangle> blockSet, final Hero hero, final Set<Rectangle> bombSet) {
        if (this.enemyCollision.blockCollision(blockSet) && this.enemyCollision.heroCollision(hero) && this.enemyCollision.bombCollision(bombSet)) {
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
     * 
     * @param blockSet
     * @param hero
     * @param dir
     * @return
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
    public EnemiesType getEnemiesType() {
        return this.enemyType;
    }
    
    /*@Override
    public void setMoving(final boolean b) {
        super.inMovement = b;
    }*/

}

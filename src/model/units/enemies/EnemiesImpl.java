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
    
    public EnemiesImpl(final Point pos, final Direction dir, final Dimension dim, final int lives, final int score, final int attack) {
        super(pos, dir, dim);
        this.enemyCollision = new EnemiesCollisionImpl(this);
        super.modifyLife(lives - 1);
        super.score = score;
        super.increaseAttack(attack - 1);
    }

    @Override
    public void move(final Direction dir, final Set<Rectangle> blockSet, final Hero hero) {
        this.enemyCollision.updateEntityRec(dir);
        if (this.enemyCollision.blockCollision(blockSet) && this.enemyCollision.heroCollision(hero)) {
            super.move(dir);
        }
    }
    
    private boolean checkCollision(final Direction dir, final Set<Rectangle> blockSet, final Hero hero) {
        if (this.enemyCollision.blockCollision(blockSet) && this.enemyCollision.heroCollision(hero)) {
            return false;
        }
        return true;
    }
    
    @Override
    public Direction getRandomDirection() {
        final Direction[] vet = Direction.values();
        return vet[new Random().nextInt(vet.length)];
    }
    
    private Direction getNewDirection(final Set<Rectangle> blockSet, final Hero hero, final Direction dir) {
        if (this.checkCollision(super.getDirection(), blockSet, hero)) {
            return dir;
        }
        return super.getDirection();
    }
    
    @Override
    public Collision getCollision() {
        return this.enemyCollision;
    }
   
    @Override
    public void updateMove(final Set<Rectangle> blockSet, final Hero hero, final Direction dir) {
        this.move(this.getNewDirection(blockSet, hero, dir), blockSet, hero);
    }

}

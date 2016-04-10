package model.utilities;

import java.awt.Dimension;
import java.awt.Point;

import model.Tile;
import model.units.Bomb;
import model.units.BombImpl;
import model.units.Hero;
import model.units.HeroImpl;
import model.units.enemy.Enemy;
import model.units.enemy.EnemyImpl;

public final class CopyFactory {

    private CopyFactory(){ }
    
    public static Tile getCopy(final Tile t){
        return new Tile(new Point(t.getPosition()), 
                new Dimension(t.getHitbox().width, t.getHitbox().height), 
                t.getType(), t.getPowerup());
    }
    
    public static Bomb getCopy(final Bomb b){
        Bomb bombCopy = new BombImpl(new Point(b.getPosition()), 
                new Dimension(b.getHitbox().width, b.getHitbox().height), 
                b.getRange());
        bombCopy.setPlanted(b.isPositioned());
        return bombCopy;
    }
    
    public static Hero getCopy(final Hero h){
        Hero heroCopy = new HeroImpl(new Point(h.getPosition()), h.getDirection(), 
                new Dimension(h.getHitbox().width, h.getHitbox().height));
        heroCopy.getDetonator().copy(h.getDetonator().getActualRange(), 
                h.getDetonator().getActualBombs(),
                h.getDetonator().getActualList());
        heroCopy.copy(h.isMoving(), h.getRemainingLives(), h.getAttack(), h.getScore(), h.isConfused(), h.hasKey());
        return heroCopy;
        
    }
    
    public static Enemy getCopy(final Enemy e) {
        Enemy enemyCopy = new EnemyImpl(new Point(e.getPosition()), e.getDirection(), new Dimension(e.getHitbox().width, e.getHitbox().height), e.getEnemyType());
        enemyCopy.copy(e.getRemainingLives(), e.getAttack(), e.getScore(), e.getDirection(), e.getEnemyType());
        return enemyCopy;
    }
}

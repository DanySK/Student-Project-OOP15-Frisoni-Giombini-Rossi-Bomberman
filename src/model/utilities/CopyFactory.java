package model.utilities;

import java.awt.Dimension;
import java.awt.Point;

import model.Tile;
import model.units.Bomb;
import model.units.BombImpl;

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
}

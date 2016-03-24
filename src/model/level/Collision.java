package model.level;

import java.awt.Rectangle;
import java.util.Set;
import java.util.function.Predicate;

import model.Tile;
import model.units.Direction;

public interface Collision {
    
    boolean blockCollision(final Set<Rectangle> blockSet);
    
    boolean fireCollision(final Set<Tile> afflictedTiles, final Rectangle recEntity);
    
    <X> boolean elementCollision(final Set<X> set, final Predicate<X> pred);
    
    boolean explosionIntersection(final Rectangle rec, final Rectangle eRec);
    
    public void updateEntityRec(final Direction dir);

}

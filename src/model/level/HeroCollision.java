package model.level;

import java.awt.Rectangle;
import java.util.Set;

import model.Tile;

public interface HeroCollision extends Collision{
    
    boolean powerUpCollision(final Set<Tile> powerUpSet);
    
    boolean bombCollision(final Set<Rectangle> bombSet, final Rectangle recEntity);

}

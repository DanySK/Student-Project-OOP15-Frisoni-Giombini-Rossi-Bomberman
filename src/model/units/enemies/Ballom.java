package model.units.enemies;

import java.awt.Dimension;
import java.awt.Point;

import model.units.Direction;

public class Ballom extends EnemiesImpl {
    
    private static final int LIVES = 1;
    private static final int SCORE = 100;
    private static final int ATTACK = 1;

    public Ballom(final Point pos, final Direction dir, final Dimension dim) {
        super(pos, dir, dim, LIVES, SCORE, ATTACK, EnemiesType.BALLOM);
    }    
}

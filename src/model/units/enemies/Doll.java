package model.units.enemies;

import java.awt.Dimension;
import java.awt.Point;

import model.units.Direction;

public class Doll extends EnemiesImpl {
    
    private static final int LIVES = 4;
    private static final int SCORE = 400;
    private static final int ATTACK = 1;

    public Doll(final Point pos, final Direction dir, final Dimension dim) {
        super(pos, dir, dim, LIVES, SCORE, ATTACK);
    }
}

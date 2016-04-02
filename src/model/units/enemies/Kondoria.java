package model.units.enemies;

import java.awt.Dimension;
import java.awt.Point;

import model.units.Direction;

public class Kondoria extends EnemiesImpl {
    
    private static final int LIVES = 8;
    private static final int SCORE = 1000;
    private static final int ATTACK = 2;

    public Kondoria(final Point pos, final Direction dir, final Dimension dim) {
        super(pos, dir, dim, LIVES, SCORE, ATTACK);
    }

}

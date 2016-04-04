package model.units.enemies;

import java.awt.Dimension;
import java.awt.Point;

import model.units.Direction;

public class Pass extends EnemiesImpl {
    
    private static final int LIVES = 10;
    private static final int SCORE = 4000;
    private static final int ATTACK = 3;

    public Pass(final Point pos, final Direction dir, final Dimension dim) {
        super(pos, dir, dim, LIVES, SCORE, ATTACK, EnemiesType.PASS);
    }

}

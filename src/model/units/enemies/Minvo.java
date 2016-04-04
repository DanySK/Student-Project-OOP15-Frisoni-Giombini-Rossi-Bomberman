package model.units.enemies;

import java.awt.Dimension;
import java.awt.Point;

import model.units.Direction;

public class Minvo extends EnemiesImpl {
    
    private static final int LIVES = 6;
    private static final int SCORE = 800;
    private static final int ATTACK = 2;

    public Minvo(final Point pos, final Direction dir, final Dimension dim) {
        super(pos, dir, dim, LIVES, SCORE, ATTACK, EnemiesType.MINVO);
    }

}

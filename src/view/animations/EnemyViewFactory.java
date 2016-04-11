package view.animations;

import model.units.enemy.Enemy;
import view.animations.unit.AbstractEnemyView;
import view.animations.unit.EntityAnimationView;

/**
 * This class associates an {@link EntityAnimationView} to each type of enemy.
 * It uses the Simple Factory pattern.
 *
 */
public class EnemyViewFactory {
    
    /**
     * Selects the view for the specified enemy.
     * 
     * @param enemy
     *          the enemy to represent
     * @return the view associated to the enemy's type
     */
    public static AbstractEnemyView getEnemyView(final Enemy enemy, final int fps) {
        switch(enemy.getEnemyType()) {
        case BALLOM:
            return new BallomView(enemy, fps);
        case MINVO:
            return new MinvoView(enemy, fps);
        case PASS:
            return new PassView(enemy, fps);
        default:
            throw new IllegalArgumentException();
        }
    }
}

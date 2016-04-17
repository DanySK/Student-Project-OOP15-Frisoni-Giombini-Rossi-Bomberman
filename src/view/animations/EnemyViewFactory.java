package view.animations;

import model.units.enemy.Enemy;
import view.animations.unit.AbstractEnemyView;
import view.animations.unit.EntityAnimationView;

/**
 * This class associates an {@link EntityAnimationView} to each type of enemy.
 * It uses the Simple Factory pattern.
 *
 */
public final class EnemyViewFactory {
    
    private EnemyViewFactory() { }
    
    /**
     * Selects the view for the specified enemy.
     * 
     * @param enemy
     *          the enemy to represent
     * @param fps
     *          the number of frame per second
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
            throw new IllegalArgumentException("There isn't a view animation associated to " + enemy.toString());
        }
    }
}

package view.animations.factory;

import model.units.enemy.Enemy;
import view.animations.BallomView;
import view.animations.MinvoView;
import view.animations.PassView;
import view.animations.unit.AbstractEnemyView;

/**
 * This class associates an {@link AbstractEnemyView} to each type of enemy.
 * It uses the Simple Factory pattern.
 * In a similar design a factory like this is defined as a static method, but it has
 * the disadvantage that is not possible subclass and change the behavior of the create method.
 *
 */
public class EnemyViewFactory {
    
    /**
     * Selects the view for the specified enemy.
     * 
     * @param enemy
     *          the enemy to represent
     * @param fps
     *          the number of frame per second
     * @return the view associated to the enemy's type
     */
    public AbstractEnemyView createEnemyView(final Enemy enemy, final int fps) {
        switch(enemy.getEnemyType()) {
        case BALLOM:
            return new BallomView(enemy, fps);
        case MINVO:
            return new MinvoView(enemy, fps);
        case PASS:
            return new PassView(enemy, fps);
        default:
            throw new IllegalArgumentException("There isn't a view animation associated to " + enemy.getEnemyType().toString());
        }
    }
}

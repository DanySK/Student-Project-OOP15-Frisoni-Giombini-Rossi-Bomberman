package view.animations;

import model.units.enemy.Enemy;
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
    public static EntityAnimationView getEnemyView(final Enemy enemy) {
        switch(enemy.getEnemyType()) {
        case BALLOM:
            return new BallomView(enemy, 60);
        case MINVO:
            return new MinvoView(enemy, 60);
        case PASS:
            return new PassView(enemy, 60);
        default:
            throw new IllegalArgumentException();
        }
    }
}

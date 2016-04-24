package model.level.collision;

import model.units.Entity;

/**
 * Implementation of {@link EnemyCollision}.
 */

public class EnemyCollisionImpl extends CollisionImpl implements EnemyCollision {

    /**
     * Constructs a new EnemyCollision object.
     * @param entity
     *          the hero entity
     */
    public EnemyCollisionImpl(final Entity entity) {
        super(entity);
    }

    @Override
    public boolean heroCollision(final Entity heroEntity) {
        if (this.entityRec.intersects(heroEntity.getHitbox())) {
            heroEntity.modifyLife(-this.entity.getAttack()); 
            return false;
        }
        return true;
    }
}

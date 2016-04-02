package model.level;

import model.units.Entity;

public class EnemiesCollisionImpl extends CollisionImpl implements EnemiesCollision {

    public EnemiesCollisionImpl(Entity entity) {
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

package model.units;

import java.util.Random;


/**
 * The entire list of powerups realized in the game.
 * and their messages.
 */

public enum PowerUpType {
    
    /**
     * Increase attack.
     */
    ATTACK() {
        public void doApply(final Hero hero) {
            hero.increaseAttack(INC);
        }
    },
    
    /**
     * Increase lives. 
     */
    LIFE() {
        public void doApply(final Hero hero) {
            hero.modifyLife(INC);
        }
    },
    
    /**
     * Increase bomb's number.
     */
    BOMB() {
        @Override
        public void doApply(final Hero hero) {
            hero.getDetonator().increaseBombs();
        }
    },
    
    /**
     * Increase bomb's range.
     */
    RANGE() {
        @Override
        public void doApply(final Hero hero) {
            hero.getDetonator().increaseRange();
        }
    },
    
    /**
     * Set flamepass.
     */
    CONFUSION_OFF() {
        @Override
        public void doApply(final Hero hero) {
            hero.setConfusion(false);
        }
    },
    
    /**
     * Set confusion.
     */
    CONFUSION_ON() {
        @Override
        public void doApply(final Hero hero) {
            hero.setConfusion(true);
        }
    },
    
    /**
     * Decrease lives.
     */
    HURT() {
        @Override
        public void doApply(final Hero hero) {
            hero.modifyLife(DEC);
        }
    },
    
    /**
     * Choose randomly a powerup.
     */
    MYSTERY() {
        @Override
        public void doApply(final Hero hero) {
            final Random random = new Random();
            PowerUpType powerup = MYSTERY;
            while (powerup == MYSTERY || powerup == KEY) {
                powerup = PowerUpType.values()[random.nextInt(PowerUpType.values().length)];
            }
            powerup.doApply(hero);
        }
    },
    
    /**
     * Set the key.
     */
    KEY() {
        @Override
        public void doApply(final Hero hero) {
            hero.setKey();
        }
    };

    private static final int INC = 1;
    private static final int DEC = -1;
    
    /**
     * Applies the powerup's power.
     * 
     * @param hero
     *          the entity that benefits powerup's power
     */
    public void apply(final Hero hero) {
        this.doApply(hero);
    }
    
    /**
     * This method applies the power up's effect to the hero.
     * 
     * @param hero
     *          the hero to who apply power up's effect
     */
    public abstract void doApply(final Hero hero);
    
}


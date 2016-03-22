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
    ATTACK(){
        public void doApply(final Hero hero){
            hero.increaseAttack();
        }
    },
    
    /**
     * Increase lives. 
     */
    LIFE(){
        public void doApply(final Hero hero){
            hero.modifyLife(INC_LIFE);
        }
    },
    
    /**
     * Increase bomb's number.
     */
    BOMB() {
        @Override
        public void doApply(final Hero hero) {
            hero.increaseBomb();
        }
    },
    
    /**
     * Increase bomb's range.
     */
    RANGE() {
        @Override
        public void doApply(final Hero hero) {
            hero.increaseRange();
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
            hero.modifyLife(DEC_LIFE);
        }
    },
    
    /**
     * Choose randomly a powerup.
     */
    MYSTERY() {
        @Override
        public void doApply(final Hero hero) {
            Random random = new Random();
            PowerUpType powerup = MYSTERY;
            while (powerup == MYSTERY) {
                powerup = PowerUpType.values()[random.nextInt(PowerUpType.values().length)];
            }
            powerup.doApply(hero);
        }
    },
    
    /**
     * Set the key.
     */
    KEY(){
        @Override
        public void doApply(final Hero hero) {
            hero.setKey();
        }
    };

    private static final int INC_LIFE = 1;
    private static final int DEC_LIFE = -1;
    
    /**
     * Applies the powerup's power.
     * 
     * @param hero
     *          the entity that benefits powerup's power
     */
    public void apply(final Hero hero){
        this.doApply(hero);
    }
    
    public abstract void doApply(final Hero hero);
    
}


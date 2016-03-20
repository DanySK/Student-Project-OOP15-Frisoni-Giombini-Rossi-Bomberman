package model.units;

import java.util.Random;

import view.LanguageHandler;

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
    FLAMEPASS() {
        @Override
        public void doApply(final Hero hero) {
            hero.setFlamepass();
        }
    },
    
    /**
     * Set confusion.
     */
    CONFUSION() {
        @Override
        public void doApply(final Hero hero) {
            hero.setConfusion();
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

    static {
        ATTACK.message = LanguageHandler.getHandler().getLocaleResource().getString("attack");
        LIFE.message = LanguageHandler.getHandler().getLocaleResource().getString("life");
        BOMB.message = LanguageHandler.getHandler().getLocaleResource().getString("bomb");
        RANGE.message = LanguageHandler.getHandler().getLocaleResource().getString("range");
        FLAMEPASS.message = LanguageHandler.getHandler().getLocaleResource().getString("flamepass");
        CONFUSION.message = LanguageHandler.getHandler().getLocaleResource().getString("confusion");
        HURT.message = LanguageHandler.getHandler().getLocaleResource().getString("hurt");
        MYSTERY.message = " ";
        KEY.message = " ";
    }


    private static final int INC_LIFE = 1;
    private static final int DEC_LIFE = -1;
    private String message;
    
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

    /**
     * Gets powerup's message.
     * 
     * @return powerup's message
     */
    public String getMessage(){
        return this.message;
    }
   
}


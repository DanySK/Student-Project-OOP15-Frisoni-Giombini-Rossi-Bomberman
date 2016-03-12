package model.units;

import java.util.Random;

import view.LanguageHandler;

public enum PowerUpType {
    ATTACK(){
        public void doApply(Hero hero){
            hero.increaseAttack();
        }
    },
    LIFE(){
        public void doApply(Hero hero){
            hero.modifyLife(INC_LIFE);
        }
    },
    BOMB() {
        @Override
        public void doApply(Hero hero) {
            hero.increaseBomb();
        }
    },
    RANGE() {
        @Override
        public void doApply(Hero hero) {
            //aumenta raggio bomba
        }
    },
    FLAMEPASS() {
        @Override
        public void doApply(Hero hero) {
            hero.setFlamepass();
        }
    },
    CONFUSION() {
        @Override
        public void doApply(Hero hero) {
            hero.setConfusion();
        }
    },
    HURT() {
        @Override
        public void doApply(Hero hero) {
            hero.modifyLife(DEC_LIFE);
        }
    },
    MYSTERY() {
        @Override
        public void doApply(Hero hero) {
            Random random = new Random();
            PowerUpType powerup = MYSTERY;
            while (powerup == MYSTERY) {
                powerup = PowerUpType.values()[random.nextInt(PowerUpType.values().length)];
            }
            powerup.doApply(hero);
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
    }
    
    
    private static final int INC_LIFE = 1;
    private static final int DEC_LIFE = -1;
    private String message;
    
    public void apply(Hero hero){
        this.doApply(hero);
    }
    
    public abstract void doApply(Hero hero);
    
    public String getMessage(){
        return this.message;
    }
}


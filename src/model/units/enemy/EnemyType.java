package model.units.enemy;

public enum EnemyType {
    
    BALLOM(1, 100, 1),
    DOLL(2, 400, 1),
    KONDORIA(3, 1000, 2),
    MINVO(4, 800, 2),
    PASS(5, 4000, 3);
    
    private final int lives;
    private final int score;
    private final int attack;
    
    private EnemyType(final int lives, final int score, final int attack) {
        this.lives = lives;
        this.score = score;
        this.attack = attack;
    }
    
    public int getEnemyLives() {
        return this.lives;
    }
    
    public int getEnemyScore() {
        return this.score;
    }
    
    public int getEnemyAttack() {
        return this.attack;
    }
    
}

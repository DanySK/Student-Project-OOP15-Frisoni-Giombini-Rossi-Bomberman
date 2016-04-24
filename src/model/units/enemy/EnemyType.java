package model.units.enemy;


/**
 * The entire list of enemy's type.
 */
public enum EnemyType {
    
    BALLOM(1, 100, 1),
    MINVO(2, 800, 1),
    PASS(3, 4000, 2);
    
    private final int live;
    private final int score;
    private final int attack;
    
    /**
     * Constructor for EnemyType.
     * @param live
     *          the enemy's live
     * @param score
     *          the enemy's score
     * @param attack
     *          the enemy's attack
     */
    private EnemyType(final int live, final int score, final int attack) {
        this.live = live;
        this.score = score;
        this.attack = attack;
    }
    
    /**
     * This method return the enemy's live.
     * @return the enemy's live
     */
    public int getEnemyLives() {
        return this.live;
    }
    
    /**
     * This method return the enemy's score.
     * @return the enemy's score
     */
    public int getEnemyScore() {
        return this.score;
    }
    
    /**
     * This method return the enemy's attack.
     * @return the enemy's attack
     */
    public int getEnemyAttack() {
        return this.attack;
    }
}

package model.test;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import model.units.Bomb;
import model.units.BombImpl;
import model.units.Direction;
import model.units.Hero;
import model.units.HeroImpl;
import model.units.PowerUpType;
import model.units.Tile;
import model.units.TileImpl;
import model.units.TileType;
import model.units.enemy.Enemy;
import model.units.enemy.EnemyImpl;
import model.units.enemy.EnemyType;
import model.utilities.MapPoint;

/**
 * This class is used to test the correct operation of the collisions.
 */
public class TestCollision {

    private static final int TILE_DIMENSION = 10;
    private static final int N_TILES = 5;

    /**
     * This test verifies the correct collision with blocks.
     */
    @Test
    public void testBlockCollision() {
        final Set<Tile> blockSet = this.createTiles();
        final Hero hero = this.createHero(1, 1);
        hero.move(Direction.LEFT, blockSet.stream().filter(tile -> tile.getType()
                .equals(TileType.CONCRETE)).map(tile -> tile.getHitbox())
                .collect(Collectors.toSet()), new HashSet<>(), new HashSet<>());
        Assert.assertTrue(hero.getPosition().equals(new Point(10, 10)));
        hero.move(Direction.RIGHT, blockSet.stream().filter(tile -> tile.getType()
                .equals(TileType.CONCRETE)).map(tile -> tile.getHitbox())
                .collect(Collectors.toSet()), new HashSet<>(), new HashSet<>());
        Assert.assertTrue(hero.getPosition().equals(new Point(11, 10)));
        hero.move(Direction.LEFT, blockSet.stream().filter(tile -> tile.getType()
                .equals(TileType.CONCRETE)).map(tile -> tile.getHitbox())
                .collect(Collectors.toSet()), new HashSet<>(), new HashSet<>());
        Assert.assertTrue(hero.getPosition().equals(new Point(10, 10)));
        hero.move(Direction.UP, blockSet.stream().filter(tile -> tile.getType()
                .equals(TileType.CONCRETE)).map(tile -> tile.getHitbox())
                .collect(Collectors.toSet()), new HashSet<>(), new HashSet<>());
        Assert.assertTrue(hero.getPosition().equals(new Point(10, 10)));
        hero.move(Direction.DOWN, blockSet.stream().filter(tile -> tile.getType()
                .equals(TileType.CONCRETE)).map(tile -> tile.getHitbox())
                .collect(Collectors.toSet()), new HashSet<>(), new HashSet<>());
        Assert.assertTrue(hero.getPosition().equals(new Point(10, 11)));
        final Enemy enemy = this.createEnemy(3, 3);
        enemy.move(Direction.RIGHT, blockSet.stream().filter(tile -> tile.getType()
                .equals(TileType.CONCRETE)).map(tile -> tile.getHitbox())
                .collect(Collectors.toSet()), hero, new HashSet<>());
        Assert.assertTrue(enemy.getPosition().equals(new Point(30, 30)));
        enemy.move(Direction.LEFT, blockSet.stream().filter(tile -> tile.getType()
                .equals(TileType.CONCRETE)).map(tile -> tile.getHitbox())
                .collect(Collectors.toSet()), hero, new HashSet<>());
        Assert.assertTrue(enemy.getPosition().equals(new Point(29, 30)));
        enemy.move(Direction.RIGHT, blockSet.stream().filter(tile -> tile.getType()
                .equals(TileType.CONCRETE)).map(tile -> tile.getHitbox())
                .collect(Collectors.toSet()), hero, new HashSet<>());
        Assert.assertTrue(enemy.getPosition().equals(new Point(30, 30)));
        enemy.move(Direction.DOWN, blockSet.stream().filter(tile -> tile.getType()
                .equals(TileType.CONCRETE)).map(tile -> tile.getHitbox())
                .collect(Collectors.toSet()), hero, new HashSet<>());
        Assert.assertTrue(enemy.getPosition().equals(new Point(30, 30)));
        enemy.move(Direction.UP, blockSet.stream().filter(tile -> tile.getType()
                .equals(TileType.CONCRETE)).map(tile -> tile.getHitbox())
                .collect(Collectors.toSet()), hero, new HashSet<>());
        Assert.assertTrue(enemy.getPosition().equals(new Point(30, 29)));
    }

    /**
     * This test verifies the correct collision with bomb.
     */
    @Test
    public void testBombCollision() {
        final Set<Tile> blockSet = this.createTiles();
        final Set<Bomb> bombSet = new HashSet<>();
        bombSet.add(new BombImpl(new Point(1, 2), 
                new Dimension(TILE_DIMENSION, TILE_DIMENSION), 1));
        final Hero hero = this.createHero(1, 1);
        hero.move(Direction.RIGHT, blockSet.stream().map(tile -> tile.getHitbox())
                .collect(Collectors.toSet()), bombSet.stream().map(tile -> tile.getHitbox())
                .collect(Collectors.toSet()), new HashSet<>());
        Assert.assertTrue(hero.getPosition().equals(new Point(10, 10)));
        final Enemy enemy = this.createEnemy(3, 3);
        bombSet.add(new BombImpl(new Point(2, 3), 
                new Dimension(TILE_DIMENSION, TILE_DIMENSION), 1));
        enemy.move(Direction.UP, blockSet.stream().map(tile -> tile.getHitbox())
                .collect(Collectors.toSet()), hero, bombSet.stream().map(tile -> tile.getHitbox())
                .collect(Collectors.toSet()));
        Assert.assertTrue(enemy.getPosition().equals(new Point(30, 30)));
    }
    
    /**
     * This test verifies the correct collision with power up.
     */
    @Test
    public void testPowerUpCollision() {
        final Set<Tile> blockSet = this.createTiles();
        final Hero hero = this.createHero(1, 1);
        final Set<Tile> powerUpSet = new HashSet<>();
        powerUpSet.add(new TileImpl(new Point(MapPoint.getCoordinate(2, TILE_DIMENSION),
                MapPoint.getCoordinate(1, TILE_DIMENSION)), 
                new Dimension(TILE_DIMENSION, TILE_DIMENSION),
                TileType.POWERUP_STATUS, Optional.of(PowerUpType.KEY)));
        powerUpSet.add(new TileImpl(new Point(MapPoint.getCoordinate(1, TILE_DIMENSION),
                MapPoint.getCoordinate(2, TILE_DIMENSION)), 
                new Dimension(TILE_DIMENSION, TILE_DIMENSION),
                TileType.POWERUP_STATUS, Optional.of(PowerUpType.ATTACK)));
        Assert.assertFalse(hero.hasKey());
        final int attackHero = hero.getAttack();
        hero.move(Direction.RIGHT, blockSet.stream().filter(tile -> tile.getType()
                .equals(TileType.CONCRETE)).map(tile -> tile.getHitbox())
                .collect(Collectors.toSet()), new HashSet<>(), powerUpSet);
        Assert.assertTrue(hero.hasKey());
        hero.move(Direction.LEFT, blockSet.stream().filter(tile -> tile.getType()
                .equals(TileType.CONCRETE)).map(tile -> tile.getHitbox())
                .collect(Collectors.toSet()), new HashSet<>(), powerUpSet);
        hero.move(Direction.DOWN, blockSet.stream().filter(tile -> tile.getType()
                .equals(TileType.CONCRETE)).map(tile -> tile.getHitbox())
                .collect(Collectors.toSet()), new HashSet<>(), powerUpSet);
        Assert.assertNotEquals(attackHero, hero.getAttack());
        Assert.assertEquals(hero.getAttack(), attackHero + 1);
    }

    /**
     * This test verifies the correct collision between enemy and hero.
     */
    @Test
    public void testHeroEnemyCollision() {
        final Set<Tile> blockSet = this.createTiles();
        final Hero hero = this.createHero(1, 1);
        final Enemy enemy = this.createEnemy(2, 1);
        final int heroLives = hero.getRemainingLives();
        enemy.move(Direction.LEFT, blockSet.stream().filter(tile -> tile.getType()
                .equals(TileType.CONCRETE)).map(tile -> tile.getHitbox())
                .collect(Collectors.toSet()), hero, new HashSet<>());
        Assert.assertNotEquals(heroLives, hero.getRemainingLives());
        Assert.assertEquals(hero.getRemainingLives(), heroLives - enemy.getAttack());
    }

    /**
     * This method creates a set of files corresponding to the game.
     * @return the set of tiles
     */
    private Set<Tile> createTiles() {
        final Set<Tile> blockSet = new HashSet<>();
        TileType type = TileType.WALKABLE;
        for (int i = 0; i < N_TILES; i++) {
            for (int j = 0; j < N_TILES; j++) {
                type = i == 0 || j == 0 || i == N_TILES - 1 || j == N_TILES - 1
                        || i % 2 == 0 && j % 2 == 0 ? TileType.CONCRETE : TileType.WALKABLE;
                blockSet.add(new TileImpl(new Point(MapPoint.getCoordinate(i, TILE_DIMENSION),
                        MapPoint.getCoordinate(j, TILE_DIMENSION)), 
                        new Dimension(TILE_DIMENSION, TILE_DIMENSION),
                        type, Optional.of(PowerUpType.KEY)));
            }
        }
        return blockSet;
    }

    /**
     * This method creates the hero entity.
     * @param posX
     *          parameter X corresponding to the position of the hero
     * @param posY
     *          parameter Y corresponding to the position of the hero
     * @return the hero entity
     */
    private Hero createHero(final int posX, final int posY) {
        return new HeroImpl(MapPoint.getPos(new Point(posX, posY), TILE_DIMENSION),
                new Dimension(TILE_DIMENSION, TILE_DIMENSION));
    }

    /**
     * This method creates the enemy entity.
     * @param posX
     *          parameter X corresponding to the position of the enemy
     * @param posY
     *          parameter Y corresponding to the position of the enemy
     * @return the enemmy entity
     */
    private Enemy createEnemy(final int posX, final int posY) {
        return new EnemyImpl(MapPoint.getPos(new Point(posX, posY), TILE_DIMENSION),
                new Dimension(TILE_DIMENSION, TILE_DIMENSION), EnemyType.BALLOM);
    }
}

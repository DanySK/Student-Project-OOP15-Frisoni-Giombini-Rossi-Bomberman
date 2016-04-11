package view.animations.unit;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import model.units.Entity;
import model.units.enemy.Enemy;
import view.GUIFactory;

/**
 * This class handles the graphical aspects related only to enemies.
 * In this implementation there is an indicator of the remaining lives
 * that is very useful in the game.
 *
 */
public abstract class AbstractEnemyView extends AbstractEntityView {

    private final Enemy enemy;
    
    /**
     * Constructs a new enemy view.
     * 
     * @param enemy
     *          the enemy to represent
     * @param fps
     *          the number of frame-per-second
     */
    public AbstractEnemyView(final Enemy enemy, final int fps) {
        super(enemy, fps);
        this.enemy = enemy;
    }

    @Override
    public Image getImage() {
        final Font font = new GUIFactory.Standard().getLifeDetailFont();
        final FontRenderContext frc = new FontRenderContext(null, true, true);
        final Rectangle2D r = font.getStringBounds(String.valueOf(((Entity) getLevelElement()).getRemainingLives()), frc);
        
        final BufferedImage b = new BufferedImage(super.getImage().getWidth(null), super.getImage().getHeight(null) + (int) Math.round(r.getHeight()), BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = b.createGraphics();
        g.drawImage(super.getImage(), 0, 0, null);
        g.setFont(font);
        g.drawString(String.valueOf(((Entity) getLevelElement()).getRemainingLives()), 0, super.getImage().getHeight(null));
        return b;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((enemy == null) ? 0 : enemy.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof AbstractEnemyView && this.enemy.equals(((AbstractEnemyView) obj).enemy);
    }
    
    @Override
    public Enemy getLevelElement() {
        return this.enemy;
    }
}

package view.animations;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import view.ImageLoader;
import view.ImageLoader.GameImage;

/**
 * This class represents a sprite of the game. 
 *
 */
public final class Sprite {
    
    // The sheet containing all the sprite images of the game
    private static BufferedImage spriteSheet;
    
    // The dimension of each sprite in the sheet
    private static final int SPRITE_HEIGHT = 32;
    private static final int SPRITE_WIDTH = 24;
    
    private Sprite() {}
    
    /**
     * This method returns the sprite in the specified position of the sheet.
     * 
     * @param xGrid
     *          the x coordinate (column)
     * @param yGrid
     *          the y coordinate (row)
     * @return the sprite image
     */
    public static BufferedImage getSprite(final Point pointGrid) {
        if (spriteSheet == null) {
            spriteSheet = ImageLoader.getLoader().createBufferedImage(GameImage.SPRITE_SHEET);
        }
        return spriteSheet.getSubimage(pointGrid.x * SPRITE_WIDTH, pointGrid.y * SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT);
    }
    
    /**
     * This method returns the sprite in the specified position of the sheet.
     * 
     * @param coordinates
     *          the coordinates where find the sprite
     * @return a list with all the sprites in the specified positions
     */
    public static List<BufferedImage> getSprites(final Point... pointGrids) {
        return Arrays.stream(pointGrids).map(p -> getSprite(p)).collect(Collectors.toList());
    }
    
    /**
     * @return the height of a sprite
     */
    public static int getSpriteHeight() {
        return SPRITE_HEIGHT;
    }
    
    /**
     * @return the width of a sprite
     */
    public static int getSpriteWidth () {
        return SPRITE_WIDTH;
    }
}

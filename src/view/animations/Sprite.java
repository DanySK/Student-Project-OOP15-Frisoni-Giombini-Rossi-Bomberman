package view.animations;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import view.ImageLoader;
import view.ImageLoader.GameImage;

/**
 * This class uses a Singleton Pattern to get one or more sprites of the game
 * from the sprite-sheet.
 *
 */
public final class Sprite {

    // The sheet containing all the sprite images of the game
    private static volatile BufferedImage spriteSheet;

    // The dimension of each sprite in the sheet
    private static final int SPRITE_HEIGHT = 31;
    private static final int SPRITE_WIDTH = 18;

    private Sprite() { }

    /**
     * This method returns the sprite in the specified position of the sheet.
     * 
     * @param pointGrid
     *          the coordinate to use
     * @return the sprite image
     */
    public static BufferedImage getSprite(final Point pointGrid) {
        if (spriteSheet == null) {
            synchronized (Sprite.class) {
                if (spriteSheet == null) {
                    spriteSheet = ImageLoader.getLoader().createBufferedImage(GameImage.SPRITE_SHEET);
                }
            }
        }
        return spriteSheet.getSubimage(pointGrid.x * SPRITE_WIDTH, pointGrid.y * SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT);
    }

    /**
     * This method returns the sprite in the specified position of the sheet.
     * 
     * @param pointGrids
     *          the coordinates where find the sprites
     * @return a list with all the sprites in the specified positions
     */
    public static List<BufferedImage> getSprites(final Point... pointGrids) {
        return Arrays.stream(pointGrids).map(p -> getSprite(p)).collect(Collectors.toList());
    }

    /**
     * @return the height of a sprite.
     */
    public static int getSpriteHeight() {
        return SPRITE_HEIGHT;
    }

    /**
     * @return the width of a sprite.
     */
    public static int getSpriteWidth() {
        return SPRITE_WIDTH;
    }
}

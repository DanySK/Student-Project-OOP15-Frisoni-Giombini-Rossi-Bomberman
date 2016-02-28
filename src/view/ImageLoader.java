package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import view.menu.MenuView;

/**
 * This class uses a Singleton Pattern to make an image loader
 * for the application.
 *
 */
public final class ImageLoader {

    private static final String IMAGES_FOLDER = "/images/";
    private static final String MENU_FOLDER = IMAGES_FOLDER + "menu/";
    private static final String TILES_FOLDER = IMAGES_FOLDER + "tiles/";
    private static final String SPRITES_FOLDER = IMAGES_FOLDER + "sprites/";

    private static volatile ImageLoader il;

    /**
     * Enumeration for all game images.
     */
    public enum GameImage {
        /**
         * Game's icon.
         */
        ICON(IMAGES_FOLDER + "icon.png"),
        /**
         * Game's title.
         */
        TITLE(MENU_FOLDER + "title.png"),
        /**
         * Bomberman's image.
         */
        BOMBERMAN(MENU_FOLDER + "bomberman.png"),
        /**
         * Bomberman's image.
         */
        BOMBERMAN_2(MENU_FOLDER + "bomberman_2.png"),
        /**
         * Play button image.
         */
        PLAY(MENU_FOLDER + "play.png"),
        /**
         * Ranking button image.
         */
        SCORES(MENU_FOLDER + "ranking.png"),
        /**
         * Settings button image.
         */
        SETTINGS(MENU_FOLDER + "settings.png"),
        /**
         * Credits button image.
         */
        CREDITS(MENU_FOLDER + "credits.png"),
        /**
         * Walkable floor.
         */
        WALKABLE(TILES_FOLDER + "walkable.png"),
        /**
         * Concrete wall.
         */
        CONCRETE(TILES_FOLDER + "concrete.png"),
        /**
         * Rubble wall.
         */
        RUBBLE(TILES_FOLDER + "rubble.png"),
        /**
         * Sprite sheet.
         */
        SPRITE_SHEET(SPRITES_FOLDER + "sprite_sheet.png");

        private final String path;

        /**
         * Creates a new GameImage.
         * 
         * @param path
         */
        GameImage(final String path) {
            this.path = path;
        }

        /**
         * @return the path of the game image.
         */
        public String getPath() {
            return this.path;
        }
    }

    /**
     * Creates a new ImageLoader.
     */
    private ImageLoader() { }

    /**
     * This method returns the ImageLoader.
     * If the ImageLoader is null it creates a new one on the first call.
     * This way the resources are loaded only if necessary.
     * 
     * @return the ImageLoader.
     */
    public static ImageLoader getLoader() {
        if (il == null) {
            synchronized (ImageLoader.class) {
                if (il == null) {
                    il = new ImageLoader();
                }
            }
        }
        return il;
    }

    /**
     * Creates an ImageIcon from the specified GameImage.
     * 
     * @param img
     *          the GameImage to use
     * @return the ImageIcon.
     */
    public ImageIcon createImageIcon(final GameImage img) {
        final java.net.URL imgURL = MenuView.class.getResource(img.getPath());
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        }
        throw new IllegalArgumentException("Couldn't find file: " + img.getPath());
    }

    /**
     * Creates an Image from the specified GameImage.
     * 
     * @param img
     *          the GameImage to use
     * @return the Image
     */
    public Image createImage(final GameImage img) {
        return createImageIcon(img).getImage();
    }

    /**
     * Creates an Image with a certain size from the specified GameImage's path.
     * 
     * @param img
     *          the GameImage to use
     * @param width
     *          the width of the image
     * @param height
     *          the height of the image
     * @return the sized Image
     */
    public Image createImageOfSize(final GameImage img, final int width, final int height) {
        return createImage(img).getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }

    /**
     * Creates a BufferedImage from the specified GameImage.
     * 
     * @param img
     *          the GameImage to use
     * @return the BufferedImage
     */
    public BufferedImage createBufferedImage(final GameImage img) {
        final ImageIcon icon = createImageIcon(img);
        final BufferedImage bi = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        final Graphics g = bi.createGraphics();
        icon.paintIcon(null, g, 0,0);
        g.dispose();
        return bi;
    }
}
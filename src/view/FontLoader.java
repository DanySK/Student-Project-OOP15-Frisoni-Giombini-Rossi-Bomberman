package view;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

/**
 * An utility class to register fonts.
 * 
 */
public final class FontLoader {

    private static final String BASE_PATH = "/font/";

    private FontLoader() {
    }

    /**
     * Registers a font.
     * @param name
     *          the font name
     */
    public static void loadFont(final String name) {
        try {
            final InputStream fontStream = FontLoader.class.getResourceAsStream(BASE_PATH + name);
            final Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}
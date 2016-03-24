package view;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

/**
 * An utility class to register fonts.
 * 
 */
public final class FontLoader {

    private static final String BASE_PATH = "/font/";

    private FontLoader() { }

    /**
     * Registers a font.
     * 
     * @param name
     *          the font name
     */
    public static void loadFont(final String name) {
        final InputStream fontStream = FontLoader.class.getResourceAsStream(BASE_PATH + name);
        try {
            final Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
        } catch (final FontFormatException e) {
            System.err.println("The font format is not valid: " + e.getMessage());
        } catch (final IOException e) {
            System.err.println("I/O error during the font loading: " + e.getMessage());
        }
    }
}
package main;

import javax.swing.SwingUtilities;

import controller.MenuController;
import view.FontLoader;
import view.SoundEffect;
import view.menu.MenuFrameImpl;

/**
 * This is the launcher class for Bomberman game with MVC implementation.
 */
public final class Bomberman {

    private Bomberman() {}
    
    /**
     * The entry point for the application.
     * 
     * @param args
     *          not used
     */
    public static void main(final String... args) {
        
        // Loads the customized font
        FontLoader.loadFont("charbb_reg.ttf");

        // Pre-loads all the sound files
        SoundEffect.init();
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuController(new MenuFrameImpl());
            }
        });
    }
}

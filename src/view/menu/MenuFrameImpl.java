package view.menu;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Objects;

import javax.swing.JFrame;

import view.ImageLoader;
import view.SoundEffect;
import view.ImageLoader.GameImage;

/**
 * The main frame of the menu game.
 * It contains all the panels that can be shown in a {@link CardLayout}.
 * It defines also the starting page and the background music.
 *
 */
public final class MenuFrameImpl implements MenuFrame {
    
    private static final String FRAME_NAME = "Bomberman";
    
    private static final double WIDTH_PERC = 0.5;
    private static final double HEIGHT_PERC = 0.6;
    
    private static volatile MenuFrame menuFrame;
    private final JFrame frame;

    /**
     * Constructs a new MainFrame.
     */
    private MenuFrameImpl() {
        this.frame = new JFrame();
        initialize();
        SoundEffect.THEME.playLoop();
    }

    /**
     * This method returns the MenuFrame.
     * If the MenuFrame is null it creates a new one on the first call.
     * 
     * @return the menu frame.
     */
    public static MenuFrame getMenuFrame() {
        if (menuFrame == null) {
            synchronized (MenuFrameImpl.class) {
                if (menuFrame == null) {
                    menuFrame = new MenuFrameImpl();
                }
            }
        }
        return menuFrame;
    }
    
    private void initialize() {
        this.frame.setTitle(FRAME_NAME);
        this.frame.setIconImage(ImageLoader.getLoader().createImage(GameImage.ICON));
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        /*
         * Registers the panels of the CardLayout.
         */
        this.frame.getContentPane().setLayout(new CardLayout());
        for (final MenuCard m : MenuCard.values()) {
            frame.getContentPane().add(m.getPanel(), m.name());
        }
        
        /* 
         * Sets the frame's dimension according to the resolution of the screen.
         * It is MUCH better than manually specify the size of a window in pixel:
         * it takes into account the current resolution.
         */
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setSize((int) (screenSize.getWidth() * WIDTH_PERC), (int) (screenSize.getHeight() * HEIGHT_PERC));

        /*
         * Instead of appearing at (0,0), upper left corner of the screen, this
         * flag makes the OS window manager take care of the default positioning
         * on screen. Results may vary, but it is generally the best choice.
         */
        this.frame.setLocationByPlatform(true);
    }
    
    @Override
    public void initView() {
        this.frame.setVisible(true);
    }

    @Override
    public void replaceCard(final MenuCard card) {
        Objects.requireNonNull(card);
        final CardLayout cl = (CardLayout) (this.frame.getContentPane().getLayout());
        cl.show(this.frame.getContentPane(), card.name());
    }

    @Override
    public void closeView() {
        this.frame.dispose();
        SoundEffect.THEME.stop();
    }
}

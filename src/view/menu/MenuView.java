package view.menu;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import view.GUIFactory;
import view.ImageLoader;
import view.ImageLoader.GameImage;
import view.LanguageHandler;

/**
 * This class manages the main menu of the game.
 * 
 */
public class MenuView extends JPanel {

    /**
     * Auto-generated UID.
     */
    private static final long serialVersionUID = 8974666695408514906L;

    // Constants for the view's customization
    private static final Insets TITLE_INSETS = new Insets(20, 0, 20, 0);
    private static final Insets BUTTON_INSETS = new Insets(-15, 20, 20, 20);
    private static final Insets IMAGE_INSETS = new Insets(20, 20, 20, 40);

    private MenuObserver observer;
    
    /**
     * Creates a new panel.
     */
    public MenuView() {
        LanguageHandler.getHandler().addEObserver((s, msg) -> {
            this.removeAll();
            initialize();
            this.revalidate();
            this.repaint();
        });
        initialize();
    }

    /**
     * Initializes the contents of the panel.
     */
    private void initialize() {
        final MenuStrategy strategy = new MenuStrategyImpl();
        final GUIFactory factory = new GUIFactory.Standard();

        // Sets the panel layout dynamically
        final JPanel panel = factory.createGradientPanel();
        final GridBagLayout gblPanel = new GridBagLayout();
        gblPanel.columnWeights = new double[]{2.0, 1.0};
        gblPanel.rowWeights = IntStream.range(0, strategy.getButtons().size() + 1).mapToDouble(i -> 1.0).toArray();
        panel.setLayout(gblPanel);

        // Sets the starting constraints
        final GridBagConstraints cnst = new GridBagConstraints();
        cnst.gridx = 0;
        cnst.gridy = 0;
        cnst.fill = GridBagConstraints.BOTH;

        // Sets title menu
        final JLabel lblTitle = new JLabel();
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setIcon(ImageLoader.getLoader().createImageIcon(GameImage.TITLE));
        cnst.gridwidth = 2;
        cnst.insets = TITLE_INSETS;
        panel.add(lblTitle, cnst);
        cnst.gridy++;

        // Sets buttons
        cnst.gridwidth = 1;
        cnst.insets = BUTTON_INSETS;
        strategy.getButtons().forEach(b -> {
            final JButton button = factory.createMenuButton(b.getName(), b.getIcon());
            button.addActionListener(e -> b.clickEvent(observer));
            panel.add(button, cnst);
            cnst.gridy++;
        });

        // Sets stretchable image
        final JLabel lblImage = new JLabel();
        lblImage.setIcon(new StretchIcon(ImageLoader.getLoader().createImage(GameImage.BOMBERMAN)));
        cnst.gridheight = strategy.getButtons().size();
        cnst.insets = IMAGE_INSETS;
        cnst.gridx = 1;
        cnst.gridy = 1;
        panel.add(lblImage, cnst);

        this.setLayout(new BorderLayout());
        this.add(panel);
    }

    /**
     * Set the observer of the MenuView.
     * 
     * @param observer
     *          the observer to use
     */
    public void setObserver(final MenuObserver observer) {
        this.observer = observer;
    }

    /**
     * This interface indicates the operations that an observer
     * of the MenuView can do.
     *
     */
    public interface MenuObserver {

        /**
         * Starts the game.
         */
        void play();
        
        /**
         * Show the scores of the player.
         */
        void ranking();

        /**
         * Show settings menu.
         */
        void settings();

        /**
         * Show some information about the author of the game.
         */
        void credits();
    }
}

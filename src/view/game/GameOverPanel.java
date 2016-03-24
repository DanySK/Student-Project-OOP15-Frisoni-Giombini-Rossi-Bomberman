package view.game;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import view.GUIFactory;
import view.ImageLoader;
import view.ImageLoader.GameImage;
import view.LanguageHandler;
import view.menu.components.StretchIcon;

/**
 * This class handles a {@link JPanel} to display at the end of the game.
 *
 */
public class GameOverPanel extends JPanel {
    
    /**
     * Auto-generated UID.
     */
    private static final long serialVersionUID = -8714937523727340290L;

    private GameOverObserver observer;
    
    /**
     * Creates a GameOverPanel.
     */
    public GameOverPanel() {
        initialize();
    }
    
    /**
     * Initializes the contents of the panel.
     */
    private void initialize() {
        final GUIFactory factory = new GUIFactory.Standard();

        // Sets the panel layout dynamically
        this.setBackground(Color.BLACK);
        final GridBagLayout gblPanel = new GridBagLayout();
        gblPanel.columnWeights = new double[]{1.0, 1.0};
        gblPanel.rowWeights = new double[]{2.0, 1.0, Double.MIN_VALUE};
        this.setLayout(gblPanel);

        // Sets the starting constraints
        final GridBagConstraints cnst = new GridBagConstraints();
        cnst.gridx = 0;
        cnst.gridy = 0;
        cnst.fill = GridBagConstraints.BOTH;

        // Sets game over image
        cnst.gridwidth = 2;
        final JLabel lblImage = new JLabel();
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblImage.setIcon(new StretchIcon(ImageLoader.getLoader().createImage(GameImage.GAME_OVER)));
        this.add(lblImage, cnst);
        cnst.gridy++;

        // Sets the message
        final JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPanel.setOpaque(false);
        textPanel.add(factory.createTitleLabel(LanguageHandler.getHandler().getLocaleResource().getString("gameOver")));
        final JLabel lblScore = new JLabel("Score: 2250, Time: 300");
        lblScore.setFont(factory.getDescriptionFont());
        lblScore.setForeground(Color.WHITE);
        lblScore.setHorizontalAlignment(SwingConstants.CENTER);
        lblScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPanel.add(lblScore);
        this.add(textPanel, cnst);
        cnst.gridy++;
        
        // Sets buttons
        cnst.gridwidth = 1;
        final JButton btnReplay = factory.createButton(LanguageHandler.getHandler().getLocaleResource().getString("replay"));
        btnReplay.addActionListener(e -> this.observer.replay());
        this.add(btnReplay, cnst);
        cnst.gridx++;
        final JButton btnExit = factory.createButton(LanguageHandler.getHandler().getLocaleResource().getString("exit"));
        btnExit.addActionListener(e -> this.observer.exit());
        this.add(btnExit, cnst);
    }
    
    /**
     * Set the observer of the GameOverPanel.
     * 
     * @param observer
     *          the observer to use
     */
    public void setObserver(final GameOverObserver observer) {
        this.observer = observer;
    }
    
    /**
     * This interface indicates the operations that an observer
     * of the GameOverPanel can do.
     *
     */
    public interface GameOverObserver {

        /**
         * Restarts the game.
         */
        void replay();
        
        /**
         * Close the game.
         */
        void exit();
    }
}

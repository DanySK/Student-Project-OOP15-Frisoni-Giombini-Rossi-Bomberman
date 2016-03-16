package view.menu.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import view.GUIFactory;
import view.ImageLoader;
import view.ImageLoader.GameImage;
import view.LanguageHandler;
import view.menu.components.FadingLabel;
import view.menu.components.StretchIcon;

/**
 * This class handles the view displayed the first time that the application
 * is started on a new PC.
 *
 */
public class WelcomeView extends JPanel {

    /**
     * Auto-generated UID.
     */
    private static final long serialVersionUID = 225527905617632929L;

    private static final Insets IMAGE_INSETS = new Insets(20, 0, 20, 0);
    private static final Insets TEXT_INSETS = new Insets(5, 5, 20, 5);
    private static final Insets TEXT_FIELD_INSETS = new Insets(5, 5, 5, 5);
    private static final int INPUT_TEXT_SIZE = 32;

    private WelcomeObserver observer;
    
    /**
     * Creates a WelcomeView.
     */
    public WelcomeView() {
        initialize();
    }

    /**
     * Initializes the contents of the panel.
     */
    private void initialize() {
        final GUIFactory factory = new GUIFactory.Standard();

        // Sets the panel layout dynamically
        final JPanel panel = factory.createGradientPanel();
        final GridBagLayout gblPanel = new GridBagLayout();
        gblPanel.columnWeights = new double[]{4.0, 1.0};
        gblPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE, Double.MIN_VALUE};
        panel.setLayout(gblPanel);

        // Sets the starting constraints
        final GridBagConstraints cnst = new GridBagConstraints();
        cnst.gridx = 0;
        cnst.gridy = 0;
        cnst.fill = GridBagConstraints.BOTH;

        // Sets welcome image
        cnst.gridwidth = 2;
        cnst.insets = IMAGE_INSETS;
        final JLabel lblImage = new JLabel();
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblImage.setIcon(new StretchIcon(ImageLoader.getLoader().createImage(GameImage.WELCOME)));
        panel.add(lblImage, cnst);
        final JLabel lblExplosion = new JLabel();
        lblExplosion.setHorizontalAlignment(SwingConstants.CENTER);
        lblExplosion.setIcon(new StretchIcon(ImageLoader.getLoader().createImage(GameImage.EXPLOSION)));
        panel.add(lblExplosion, cnst);
        cnst.gridy++;

        // Sets welcome text
        cnst.insets = TEXT_INSETS;
        final JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPanel.setOpaque(false);
        textPanel.add(factory.createTitleLabel(
                LanguageHandler.getHandler().getLocaleResource().getString("welcome")), BorderLayout.CENTER);
        
        // Sets description text
        final JPanel descriptionPanel = new JPanel(new FlowLayout());
        descriptionPanel.setOpaque(false);
        final List<FadingLabel> labels = new ArrayList<>();
        final String description = LanguageHandler.getHandler().getLocaleResource().getString("inputName");
        for (final String word : description.split(" ")) {
            final FadingLabel wordLabel = factory.createFadingLabelOfColor(word, Color.WHITE);
            descriptionPanel.add(wordLabel);
            labels.add(wordLabel);
        }
        textPanel.add(descriptionPanel);
        panel.add(textPanel, cnst);
        cnst.gridy++;

        // Sets input field
        cnst.gridwidth = 1;
        cnst.insets = TEXT_FIELD_INSETS;
        final JTextField nameField = factory.createTextField(true, INPUT_TEXT_SIZE);
        panel.add(nameField, cnst);
        cnst.gridx++;
        final JButton save = factory.createButton(LanguageHandler.getHandler().getLocaleResource().getString("play"));
        save.addActionListener(e -> this.observer.setName(nameField.getText()));
        panel.add(save, cnst);
        this.setLayout(new BorderLayout());
        this.add(panel);
        
        // Starts the fading animations
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (final FadingLabel label : labels) {
                    label.fadeIn();
                }
            }
        }).start();
    }
    
    /**
     * Set the observer of the WelcomeView.
     * 
     * @param observer
     *          the observer to use
     */
    public void setObserver(WelcomeObserver observer) {
        this.observer = observer;
    }
    
    /**
     * This interface indicates the operations that an observer
     * of a WelcomeView can do.
     *
     */
    public interface WelcomeObserver {

        /**
         * Sets the user name.
         * 
         * @param name
         *              the name of the user
         */
        void setName(String name);
    }
}

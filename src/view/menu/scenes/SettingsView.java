package view.menu.scenes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import view.GUIFactory;
import view.ImageLoader;
import view.ImageLoader.GameImage;
import view.LanguageHandler;
import view.LanguageHandler.Language;
import view.SoundEffect;
import view.menu.AbstractMenuPanel;
import view.menu.components.StretchIcon;

/**
 * This class handles the settings menu of the game.
 *
 */
public class SettingsView extends AbstractMenuPanel {

    /**
     * Auto-generated UID.
     */
    private static final long serialVersionUID = 7311049195166941968L;

    private static final Insets IMAGE_INSETS = new Insets(40, 40, 40, 20);

    private SettingsObserver observer;
    
    @Override
    public String getTitle() {
        return LanguageHandler.getHandler().getLocaleResource().getString("settings");
    }

    @Override
    public JPanel getCenterPanel() {
        final GUIFactory factory = new GUIFactory.Standard();
        final String on = LanguageHandler.getHandler().getLocaleResource().getString("on");
        final String off = LanguageHandler.getHandler().getLocaleResource().getString("off");

        final JPanel panel = new JPanel();
        final GridBagLayout gblPanel = new GridBagLayout();
        gblPanel.columnWeights = new double[]{1.0, 1.0};
        gblPanel.rowWeights = new double[]{1.0};
        panel.setLayout(gblPanel);

        final GridBagConstraints cnst = new GridBagConstraints();
        cnst.gridx = 0;
        cnst.gridy = 0;
        cnst.fill = GridBagConstraints.BOTH;

        // Sets the image
        cnst.insets = IMAGE_INSETS;
        final JLabel lblImage = new JLabel();
        lblImage.setIcon(new StretchIcon(ImageLoader.createImage(GameImage.BOMBERMAN_2)));
        panel.add(lblImage, cnst);

        // Sets the options panel
        final JPanel settings = new JPanel();
        settings.setLayout(new BoxLayout(settings, BoxLayout.Y_AXIS));
        settings.setOpaque(false);

        // Sets radio buttons for Music
        cnst.gridx++;
        final ButtonGroup groupMusic = new ButtonGroup();
        final JRadioButton radMusicOn = factory.createRadioButton(on, SoundEffect.isMusicOn());
        radMusicOn.addActionListener(e -> {
            if (!SoundEffect.isMusicOn()) {
                SoundEffect.setMusicOn();
                SoundEffect.THEME.playLoop();
            }
        });
        final JRadioButton radMusicOff = factory.createRadioButton(off, !SoundEffect.isMusicOn());
        radMusicOff.addActionListener(e -> SoundEffect.setMusicOff());
        groupMusic.add(radMusicOn);
        groupMusic.add(radMusicOff);
        settings.add(factory.createHorizontalComponentPanel(
                LanguageHandler.getHandler().getLocaleResource().getString("music"), radMusicOn, radMusicOff));

        // Sets radio buttons for DarkMode
        final ButtonGroup groupDarkMode = new ButtonGroup();
        final JRadioButton radDarkModeOn = factory.createRadioButton(on);
        final JRadioButton radDarkModeOff = factory.createRadioButton(off, true);
        radDarkModeOn.addActionListener(e -> {
            this.observer.setDarkMode(true);
            SoundEffect.BOO_LAUGH.playOnce();
        });
        radDarkModeOff.addActionListener(e -> {
            this.observer.setDarkMode(false);
        });
        groupDarkMode.add(radDarkModeOn);
        groupDarkMode.add(radDarkModeOff);
        settings.add(factory.createHorizontalComponentPanel(
                LanguageHandler.getHandler().getLocaleResource().getString("darkMode"), radDarkModeOn, radDarkModeOff));

        // Sets comboBox for languages
        final JComboBox<Language> comboLanguages = factory.createComboBox(LanguageHandler.getHandler().getSupportedLanguages());
        LanguageHandler.getHandler().getCurrentLanguage().ifPresent(l -> comboLanguages.setSelectedItem(l));
        comboLanguages.addActionListener(e -> {
            LanguageHandler.getHandler().setLocale((Language) comboLanguages.getSelectedItem());
        });
        settings.add(factory.createHorizontalComponentPanel(
                LanguageHandler.getHandler().getLocaleResource().getString("language"), comboLanguages));

        panel.add(settings);
        panel.setOpaque(false);
        return panel;
    }
    
    /**
     * Set the observer of the SettingsView.
     * 
     * @param observer
     *          the observer to use
     */
    public void setObserver(final SettingsObserver observer) {
        this.observer = observer;
    }

    /**
     * This interface indicates the operations that an observer
     * of a SettingsView can do.
     *
     */
    public interface SettingsObserver {

        /**
         * Sets the Dark Mode on / off.
         * 
         * @param darkMode
         *              true if enabled, false otherwise
         */
        void setDarkMode(boolean darkMode);
    }
}

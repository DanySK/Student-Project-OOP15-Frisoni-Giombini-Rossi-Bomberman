package view.game;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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

    private static final long RECORD_ANIMATION_PERIOD = 100L;
    private static final int RGB = 256;
    private static final String VALUE_SEPARATOR = ": ";
    private static final String SEPARATOR = ", ";

    private GameOverObserver observer;

    private final Random seed;
    private final int score;
    private final int time;
    private final boolean isRecord;

    /**
     * Creates a GameOverPanel.
     * 
     * @param score
     *          the score obtained by the player
     * @param time
     *          the time reached by the player
     * @param isRecord
     *          true if the score represents a new record, false otherwise
     */
    public GameOverPanel(final int score, final int time, final boolean isRecord) {
        this.seed = new Random();
        this.score = score;
        this.time = time;
        this.isRecord = isRecord;
        initialize();
    }

    /**
     * Initializes the contents of the panel.
     */
    private void initialize() {
        final GUIFactory factory = new GUIFactory.Standard();

        // Sets the panel layout
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
        lblImage.setIcon(new StretchIcon(ImageLoader.createImage(GameImage.GAME_OVER)));
        this.add(lblImage, cnst);
        cnst.gridy++;

        // Sets the message
        final JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPanel.setOpaque(false);
        textPanel.add(factory.createTitleLabel(LanguageHandler.getHandler().getLocaleResource().getString("gameOver")));
        final JLabel lblScore = factory.createLabel(
                LanguageHandler.getHandler().getLocaleResource().getString("score") + VALUE_SEPARATOR + this.score
                + SEPARATOR
                + LanguageHandler.getHandler().getLocaleResource().getString("time") + VALUE_SEPARATOR + this.time,
                factory.getDescriptionFont(), Color.WHITE);
        textPanel.add(lblScore);
        if (this.isRecord) {
            final JLabel lblRecord = factory.createLabel(LanguageHandler.getHandler().getLocaleResource().getString("record"),
                    factory.getSmallFont(), Color.WHITE);
            animateLabel(lblRecord);
            textPanel.add(lblRecord);
        }
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

    /*
     * Animates a label.
     * It periodically changes the foreground with a random color.
     * It is used principally to render a new record.
     */
    private void animateLabel(final JLabel label) {
        Objects.requireNonNull(label);
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                label.setForeground(new Color(seed.nextInt(RGB), seed.nextInt(RGB), seed.nextInt(RGB)));
            }
        }, 0L, RECORD_ANIMATION_PERIOD);
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
     * of a GameOverPanel can do.
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

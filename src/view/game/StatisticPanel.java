package view.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import controller.GameController;
import view.GUIFactory;
import view.ImageLoader;
import view.LanguageHandler;
import view.ImageLoader.GameImage;

/**
 * A custom {@link JPanel} for the rendering of:
 * - the elapsed time
 * - the current score
 * - the statistics of the hero.
 * 
 */
public class StatisticPanel extends JPanel {

    /**
     * Auto-generated UID.
     */
    private static final long serialVersionUID = 867883566833239595L;

    private static final int PANEL_HEIGHT = 50;
    private static final int NUM_STATS = 4;
    private static final String VALUE_SEPARATOR = " ";
    private static final String SEPARATOR = "x";
    private static final Color BG_COLOR = new Color(60, 60, 60);

    private final GameController controller;

    private final JLabel time;
    private final JLabel score;

    private final JLabel life;
    private final JLabel attack;
    private final JLabel bombs;
    private final JLabel range;

    /**
     * Creates a new StatisticPanel.
     * 
     * @param controller
     *          the controller of the game
     */
    public StatisticPanel(final GameController controller) {
        this.controller = Objects.requireNonNull(controller);
        
        final GUIFactory factory = new GUIFactory.Standard();
        this.setLayout(new GridLayout(0, 2));
        this.setBackground(BG_COLOR);
        
        // Sets the panel containing the time and the score
        final JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 2));
        infoPanel.setOpaque(false);
        this.time = factory.createLabel(factory.getDescriptionFont(), Color.WHITE);
        this.time.setHorizontalAlignment(SwingConstants.CENTER);
        updateTime(0L);
        infoPanel.add(this.time);
        this.score = factory.createLabel(factory.getDescriptionFont(), Color.WHITE);
        infoPanel.add(this.score);
        this.add(infoPanel);

        // Sets the panel containing the statistics of the hero
        final JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(0, NUM_STATS));
        statsPanel.setOpaque(false);
        this.life = factory.createLabel(factory.getDescriptionFont(), Color.WHITE);
        this.attack = factory.createLabel(factory.getDescriptionFont(), Color.WHITE);
        this.bombs = factory.createLabel(factory.getDescriptionFont(), Color.WHITE);
        this.range = factory.createLabel(factory.getDescriptionFont(), Color.WHITE);
        statsPanel.add(factory.createImageWithLabelPanel(ImageLoader.createImage(GameImage.LIFE_INFO), this.life));
        statsPanel.add(factory.createImageWithLabelPanel(ImageLoader.createImage(GameImage.ATTACK_INFO), this.attack));
        statsPanel.add(factory.createImageWithLabelPanel(ImageLoader.createImage(GameImage.BOMBS_INFO), this.bombs));
        statsPanel.add(factory.createImageWithLabelPanel(ImageLoader.createImage(GameImage.RANGE_INFO), this.range));
        this.add(statsPanel);
    }

    /**
     * Updates the rendering of the elapsed time.
     * 
     * @param seconds
     *          the number of seconds since the game's start
     */
    public final void updateTime(final long seconds) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StatisticPanel.this.time.setText(
                        LanguageHandler.getHandler().getLocaleResource().getString("statsTime") + VALUE_SEPARATOR
                        + String.format("%02d:%02d",
                        TimeUnit.SECONDS.toMinutes(seconds),
                        TimeUnit.SECONDS.toSeconds(seconds) % TimeUnit.MINUTES.toSeconds(1L)));
            }
        });
    }

    /**
     * Updates the rendering of the score.
     * 
     * @param score
     *          the current score game
     */
    public final void updateScore(final long score) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StatisticPanel.this.score.setText(LanguageHandler.getHandler().getLocaleResource().getString("statsScore")
                        + VALUE_SEPARATOR + score);
            }
        });
    }

    /**
     * Updates the rendering of the Hero's statistics.
     */
    public final void updateStats() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StatisticPanel.this.life.setText(String.valueOf(SEPARATOR + StatisticPanel.this.controller.getHero().getRemainingLives()));
                StatisticPanel.this.attack.setText(String.valueOf(SEPARATOR + StatisticPanel.this.controller.getHero().getAttack()));
                StatisticPanel.this.bombs.setText(String.valueOf(SEPARATOR + StatisticPanel.this.controller.getHero().getDetonator().getActualBombs()));
                StatisticPanel.this.range.setText(String.valueOf(SEPARATOR + StatisticPanel.this.controller.getHero().getDetonator().getActualRange()));
            }
        });
    }

    @Override
    public final Dimension getPreferredSize() {
        return new Dimension(0, PANEL_HEIGHT);
    }
}

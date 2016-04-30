package view.menu.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.stream.IntStream;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;

import controller.ScoreHandler;
import view.GUIFactory;
import view.ImageLoader;
import view.ImageLoader.GameImage;
import view.LanguageHandler;
import view.menu.AbstractMenuPanel;
import view.menu.components.StretchIcon;

/**
 * This class handles the view dedicated to the display of the user's scores.
 *
 */
public class ScoresScene extends AbstractMenuPanel {

    /**
     * Auto-generated UID.
     */
    private static final long serialVersionUID = -4853695530674764844L;

    private static final Insets INSETS = new Insets(40, 40, 40, 40);
    private static final Border CHART_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    private static final String VALUE_SEPARATOR = ": ";
    private static final String SEPARATOR = " ";

    private GUIFactory factory;

    private JTabbedPane jtb;
    private JPanel scorePanel;
    private JPanel recordPanel;

    /**
     * Constructs a new score scene.
     */
    public ScoresScene() {
        super();
        ScoreHandler.getHandler().addEObserver((s, msg) -> {
            switch (msg) {
            case RECORD:
                this.recordPanel = createBestScorePanel();
                refreshTabbedPane();
                break;
            case LAST_SCORES:
                this.scorePanel = createProgressPanel();
                refreshTabbedPane();
                break;
            default:
                throw new IllegalArgumentException("There isn't a view action associated to " + msg);
            }
        });
    }

    @Override
    public String getTitle() {
        return LanguageHandler.getHandler().getLocaleResource().getString("scores");
    }

    @Override
    public JPanel getCenterPanel() {
        factory = new GUIFactory.Standard();
        final JPanel panel = new JPanel(new BorderLayout());

        this.recordPanel = createBestScorePanel();
        this.scorePanel = createProgressPanel();

        jtb = factory.createLeftTabbedPane();
        jtb.addTab(LanguageHandler.getHandler().getLocaleResource().getString("bestScore"), this.recordPanel);
        jtb.addTab(LanguageHandler.getHandler().getLocaleResource().getString("progress"), this.scorePanel);

        panel.add(jtb, BorderLayout.CENTER);
        panel.setOpaque(false);
        return panel;
    }

    private void refreshTabbedPane() {
        ScoresScene.this.jtb.removeAll();
        ScoresScene.this.jtb.addTab(LanguageHandler.getHandler().getLocaleResource().getString("bestScore"), this.recordPanel);
        ScoresScene.this.jtb.addTab(LanguageHandler.getHandler().getLocaleResource().getString("progress"), this.scorePanel);
        ScoresScene.this.jtb.revalidate();
        ScoresScene.this.jtb.repaint();
    }

    private JPanel createBestScorePanel() {
        // Sets the panel layout
        final JPanel panel = new JPanel();
        final GridBagLayout gblPanel = new GridBagLayout();
        gblPanel.columnWeights = new double[]{Double.MIN_VALUE, 1.0};
        gblPanel.rowWeights = new double[]{1.0};
        panel.setLayout(gblPanel);
        panel.setBackground(Color.DARK_GRAY);

        // Sets the starting constraints
        final GridBagConstraints cnst = new GridBagConstraints();
        cnst.gridx = 0;
        cnst.gridy = 0;
        cnst.fill = GridBagConstraints.HORIZONTAL;
        cnst.insets = INSETS;

        // Sets the record panel
        final JPanel bestScorePanel = new JPanel();
        if (ScoreHandler.getHandler().isScoreEmpty()) {
            bestScorePanel.add(factory.createLabel(LanguageHandler.getHandler().getLocaleResource().getString("noScores"),
                    factory.getDescriptionFont(), Color.WHITE));
        } else {
            bestScorePanel.setLayout(new BoxLayout(bestScorePanel, BoxLayout.Y_AXIS));
            bestScorePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            bestScorePanel.setAlignmentY(Component.CENTER_ALIGNMENT);
            final JLabel name = factory.createLabel(ScoreHandler.getHandler().getPlayerName(), factory.getSmallFont(), factory.getBombermanColor().brighter());
            final JLabel score = factory.createLabel(LanguageHandler.getHandler().getLocaleResource().getString("bestScore") + VALUE_SEPARATOR
                    + ScoreHandler.getHandler().getRecord().getX(), factory.getSmallFont(), Color.WHITE);
            final JLabel time = factory.createLabel(
                    LanguageHandler.getHandler().getLocaleResource().getString("time") + VALUE_SEPARATOR
                    + ScoreHandler.getHandler().getRecord().getY() + SEPARATOR
                    + LanguageHandler.getHandler().getLocaleResource().getString("seconds"), factory.getSmallFont(), Color.WHITE);
            bestScorePanel.add(name);
            bestScorePanel.add(score);
            bestScorePanel.add(time);
        }
        bestScorePanel.setOpaque(false);
        panel.add(bestScorePanel, cnst);
        cnst.gridx++;

        // Sets the image
        cnst.fill = GridBagConstraints.BOTH;
        final JLabel lblImage = new JLabel();
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblImage.setIcon(new StretchIcon(ImageLoader.createImage(GameImage.MEDAL)));
        panel.add(lblImage, cnst);

        return panel;
    }

    private JPanel createProgressPanel() {
        final JPanel panel = new ChartPanel(factory.createDarkBarChart(
                LanguageHandler.getHandler().getLocaleResource().getString("chartTitle"),
                LanguageHandler.getHandler().getLocaleResource().getString("games"),
                LanguageHandler.getHandler().getLocaleResource().getString("results"),
                createDataset()));
        panel.setBorder(CHART_BORDER);
        panel.setBackground(Color.DARK_GRAY);
        return panel;
    }

    private DefaultCategoryDataset createDataset() {
        final String series1 = LanguageHandler.getHandler().getLocaleResource().getString("score");
        final String series2 = LanguageHandler.getHandler().getLocaleResource().getString("time");

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (!ScoreHandler.getHandler().isScoreEmpty()) {
            IntStream.range(0, ScoreHandler.getHandler().getLastScores().size()).forEach(i -> {
                dataset.addValue(ScoreHandler.getHandler().getLastScores().get(i).getX(), series1, String.valueOf(i + 1));
                dataset.addValue(ScoreHandler.getHandler().getLastScores().get(i).getY(), series2, String.valueOf(i + 1));
            });
        }
        return dataset;
    }
}

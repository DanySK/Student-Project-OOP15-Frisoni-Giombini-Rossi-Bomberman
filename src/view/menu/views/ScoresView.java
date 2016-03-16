package view.menu.views;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;

import org.jfree.chart.ChartPanel;
import org.jfree.data.category.DefaultCategoryDataset;

import view.GUIFactory;
import view.LanguageHandler;
import view.menu.AbstractMenuPanel;

/**
 * This class handles the view dedicated to the display of the user's scores.
 *
 */
public class ScoresView extends AbstractMenuPanel {

    /**
     * Auto-generated UID.
     */
    private static final long serialVersionUID = -4853695530674764844L;

    private static final Border CHART_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5); 

    private GUIFactory factory;

    @Override
    public String getTitle() {
        return LanguageHandler.getHandler().getLocaleResource().getString("scores");
    }

    @Override
    public JPanel getPanel() {
        factory = new GUIFactory.Standard();
        final JPanel panel = new JPanel(new BorderLayout());

        final JTabbedPane jtb = factory.createLeftTabbedPane();
        jtb.addTab(LanguageHandler.getHandler().getLocaleResource().getString("bestScore"), createBestScorePanel());
        jtb.addTab(LanguageHandler.getHandler().getLocaleResource().getString("progress"), createProgressPanel());

        panel.add(jtb, BorderLayout.CENTER);
        panel.setOpaque(false);
        return panel;
    }

    private JPanel createBestScorePanel() {
        final JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        return panel;
    }

    private JPanel createProgressPanel() {
        final JPanel panel = new ChartPanel(factory.createDarkBarChart(
                LanguageHandler.getHandler().getLocaleResource().getString("chartTitle"),
                LanguageHandler.getHandler().getLocaleResource().getString("dates"),
                LanguageHandler.getHandler().getLocaleResource().getString("results"),
                createDataset()));
        panel.setBorder(CHART_BORDER);
        panel.setBackground(Color.DARK_GRAY);
        return panel;
    }

    private DefaultCategoryDataset createDataset() {
        // Row keys
        final String series1 = LanguageHandler.getHandler().getLocaleResource().getString("score");
        final String series2 = LanguageHandler.getHandler().getLocaleResource().getString("time");

        // Column keys
        final String category1 = "Category 1";
        final String category2 = "Category 2";
        final String category3 = "Category 3";

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(600.0, series1, category1);
        dataset.addValue(450.0, series1, category2);
        dataset.addValue(1100.0, series1, category3);

        dataset.addValue(700.0, series2, category1);
        dataset.addValue(400.0, series2, category2);
        dataset.addValue(2000.0, series2, category3);

        return dataset;
    }
}

package view.menu.views;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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

    @Override
    public String getTitle() {
        return LanguageHandler.getHandler().getLocaleResource().getString("scores");
    }

    @Override
    public JPanel getPanel() {
        final GUIFactory factory = new GUIFactory.Standard();
        final JPanel panel = new JPanel(new BorderLayout());
        
        final JTabbedPane jtb = factory.createLeftTabbedPane();
        final JPanel informationPanel = new JPanel();
        informationPanel.setBackground(Color.DARK_GRAY);
        final JPanel chartsPanel = new JPanel();
        chartsPanel.setBackground(Color.DARK_GRAY);
        jtb.addTab("General", informationPanel);
        jtb.addTab("Charts", chartsPanel);
        
        panel.add(jtb, BorderLayout.CENTER);
        panel.setOpaque(false);
        return panel;
    }

}

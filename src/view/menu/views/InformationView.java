package view.menu.views;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import view.GUIFactory;
import view.LanguageHandler;
import view.menu.AbstractMenuPanel;
import view.menu.views.panels.CommandsPanel;
import view.menu.views.panels.CreditsPanel;
import view.menu.views.panels.PowerUpsPanel;

/**
 * This class handles the information scene of the menu.
 *
 */
public class InformationView extends AbstractMenuPanel {

    /**
     * Auto-generated UID.
     */
    private static final long serialVersionUID = 1713293706664779618L;

    @Override
    public String getTitle() {
        return LanguageHandler.getHandler().getLocaleResource().getString("info");
    }

    @Override
    public JPanel getPanel() {
        final JPanel panel = new JPanel(new BorderLayout());

        final JTabbedPane jtb = new GUIFactory.Standard().createLeftTabbedPane();

        jtb.addTab(LanguageHandler.getHandler().getLocaleResource().getString("commands"), new CommandsPanel().getPanel());
        jtb.addTab(LanguageHandler.getHandler().getLocaleResource().getString("powerUps"), new PowerUpsPanel().getPanel());
        jtb.addTab(LanguageHandler.getHandler().getLocaleResource().getString("authors"), new CreditsPanel().getPanel());
        
        panel.add(jtb, BorderLayout.CENTER);
        panel.setOpaque(false);
        return panel;
    }
}

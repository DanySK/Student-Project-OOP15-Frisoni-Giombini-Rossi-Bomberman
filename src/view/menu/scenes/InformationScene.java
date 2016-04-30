package view.menu.scenes;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import view.GUIFactory;
import view.LanguageHandler;
import view.menu.AbstractMenuPanel;
import view.menu.scenes.panels.CommandsPanel;
import view.menu.scenes.panels.CreditsPanel;
import view.menu.scenes.panels.PowerUpsPanel;

/**
 * This class handles the information scene of the menu.
 *
 */
public class InformationScene extends AbstractMenuPanel {

    /**
     * Auto-generated UID.
     */
    private static final long serialVersionUID = 1713293706664779618L;

    @Override
    public String getTitle() {
        return LanguageHandler.getHandler().getLocaleResource().getString("info");
    }

    @Override
    public JPanel getCenterPanel() {
        final JPanel panel = new JPanel(new BorderLayout());

        final JTabbedPane jtb = new GUIFactory.Standard().createLeftTabbedPane();

        jtb.addTab(LanguageHandler.getHandler().getLocaleResource().getString("commands"), new CommandsPanel());
        jtb.addTab(LanguageHandler.getHandler().getLocaleResource().getString("powerUps"), new PowerUpsPanel());
        jtb.addTab(LanguageHandler.getHandler().getLocaleResource().getString("authors"), new CreditsPanel());
        
        panel.add(jtb, BorderLayout.CENTER);
        panel.setOpaque(false);
        return panel;
    }
}

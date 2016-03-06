package view.menu;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import view.GUIFactory;
import view.LanguageHandler;
import view.menu.MenuFrame.MenuCard;

/**
 * This abstract class represents a standard menu panel with:
 * - a title
 * - a body
 * - a "back to menu" button.
 *
 */
public abstract class AbstractMenuPanel extends JPanel {

    /**
     * Auto-generated UID.
     */
    private static final long serialVersionUID = 6859160481343879042L;
    
    /**
     * Constructs a new AbstractMenuPanel.
     */
    public AbstractMenuPanel() {
        super();
        LanguageHandler.getHandler().addEObserver((s, msg) -> {
            this.removeAll();
            initialize();
            this.revalidate();
            this.repaint();
        });
        initialize();
    }
    
    /**
     * @return the title of the AbstractMenuPanel.
     */
    public abstract String getTitle();

    /**
     * @return the body of the AbstractMenuPanel.
     */
    public abstract JPanel getPanel();
    
    /**
     * Initializes the contents of the panel.
     */
    private void initialize() {
        final GUIFactory factory = new GUIFactory.Standard();
        
        final JPanel panel = factory.createGradientPanel();
        panel.setLayout(new BorderLayout());
        
        // Sets the title
        panel.add(factory.createTitleLabel(getTitle()), BorderLayout.NORTH);
        
        // Sets the body panel
        panel.add(getPanel(), BorderLayout.CENTER);
        
        // Sets the "back" button
        final JButton btnBack = factory.createButton(
                LanguageHandler.getHandler().getLocaleResource().getString("goBack"));
        btnBack.addActionListener(e -> MenuFrameImpl.getMenuFrame().replaceCard(MenuCard.HOME));
        panel.add(btnBack, BorderLayout.SOUTH);
        
        this.setLayout(new BorderLayout());
        this.add(panel);
    }
}

package view.game;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.level.Level;

/**
 * A class for making a JPanel with statistic information of the hero to display to the user.
 * 
 */
public class StatisticPanel {
    
    private static final Color BG_COLOR = new Color(60, 60, 60);
    
    // private final Level model;
    
    private JPanel panel;
    
    /**
     * 
     */
    public StatisticPanel(final Level model) {
        // this.model = model;
        createControl();
        updateControl();
    }
    
    private void createControl() {
        this.panel = new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.X_AXIS));
        this.panel.setBackground(BG_COLOR);
        this.panel.setPreferredSize(new Dimension(0, 50));
    }
    
    /**
     * Updates statics.
     */
    public final void updateControl() {
    }
    
    /**
     * @return the logger panel
     */
    public JPanel getPanel() {
        return this.panel;
    }
}

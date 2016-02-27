package view.game;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import model.level.Level;

/**
 * A class for making a {@link JPanel} with log messages to display to the user.
 * 
 */
public class LoggerPanel {
    
    private static final int BORDER_THICKNESS = 2;
    private static final int MARGIN = 10;
    private static final Color BORDER_COLOR = Color.BLACK;
    private static final Color BG_COLOR = new Color(60, 60, 60);
    private static final Color FG_COLOR = Color.WHITE;
    private static final String START_MSG = "< ";
    private static final String END_MSG = " >";
    
    //private final Level model;
    
    private JPanel panel;
    private JTextField logField;
    
    /**
     * Creates a new LoggerPanel.
     * 
     * @param model
     *          the model with the data
     */
    public LoggerPanel(final Level model) {
        //this.model = model;
        createControl();
        updateControl();
    }
    
    private void createControl() {
        this.panel = new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.X_AXIS));
        this.panel.setBackground(BG_COLOR);
        
        final Border line = BorderFactory.createLineBorder(BORDER_COLOR, BORDER_THICKNESS);
        final Border empty = new EmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN);
        final CompoundBorder border = new CompoundBorder(line, empty);
        
        this.logField = new JTextField();
        this.logField.setBorder(border);
        this.logField.setBackground(BG_COLOR);
        this.logField.setForeground(FG_COLOR);
        this.logField.setHorizontalAlignment(JTextField.CENTER);
        this.logField.setEditable(false);
        
        this.panel.add(this.logField);
    }
    
    /**
     * Updates the text to display.
     */
    public final void updateControl() {
        this.logField.setText(START_MSG + END_MSG);
    }
    
    /**
     * @return the logger panel
     */
    public JPanel getPanel() {
        return this.panel;
    }
}

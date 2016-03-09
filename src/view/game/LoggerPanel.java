package view.game;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import model.level.Level;
import view.GUIFactory;

/**
 * A class for making a {@link JPanel} with log messages to display to the user.
 * 
 */
public class LoggerPanel {
    
    private static final long TEXT_DELAY = 4000L;
    
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
        final GUIFactory factory = new GUIFactory.Standard();
        
        this.panel = new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.X_AXIS));
        
        this.logField = factory.createTextField(false);
        this.panel.add(this.logField);
    }
    
    /**
     * Updates the text to display.
     */
    public final void updateControl() {
        showText("");
    }
    
    /**
     * Displays a text for a certain amount of time.
     * 
     * @param text
     *            the text to show
     */
    private void showText(final String text) {
        this.logField.setText(text);
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        LoggerPanel.this.logField.setText("");
                    }
                });
            }
        }, TEXT_DELAY, 1);
    }
    
    /**
     * @return the logger panel.
     */
    public JPanel getPanel() {
        return this.panel;
    }
}

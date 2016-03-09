package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import view.menu.FadingLabel;
import view.menu.GradientPanel;

/**
 * This interface uses an Abstract Factory pattern to define
 * some common aspects of the GUI and to facilitate their change.
 *
 */
public interface GUIFactory {
    
    /**
     * @return a {@link JPanel} with a gradient color in background.
     */
    JPanel createGradientPanel();
    
    /**
     * Creates a customized {@link JButton}.
     * 
     * @param text
     *          the content of the button
     * @return the specified button
     */
    JButton createButton(String text);
    
    /**
     * Creates a customized {@link JButton} with the text and the icon specified.
     * 
     * @param text
     *          the content of the button
     * @param image
     *          the image icon to display
     * @return the specified button
     */
    JButton createMenuButton(String text, ImageIcon image);
    
    /**
     * Creates a customized {@link JLabel} with an horizontal alignment.
     * 
     * @param text
     *          the content of the label
     * @return the specified title label
     */
    JComponent createTitleLabel(String text);
    
    /**
     * Creates a customized {@link JLabel} with a fading effect.
     * 
     * @param text
     *          the content of the label
     * @param color
     *          the color of the text
     * @return the specified fading label
     */
    FadingLabel createFadingLabelOfColor(String text, Color color);
    
    /**
     * @return a {@link Font} suitable for full frame mode.
     */
    Font getFullFrameFont();
    
    /**
     * Creates a customized {@link JRadioButton}.
     * 
     * @param text
     *          the text to show
     * @return the specified radio button
     */
    JRadioButton createRadioButton(String text);
    
    /**
     * Creates a customized {@link JRadioButton}.
     * 
     * @param text
     *          the text to show
     * @param selected
     *          the initial state of the button
     * @return the specified radio button
     */
    JRadioButton createRadioButton(String text, boolean selected);
    
    /**
     * Creates a customized {@link JComboBox}.
     * 
     * @param <E>
     *          the type of the combo box elements
     * @param items
     *          the items to add to the combo box
     * @return the specified combo box
     */
    <E> JComboBox<E> createComboBox(E[] items);
    
    /**
     * Creates a customized {@link JTextField}.
     * 
     * @param isEditable
     *          true if the text field is editable, false otherwise
     * @return the specified text field
     */
    JTextField createTextField(boolean isEditable);
    
    /**
     * Creates an horizontal customized panel with the given description and
     * the specified components on the right.
     * 
     * @param text
     *          the description for the choice
     * @param components
     *          the components to add
     * @return the specified panel
     */
    JPanel createHorizontalComponentPanel(String text, JComponent... components);
    
    /**
     * A standard implementation of {@link GUIFactory}.
     *
     */
    class Standard implements GUIFactory {
        
        private static final String FONT_FAMILY = "Char BB";
        private static final Font DESCRIPTION_FONT = new Font(FONT_FAMILY, Font.PLAIN, 24);
        private static final Font SMALL_FONT = new Font(FONT_FAMILY, Font.PLAIN, 32);
        private static final Font MEDIUM_FONT = new Font(FONT_FAMILY, Font.PLAIN, 56);
        private static final Font BIG_FONT = new Font(FONT_FAMILY, Font.PLAIN, 72);
        
        private static final Color COLOR_BUTTON = new Color(50, 50, 50);
        private static final Color PRIMARY_COLOR = new Color(60, 60, 60);
        private static final Color SECONDARY_COLOR = new Color(30, 30, 30);
        private static final int LINE_BORDER_THICKNESS = 2;
        private static final Color LINE_BORDER_COLOR = Color.BLACK;
        private static final Border SMALL_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        private static final Border REGULAR_BORDER = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        private static final Border LINE_BORDER = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LINE_BORDER_COLOR, LINE_BORDER_THICKNESS),
                SMALL_BORDER);
        
        @Override
        public JPanel createGradientPanel() {
            return new GradientPanel(PRIMARY_COLOR, SECONDARY_COLOR);
        }
        
        @Override
        public JButton createButton(final String text) {
            final JButton button = new JButton(text);
            button.setForeground(Color.WHITE);
            button.setBackground(COLOR_BUTTON);
            button.setFont(SMALL_FONT);
            button.addActionListener(e -> SoundEffect.SELECT.playOnce());
            return button;
        }

        @Override
        public JButton createMenuButton(final String text, final ImageIcon image) {
            final JButton button = createButton(text);
            button.setIcon(image);
            button.addActionListener(e -> SoundEffect.FOCUS.playOnce());
            return button;
        }

        @Override
        public JComponent createTitleLabel(final String text) {
            final JLabel title = new JLabel(text);
            title.setFont(MEDIUM_FONT);
            title.setHorizontalAlignment(SwingConstants.CENTER);
            title.setForeground(Color.WHITE);
            title.setBorder(REGULAR_BORDER);
            return title;
        }
        
        @Override
        public FadingLabel createFadingLabelOfColor(final String text, final Color color) {
            final FadingLabel label = new FadingLabel(text);
            label.setFont(SMALL_FONT);
            label.setForeground(color);
            label.setBorder(REGULAR_BORDER);
            return label;
        }

        @Override
        public Font getFullFrameFont() {
            return BIG_FONT;
        }
        
        private JLabel createDescriptionLabel(final String text) {
            final JLabel description = new JLabel(text + ": ");
            description.setFont(SMALL_FONT);
            description.setForeground(Color.LIGHT_GRAY);
            return description;
        }
        
        @Override
        public JRadioButton createRadioButton(final String text) {
            final JRadioButton radio = new JRadioButton(text);
            radio.setFont(SMALL_FONT);
            radio.setOpaque(false);
            radio.setForeground(Color.WHITE);
            return radio;
        }

        @Override
        public JRadioButton createRadioButton(final String text, final boolean selected) {
            final JRadioButton radio = new JRadioButton(text, selected);
            radio.setFont(SMALL_FONT);
            radio.setOpaque(false);
            radio.setForeground(Color.WHITE);
            return radio;
        }
        
        @Override
        public <E> JComboBox<E> createComboBox(final E[] items) {
            final JComboBox<E> combo = new JComboBox<>(items);
            combo.setFont(SMALL_FONT);
            combo.setBackground(Color.DARK_GRAY);
            combo.setForeground(Color.WHITE);
            combo.setEditable(false);
            return combo;
        }
        
        @Override
        public JTextField createTextField(final boolean isEditable) {
            final JTextField field = new JTextField();
            field.setFont(DESCRIPTION_FONT);
            field.setBorder(LINE_BORDER);
            field.setBackground(PRIMARY_COLOR);
            field.setForeground(Color.WHITE);
            field.setHorizontalAlignment(JTextField.CENTER);
            field.setEditable(isEditable);
            return field;
        }
        
        @Override
        public JPanel createHorizontalComponentPanel(final String text, final JComponent... components) {
            final JPanel panel = new JPanel(new FlowLayout());
            panel.add(createDescriptionLabel(text));
            for (final JComponent radio : components) {
                panel.add(radio);
            }
            panel.setOpaque(false);
            return panel;
        }
    }
}

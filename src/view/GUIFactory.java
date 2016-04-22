package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.TableModel;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;

import view.menu.components.FadingLabel;
import view.menu.components.GradientPanel;
import view.menu.components.StretchIcon;

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
     * Creates a customized {@link JLabel}.
     * 
     * @param text
     *          the text to display
     * @param font
     *          the font of the label
     * @param color
     *          the foreground color
     * @return the specified label
     */
    JLabel createLabel(String text, Font font, Color color);
    
    /**
     * Creates a customized {@link JLabel}.
     * 
     * @param font
     *          the font of the label
     * @param color
     *          the foreground color
     * @return the specified label
     */
    JLabel createLabel(Font font, Color color);
    
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
     * @return a small {@link Font} for description texts.
     */
    Font getDescriptionFont();
    
    /**
     * @return a small {@link Font} for detail texts.
     */
    Font getSmallFont();
    
    /**
     * @return a {@link Font} suitable for full frame mode.
     */
    Font getFullFrameFont();
    
    /**
     * @return a {@link Font} for the rendering of the remaining enemy's life. 
     */
    Font getLifeDetailFont();
    
    /**
     * @return the characteristic color of Bomberman.
     */
    Color getBombermanColor();
    
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
     * @param fontSize
     *          the font size for the text
     * @return the specified text field
     */
    JTextField createTextField(boolean isEditable, int fontSize);
    
    /**
     * Creates a customized {@link JTabbedPane}.
     * 
     * @return the specified tabbed pane
     */
    JTabbedPane createLeftTabbedPane();
    
    /**
     * Creates a customized {@link JList}.
     * 
     * @param <E>
     *          the type of each entry
     * @param dataModel
     *          the data model for the list population
     * @param cellRenderer
     *          the cell renderer
     * @return the specified list
     */
    <E> JList<E> createList(ListModel<E> dataModel, ListCellRenderer<? super E> cellRenderer);
    
    /**
     * Creates a customized {@link JTable}.
     * 
     * @param model
     *          the table model
     * @return the specified table
     */
    JTable createTable(TableModel model);
    
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
     * Creates a customized horizontal panel with an image and a label on the right.
     * 
     * @param image
     *          the image to show
     * @param text
     *          the text associated to the image
     * @return the specified panel
     */
    JPanel createImageWithLabelPanel(Image image, JLabel text);
    
    /**
     * Creates a customized bar chart with a dark theme.
     * 
     * @param title
     *          the title of the chart
     * @param categoryAxisLabel
     *          the label for the x axis
     * @param valueAxisLabel
     *          the label for the y axis
     * @param dataset
     *          the data required for the chart rendering
     * @return a bar chart
     */
    JFreeChart createDarkBarChart(String title, String categoryAxisLabel, String valueAxisLabel, CategoryDataset dataset);
    
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
        private static final Font LIFE_DETAIL_FONT = new Font("Serif", Font.PLAIN, 12);
        
        private static final Color COLOR_BUTTON = new Color(50, 50, 50);
        private static final Color PRIMARY_COLOR = new Color(60, 60, 60);
        private static final Color SECONDARY_COLOR = new Color(30, 30, 30);
        private static final Color PINK_BOMBERMAN_COLOR = new Color(227, 110, 155);
        private static final Color VIOLET_BOMBERMAN_COLOR = new Color(105, 93, 179);
        
        private static final int LINE_BORDER_THICKNESS = 2;
        private static final Color LINE_BORDER_COLOR = Color.BLACK;
        private static final int ROW_MARGIN_TABLE = 10;
        private static final Insets TABBED_PANE_AREA_INSETS = new Insets(2, 2, 2, 2);
        private static final Border SMALL_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        private static final Border REGULAR_BORDER = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        private static final Border LINE_BORDER = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LINE_BORDER_COLOR, LINE_BORDER_THICKNESS),
                SMALL_BORDER);
        
        /*
         * Static initializer to set a custom developer Look-And-Feel
         * This only effects the developer defaults, not the system or look and feel defaults.
         */
        static {
            UIManager.put("TabbedPane.selected", VIOLET_BOMBERMAN_COLOR);
            UIManager.put("TabbedPane.contentAreaColor", Color.WHITE);
            UIManager.put("TabbedPane.contentBorderInsets", TABBED_PANE_AREA_INSETS);
        }
        
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
            return button;
        }

        @Override
        public JLabel createLabel(final Font font, final Color color) {
            final JLabel label = new JLabel();
            label.setFont(font);
            label.setForeground(color);
            return label;
        }
        
        @Override
        public JLabel createLabel(final String text, final Font font, final Color color) {
            final JLabel label = createLabel(font, color);
            label.setText(text);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            return label;
        }
        
        @Override
        public JComponent createTitleLabel(final String text) {
            final JLabel title = createLabel(text, MEDIUM_FONT, Color.WHITE);
            title.setHorizontalAlignment(SwingConstants.CENTER);
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        public Font getDescriptionFont() {
            return DESCRIPTION_FONT;
        }
        
        @Override
        public Font getSmallFont() {
            return SMALL_FONT;
        }
        
        @Override
        public Font getFullFrameFont() {
            return BIG_FONT;
        }
        
        @Override
        public Font getLifeDetailFont() {
            return LIFE_DETAIL_FONT;
        }
        
        @Override
        public Color getBombermanColor() {
            return VIOLET_BOMBERMAN_COLOR;
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
        public JTextField createTextField(final boolean isEditable, final int fontSize) {
            final JTextField field = new JTextField();
            field.setFont(new Font(FONT_FAMILY, Font.PLAIN, fontSize));
            field.setBorder(LINE_BORDER);
            field.setBackground(PRIMARY_COLOR);
            field.setForeground(Color.WHITE);
            field.setCaretColor(Color.WHITE);
            field.setHorizontalAlignment(JTextField.CENTER);
            field.setEditable(isEditable);
            return field;
        }
        
        @Override
        public JTabbedPane createLeftTabbedPane() {
            final JTabbedPane jtb = new JTabbedPane();
            jtb.setFont(DESCRIPTION_FONT);
            jtb.setBackground(PRIMARY_COLOR);
            jtb.setForeground(Color.WHITE);
            jtb.setTabPlacement(JTabbedPane.LEFT);
            jtb.setBorder(SMALL_BORDER);
            jtb.setOpaque(false);
            return jtb;
        }
        
        @Override
        public <E> JList<E> createList(final ListModel<E> dataModel, final ListCellRenderer<? super E> cellRenderer) {
            final JList<E> list = new JList<>(dataModel);
            list.setCellRenderer(cellRenderer);
            list.setBackground(Color.DARK_GRAY);
            list.setForeground(Color.WHITE);
            list.setOpaque(false);
            return list;
        }
        
        @Override
        public JTable createTable(final TableModel model) {
            final JTable table = new JTable(model);
            table.setBackground(Color.DARK_GRAY);
            table.setForeground(Color.WHITE);
            table.setRowHeight(table.getRowHeight() + ROW_MARGIN_TABLE);
            return table;
        }
        
        @Override
        public JPanel createHorizontalComponentPanel(final String text, final JComponent... components) {
            final JPanel panel = new JPanel(new FlowLayout());
            panel.add(createLabel(text + ":", SMALL_FONT, Color.LIGHT_GRAY));
            for (final JComponent radio : components) {
                panel.add(radio);
            }
            panel.setOpaque(false);
            return panel;
        }
        
        @Override
        public JPanel createImageWithLabelPanel(final Image image, final JLabel label) {
            final JPanel panel = new JPanel();
            final GridBagLayout gblPanel = new GridBagLayout();
            gblPanel.columnWeights = new double[]{2.0, 1.0};
            gblPanel.rowWeights = new double[]{1.0};
            panel.setLayout(new GridLayout(0, 2));
            
            final GridBagConstraints cnst = new GridBagConstraints();
            cnst.gridx = 0;
            cnst.gridy = 0;
            cnst.weightx = 1;
            cnst.weighty = 1;
            cnst.fill = GridBagConstraints.BOTH;
            
            final JLabel lblImage = new JLabel();
            lblImage.setIcon(new StretchIcon(image));
            panel.add(lblImage, cnst);
            cnst.gridx++;
            
            panel.add(label, cnst);
            panel.setOpaque(false);
            return panel;
        }

        @Override
        public JFreeChart createDarkBarChart(final String title, final String categoryAxisLabel,
                final String valueAxisLabel, final CategoryDataset dataset) {
            
            final CategoryAxis domainAxis = new CategoryAxis(categoryAxisLabel);
            final NumberAxis rangeAxis = new NumberAxis(valueAxisLabel);
            final BarRenderer renderer = new BarRenderer();
            
            // Customization of the plot
            final CategoryPlot plot = new CategoryPlot(dataset, domainAxis, rangeAxis, renderer);
            plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
            plot.getDomainAxis().setTickLabelPaint(Color.WHITE);
            plot.getDomainAxis().setLabelPaint(Color.WHITE);
            plot.getRangeAxis().setTickLabelPaint(Color.WHITE);
            plot.getRangeAxis().setLabelPaint(Color.WHITE);
            plot.setOutlinePaint(Color.BLACK);
            plot.setOutlineStroke(new BasicStroke(2.0f));
            plot.setBackgroundPaint(Color.DARK_GRAY.brighter());
            plot.setRangeGridlinesVisible(true);
            plot.setRangeGridlinePaint(Color.BLACK);
            plot.setDomainGridlinesVisible(true);
            plot.setDomainGridlinePaint(Color.BLACK);
            plot.setDataset(0, dataset);
            
            /*
             * Customization of the bars.
             * Sets a default color only for the first two series.
             */
            renderer.setSeriesPaint(0, PINK_BOMBERMAN_COLOR);
            renderer.setSeriesPaint(1, VIOLET_BOMBERMAN_COLOR);
            renderer.setShadowVisible(false);
            
            // Creates the chart
            final JFreeChart chart = new JFreeChart(title, plot);
            
            // Customization of the chart
            chart.setBackgroundPaint(Color.DARK_GRAY);
            chart.getTitle().setFont(DESCRIPTION_FONT);
            chart.getTitle().setPaint(Color.WHITE);
            
            return chart;
        }
    }
}

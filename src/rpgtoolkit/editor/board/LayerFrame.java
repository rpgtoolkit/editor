package rpgtoolkit.editor.board;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import rpgtoolkit.editor.utilities.LayerTableModel;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public class LayerFrame extends JInternalFrame implements ChangeListener
{
    /*
     * *************************************************************************
     * Class Members
     * *************************************************************************
     */
    
    private AbstractBoardView boardView;
    
    private JSlider opacitySlider;
    private JLabel opacitySliderLabel;
    private JTable layerTable;
    private JScrollPane layerScrollPane;
    
    private JPanel sliderPanel;
    private JPanel layerPanel;
    private JPanel contentPanel;
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    
    public LayerFrame()
    {
        
    }
    
    public LayerFrame(AbstractBoardView boardView)
    {
        super("Board Layers", true, true, true, true);
        
        this.boardView = boardView;
        this.initialize();
    }
    
    /*
     * *************************************************************************
     * Private Methods
     * *************************************************************************
     */
    
    private void initialize()
    {
        this.opacitySlider = new JSlider(0, 100, 100);
        this.opacitySlider.addChangeListener(this);
        
        this.opacitySliderLabel = new JLabel("Opacity");
        this.opacitySliderLabel.setLabelFor(this.opacitySlider);
        
        this.sliderPanel = new JPanel();
        this.sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.X_AXIS));
        this.sliderPanel.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));
        this.sliderPanel.add(this.opacitySliderLabel);
        this.sliderPanel.add(this.opacitySlider);
        this.sliderPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                                            this.sliderPanel.getPreferredSize()
                                                    .height));
        
        this.layerTable = new JTable(new LayerTableModel(this.boardView));
        this.layerTable.getColumnModel().getColumn(0).setPreferredWidth(32);
        this.layerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //this.layerTable.getSelectionModel().addListSelectionListener(this);
        
        this.layerScrollPane = new JScrollPane(this.layerTable);
        
        this.layerPanel = new JPanel();
        this.layerPanel.setLayout(new GridBagLayout());
        //this.layerPanel.setPreferredSize(new Dimension(120, 120));
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(3, 0, 0, 0);
        constraints.weightx = 1;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0;
        constraints.gridy++;
        this.layerPanel.add(this.sliderPanel, constraints);
        constraints.weighty = 1;
        constraints.gridy++;
        this.layerPanel.add(layerScrollPane, constraints);
        constraints.weighty = 0;
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.gridy++;
        
        this.contentPanel = new JPanel();
        this.contentPanel.add(this.layerPanel);
        
        this.setContentPane(this.contentPanel);
        this.pack();
    }

    /**
     * Possibly consider moving this to a dedicated listener class later.
     * For now leave it here for simplicity.
     * 
     * @param e 
     */
    @Override
    public void stateChanged(ChangeEvent e)
    {
        if (e.getSource().equals(this.opacitySlider))
        {
            if (this.boardView != null)
            {
                if (this.layerTable.getSelectedRow() > -1)
                {
                    this.boardView.getLayer(this.layerTable.getSelectedRow()).
                            setOpacity(this.opacitySlider.getValue() / 100.0f);
                }
            }
        }
    }
}

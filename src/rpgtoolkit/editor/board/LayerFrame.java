package rpgtoolkit.editor.board;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import rpgtoolkit.editor.utilities.LayerTableModel;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public class LayerFrame extends JInternalFrame 
{
    private AbstractBoardView boardView;
    
    private JTable layerTable;
    private JScrollPane layerScrollPane;
    private JPanel contentPanel;
    
    public LayerFrame()
    {
        
    }
    
    public LayerFrame(AbstractBoardView boardView)
    {
        super("Board Layers", true, true, true, true);
        
        this.boardView = boardView;
        this.initialize();
    }
    
    private void initialize()
    {
        this.layerTable = new JTable(new LayerTableModel(this.boardView));
        this.layerTable.getColumnModel().getColumn(0).setPreferredWidth(32);
        this.layerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //this.layerTable.getSelectionModel().addListSelectionListener(this);
        
        this.layerScrollPane = new JScrollPane(this.layerTable);
        
        this.contentPanel = new JPanel();
        this.contentPanel.add(this.layerScrollPane);
        
        this.setContentPane(this.contentPanel);
        this.pack();
    }
}

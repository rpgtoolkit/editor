package rpgtoolkit.editor.board;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import rpgtoolkit.editor.utilities.LayerTableModel;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public class LayerFrame extends JFrame 
{
    private AbstractBoardView boardView;
    
    private JTable layerTable;
    private JPanel contentPanel;
    
    public LayerFrame()
    {
        
    }
    
    public LayerFrame(AbstractBoardView boardView)
    {
        this.boardView = boardView;
        this.initialize();
    }
    
    private void initialize()
    {
        this.layerTable = new JTable(new LayerTableModel(this.boardView));
        this.layerTable.getColumnModel().getColumn(0).setPreferredWidth(32);
        this.layerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //this.layerTable.getSelectionModel().addListSelectionListener(this);
        
        this.contentPanel = new JPanel();
        this.contentPanel.add(this.layerTable);
        
        this.setContentPane(this.contentPanel);
        this.setTitle("Board Layers");
        this.pack();
    }
}

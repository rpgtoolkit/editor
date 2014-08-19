package rpgtoolkit.editor.utilities;

import javax.swing.table.AbstractTableModel;
import rpgtoolkit.common.io.types.Board;
import rpgtoolkit.editor.board.AbstractBoardView;
import rpgtoolkit.editor.board.BoardLayerView;
import rpgtoolkit.editor.board.event.BoardChangeListener;
import rpgtoolkit.editor.board.event.BoardChangedEvent;

/**
 * We want to update the board model here, not the view. After updating the
 * model will fire an event to notify the view of the change.
 * 
 * @author Joshua Michael Daly
 * @version 0.1
 */
public class LayerTableModel extends AbstractTableModel implements BoardChangeListener
{
    private AbstractBoardView boardView;
   
    private static final String[] columnNames = {
        "Locked", "Show", "Layer Name"
    };
    
    /*
     * ************************************************************************* 
     * Public Constructors
     * *************************************************************************
     */
    
    /**
     *
     */
    public LayerTableModel()
    {
       
    }
    
    /**
     *
     * @param board
     */
    public LayerTableModel(AbstractBoardView board)
    {
        this.boardView = board;
        this.boardView.getBoard().addBoardChangeListener(this);
    }
    
    /*
     * ************************************************************************* 
     * Public Getters and Setters
     * *************************************************************************
     */
    
    /**
     * 
     * @return 
     */
    public AbstractBoardView getBoardView()
    {
        return this.boardView;
    }
    
    /**
     *
     * @param board
     */
    public void setBoardView(AbstractBoardView board)
    {
        this.boardView = board;
        //fireBoardDataChanged();
    }
    
    @Override
    public String getColumnName(int column)
    {
        return LayerTableModel.columnNames[column];
    }
    
    @Override
    public int getRowCount()
    {
        if (this.boardView == null)
        {
            return 0;
        }
        else
        {
            return this.boardView.getTotalLayers();
        }
    }

    @Override
    public int getColumnCount()
    {
        return LayerTableModel.columnNames.length;
    }
    
    @Override
    public Class getColumnClass(int column)
    {
        switch (column)
        {
            case 0:
                return Boolean.class;
            case 1:
                return Boolean.class;
            case 2:
                return String.class;
        }
        
        return null;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        BoardLayerView layer = this.boardView.getLayer(this.getRowCount() - 
                rowIndex - 1);
        
        if (layer != null)
        {
            if (columnIndex == 0)
            {
                return null;
                //return layer.isLocked();
            }
            else if (columnIndex == 1)
            {
                return layer.isVisible();
            }
            else if (columnIndex == 2)
            {
                Board board = this.boardView.getBoard();
                return board.getLayerTitle(this.getRowCount() - rowIndex - 1);
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        BoardLayerView layer = this.boardView.getLayer(this.getRowCount() - 
                rowIndex - 1);
        
        return !(columnIndex == 0 && layer != null && !layer.isVisible());
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex)
    {
        // The layer locking and visibility is solely view related so we 
        // don't have to worry about the model there, but the name is 
        // linked to the model board in the background.
        BoardLayerView layer = this.boardView.getLayer(this.getRowCount() - 
                rowIndex - 1);
        
        if (layer != null)
        {
            if (columnIndex == 0)
            {
                //layer.setLocked((Boolean)value);
            }
            else if (columnIndex == 1)
            {
                layer.setVisibility((Boolean)value);
            }
            else if (columnIndex == 2)
            {
                // View need to do this using the board models layerTitles
                // the model will then need to update the view, not the other
                // way around.
                Board board = this.boardView.getBoard();
                board.setLayerTitle(this.getRowCount() - rowIndex - 1, 
                        value.toString());
            }
            
            this.fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    @Override
    public void boardChanged(BoardChangedEvent e)
    {
        // Do not respond to this, or the opacity slider will not work!
    }

    @Override
    public void boardLayerAdded(BoardChangedEvent e)
    {
        this.fireTableDataChanged();
    }
    
    @Override
    public void boardLayerMovedUp(BoardChangedEvent e)
    {
        //this.fireTableRowsUpdated(firstRow, lastRow);
        this.fireTableDataChanged();
    }

    @Override
    public void boardLayerMovedDown(BoardChangedEvent e)
    {
        //this.fireTableRowsUpdated(firstRow, lastRow);
        this.fireTableDataChanged();
    }

    @Override
    public void boardLayerDeleted(BoardChangedEvent e)
    {
        this.fireTableDataChanged();
    }
    
}

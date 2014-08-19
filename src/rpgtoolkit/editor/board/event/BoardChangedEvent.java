package rpgtoolkit.editor.board.event;

import java.util.EventObject;
import rpgtoolkit.common.io.types.Board;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public class BoardChangedEvent extends EventObject
{
    private int layer;
    
    /*
     * ************************************************************************* 
     * Public Constructors
     * *************************************************************************
     */
    
    public BoardChangedEvent(Board board)
    {
        super(board);
    }
    
    /*
     * ************************************************************************* 
     * Public Getters and Setters
     * *************************************************************************
     */
    
    public int getLayer()
    {
        return this.layer;
    }
    
    public void setLayer(int layer)
    {
        this.layer = layer;
    }
}

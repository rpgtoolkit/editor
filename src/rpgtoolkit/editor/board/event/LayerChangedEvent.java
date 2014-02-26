package rpgtoolkit.editor.board.event;

import java.util.EventObject;
import rpgtoolkit.editor.board.BoardLayer;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public class LayerChangedEvent extends EventObject
{
    /*
     * ************************************************************************* 
     * Public Constructors
     * *************************************************************************
     */
    
    public LayerChangedEvent(BoardLayer layer)
    {
        super(layer);
    }
}

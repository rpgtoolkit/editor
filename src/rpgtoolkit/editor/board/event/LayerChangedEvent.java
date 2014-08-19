package rpgtoolkit.editor.board.event;

import java.util.EventObject;
import rpgtoolkit.editor.board.BoardLayerView;

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
    
    public LayerChangedEvent(BoardLayerView layer)
    {
        super(layer);
    }
}

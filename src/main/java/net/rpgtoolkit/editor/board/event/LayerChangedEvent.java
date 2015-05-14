package net.rpgtoolkit.editor.board.event;

import java.util.EventObject;
import net.rpgtoolkit.editor.board.BoardLayerView;

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

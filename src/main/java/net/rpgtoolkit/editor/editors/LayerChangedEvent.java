package net.rpgtoolkit.editor.editors;

import java.util.EventObject;
import net.rpgtoolkit.editor.editors.BoardLayerView;

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

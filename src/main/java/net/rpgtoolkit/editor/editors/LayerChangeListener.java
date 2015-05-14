package net.rpgtoolkit.editor.editors;

import java.util.EventListener;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public interface LayerChangeListener extends EventListener 
{
    /**
     * 
     * 
     * @param e
     */
    public void layerChanged(LayerChangedEvent e);
}

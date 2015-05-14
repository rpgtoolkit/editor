package net.rpgtoolkit.editor.editors;

import java.util.EventListener;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public interface TileSelectionListener extends EventListener
{
    public void tileSelected(TileSelectionEvent e);
    
    public void tileRegionSelected(TileRegionSelectionEvent e);
}

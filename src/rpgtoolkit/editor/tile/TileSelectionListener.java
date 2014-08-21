package rpgtoolkit.editor.tile;

import java.util.EventListener;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public interface TileSelectionListener extends EventListener
{
    public void tileSelected(TileSelectionEvent e);
    
    // TODO: Add this at a later date.
    //public void tileRegionSelected(TileRegionSelectionEvent e);
}

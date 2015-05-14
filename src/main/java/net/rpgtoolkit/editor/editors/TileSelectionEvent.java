package net.rpgtoolkit.editor.editors;

import java.util.EventObject;
import net.rpgtoolkit.common.Tile;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public class TileSelectionEvent extends EventObject
{

    private final Tile tile;
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public TileSelectionEvent(Object source, Tile tile)
    {
        super(source);
        this.tile = tile;
    }
    
    /*
     * *************************************************************************
     * Public Getters
     * *************************************************************************
     */
    public Tile getTile()
    {
        return this.tile;
    }
    
}

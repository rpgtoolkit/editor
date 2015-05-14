package net.rpgtoolkit.editor.editors;



import java.util.EventObject;
import net.rpgtoolkit.common.Tile;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public class TileRegionSelectionEvent extends EventObject
{
    private final Tile[][] tiles;

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public TileRegionSelectionEvent(Object source, Tile[][] tiles)
    {
        super(source);
        this.tiles = tiles;
    }
    
    /*
     * *************************************************************************
     * Public Getters
     * *************************************************************************
     */
    public Tile[][] getTiles()
    {
        return this.tiles;
    }
}

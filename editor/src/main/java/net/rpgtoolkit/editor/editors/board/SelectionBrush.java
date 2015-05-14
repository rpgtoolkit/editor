package net.rpgtoolkit.editor.editors.board;

import java.awt.Rectangle;
import net.rpgtoolkit.common.Tile;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */ 
public class SelectionBrush extends CustomBrush
{

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public SelectionBrush(Tile[][] tiles)
    {
        super(tiles);
    }
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    @Override
    public Rectangle doPaint(int x, int y, Rectangle selection) throws Exception
    {
        // Do nothing on paint. Perhaps collected the selected tiles here?
        return null;
    }
    
}

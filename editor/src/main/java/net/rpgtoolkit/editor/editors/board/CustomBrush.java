package net.rpgtoolkit.editor.editors.board;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

import net.rpgtoolkit.common.Tile;
import net.rpgtoolkit.editor.editors.AbstractBoardView;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public class CustomBrush extends AbstractBrush
{

    protected Rectangle bounds;
    protected Tile[][] tiles;
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */    
    public CustomBrush(Tile[][] tiles)
    {
        this.tiles = tiles;
        this.bounds = new Rectangle(this.tiles.length, this.tiles[0].length);
    }
    
    /*
     * *************************************************************************
     * Public Getters and Setters
     * *************************************************************************
     */
    @Override
    public Shape getShape()
    {
        return this.getBounds();
    }
    
    @Override
    public Rectangle getBounds()
    {
        return this.bounds;
    }
    
    public void setBounds(Rectangle rectangle)
    {
        this.bounds = rectangle;
    }
    
    public Tile[][] getTiles()
    {
        return this.tiles;
    }
    
    public void setTiles(Tile[][] tiles)
    {
        this.tiles = tiles;
        this.resize();
    }
    
    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */

    @Override
    public void drawPreview(Graphics2D g2d, AbstractBoardView view)
    {
        
    }

    @Override
    public boolean equals(Brush brush)
    {
        if (brush instanceof CustomBrush)
        {
            if (brush == this)
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public void startPaint(MultiLayerContainer container, int layer)
    {
        super.startPaint(container, layer);
    }
    
    @Override
    public Rectangle doPaint(int x, int y, Rectangle selection) throws Exception
    {
        BoardLayerView layer = this.affectedContainer.getLayer(this.initialLayer);
        
        if (layer == null)
        {
            return null;
        }
        
        int layerWidth = layer.getLayer().getBoard().getWidth();
        int layerHeight = layer.getLayer().getBoard().getHeight();
        
        int centerX = x - this.bounds.width / 2;
        int centerY = y - this.bounds.height / 2;
        
        super.doPaint(x, y, selection);
        
        // TODO: Some small inefficiencies in this with regard to the ifs.
        for (int offsetY = centerY; offsetY < centerY + bounds.height; offsetY++)
        {
            if (offsetY < 0)
            {
               continue;
            }
            else if (offsetY == layerHeight)
            {
                break;
            }
            
            for (int offsetX = centerX; offsetX < centerX + bounds.width; offsetX++)
            {
                if (offsetX < 0)
                {
                    continue;
                }
                else if (offsetX == layerWidth)
                {
                    break;
                }
                
                layer.getLayer().setTileAt(offsetX, offsetY , 
                        tiles[offsetX - centerX][offsetY - centerY]);
            }
        }
        
        return new Rectangle(centerX, centerY, 
                this.bounds.width, this.bounds.height);
    }
    
    /*
     * *************************************************************************
     * Private Methods
     * *************************************************************************
     */
    public void resize()
    {
        this.bounds = new Rectangle(this.tiles.length, this.tiles[0].length);
    }
    
}

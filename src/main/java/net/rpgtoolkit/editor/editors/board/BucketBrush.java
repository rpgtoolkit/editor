package net.rpgtoolkit.editor.editors.board;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.Stack;
import net.rpgtoolkit.common.Tile;
import net.rpgtoolkit.editor.editors.AbstractBoardView;
import net.rpgtoolkit.editor.editors.BoardLayerView;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public class BucketBrush extends AbstractBrush
{

    protected Tile pourTile;
    protected Tile oldTile;
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public BucketBrush()
    {
        
    }
    
    /*
     * *************************************************************************
     * Public Getters
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
        return new Rectangle(0, 0, 1, 1);
    }
    
    public Tile getPourTile()
    {
        return this.pourTile;
    }

    public void setPourTile(Tile tile)
    {
        this.pourTile = tile;
    }
    
    public Tile getOldTile()
    {
        return this.oldTile;
    }
    
    public void setOldTile(Tile tile)
    {
        this.oldTile = tile;
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
        return brush instanceof BucketBrush;
    }
    
    @Override
    public Rectangle doPaint(int x, int y, Rectangle selection)
    {
        BoardLayerView layer = this.affectedContainer.getLayer(this.initialLayer);
        
        if (layer == null)
        {
            return null;
        }
        
        this.oldTile = layer.getLayer().getTileAt(x, y);
        
        if (this.oldTile == this.pourTile)
        {
            return null;
        }
        
        if (selection == null)
        {
            Rectangle area = new Rectangle(new Point(x, y));
            Stack<Point> stack = new Stack<>();
            
            stack.push(new Point(x, y));
            
            while(!stack.empty())
            {
                // Remove the next tile from the stack.
                Point point = stack.pop();
                
                if (layer.getLayer().contains(point.x, point.y) && 
                        layer.getLayer().getTileAt(point.x, point.y) == this.oldTile)
                {
                    layer.getLayer().setTileAt(point.x, point.y, this.pourTile);
                    area.add(point);
                    
                    stack.push(new Point(point.x, point.y - 1));
                    stack.push(new Point(point.x, point.y + 1));
                    stack.push(new Point(point.x + 1, point.y));
                    stack.push(new Point(point.x - 1, point.y));
                }
            }
            
            return new Rectangle(area.x, area.y, area.width + 1, area.height + 1);
        }
        else
        {
            if (selection.contains(x, y))
            {
                for (int y2 = selection.y; y2 < selection.height + selection.y; y2++)
                {
                    for (int x2 = selection.x; x2 < selection.width + selection.x; x2++)
                    {
                        layer.getLayer().setTileAt(x2, y2, this.pourTile);
                    }
                }
            }
            
            return selection;
        }
    }
    
}

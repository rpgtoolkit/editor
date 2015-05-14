package net.rpgtoolkit.editor.board.tool;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import net.rpgtoolkit.common.editor.types.Tile;
import net.rpgtoolkit.editor.board.AbstractBoardView;
import net.rpgtoolkit.editor.board.BoardLayerView;

/**
 *
 *
 * @author Joshua Michael Daly
 */
public class ShapeBrush extends AbstractBrush
{

    protected Area shape;
    protected Tile paintTile;

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public ShapeBrush()
    {

    }

    public ShapeBrush(Area shape)
    {
        this.shape = shape;
        this.paintTile = new Tile();
    }

    public ShapeBrush(AbstractBrush abstractBrush)
    {
        super(abstractBrush);

        if (abstractBrush instanceof ShapeBrush)
        {
            this.shape = ((ShapeBrush) abstractBrush).shape;
            this.paintTile = ((ShapeBrush) abstractBrush).paintTile;
        }
    }

    /*
     * *************************************************************************
     * Public Getters and Setters
     * *************************************************************************
     */
    public Tile getTile()
    {
        return this.paintTile;
    }

    public void setTile(Tile tile)
    {
        this.paintTile = tile;
    }

    @Override
    public Rectangle getBounds()
    {
        return this.shape.getBounds();
    }

    @Override
    public Shape getShape()
    {
        return this.shape;
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    public void makeRectangleBrush(Rectangle rectangle)
    {
        this.shape = new Area(new Rectangle2D.Double(rectangle.x, rectangle.y,
                rectangle.width, rectangle.height));
    }

    @Override
    public void drawPreview(Graphics2D g2d, Dimension dimension,
            AbstractBoardView view)
    {
        g2d.fill(shape);
    }

    @Override
    public void drawPreview(Graphics2D g2d, AbstractBoardView view)
    {

    }

    @Override
    public boolean equals(Brush brush)
    {
        return brush instanceof ShapeBrush
                && ((ShapeBrush) brush).shape.equals(this.shape);
    }

    @Override
    public Rectangle doPaint(int x, int y, Rectangle selection) throws Exception
    {
        Rectangle shapeBounds = this.shape.getBounds();
        int centerX = x - shapeBounds.width / 2;
        int centerY = y - shapeBounds.height / 2;

        super.doPaint(x, y, selection);

        for (int layer = 0; layer < this.affectedLayers; layer++)
        {
            BoardLayerView boardLayer = this.affectedContainer.getLayer(
                    this.initialLayer + layer);

            if (boardLayer != null)
            {
                for (int i = 0; i <= shapeBounds.height + 1; i++)
                {
                    for (int j = 0; j <= shapeBounds.width + 1; j++)
                    {
                        if (this.shape.contains(i, j))
                        {
                            boardLayer.getLayer().setTileAt(j + centerX, i + centerY,
                                    this.paintTile);
                        }
                    }
                }
            }
        }

        return new Rectangle(
                centerX, centerY, shapeBounds.width, shapeBounds.height);
    }
}

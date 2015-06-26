/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.board;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import net.rpgtoolkit.common.assets.Tile;

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
    /**
     * 
     */
    public ShapeBrush()
    {

    }

    /**
     * 
     * 
     * @param shape 
     */
    public ShapeBrush(Area shape)
    {
        this.shape = shape;
        this.paintTile = new Tile();
    }

    /**
     * 
     * 
     * @param abstractBrush 
     */
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
    /**
     * 
     * 
     * @return 
     */
    public Tile getTile()
    {
        return this.paintTile;
    }

    /**
     * 
     * 
     * @param tile 
     */
    public void setTile(Tile tile)
    {
        this.paintTile = tile;
    }

    /**
     * 
     * 
     * @return 
     */
    @Override
    public Rectangle getBounds()
    {
        return this.shape.getBounds();
    }

    /**
     * 
     * 
     * @return 
     */
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
    /**
     * 
     * 
     * @param rectangle 
     */
    public void makeRectangleBrush(Rectangle rectangle)
    {
        this.shape = new Area(new Rectangle2D.Double(rectangle.x, rectangle.y,
                rectangle.width, rectangle.height));
    }

    /**
     * 
     * 
     * @param g2d
     * @param dimension
     * @param view 
     */
    @Override
    public void drawPreview(Graphics2D g2d, Dimension dimension,
            AbstractBoardView view)
    {
        g2d.fill(shape);
    }

    /**
     * 
     * 
     * @param g2d
     * @param view 
     */
    @Override
    public void drawPreview(Graphics2D g2d, AbstractBoardView view)
    {

    }

    /**
     * 
     * 
     * @param brush
     * @return 
     */
    @Override
    public boolean equals(Brush brush)
    {
        return brush instanceof ShapeBrush
                && ((ShapeBrush) brush).shape.equals(this.shape);
    }

    /**
     * 
     * 
     * @param x
     * @param y
     * @param selection
     * @return
     * @throws Exception 
     */
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

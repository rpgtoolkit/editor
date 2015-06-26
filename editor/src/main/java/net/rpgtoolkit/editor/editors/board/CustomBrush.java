/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.board;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

import net.rpgtoolkit.common.assets.Tile;

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
    /**
     * 
     * 
     * @param tiles 
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
    /**
     * 
     * 
     * @return 
     */
    @Override
    public Shape getShape()
    {
        return this.getBounds();
    }
    
    /**
     * 
     * 
     * @return 
     */
    @Override
    public Rectangle getBounds()
    {
        return this.bounds;
    }
    
    /**
     * 
     * 
     * @param rectangle 
     */
    public void setBounds(Rectangle rectangle)
    {
        this.bounds = rectangle;
    }
    
    /**
     * 
     * 
     * @return 
     */
    public Tile[][] getTiles()
    {
        return this.tiles;
    }
    
    /**
     * 
     * 
     * @param tiles 
     */
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
    /**
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
        if (brush instanceof CustomBrush)
        {
            if (brush == this)
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 
     * 
     * @param container
     * @param layer 
     */
    @Override
    public void startPaint(MultiLayerContainer container, int layer)
    {
        super.startPaint(container, layer);
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
    /**
     * 
     */
    public void resize()
    {
        this.bounds = new Rectangle(this.tiles.length, this.tiles[0].length);
    }
    
}

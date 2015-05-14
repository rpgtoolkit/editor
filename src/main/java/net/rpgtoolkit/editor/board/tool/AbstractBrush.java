package net.rpgtoolkit.editor.board.tool;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import net.rpgtoolkit.common.editor.types.MultiLayerContainer;
import net.rpgtoolkit.editor.board.AbstractBoardView;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public abstract class AbstractBrush implements Brush
{
    
    protected int affectedLayers = 1;
    protected MultiLayerContainer affectedContainer;
    protected boolean isPainting = false;
    protected int initialLayer;
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */  
    public AbstractBrush()
    {
        
    }
    
    public AbstractBrush(AbstractBrush abstractBrush)
    {
        this.affectedLayers = abstractBrush.affectedLayers;
    }
    
    /*
     * *************************************************************************
     * Public Getters and Setters
     * *************************************************************************
     */
    @Override
    public int getAffectedLayers()
    {
        return this.affectedLayers;
    }
    
    public void setAffectedLayers(int layers)
    {
        this.affectedLayers = layers;
    }
    
    public int getInitialLayer()
    {
        return this.initialLayer;
    }
    
    public abstract Shape getShape();
    
    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    @Override
    public void startPaint(MultiLayerContainer container, int layer)
    {
        this.affectedContainer = container;
        this.initialLayer = layer;
        this.isPainting = true;
    }
    
    @Override
    public Rectangle doPaint(int x, int y, Rectangle selection) throws Exception
    {
        if (!this.isPainting)
        {
            throw new Exception("Attempted to call doPaint() without calling"
                    + "startPaint() beforehand.");
        }
        
        return null;
    }
    
    @Override
    public void endPaint()
    {
        this.isPainting = false;
    }
    
    @Override
    public void drawPreview(Graphics2D g2d, Dimension dimension, 
            AbstractBoardView view)
    {
        // TODO: draw an off-board preview here.
    }
    
}

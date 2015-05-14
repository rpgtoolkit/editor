package net.rpgtoolkit.editor.editors.board;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import net.rpgtoolkit.common.MultiLayerContainer;
import net.rpgtoolkit.editor.editors.AbstractBoardView;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public interface Brush
{
    public int getAffectedLayers();
    
    public Rectangle getBounds();
    
    public void startPaint(MultiLayerContainer container, int layer);
    
    public Rectangle doPaint(int x, int y, Rectangle selection) throws Exception;
    
    public void endPaint();
    
    public void drawPreview(Graphics2D g2d, AbstractBoardView view);
    
    public void drawPreview(Graphics2D g2d, Dimension dimension, 
            AbstractBoardView view);
    
    public boolean equals(Brush brush);
}

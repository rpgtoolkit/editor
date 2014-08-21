package rpgtoolkit.editor.board.brush;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import rpgtoolkit.common.editor.types.MultiLayerContainer;
import rpgtoolkit.editor.board.AbstractBoardView;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public interface Brush
{
    public int getAffectedLayers();
    
    public Rectangle getBounds();
    
    public void startPaint(MultiLayerContainer container, int x, int y,
            int button, int layer);
    
    public Rectangle doPaint(int x, int y) throws Exception;
    
    public void endPaint();
    
    public void drawPreview(Graphics2D g2d, AbstractBoardView view);
    
    public void drawPreview(Graphics2D g2d, Dimension dimension, 
            AbstractBoardView view);
    
    public boolean equals(Brush brush);
}

package net.rpgtoolkit.editor.editors.board;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import net.rpgtoolkit.editor.editors.AbstractBoardView;
import net.rpgtoolkit.editor.editors.BoardLayerView;
import net.rpgtoolkit.common.BoardSprite;

/**
 *
 * @author Joshua Michael Daly
 */
public class SpriteBrush extends AbstractBrush
{

    private BoardSprite boardSprite;
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public SpriteBrush()
    {
        this.boardSprite = new BoardSprite();
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
        if (this.boardSprite.getSpriteFile() != null) 
        {
            return new Rectangle(0, 0, this.boardSprite.getWidth(), 
                    this.boardSprite.getHeight());
        }
        else
        {
            return new Rectangle(0, 0, 1, 1);
        }
    }

    public BoardSprite getBoardSprite()
    {
        return boardSprite;
    }

    public void setBoardSprite(BoardSprite boardSprite)
    {
        this.boardSprite = boardSprite;
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
    public Rectangle doPaint(int x, int y, Rectangle selection) throws Exception
    {
        super.doPaint(x, y, selection);
        
        BoardLayerView boardLayerView = this.affectedContainer.getLayer(
                this.initialLayer);
        
        if (boardLayerView != null)
        {
            this.boardSprite = new BoardSprite();
            this.boardSprite.setX(x);
            this.boardSprite.setY(y);
            this.boardSprite.setLayer(this.initialLayer);
            
            this.affectedContainer.getLayer(this.initialLayer).
                        getLayer().getSprites().add(this.boardSprite);
            
            boardLayerView.getLayer().getBoard().fireBoardChanged();
            
            Rectangle shapeBounds = this.getBounds();
            int centerX = x - shapeBounds.width / 2;
            int centerY = y - shapeBounds.height / 2;
        
            return new Rectangle(
                    centerX, centerY, shapeBounds.width, shapeBounds.height);
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean equals(Brush brush)
    {
        return brush instanceof SpriteBrush &&
                ((SpriteBrush) brush).boardSprite.equals(this.boardSprite);
    }
    
}

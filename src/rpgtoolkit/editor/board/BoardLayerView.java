package rpgtoolkit.editor.board;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.ArrayList;
import rpgtoolkit.common.editor.types.BoardLayer;
import rpgtoolkit.common.editor.types.MultiLayerContainer;
import rpgtoolkit.common.editor.types.Tile;
import rpgtoolkit.common.io.types.Board;
import rpgtoolkit.editor.board.types.BoardSprite;
import rpgtoolkit.editor.board.types.BoardVector;
import rpgtoolkit.editor.exceptions.TilePixelOutOfRangeException;

/**
 *
 *
 * @author Joshua Michael Daly
 * @version 0.1
 */
public final class BoardLayerView implements Cloneable
{

    /**
     * Layer this view represents.
     */
    private BoardLayer layer;
    /**
     * Is it visible?
     */
    protected boolean isVisible;
    /**
     * Is it locked?
     */
    protected boolean isLocked;
    /**
     * A reference to to MultilayerContainer this layer belongs to.
     */
    protected MultiLayerContainer parentContainer;
    /**
     * Layer opacity 100%, 80% etc.
     */
    protected float opacity;
    /**
     * Bounds of the layer.
     */
    protected Rectangle bounds;

    /*
     * *************************************************************************
     * Constructors
     * *************************************************************************
     */
    /**
     * Default constructor.
     */
    public BoardLayerView()
    {

    }

    /**
     * Used to create a new layer with the specified height and width.
     *
     * @param width Width to use.
     * @param height Height to use.
     */
    public BoardLayerView(int width, int height)
    {

    }

    /**
     * Used to create a new layer with the specified bounds from a rectangle.
     *
     * @param rectangle Rectangle to get the bounds from.
     */
    public BoardLayerView(Rectangle rectangle)
    {

    }

    /**
     * Used to create a new layer from an existing board.
     *
     * @param board board to copy layers from
     */
    public BoardLayerView(Board board)
    {

    }

    /**
     *
     *
     * @param layer
     */
    public BoardLayerView(BoardLayer layer)
    {
        this.layer = layer;
        this.isVisible = true;
        this.isLocked = false;
        this.opacity = 1.0f;
    }

    /**
     *
     *
     * @param board Parent board.
     * @param width Width to use.
     * @param height Height to use.
     */
    public BoardLayerView(Board board, int width, int height)
    {

    }

    /*
     * *************************************************************************
     * Getters and Setters
     * *************************************************************************
     */
    public BoardLayer getLayer()
    {
        return this.layer;
    }
    
    public void setLayer(BoardLayer layer)
    {
        this.layer = layer;
    }
    
    /**
     * Gets the layer width in tiles.
     *
     * @return Layer width in tiles.
     */
    public int getWidth()
    {
        return this.bounds.width;
    }

    /**
     * Sets the layer width in tiles.
     *
     * @param width New layer width in tiles.
     */
    public void setWidth(int width)
    {
        this.bounds.width = width;
    }

    /**
     * Gets the layer height in tiles.
     *
     * @return Layer height in tiles.
     */
    public int getHeight()
    {
        return bounds.height;
    }

    /**
     * Sets the layer height in tiles.
     *
     * @param height New layer height in tiles.
     */
    public void setHeight(int height)
    {
        this.bounds.height = height;
    }

    /**
     * Gets the layer bounds in tiles.
     *
     * @return The layer bounds in tiles
     */
    public Rectangle getBounds()
    {
        return new Rectangle(bounds);
    }

    /**
     * Gets the layer bounds in tiles to the given rectangle.
     *
     * @param rectangle The rectangle to which the layer bounds are assigned.
     */
    public void getBounds(Rectangle rectangle)
    {
        rectangle.setBounds(bounds);
    }

    /**
     * Sets the bounds (in tiles) to the specified Rectangle.
     *
     * @param bounds The bounds to set.
     */
    protected void setBounds(Rectangle bounds)
    {
        this.bounds = new Rectangle(bounds);
    }

    /**
     * Gets the layers current opacity which is a value between 0.0f and 1.0f.
     *
     * @return current layer opacity
     */
    public float getOpacity()
    {
        return opacity;
    }

    /**
     * Sets layer opacity. If it is different from the previous value and the
     * layer is visible, a BoardChangedEvent is fired.
     *
     * @param opacity The new opacity for this layer.
     */
    public void setOpacity(float opacity)
    {
        if (this.opacity != opacity)
        {
            this.opacity = opacity;

            if (this.isVisible() && this.layer != null)
            {
                this.layer.getBoard().fireBoardChanged();
            }
        }
    }

    /**
     * Gets the current visibility of this layer.
     *
     * @return The visibility <code>true</code> or <code>false</code>.
     */
    public boolean isVisible()
    {
        return isVisible;
    }

    /**
     * Sets the visibility of this board layer. If it changes from its current
     * value, a BoardChangedEvent is fired visibility.
     *
     * @param visible <code>true</code> to make the layer visible;
     * <code>false</code> to make it invisible
     */
    public void setVisibility(boolean visible)
    {
        if (isVisible != visible)
        {
            isVisible = visible;

            if (this.layer != null)
            {
                this.layer.getBoard().fireBoardChanged();
            }
        }
    }

    /**
     * Sets the offset of this map layer. The offset is a distance by which to
     * shift this layer from the origin of the map.
     *
     * @param xOffset X offset in tiles.
     * @param yOffset Y offset in tiles.
     */
    public void setOffset(int xOffset, int yOffset)
    {
        bounds.x = xOffset;
        bounds.y = yOffset;
    }

    public boolean isIsVisible()
    {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible)
    {
        this.isVisible = isVisible;
    }

    public boolean isIsLocked()
    {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked)
    {
        this.isLocked = isLocked;
    }

    public MultiLayerContainer getParentContainer()
    {
        return parentContainer;
    }

    public void setParentContainer(MultiLayerContainer parentContainer)
    {
        this.parentContainer = parentContainer;
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    /**
     * Draws the tiles for this layer.
     *
     * @param g Graphics context to draw to.
     * @throws TilePixelOutOfRangeException Throws an exception if the tiles
     * pixel value is out of the allowed range.
     */
    public void drawTiles(Graphics2D g) throws TilePixelOutOfRangeException
    {
        Board parentBoard = this.layer.getBoard();

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                this.opacity));

        for (int x = 0; x < parentBoard.getWidth(); x++)
        {
            for (int y = 0; y < parentBoard.getHeight(); y++)
            {
                if (this.layer.getTiles()[x][y] != null)
                {
                    Tile tile = this.layer.getTiles()[x][y];

                    g.drawImage(tile.getTileAsImage(), (x * 32),
                            (y * 32), null);
                }
                else
                {
                    g.setColor(Color.white);
                }
            }
        }
    }

    /**
     * Draws the vectors for this layer.
     *
     * @param g The graphics context to draw to.
     */
    public void drawVectors(Graphics2D g)
    {

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                this.opacity));

        // Draw Vectors
        ArrayList<BoardVector> vectors = this.layer.getVectors();

        for (BoardVector vector : vectors)
        {
            if (vector.isSelected())
            {
                g.setStroke(new BasicStroke(3.0f)); // Draw it thicker.
            }
            
            // Draw lines from points 0 > 1 , 1 > 2, 2 > 3 etc..
            int count = vector.getPointCount();

            switch (vector.getTileType())
            {
                case 1:
                    g.setColor(Color.WHITE);
                    break;
                case 2:
                    g.setColor(Color.GREEN);
                    break;
                case 16:
                    g.setColor(Color.RED);
                    break;
                default:
                    g.setColor(Color.WHITE);
            }

            for (int i = 0; i < count - 1; i++)
            {
                g.drawLine(
                        vector.getPointX(i), 
                        vector.getPointY(i), 
                        vector.getPointX(i + 1), 
                        vector.getPointY(i + 1));
            }

            if (vector.isClosed())
            {
                // Draw the final lines
                g.drawLine(
                        vector.getPointX(count - 1),
                        vector.getPointY(count - 1),
                        vector.getPointX(0),
                        vector.getPointY(0));
            }
            
            if (vector.isSelected())
            {
                g.setStroke(new BasicStroke(1.0f)); // Return to normal stroke.
            }
        }
    }

    /**
     *
     * @param g
     */
    public void drawSprites(Graphics2D g)
    {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                this.opacity));

        for (BoardSprite sprite : this.layer.getSprites())
        {
            int x = (int) sprite.getX();
            int y = (int) sprite.getY();

            // Get the south facing frame.
            g.drawImage(sprite.getSpriteFile().getAnimationFrame(2), x - 16,
                    y - 32, null);
        }
    }
    
}

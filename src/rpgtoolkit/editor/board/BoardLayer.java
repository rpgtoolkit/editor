package rpgtoolkit.editor.board;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import rpgtoolkit.common.editor.types.MultiLayerContainer;
import rpgtoolkit.common.editor.types.Tile;
import rpgtoolkit.common.io.types.Board;
import rpgtoolkit.editor.board.event.LayerChangeListener;
import rpgtoolkit.editor.board.event.LayerChangedEvent;
import rpgtoolkit.editor.board.types.BoardSprite;
import rpgtoolkit.editor.board.types.BoardVector;
import rpgtoolkit.editor.exceptions.TilePixelOutOfRangeException;

/**
 * 
 * 
 * @author Joshua Michael Daly
 * @version 0.1
 */
public final class BoardLayer implements Cloneable
{
    /*
     * *************************************************************************
     * Class Members
     * *************************************************************************
     */
    
    /**
     * 
     */
    private final LinkedList layerChangeListeners = new LinkedList<>();
    
    /**
     * The name of the layer.
     */
    protected String name;
    /**
     * What number is this layer on the board?
     */
    protected int number;
    /**
     * Is it visible?
     */
    protected boolean isVisible;
    /**
     * Is it locked?
     */
    protected boolean isLocked;
    /**
     * A reference to the board this layer belongs to.
     */
    protected Board parentBoard;
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
    
    /**
     * A list of all the tiles used on this layer.
     */
    protected ArrayList<Tile> tiles;
    
    /*
     * *************************************************************************
     * Constructors
     * *************************************************************************
     */
    
    /**
     * Default constructor.
     */
    public BoardLayer()
    {
        
    }
    
    /**
     * Used to create a new layer with the specified height and width.
     * 
     * @param width Width to use.
     * @param height Height to use.
     */
    public BoardLayer(int width, int height)
    {
        
    }
    
    /**
     * Used to create a new layer with the specified bounds from a rectangle.
     * 
     * @param rectangle Rectangle to get the bounds from.
     */
    public BoardLayer(Rectangle rectangle)
    {
        
    }
    
    /**
     * Used to create a new layer from an existing board.
     * 
     * @param board board to copy layers from
     */
    public BoardLayer(Board board)
    {
        
    }
    
    /**
     * 
     * 
     * @param board Parent board.
     * @param layerNumber
     */
    public BoardLayer(Board board, int layerNumber)
    {
        this.parentBoard = board;
        this.number = layerNumber;
        this.tiles = new ArrayList();
        this.isVisible = true;
        this.isLocked = false;
        this.opacity = 1.0f;
        this.initializeLayer();
    }
    
    /**
     * 
     * 
     * @param board Parent board.
     * @param width Width to use.
     * @param height Height to use.
     */
    public BoardLayer(Board board, int width, int height)
    {
        
    }
    
    /*
     * *************************************************************************
     * Getters and Setters
     * *************************************************************************
     */
   
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
     * Gets the name of this layer.
     * 
     * @return The name of the layer.
     */
    public String getName() 
    {
        return name;
    }

    /**
     * Sets the name of this layer.
     *
     * @param name The new name.
     */
    public void setName(String name) 
    {
        this.name = name;
    }

    /**
     * Sets the map this layer is part of.
     *
     * @param board The board.
     */
    public void setBoard(Board board)
    {
        parentBoard = board;
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

            if (this.isVisible() && parentBoard != null) 
            {
                parentBoard.fireBoardChanged();
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
     *                <code>false</code> to make it invisible
     */
    public void setVisibility(boolean visible) 
    {
        if (isVisible != visible) 
        {
            isVisible = visible;
            
            if (parentBoard != null) 
            {
                this.parentBoard.fireBoardChanged();
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
    
    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
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

    public Board getParentBoard()
    {
        return parentBoard;
    }

    public void setParentBoard(Board parentBoard)
    {
        this.parentBoard = parentBoard;
    }

    public MultiLayerContainer getParentContainer()
    {
        return parentContainer;
    }

    public void setParentContainer(MultiLayerContainer parentContainer)
    {
        this.parentContainer = parentContainer;
    }

    public ArrayList<Tile> getTiles()
    {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles)
    {
        this.tiles = tiles;
    }
    
    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    
    /**
     * Initializes this layer.
     */
    public void initializeLayer()
    {
        int width = parentBoard.getWidth();
        int height = parentBoard.getHeight();
        
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                int tileIndex = parentBoard.getIndexAtLocation(x, y, 
                        number) - 1;

                // There is tile data on this layer 
                if (tileIndex != -1)
                {
                    tiles.add(parentBoard.getTileFromIndex(tileIndex));
                }
                else // -1 no tile data on this layer
                {
                    tiles.add(new Tile());
                }
            }
        }
    }
    
    /**
     * Draws the tiles for this layer.
     * 
     * @param g Graphics context to draw to.
     * @throws TilePixelOutOfRangeException Throws an exception if the tiles
     * pixel value is out of the allowed range.
     */
    public void drawTiles(Graphics2D g) throws TilePixelOutOfRangeException
    {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 
                this.opacity));
        
        for (int x = 0; x < parentBoard.getWidth(); x++)
        {
            for (int y = 0; y < parentBoard.getHeight(); y++)
            {
                int indexToPaint = parentBoard.getIndexAtLocation(x, y, 
                        number) - 1;

                if (indexToPaint >= 0)
                {
                    Tile tile = parentBoard.getTileFromIndex(indexToPaint);

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
        ArrayList<BoardVector> vectors = parentBoard.getVectors();
        
        for (BoardVector vector : vectors)
        {
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
                g.drawLine(vector.getPointX(i), vector.getPointY(i)
                        , vector.getPointX(i + 1), vector.getPointY(i + 1));
            }

            // Draw the final lines
            g.drawLine(vector.getPointX(count - 1), 
                    vector.getPointY(count - 1), 
                    vector.getPointX(0), 
                    vector.getPointY(0));
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
        
        for (BoardSprite sprite : parentBoard.getSprites())
        {
            int x = (int)sprite.getX();
            int y = (int)sprite.getY();
            
            // Get the south facing frame.
            g.drawImage(sprite.getSpriteFile().getAnimationFrame(2), x - 16, 
                    y - 32, null);
        }
    }
    
     /**
     * 
     * 
     * @param listener 
     */
    public void addLayerChangeListener(LayerChangeListener listener)
    {
       this.layerChangeListeners.add(listener); 
    }
    
    /**
     * 
     * 
     * @param listener 
     */
    public void removeLayerChangeListener(LayerChangeListener listener)
    {
       this.layerChangeListeners.remove(listener); 
    }
    
    /*
     * *************************************************************************
     * Protected Methods
     * *************************************************************************
     */
    
    // None yet.
    
    /*
     * *************************************************************************
     * Private Methods
     * *************************************************************************
     */
    
    /**
     * Currently redundant...
     */
    private void fireLayerChanged()
    {
        LayerChangedEvent event = null;
        Iterator iterator = this.layerChangeListeners.iterator();

        while (iterator.hasNext()) 
        {
            if (event == null) 
            {
                event = new LayerChangedEvent(this);
            }
            
            ((LayerChangeListener)iterator.next()).layerChanged(event);
        }
    }
}

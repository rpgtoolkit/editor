package rpgtoolkit.editor.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import javax.swing.JPanel;
import rpgtoolkit.common.editor.types.BoardLayer;
import rpgtoolkit.common.editor.types.MultiLayerContainer;
import rpgtoolkit.common.io.types.Board;
import rpgtoolkit.common.utilities.TileSetCache;
import rpgtoolkit.editor.board.event.BoardChangeListener;
import rpgtoolkit.editor.board.event.BoardChangedEvent;
import rpgtoolkit.editor.exceptions.TilePixelOutOfRangeException;

/**
 * This class is an Abstract model for the visual representation of a 
 * RPG-Toolkit board file. It deals with initializing most of the members
 * needed by a concrete class, it also provides some of the core functionality 
 * such as layer management. It does NOT perform the actual rendering of the 
 * board it only defines abstract methods that a sub class can use, this is 
 * due to the fact that the Toolkit supports both flat 2D boards and isometric 
 * boards.
 * 
 * NOTE: The TileSetCache used in this Abstract class should be centralized to
 * make board rendering more efficient.
 * 
 * @author Joshua Michael Daly
 * @version 0.1
 */
public abstract class AbstractBoardView extends JPanel implements 
        MultiLayerContainer, BoardChangeListener
{
    /*
     * *************************************************************************
     * Class Members
     * *************************************************************************
     */
    
    // Constants
    private static final int ZOOM_NORMALSIZE = 5;
    private static final Color DEFAULT_GRID_COLOR = Color.BLACK;
    private static final Color DEFAULT_BACKGROUND_COLOR = new Color(64, 64, 64);
    private static final double[] zoomLevels = {
    0.0625, 0.125, 0.25, 0.5, 0.75, 1.0, 1.5, 2.0, 3.0, 4.0 };
    
    // This Cache should be centralised
    private TileSetCache tileSetCache;
    
    // Layer properties
    private ArrayList<BoardLayerView> layers;
    private Rectangle bounds;   // in tiles
    
    // Zooming properties
    private int zoomLevel;
    private double zoom;
    
    /**
     * Used to scale the board view.
     */
    protected AffineTransform affineTransform;
    
    /**
     * The board model that this view represents.
     */
    protected Board board;
    
    /**
     * A BufferedImage which this view can be drawn to.
     */
    protected BufferedImage bufferedImage;
    
    /**
     * The parent BoardEditor for this BoardView.
     */
    protected BoardEditor boardEditor;
    
    // Grid properties
    /**
     * A boolean value that indicates whether the grid is visible or not.
     */
    protected boolean isShowGrid;
    private boolean antialiasGrid;
    private Color gridColor;
    private int gridOpacity;
    
    // Coordinate properties
    /**
     * A boolean value that indicates whether the coordinates are visible or not.
     */
    protected boolean isShowCoordinates;
    
    // Vector properties
    /**
     * A boolean value that indicates whether the vectors are visible or not.
     */
    protected boolean isShowVectors;
   
    /*
     * *************************************************************************
     * Constructors
     * *************************************************************************
     */
    
    /**
     * Default constructor.
     */
    public AbstractBoardView()
    {
        
    }
    
    /**
     * This constructor is used when creating a new board.
     * 
     * @param boardEditor The parent BoardEditor for this view.
     */
    public AbstractBoardView(BoardEditor boardEditor)
    {
        this.board = new Board();
        this.boardEditor = boardEditor;
        this.initialize();
    }
    
    /**
     * This constructor is used when opening an existing board.
     * 
     * @param board The Toolkit board that this view represents.
     * @param boardEditor The parent BoardEditor for this view.
     */
    public AbstractBoardView(BoardEditor boardEditor, Board board)
    {
        this.board = board;
        this.boardEditor = boardEditor;
        this.initialize();
    }
    
    /*
     * *************************************************************************
     * Getters and Setters
     * *************************************************************************
     */
    
    /**
     * Gets the default color for the grid.
     * 
     * @return The color.
     */
    public Color getDefaultGridColor()
    {
        return DEFAULT_GRID_COLOR;
    }
    
    /**
     * Gets the default background color.
     * 
     * @return The color.
     */
    public Color getDefaultBackgroudColor()
    {
        return DEFAULT_BACKGROUND_COLOR;
    }
    
    /**
     * Sets the current BoardEditor for this board view.
     * 
     * @param boardEditor The parent BoardEditor.
     */    
    public void setBoardEditor(BoardEditor boardEditor)
    {
        this.boardEditor = boardEditor;
    }

    /**
     * Gets the board associated with this view.
     * 
     * @return The board model.
     */
    public Board getBoard()
    {
        return this.board;
    }
    
    /**
     * Sets the board associated with this view.
     * 
     * @param board The board model.
     */
    public void setBoard(Board board)
    {
        this.board = board;
    }
    
    /**
     * Gets the grid color.
     * 
     * @return The color of the grid.
     */
    public Color getGridColor()
    {
        return this.gridColor;
    }
    
    /**
     * Sets the grid color
     * 
     * @param color The color to use.
     */
    public void setGridColor(Color color)
    {
        this.gridColor = color;
        this.repaint();
    }
    
    /**
     * Gets the opacity of the grid.
     * 
     * @return The opacity, a whole number 100%, 80%, 55% etc.
     */
    public int getGridOpacity()
    {
        return this.gridOpacity;
    }
    
    /**
     * Sets the opacity of the grid.
     * 
     * @param opacity The opacity to use, a whole number 100%, 80%, 55% etc.
     */
    public void setGridOpacity(int opacity)
    {
        this.gridOpacity = opacity;
        this.repaint();
    }
    
    /**
     * Is the grid anti-aliased?
     * 
     * @return Is the grid being anti-aliased?
     */
    public boolean isAntialiasGrid()
    {
        return this.antialiasGrid;
    }
    
    /**
     * Sets the grid to be anti-aliased or not.
     * 
     * @param isAntialias Will the grid be anti-aliased?
     */
    public void setAntialiasGrid(boolean isAntialias)
    {
        this.antialiasGrid = isAntialias;
        this.repaint();
    }
    
    /**
     * Is the grid visible?
     * 
     * @return Is it visible?
     */
    public boolean isShowGrid()
    {
        return isShowGrid;
    }
    
    /**
     * Sets the grids visibility.
     * 
     * @param isVisible Will it be visible?
     */
    public void setShowGrid(boolean isVisible)
    {
        this.isShowGrid = isVisible;
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Are the coordinates visible?
     * 
     * @return Are they visible?
     */
    public boolean isShowCoordinates() 
    {
        return this.isShowCoordinates;
    }

    /**
     * Sets the coordinates visibility.
     * 
     * @param isVisible Will they be visible?
     */
    public void setShowCoordinates(boolean isVisible) 
    {
        this.isShowCoordinates = isVisible;
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Are the vectors visible?
     * 
     * @return Are they visible?
     */
    public boolean isShowVectors() 
    {
        return this.isShowVectors;
    }

    /**
     * Sets the vectors visibility.
     * 
     * @param isVisible Will they be visible?
     */
    public void setShowVectors(boolean isVisible) 
    {
        this.isShowVectors = isVisible;
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Gets the current zoom.
     * 
     * @return The zoom factor i.e. 0.5 = 50%, 2.0 = 200% etc.
     */
    public double getZoom() 
    {
        return this.zoom;
    }
    
    /**
     * Sets the current zoom factor.
     * 
     * @param zoom The amount to zoom by.
     */
    public void setZoom(double zoom) 
    {
        if (zoom > 0) 
        {
            this.zoom = zoom;
            this.rescale();
        }
    }
    
    /**
     * Gets the current zoom level.
     * 
     * @return The current zoom factor.
     */
    public int getZoomLevel() 
    {
        return this.zoomLevel;
    }

    /**
     * Sets the zoom level.
     * 
     * @param index The zoom level to use, the index is used to access the
     * zoom level in an array.
     */
    public void setZoomLevel(int index) 
    {
        if (index >= 0 && index < zoomLevels.length) 
        {
            this.zoomLevel = index;
            this.setZoom(zoomLevels[index]);
        }
    }
    
    /**
     * Returns the total number of layers.
     *
     * @return The size of the layer ArrayList.
     */
    @Override
    public int getTotalLayers() 
    {
        return this.layers.size();
    }
    
    /**
     * Returns a <code>Rectangle</code> representing the maximum bounds in
     * tiles.
     * 
     * @return A new rectangle containing the maximum bounds of this container.
     */
    @Override
    public Rectangle getBounds() 
    {
        return new Rectangle(this.bounds);
    }
    
    /**
     * Returns the layer at the specified ArrayList index.
     *
     * @param index The index of the layer to return.
     * @return The layer at the specified index, or null if the index is out of
     *         bounds.
     */
    @Override
    public BoardLayerView getLayer(int index) 
    {
        try 
        {
            return this.layers.get(index);
        } 
        catch (ArrayIndexOutOfBoundsException e) 
        {
            
        }
        
        return null;
    }
    
    /**
     * Sets a layer based on an index value.
     * 
     * @param index The position in the layers ArrayList to insert the new 
     * layer.
     * @param layer The layer we want to add.
     */
    @Override
    public void setLayer(int index, BoardLayerView layer) 
    {
        this.layers.set(index, layer);
    }
    
    /**
     * Returns the layer ArrayList.
     *
     * @return The layer ArrayList.
     */
    @Override
    public ArrayList<BoardLayerView> getLayerArrayList() 
    {
        return this.layers;
    }

    /**
     * Sets the layer ArrayList to the passed ArrayList.
     *
     * @param layers The new set of layers.
     */
    @Override
    public void setLayerArrayList(ArrayList<BoardLayerView> layers) 
    {
        this.layers = layers;
    }
    
    /**
     * Gets a listIterator of all layers.
     *
     * @return A listIterator.
     */
    @Override
    public ListIterator<BoardLayerView> getLayers() 
    {
        return this.layers.listIterator();
    }
    
    /*
     * *************************************************************************
     * Abstract Methods
     * *************************************************************************
     */
    
    /**
     * A concrete BoardView will implement its own layer drawing code here.
     * 
     * @throws TilePixelOutOfRangeException Thrown if a tiles pixel value is
     * out of the allowed range.
     */
    protected abstract void paintBoard() throws TilePixelOutOfRangeException;
    
    /**
     * A concrete BoardView will implement its own layer drawing code here.
     * 
     * @param g The graphics context to draw on.
     */
    protected abstract void paintLayers(Graphics2D g);
    
    /**
     * A concrete BoardView will implement its own vector drawing code here.
     * 
     * @param g The graphics context to draw on.
     */
    protected abstract void paintVectors(Graphics2D g);
    
    /**
     * A concrete BoardView will implement its own grid drawing code here.
     * 
     * @param g The graphics context to draw on.
     */
    protected abstract void paintGrid(Graphics2D g);
    
    /**
     * A concrete BoardView will implement its own coordinate drawing code here.
     * 
     * @param g The graphics context to draw on.
     */
    protected abstract void paintCoordinates(Graphics2D g);
    
    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    
    /**
     * Zooms in on this board view.
     * 
     * @return Is the zoom level less than the maximum zoom level?
     */
    public boolean zoomIn() 
    {
        if (this.zoomLevel < zoomLevels.length - 1) 
        {
            this.setZoomLevel(this.zoomLevel + 1);
            this.rescale();
        }

        return this.zoomLevel < zoomLevels.length - 1;
    }

    /**
     * Zooms out on this board view.
     * 
     * @return Is the zoom greater than 0?
     */
    public boolean zoomOut() 
    {
        if (this.zoomLevel > 0) 
        {
            this.setZoomLevel(this.zoomLevel - 1);
            this.rescale();
        }

        return this.zoomLevel > 0;
    }

    /**
     * Changes the bounds of this container to include all layers completely.
     */
    @Override
    public void fitBoundsToLayers() 
    {
        int width = 0;
        int height = 0;

        Rectangle layerBounds = new Rectangle();

        for (int i = 0; i < this.layers.size(); i++) 
	{
            	this.getLayer(i).getBounds(layerBounds);

 		if (width < layerBounds.width)
		{ 
			width = layerBounds.width;
		}

            	if (height < layerBounds.height)
		{ 
			height = layerBounds.height;
		}
        }

        this.bounds.width = width;
        this.bounds.height = height;
    }

    /**
     * Adds a layer to the board.
     *
     * @param layer The {@link MapLayer} to add.
     * @return The layer passed to the method.
     */
    @Override
    public BoardLayerView addLayerView(BoardLayerView layer) 
    {
        this.layers.add(layer);
        return layer;
    }

    /**
     * Adds the BoardLayerView <code>l</code> after the MapLayer <code>after</code>.
     *
     * @param layer The layer to add.
     * @param after Specifies the layer to add <code>l</code> after.
     */
    @Override
    public void addLayerAfter(BoardLayerView layer, BoardLayerView after) 
    {
        this.layers.add(this.layers.indexOf(after) + 1, layer);
    }

    /**
     * Add a layer at the specified index, which should be within
     * the valid range.
     *
     * @param index The position at which to add the layer.
     * @param layer The layer to add.
     */
    @Override
    public void addLayer(int index, BoardLayerView layer) 
    {
        this.layers.add(index, layer);
    }

    /**
     * Adds all the layers in a given java.util.Collection.
     *
     * @param layers A collection of layers to add.
     */
    @Override
    public void addAllLayers(Collection<BoardLayerView> layers) 
    {
        this.layers.addAll(layers);
    }

    /**
     * Removes the layer at the specified index. Layers above this layer will
     * move down to fill the gap.
     *
     * @param index The index of the layer to be removed.
     * @return The layer that was removed from the list.
     */
    @Override
    public BoardLayerView removeLayer(int index) 
    {
        return this.layers.remove(index);
    }

    /**
     * Moves the layer at <code>index</code> up one in the ArrayList.
     *
     * @param index The index of the layer to swap up.
     */
    @Override
    public void swapLayerUp(int index) 
    {
        if (index + 1 == this.layers.size()) 
        {            
            throw new RuntimeException(
                    "Can't swap up when already at the top.");
        }

        BoardLayerView hold = this.layers.get(index + 1);
        BoardLayerView move = this.layers.get(index);
        this.layers.set(index + 1, move);
        this.layers.set(index, hold);
    }

    /**
     * Moves the layer at <code>index</code> down one in the ArrayList.
     *
     * @param index The index of the layer to swap down.
     */
    @Override
    public void swapLayerDown(int index) 
    {
        if (index - 1 < 0) 
        {
            throw new RuntimeException(
                    "Can't swap down when already at the bottom.");
        }

        BoardLayerView hold = this.layers.get(index - 1);
        BoardLayerView move = this.layers.get(index);
        this.layers.set(index - 1, move);
        this.layers.set(index, hold);
    }

    /**
     * Determines whether the point (x,y) falls within the container.
     *
     * @param x
     * @param y
     * @return <code>true</code> if the point is within the plane,
     *         <code>false</code> otherwise.
     */
    @Override
    public boolean inBounds(int x, int y) 
    {
        return x >= 0 && y >= 0 && x < this.bounds.width 
                && y < this.bounds.height;
    }

    /**
     * Return an iterator for the layers ArrayList.
     * 
     * @return The iterator.
     */
    @Override
    public Iterator<BoardLayerView> iterator() 
    {
        return this.layers.iterator();
    }
    
    @Override
    public void boardChanged(BoardChangedEvent e)
    {
        this.repaint();
    }
    
    @Override 
    public void boardLayerAdded(BoardChangedEvent e)
    {
        this.addLayerView(new BoardLayerView(e.getLayer()));
        this.repaint();
    }
    
    @Override
    public void boardLayerMovedUp(BoardChangedEvent e)
    {
        this.swapLayerUp(e.getLayer().getNumber() - 1);
        this.repaint();
    }
    
    @Override
    public void boardLayerMovedDown(BoardChangedEvent e)
    {
        this.swapLayerDown(e.getLayer().getNumber() + 1);
        this.repaint();
    }
    
    @Override
    public void boardLayerCloned(BoardChangedEvent e)
    {
        this.addLayer(e.getLayer().getNumber(), new BoardLayerView(e.getLayer()));
        this.repaint();
    }
    
    @Override
    public void boardLayerDeleted(BoardChangedEvent e)
    {
        this.removeLayer(e.getLayer().getNumber());
        this.repaint();
    }
  
    /*
     * *************************************************************************
     * Private Methods 
     * *************************************************************************
     */
    
    /**
     * Initializes a BoardView, it sets most of the BoardView's members but 
     * it does not do anything with regard to the board model or parent editor.
     */
    private void initialize()
    {
        this.board.addBoardChangeListener(this);
        
        this.layers = new ArrayList();
        this.bounds = new Rectangle();
        
        this.zoom = 1.0;
        this.zoomLevel = ZOOM_NORMALSIZE;
        this.affineTransform = new AffineTransform();
        
        this.loadTileSetCache(board);
        this.setPreferredSize(new Dimension((board.getWidth() * 32)
                , (board.getHeight() * 32)));
        
        bufferedImage = new BufferedImage((board.getWidth() * 32)
            , (board.getHeight() * 32), BufferedImage.TYPE_INT_ARGB);
        
        this.isShowGrid = false;
        this.antialiasGrid = true;
        this.gridColor = DEFAULT_GRID_COLOR;
        this.gridOpacity = 100;
        
        this.isShowCoordinates = false;
        
        this.isShowVectors = false;
    }
    
    /**
     * Re-scales this board view based on the current zoom level.
     */
    private void rescale() 
    {  
        this.affineTransform = AffineTransform.getScaleInstance(this.zoom, 
                this.zoom); 
        int width = (int) ((this.board.getWidth() * 32) * this.zoom);  
        int height = (int) ((this.board.getHeight() * 32) * this.zoom);  
        this.setPreferredSize(new Dimension(width, height));
        this.repaint(); 
    }
    
    /**
     * Populates the TileSetCache for this board. Note: This Cache should be 
     * centralized to save each editor needlessly eating memory for the same
     * tile sets!
     * 
     * @param board The Toolkit board we want to load tiles for.
     */
    private void loadTileSetCache(Board board)
    {
        this.tileSetCache = new TileSetCache();
        board.initializeTileSetCache(this.tileSetCache);
        
        for (BoardLayer layer : board.getLayers())
        {
            BoardLayerView layerView = new BoardLayerView(layer);
            this.addLayerView(layerView);
        }
    }
}

package net.rpgtoolkit.editor.editors;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.Scrollable;

import net.rpgtoolkit.common.Tile;
import net.rpgtoolkit.common.TileSet;

/**
 * TODO: Deal with TileRegionSelectionEvents at some later date...
 * 
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public final class TilesetCanvas extends JPanel implements Scrollable
{
    private static final int TILES_PER_ROW = 9;
    
    private final LinkedList<TileSelectionListener> tileSelectionListeners =
            new LinkedList<>();
    
    private final TileSet tileset;
    private final BufferedImage bufferedImage;
    
    private Rectangle selection;
    
    private final TilesetMouseAdapter tilesetMouseAdapter;

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public TilesetCanvas(TileSet tileset)
    {
        super();

        this.tileset = tileset;
        this.bufferedImage = new BufferedImage(289, (32 * ((tileset.getTileCount()
                / TILES_PER_ROW) + 1)) + 1, BufferedImage.TYPE_INT_ARGB);
        
        this.tilesetMouseAdapter = new TilesetMouseAdapter();
        this.addMouseListener(this.tilesetMouseAdapter);
        this.addMouseMotionListener(this.tilesetMouseAdapter);
    }

    /*
     * *************************************************************************
     * Public Getters and Setters
     * *************************************************************************
     */
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(this.bufferedImage.getWidth(), 
                this.bufferedImage.getHeight());
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    public void addTileSelectionListener(TileSelectionListener listener)
    {
        this.tileSelectionListeners.add(listener);
    }
    
    public void removeTileSelectionListener(TileSelectionListener listener)
    {
        this.tileSelectionListeners.remove(listener);
    }
    
    @Override
    public void paint(Graphics g)
    {
        this.paintBackground(g);
        
        Graphics2D g2d = this.bufferedImage.createGraphics();
        this.paintTileSet(g2d);
        this.paintGrid(g2d);
        
        if (this.selection != null)
        {
            this.paintSelection(g2d);
        }
        
        g.drawImage(this.bufferedImage, 0, 0, this);
    }
    
    @Override
    public Dimension getPreferredScrollableViewportSize()
    {
        if (this.tileset != null)
        {
            int tileWidth = this.tileset.getTileWidth() + 1;
            
            return new Dimension(TILES_PER_ROW * tileWidth + 1, 200);
        }
        else
        {
            return new Dimension(0, 0);
        }
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, 
            int orientation, int direction)
    {
        if (this.tileset != null)
        {
            return this.tileset.getTileWidth();
        }
        else
        {
            return 0;
        }
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, 
            int orientation, int direction)
    {
        if (this.tileset != null)
        {
            return this.tileset.getTileWidth();
        }
        else
        {
            return 0;
        }
    }

    @Override
    public boolean getScrollableTracksViewportWidth()
    {
        return this.tileset == null || TILES_PER_ROW == 0;
    }

    @Override
    public boolean getScrollableTracksViewportHeight()
    {
        return false;
    }

    /*
     * *************************************************************************
     * Private Methods
     * *************************************************************************
     */
    private void fireTileSelectionEvent(Tile selectedTile)
    {
        TileSelectionEvent event = new TileSelectionEvent(this, selectedTile);
        
        for (TileSelectionListener listener : this.tileSelectionListeners)
        {
            listener.tileSelected(event);
        }
    }
    
    private void fireTileRegionSelectionEvent(Rectangle selection)
    {
        Tile[][] region = createTileLayerFromRegion(selection);
        TileRegionSelectionEvent event = new TileRegionSelectionEvent(this, region);
        
        for (TileSelectionListener listener : this.tileSelectionListeners) 
        {
            listener.tileRegionSelected(event);
        }
    }
    
    private Tile[][] createTileLayerFromRegion(Rectangle rectangle)
    {
        Tile[][] tiles = new Tile[rectangle.width + 1][rectangle.height + 1];
        
        for (int y = rectangle.y; y <= rectangle.y + rectangle.height; y++)
        {
            for (int x = rectangle.x; x <= rectangle.x + rectangle.width; x++)
            {
                tiles[x - rectangle.x][y - rectangle.y] = this.getTileAt(x, y);
            }
        }
        
        return tiles;
    }
    
    private void paintBackground(Graphics g)
    {
        Rectangle clipRectangle = g.getClipBounds();
        int side = 10;
        
        int startX = clipRectangle.x / side;
        int startY = clipRectangle.y / side;
        int endX = (clipRectangle.x + clipRectangle.width) / side + 1;
        int endY = (clipRectangle.y + clipRectangle.height) / side + 1;
        
        // Fill with white background.
        g.setColor(Color.WHITE);
        g.fillRect(clipRectangle.x, clipRectangle.y, 
                clipRectangle.width, clipRectangle.height);
        
        // Draw darker squares.
        g.setColor(Color.LIGHT_GRAY);
        
        for (int y = startY; y < endY; y++)
        {
            for (int x = startX; x < endX; x++)
            {
                if ((y + x) % 2 == 1)
                {
                    g.fillRect(x * side, y * side, side, side);
                }
            }
        }
    }
    
    private void paintTileSet(Graphics2D g2d)
    {
        int x = 0;
        int y = 0;
        int i = 0;

        for (Tile tile : this.tileset.getTiles())
        {
            g2d.drawImage(tile.getTileAsImage(), x, y, this);

            // Update coordinates to draw at.
            x += 32;
            i++;
            if (i % 9 == 0)
            {
                y += 32;
                x = 0;
            }
        }
    }
    
    private void paintGrid(Graphics2D g2d)
    {
        // Determine tile size
        Dimension tileSize = new Dimension(this.tileset.getTileWidth(), 
                this.tileset.getTileHeight());

        // Determine lines to draw from clipping rectangle
        Rectangle clipRectangle = new Rectangle(bufferedImage.getWidth(), 
                bufferedImage.getHeight());
        
        int startX = (clipRectangle.x / tileSize.width * tileSize.width) ;
        int startY = (clipRectangle.y / tileSize.height * tileSize.height);
        int endX = (clipRectangle.x + clipRectangle.width);
        int endY = (clipRectangle.y + clipRectangle.height);

        g2d.setColor(Color.BLACK);
        
        for (int x = startX; x <= endX; x += tileSize.width) 
        {
            g2d.drawLine(x, clipRectangle.y, x, clipRectangle.y + 
                    clipRectangle.height - 1);
        }
        
        for (int y = startY; y <= endY; y += tileSize.height) 
        {
            g2d.drawLine(clipRectangle.x, y, clipRectangle.x + 
                    clipRectangle.width - 1, y);
        }
    }
    
    private void paintSelection(Graphics2D g2d)
    {
        g2d.setColor(new Color(100, 100, 255));
        g2d.draw3DRect(this.selection.x * this.tileset.getTileWidth(), 
                this.selection.y * this.tileset.getTileHeight(), 
                (this.selection.width + 1) * this.tileset.getTileWidth(), 
                (this.selection.height + 1) * this.tileset.getTileHeight(),
                false);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.2f));
        g2d.fillRect(this.selection.x * this.tileset.getTileWidth() + 1, 
                this.selection.y * this.tileset.getTileHeight() + 1, 
                (this.selection.width + 1) * this.tileset.getTileWidth() - 1, 
                (this.selection.height + 1) * this.tileset.getTileHeight() - 1);
    }
    
    private void scrollTileToVisible(Point tile)
    {
        int tileWidth = this.tileset.getTileWidth() + 1;
        int tileHeight = this.tileset.getTileHeight() + 1;
        
        this.scrollRectToVisible(new Rectangle(tile.x * tileWidth, 
                tile.y * tileHeight, 
                tileWidth + 1, 
                tileHeight + 1));
    }
    
    /*
     * *************************************************************************
     * Private Getters and Setters
     * *************************************************************************
     */
    /**
     * Converts pixel coordinates to tile coordinates. The returned coordinates
     * are at least 0 and adjusted with respect to the number of tiles per row
     * and the number of rows.
     * 
     * @param x x coordinate
     * @param y y coordinate
     * @return tile coordinates
     */
    private Point getTileCoordinates(int x, int y)
    {
        int tileWidth = this.tileset.getTileWidth() + 1;
        int tileHeight = this.tileset.getTileHeight() + 1;
        int tileCount = this.tileset.getTileCount();
        int rows = tileCount / TILES_PER_ROW + 
                (tileCount % TILES_PER_ROW > 0 ? 1 : 0);
        
        int tileX = Math.max(0, Math.min(x / tileWidth, TILES_PER_ROW - 1));
        int tileY = Math.max(0, Math.min(y / tileHeight, rows - 1));
        
        return new Point(tileX, tileY);
    }
    
    /**
     * Retrieves the tile at the given tile coordinates. It assumes the tile 
     * coordinates are adjusted to the number of tiles per row.
     * 
     * @param x x tile coordinate
     * @param y y tile coordinate
     * @return the tile at the given tile coordinates, or <code>null</code>
     *          if the index is out of range
     */
    private Tile getTileAt(int x, int y)
    {
        int tileAt = y * TILES_PER_ROW + x;
        
        if (tileAt >= tileset.getTileCount())
        {
            return null;
        }
        else 
        {
            return tileset.getTile(tileAt);
        }
    }
    
    private void setSelection(Rectangle rectangle)
    {
        this.selection = rectangle;
        this.repaint();
    }

    /*
     * *************************************************************************
     * Private Inner Classes
     * *************************************************************************
     */
    private class TilesetMouseAdapter extends MouseAdapter
    {
        private Point origin;
        
        /*
         * *********************************************************************
         * Public Constructors
         * *********************************************************************
         */
        public TilesetMouseAdapter()
        {

        }

        /*
         * *********************************************************************
         * Public Methods
         * *********************************************************************
         */
        @Override
        public void mousePressed(MouseEvent e)
        {
            this.origin = getTileCoordinates(e.getX(), e.getY());
            setSelection(new Rectangle(this.origin.x, this.origin.y, 0, 0));
            scrollTileToVisible(this.origin);
            
            Tile clickedTile = getTileAt(this.origin.x, this.origin.y);
            
            if (clickedTile != null)
            {
                fireTileSelectionEvent(clickedTile);
            }
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            Point point = getTileCoordinates(e.getX(), e.getY());
            Rectangle select = new Rectangle(this.origin.x, this.origin.y, 0, 0);
            select.add(point);
            
            if (!select.equals(selection))
            {
                setSelection(select);
                scrollTileToVisible(point);
            }
            
            if (selection.getWidth() > 0 || selection.getHeight() > 0)
            {
                fireTileRegionSelectionEvent(selection);
            }
        }
    }
    
}

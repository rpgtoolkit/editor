package net.rpgtoolkit.editor.editors;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import net.rpgtoolkit.common.assets.Tile;
import net.rpgtoolkit.common.assets.TilePixelOutOfRangeException;

/**
 * TileCanvas class is responsible for managing the drawing and editing of a
 * tile on screen. The canvas is split up into 32x32 "pixels" where each pixel
 * is scaled correctly to match the screen resolution
 *
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 * @version 0.1
 */
public class TileCanvas extends JPanel implements MouseListener, MouseMotionListener
{

    private Tile tile;
    private int pixelWidth = 10;
    private int pixelHeight = 10;
    private boolean hasChanged = false;
    private BufferedImage transpImage;

    public TileCanvas(Tile tile)
    {
        this.setSize(320, 320);
        this.tile = tile;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        try
        {
            transpImage = ImageIO.read(getClass().getResourceAsStream(
                    "rpgtoolkit/resources/transp.png"));
        }
        catch (IOException e)
        {
        }
    }

    public void changeTile(Tile tile)
    {
        this.tile = tile;
        this.hasChanged = false;
        this.repaint();
    }

    public void setScale(int scale)
    {
        pixelWidth = scale;
        pixelHeight = scale;
    }

    public boolean hasChanged()
    {
        return this.hasChanged;
    }

    public void clearChangedFlag() // Use this after saving!
    {
        this.hasChanged = false;
    }

    private void updateTile(int x, int y)
    {
        try
        {
            // Work out which pixel we clicked
            int pixelRow = x / pixelWidth;
            int pixelCol = y / pixelHeight;

            Color c = new Color(255, 255, 255, 0);
            tile.setPixel(pixelRow, pixelCol, c);

            this.repaint();
        }
        catch (TilePixelOutOfRangeException e)
        {

        }
    }

    @Override
    public void paint(Graphics g)
    {
        try
        {
            g.drawImage(transpImage, 0, 0, null);

            // Draw the tile
            for (int x = 0; x < 32; x++)
            {
                for (int y = 0; y < 32; y++)
                {
                    g.setColor(this.tile.getPixel(x, y));
                    g.fillRect((x * pixelWidth + 1), (y * pixelHeight + 1),
                            pixelWidth, pixelHeight);

                    if (pixelWidth > 5)
                    {
                        g.setColor(Color.BLACK);
                        g.drawRect((x * pixelWidth + 1), (y * pixelHeight + 1),
                                pixelWidth, pixelHeight);
                    }
                }

            }
        }
        catch (TilePixelOutOfRangeException e)
        {
        }
    }

    // Add a mouse adapter here.
    public void mouseClicked(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {
        this.updateTile(e.getX(), e.getY());
    }

    public void mouseReleased(MouseEvent e)
    {
        
    }

    public void mouseEntered(MouseEvent e)
    {

    }

    public void mouseExited(MouseEvent e)
    {

    }

    public void mouseDragged(MouseEvent e)
    {
        this.updateTile(e.getX(), e.getY());
        hasChanged = true;
    }

    public void mouseMoved(MouseEvent e)
    {

    }
}

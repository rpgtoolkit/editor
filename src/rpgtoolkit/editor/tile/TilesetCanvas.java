package rpgtoolkit.editor.tile;

import rpgtoolkit.common.editor.types.Tile;
import rpgtoolkit.common.io.types.TileSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public final class TilesetCanvas extends JPanel implements MouseListener
{
    private final TileSet tileset;
    private final BufferedImage bufferedImage;


    public TilesetCanvas(TileSet tileset)
    {
        super();

        this.tileset = tileset;
        bufferedImage = new BufferedImage(480, (32 * ((tileset.getTileCount() 
                / 15) + 1)), BufferedImage.TYPE_INT_ARGB);
        this.testPaint();
        this.repaint();
        this.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g)
    {
        g.drawImage(bufferedImage, 0, 0, this);
    }

    @Override
    public Dimension getPreferredSize()
    {
        return createPreferredSize();
    }

    private Dimension createPreferredSize()
    {
        return new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    public void testPaint()
    {
        Graphics2D g = bufferedImage.createGraphics();
        int x = 0, y = 0, i = 0;

        for (Tile tile : tileset.getTiles())
        {
            g.drawImage(tile.getTileAsImage(), x, y, this);

            //Update coordinates to draw at
            x += 32;
            i++;
            if (i % 15 == 0)
            {
                y += 32;
                x = 0;
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        System.out.println(e.getX());
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        
    }
}
package rpgtoolkit.editor.tile;

import rpgtoolkit.common.editor.types.Tile;
import rpgtoolkit.common.io.types.TileSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class TilesetCanvas extends JPanel implements MouseListener
{
    private TileSet tileset;
    private BufferedImage bi;


    public TilesetCanvas(TileSet tileset)
    {
        super();

        this.tileset = tileset;
        bi = new BufferedImage(480, (32 * ((tileset.getTileCount() / 15) + 1)), BufferedImage.TYPE_INT_ARGB);
        this.testPaint();
        this.repaint();
        this.addMouseListener(this);
    }

    public void paint(Graphics g)
    {
        g.drawImage(bi, 0, 0, this);
    }

    @Override
    public Dimension getPreferredSize()
    {
        return createPreferredSize();
    }

    private Dimension createPreferredSize()
    {
        return new Dimension(bi.getWidth(), bi.getHeight());
    }

    public void testPaint()
    {
        Graphics2D g = bi.createGraphics();
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

    public void mouseClicked(MouseEvent e)
    {
        System.out.println(e.getX());
    }

    public void mousePressed(MouseEvent e)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseReleased(MouseEvent e)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseEntered(MouseEvent e)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseExited(MouseEvent e)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
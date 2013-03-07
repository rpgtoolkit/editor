package rpgtoolkit.common.editor.types;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import rpgtoolkit.editor.exceptions.TilePixelOutOfRangeException;

/**
 * 
 * @author Geoff Wilson
 * @author Joshua Daly
 */
public class Tile extends WritableRaster
{
    private int previousZoomLevel;
    private BufferedImage tileImage;

    public Tile()
    {
        super(ColorModel.getRGBdefault().createCompatibleSampleModel(32, 32), new Point(0, 0));
    }

    public void setPixel(int x, int y, Color newPixel) throws TilePixelOutOfRangeException
    {
        if ((x > 31) || (y > 31) || (x < 0) || (y < 0))
        {
            throw new TilePixelOutOfRangeException("Invalid Pixel Coordinates");
        }

        int[] colorBands = new int[]{newPixel.getRed(), newPixel.getGreen(), newPixel.getBlue(), newPixel.getAlpha()};
        this.setPixel(x, y, colorBands);

    }

    public Color getPixel(int x, int y) throws TilePixelOutOfRangeException
    {
        if ((x > 31) || (y > 31) || (x < 0) || (y < 0))
        {
            throw new TilePixelOutOfRangeException("Invalid Pixel Coordinates");
        }

        int[] pixel = this.getPixel(x, y, new int[this.getNumBands()]);

        return new Color(pixel[0], pixel[1], pixel[2], pixel[3]);
    }

    public BufferedImage getTileAsImage()
    {
        if (tileImage == null)
        {
            tileImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            tileImage.setData(this);
        }
        
        return tileImage;
    }
}

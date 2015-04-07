package rpgtoolkit.common.editor.types;

//import uk.co.tkce.engine.Texture;
//import uk.co.tkce.engine.TextureLoader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import rpgtoolkit.common.io.types.TileSet;

/**
 * This class stores the necessary data for a single frame, Animations are made up of an
 * ArrayList of these objects.
 *
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 * @version svn
 */
public class AnimationFrame
{

    // This is not a TK3.1 file format so there is no header, or versions

    // Variables
    private String frameName;
    private Tile frameTile;
    private long transparentColour;
    private String frameSound;
    private BufferedImage image;
    private int imageType = 0; // temp 0 = tile, 1 = png

    //private Texture glTexture;

    /**
     * Creates a new frame based on the specified parameters.
     *
     * @param frameName         String value for the name of the new frame.
     * @param transparentColour Number value representing the transparent color used in the animation.
     * @param frameSound        File name of the sound effect that goes with this frame.
     */
    public AnimationFrame(String frameName, long transparentColour, String frameSound)
    {
        this.frameName = frameName;

        System.out.println("\t\tLoading Frame: " + frameName);
        // Check if we are using PNG or TST frames, will add more file formats in a future version.
        if ((frameName.toLowerCase().endsWith("png")) || (frameName.toLowerCase().endsWith("gif")))
        {
            try
            {
                /*
                // Create the full path to the frame file, and load the frame data
                FileInputStream fis = new FileInputStream(System.getProperty("project.path") + "/bitmap/" + frameName);
                Raster wr = ImageIO.read(fis).getData();

                // We are creating an image from a raster rather than reading directly from IO because we need to specify the
                // image type correctly, this is for the engine.
                image = new BufferedImage(wr.getWidth(), wr.getHeight(), BufferedImage.TYPE_INT_ARGB);
                image.setData(wr);
                imageType = 1;    */

                frameName = frameName.replace("\\", "/");
                FileInputStream fis = new FileInputStream(System.getProperty("project.path") + "/Bitmap/" + frameName);
                image = ImageIO.read(fis);
                imageType = 1;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            // Loads the tileset and fetches the correct tile
            frameName = frameName.toLowerCase();
            String tilesetName = frameName.split(".tst")[0] + ".tst";
            String tilesetPath = System.getProperty("project.path") + "/Tiles/" + tilesetName;
            TileSet tileSet = new TileSet(new File(tilesetPath), 1); // get a single tile from the set (faster...)
            frameTile = tileSet.getSingleTileFromSet(Integer.parseInt(frameName.split(".tst")[1]) - 1);
        }

        // Set the transparent color and frame sound, these are not currently used.
        this.transparentColour = transparentColour;
        this.frameSound = frameSound;

    }

//    public void loadTexture(TextureLoader loader)
//    {
//        try
//        {
//            if (imageType == 1)
//            {
//                glTexture = loader.getTexture(image);
//
//            }
//            else
//            {
//                glTexture = loader.getTexture(frameTile.getTileAsImage());
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }

    public String getFrameName()
    {
        return this.frameName;
    }

    public void setFrameName(String frameName)
    {
        this.frameName = frameName;
    }

    public long getTransparentColour()
    {
        return this.transparentColour;
    }

    public void setTransparentColour(long tC)
    {
        this.transparentColour = tC;
    }

    public String getFrameSound()
    {
        return this.frameSound;
    }

    public void setFrameSound(String frameSound)
    {
        this.frameSound = frameSound;
    }

    public Tile getFrameTile()
    {
        return frameTile;
    }

//    public Texture getGlTexture()
//    {
//        return glTexture;
//    }

    public BufferedImage getFrameImage()
    {
        if (imageType == 1)
        {
            return image;

        }
        else
        {
            return frameTile.getTileAsImage();
        }
    }
}

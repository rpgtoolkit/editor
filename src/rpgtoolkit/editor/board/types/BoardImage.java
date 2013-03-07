package rpgtoolkit.editor.board.types;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BoardImage
{

    private long drawType;
    private long layer;
    private long boundLeft; // should be RECT
    private long boundTop;
    private long transparentColour;
    private long canvasPointer; // Wont be needed in Java
    private double scrollX;
    private double scrollY;
    private String fileName;
    private BufferedImage image;

    // For now you will have to manually set this in code (there is no ToolkitCE board editor yet! :)
    private int scrollRatio; // Toolkit CE Only - The first new data!

    public BoardImage()
    {

    }

    public String getFileName()
    {
        return fileName;
    }

    public BufferedImage getAsImage()
    {
        return image;
    }

    public void setBoundLeft(long boundLeft)
    {
        this.boundLeft = boundLeft;
    }

    public void setBoundTop(long boundTop)
    {
        this.boundTop = boundTop;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;

        try
        {
            if (!fileName.equals(""))
            {
                FileInputStream fis = new FileInputStream(System.getProperty("project.path") + "/bitmap/" + fileName);
                image = ImageIO.read(fis);
            }
        }
        catch (IOException e)
        {
        }
    }

    public void setDrawType(long drawType)
    {
        this.drawType = drawType;
    }

    public void setLayer(long layer)
    {
        this.layer = layer;
    }

    public void setTransparentColour(long transparentColour)
    {
        this.transparentColour = transparentColour;
    }

    public void setCanvasPointer(long canvasPointer)
    {
        this.canvasPointer = canvasPointer;
    }

    public void setScrollX(double scrollX)
    {
        this.scrollX = scrollX;
    }

    public void setScrollY(double scrollY)
    {
        this.scrollY = scrollY;
    }

    public void setScrollRatio(int ratio)
    {
        this.scrollRatio = ratio;
    }

    public int getScrollRatio()
    {
        return this.scrollRatio;
    }
}

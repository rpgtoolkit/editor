package rpgtoolkit.common.io.types;

import rpgtoolkit.editor.exceptions.CorruptFileException;
import rpgtoolkit.common.utilities.BinaryIO;

import java.io.*;
import java.util.ArrayList;

public class AnimatedTile extends BasicType
{
    // Constants
    private final String FILE_HEADER = "RPGTLKIT TILEANIM";
    private final int MAJOR_VERSION = 2;
    private final int MINOR_VERSION = 0;

    // Animated Tile Variables
    private long frameCount;
    private ArrayList<String> frames;
    private long framesPerSecond;

    public AnimatedTile(File fileName)
    {
        super(fileName);
        this.open();
    }

    // Hack to fix AnimatedTiles (they don't actually animated at the moment)
    public String getFirstFrame()
    {
        return frames.get(0);
    }

    public boolean open()
    {
        try
        {
            inputStream = new FileInputStream(this.file);
            binaryIO = new BinaryIO(inputStream);
            frames = new ArrayList<>();

            if (binaryIO.readBinaryString().equals(FILE_HEADER))
            {
                int majorVersion = binaryIO.readBinaryInteger();
                binaryIO.readBinaryInteger();

                if (majorVersion == MAJOR_VERSION)
                {
                    framesPerSecond = binaryIO.readBinaryLong();
                    frameCount = binaryIO.readBinaryLong();
                    for (int i = 0; i < frameCount; i++)
                    {
                        frames.add(binaryIO.readBinaryString());
                    }
                }
            }

            inputStream.close();

            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (CorruptFileException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean save()
    {
        try
        {
            outputStream = new FileOutputStream(this.file);
            binaryIO.setOutputStream(outputStream);

            binaryIO.writeBinaryString(FILE_HEADER);
            binaryIO.writeBinaryInteger(MAJOR_VERSION);
            binaryIO.writeBinaryInteger(MINOR_VERSION);
            binaryIO.writeBinaryLong(framesPerSecond);
            binaryIO.writeBinaryLong(frames.size() - 1);
            for (String string : frames)
            {
                binaryIO.writeBinaryString(string);
            }

            outputStream.close();
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAs(File fileName)
    {
        this.file = fileName;
        return this.save();
    }
}

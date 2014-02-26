package rpgtoolkit.common.io.types;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import rpgtoolkit.common.utilities.BinaryIO;

public class BasicType
{
    protected File fileName;
    protected FileInputStream inputStream;
    protected FileOutputStream outputStream;
    protected BinaryIO binaryIO;

    public BasicType()
    {

    }

    public BasicType(File fileName)
    {
        try
        {
            this.fileName = fileName;
            inputStream = new FileInputStream(this.fileName);
            binaryIO = new BinaryIO(inputStream);
        }
        catch (FileNotFoundException e)
        {
            
        }
    }
}

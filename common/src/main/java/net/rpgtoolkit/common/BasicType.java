package net.rpgtoolkit.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import net.rpgtoolkit.common.utilities.BinaryIO;

public class BasicType
{

    protected File file;
    protected FileInputStream inputStream;
    protected FileOutputStream outputStream;
    protected BinaryIO binaryIO;

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public BasicType()
    {

    }

    public BasicType(File fileName)
    {
        try
        {
            this.file = fileName;
            inputStream = new FileInputStream(this.file);
            binaryIO = new BinaryIO(inputStream);
        }
        catch (FileNotFoundException e)
        {

        }
    }
    
    /*
     * *************************************************************************
     * Public Getters and Setters
     * *************************************************************************
     */
    public File getFile()
    {
        return file;
    }

    public void setFile(File fileName)
    {
        this.file = fileName;
    }
    
}

package rpgtoolkit.common.io.types;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Program
{
    private String fileName;
    private StringBuffer programBuffer;

    public Program(String fileName)
    {
        try
        {
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            programBuffer = new StringBuffer();

            char[] chr = new char[(int) file.length()];
            fr.read(chr);
            programBuffer.append(chr);
        }
        catch (FileNotFoundException e1)
        {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (IOException e1)
        {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public StringBuffer getProgramBuffer()
    {
        return programBuffer;
    }
}

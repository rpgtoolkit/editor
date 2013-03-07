package rpgtoolkit.common.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import rpgtoolkit.editor.exceptions.CorruptFileException;

/**
 * This class is design to emulate the "CommonBinaryIO.bas" file
 * that is used in Toolkit 3.1, saves each class needing their own
 * code to constantly check for string termination.
 * <p/>
 * The emulated version attempts to correct some of the issues with
 * the VB6 code, primarily error handling, the code should not simply
 * resume on next line after an error, this can have a knock on effect of
 * corrupting the rest of the data read from the file, even worse if you
 * are writing data to the file. On an error the task should be abandoned
 * unless proper error recovery can be implemented.
 *
 * @author Geoff Wilson (Java), Colin James Fitzpatrick (VB6)
 * @version 0.1
 */
public class BinaryIO
{
    private FileInputStream inputStream;
    private FileOutputStream outputStream;
    private FileChannel channel;
    private ByteBuffer buffer;

    /**
     * Constructs an instance of the Common Binary IO emulator class
     *
     * @param inputStream Input stream to read the data from
     */
    public BinaryIO(FileInputStream inputStream)
    {
        this.inputStream = inputStream;
    }

    public void setOutputStream(FileOutputStream outputStream)
    {
        this.outputStream = outputStream;
        channel = this.outputStream.getChannel();
    }

    /**
     * Reads bytes from the input stream untill a null value is read
     *
     * @return String of bytes read from the input stream
     * @throws CorruptFileException
     */
    public String readBinaryString() throws CorruptFileException
    {
        try
        {
            String stringRead = "";
            boolean loop = true;

            while (loop)
            {
                int asciiChar = inputStream.read();
                if (asciiChar == 0)
                {
                    loop = false;
                }
                else
                {
                    // Append ascii char to string
                    stringRead += (char) asciiChar;
                }
            }

            return stringRead;
        }
        catch (IOException e) // STOP ON ERROR!
        {
            throw new CorruptFileException(inputStream.toString() + " is corrupt!");
        }
    }

    public void writeBinaryString(String theString)
    {
        try
        {
            boolean loop = true;
            char[] stringArray = theString.toCharArray();

            for (char aStringArray : stringArray)
            {
                outputStream.write((int) aStringArray);
            }
            outputStream.write(0); // Strings are null terminated

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Reads an integer from the input stream, in this case Intger = 2 bytes
     *
     * @return Integer value of the bytes read from the input stream
     * @throws CorruptFileException Details of exception thrown
     */
    public int readBinaryInteger() throws CorruptFileException
    {
        try
        {
            byte[] byteArray = new byte[2];
            for (int i = 0; i < 2; i++)
            {
                byteArray[i] = (byte) inputStream.read();
            }
            buffer = ByteBuffer.wrap(byteArray);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            return buffer.getShort();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public void writeBinaryInteger(int data)
    {
        try
        {
            buffer = ByteBuffer.allocate(2);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.putShort((short) data);
            buffer.rewind();
            channel.write(buffer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Reads a long from the input stream, in this case Long = 4 bytes
     *
     * @return Long value of the bytes read from the input stream
     * @throws CorruptFileException
     */
    public long readBinaryLong() throws CorruptFileException
    {
        try
        {
            byte[] byteArray = new byte[4];
            for (int i = 0; i < 4; i++)
            {
                byteArray[i] = (byte) inputStream.read();
            }
            buffer = ByteBuffer.wrap(byteArray);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            return buffer.getInt();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public void writeBinaryLong(long data)
    {
        try
        {
            buffer = ByteBuffer.allocate(4);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.putInt((int) data);
            buffer.rewind();
            channel.write(buffer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public double readBinaryDouble() throws CorruptFileException
    {
        try
        {
            byte[] byteArray = new byte[8];
            for (int i = 0; i < 8; i++)
            {
                byteArray[i] = (byte) inputStream.read();
            }
            buffer = ByteBuffer.wrap(byteArray);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            return buffer.getDouble();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public void writeBinaryDouble(double data)
    {
        try
        {
            buffer = ByteBuffer.allocate(8);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.putDouble(data);
            buffer.rewind();
            channel.write(buffer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public byte readBinaryByte() throws CorruptFileException
    {
        try
        {
            return (byte) inputStream.read();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    public void writeBinaryByte(byte data)
    {
        try
        {
            outputStream.write(data);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void closeInput()
    {
        try
        {
            inputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}

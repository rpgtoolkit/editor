package net.rpgtoolkit.common.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

/**
 * A reader for binary primitives and various common
 * encodings in either little-endian or big-endian byte order.
 * 
 * @author Chris Hutchinson
 */
public class BinaryReader {

    private static final int DEFAULT_BUFFER_SIZE = 4096;

    protected InputStream in;
    protected ByteBuffer data;
    protected ByteOrder order;
    protected byte[] buffer;

    public BinaryReader(InputStream in, ByteOrder order) {
        this.in = in;
        this.order = order;
        this.buffer = new byte[DEFAULT_BUFFER_SIZE];
        this.data = ByteBuffer.wrap(buffer);
    }

    public BinaryReader(InputStream in) {
        this(in, ByteOrder.BIG_ENDIAN);
    }

    public int readBytes(byte[] buffer, int offset, int length)
            throws IOException {
        return in.read(buffer, offset, length);
    }

    public boolean readBoolean() throws IOException {
        return (in.read() != 0);
    }

    public byte readInt8() throws IOException {
        return (byte) (in.read());
    }

    public int readUnsignedInt8() throws IOException {
        return (in.read() & 0xff);
    }

    public char readChar() throws IOException {
        return readChar(order);
    }

    public char readChar(ByteOrder order) throws IOException {
        in.read(buffer, 0, Character.BYTES);
        return data.order(order).getChar(0);
    }

    public short readInt16() throws IOException {
        return readInt16(order);
    }

    public short readInt16(ByteOrder order) throws IOException {
        in.read(buffer, 0, Short.BYTES);
        return data.order(order).getShort(0);
    }

    public int readUnsignedInt16(ByteOrder order) throws IOException {
        in.read(buffer, 0, Short.BYTES);
        return data.order(order).getShort(0) & 0xffff;
    }

    public int readUnsignedInt16() throws IOException {
        return readUnsignedInt16(order);
    }

    public int readInt32(ByteOrder order) throws IOException {
        in.read(buffer, 0, Integer.BYTES);
        return data.order(order).getInt(0);
    }

    public int readInt32() throws IOException {
        return readInt32(order);
    }

    public long readInt64(ByteOrder order) throws IOException {
        in.read(buffer, 0, Long.BYTES);
        return data.order(order).getLong(0);
    }

    public long readInt64() throws IOException {
        return readInt64(order);
    }

    public float readSingle(ByteOrder order) throws IOException {
        in.read(buffer, 0, Float.BYTES);
        return data.order(order).getFloat(0);
    }

    public float readSingle() throws IOException {
        return readSingle(order);
    }

    public double readDouble(ByteOrder order) throws IOException {
        in.read(buffer, 0, Double.BYTES);
        return data.order(order).getDouble(0);
    }

    public double readDouble() throws IOException {
        return readDouble(order);
    }
    
    /**
     * Reads a null-terminated ASCII string.
     * 
     * @return ASCII-encoded string
     * @throws IOException when EOF detected before the end of
     * a string is located, or another I/O error occurs.
     */
    public String readTerminatedString() throws IOException {
    
        final StringBuilder builder = new StringBuilder();
        
        int ch = 0;
        
        while (true) {
            ch = in.read();
            if (ch < 0) throw new EOFException();
            if (ch == 0) break;
            builder.append((char) ch);
        }
        
        return builder.toString();
        
    }
    
    /***
     * Reads the specified number of bytes and attempts to decode
     * them using the specified character set.
     * 
     * @param charset charset to use for decoding
     * @param length number of bytes to read
     * @return String
     * @throws CharacterCodingException when a decoding error occurs
     * @throws IOException when an unexpected I/O error occurs
     */
    
    public String readString(Charset charset, int length)
            throws IOException, CharacterCodingException {
        
        final CharsetDecoder decoder = charset.newDecoder()
                .onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT);
        
        final ByteBuffer out = ByteBuffer.allocate(length);
                
        int read = 0;
        int total = 0;
        
        while (total < length) {
            read = in.read(buffer, 0, length);
            total += read;
            if (read < 0) break;
            out.put(buffer, 0, read);
        }
                
        out.position(0);
                
        return decoder.decode(out).toString();
                
    }
    
}

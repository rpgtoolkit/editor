/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.common.io;

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import org.junit.Test;

import static org.junit.Assert.*;

public class BinaryReaderTests {
    
  
    @Test
    public void testReadLittleEndian() throws IOException {
        
        final BinaryReader reader = new BinaryReader(
                ByteOrder.LITTLE_ENDIAN,
                Object.class.getResourceAsStream("/random"));
         
        final boolean ib = reader.readBoolean();
        final byte i8 = reader.readInt8();
        final int u8 = reader.readUnsignedInt8();
        final char uc = reader.readChar();
        final short i16 = reader.readInt16();
        final int u16 = reader.readUnsignedInt16();
        final int i32 = reader.readInt32();
        final long i64 = reader.readInt64();
        final float f32 = reader.readSingle();
        final double f64 = reader.readDouble();
                
        final String strUtf8 = reader.readString(StandardCharsets.UTF_8, 5);
        final String strUtf16 = reader.readString(StandardCharsets.UTF_16LE, 10);
               
        assertTrue(ib == true);
        assertTrue(i8 == (byte) 0x62);
        assertTrue(u8 == (int) 0x43);
        assertTrue(uc == (char) 0xA9C2);
        assertTrue(i16 == (short) 0x89C8);
        assertTrue(u16 == (int) 0x1905);
        assertTrue(i32 == (int) 0x22F815A3);
        assertTrue(i64 == (long) 0x90C58115F5E0C033L);
        assertTrue(f32 == (float) Float.intBitsToFloat(0x3468062A));
        assertTrue(f64 == (double) Double.longBitsToDouble(
                0x14C26636A57AAAD3L));
        assertEquals("hello", strUtf8);
        assertEquals("utf16", strUtf16);
        
    }

    @Test
    public void testReadBigEndian() throws IOException {

        final BinaryReader reader = new BinaryReader(
                ByteOrder.BIG_ENDIAN,
                Object.class.getResourceAsStream("/random"));
                        
        final boolean ib = reader.readBoolean();
        final byte i8 = reader.readInt8();
        final int u8 = reader.readUnsignedInt8();
        final char uc = reader.readChar();
        final short i16 = reader.readInt16();
        final int u16 = reader.readUnsignedInt16();
        final int i32 = reader.readInt32();
        final long i64 = reader.readInt64();
        final float f32 = reader.readSingle();
        final double f64 = reader.readDouble();
               
        final String strUtf8 = reader.readString(StandardCharsets.UTF_8, 5);
        final String strUtf16LE = 
                reader.readString(StandardCharsets.UTF_16LE, 10);
        final String strUtf16 = 
                reader.readString(StandardCharsets.UTF_16BE, 10);

        assertTrue(ib == true);
        assertTrue(i8 == (byte) 0x62);
        assertTrue(u8 == (int) 0x43);
        assertTrue(uc == (char) 0xC2A9);
        assertTrue(i16 == (short) 0xC889);
        assertTrue(u16 == (int) 0x0519);
        assertTrue(i32 == (int) 0xA315F822);
        assertTrue(i64 == (long) 0x33C0E0F51581C590L);
        assertTrue(f32 == (float) Float.intBitsToFloat(0x2A066834));
        assertTrue(f64 == (double) Double.longBitsToDouble(
                0xD3AA7AA53666C214L));
        assertEquals("hello", strUtf8);
        assertEquals("utf16", strUtf16LE);
        assertEquals("utf16", strUtf16);

    }

}

/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.common.assets;

import java.io.File;
import java.io.IOException;

import net.rpgtoolkit.common.CorruptAssetException;

public class Background extends BasicType
{
    // Constants
    private final String FILE_HEADER = "RPGTLKIT BKG";
    private final int MAJOR_VERSION = 2;
    private final int MINOR_VERSION = 3;

    // Background variables
    private String backgroundImage;
    private String backgroundMusic;
    private String movingMusic;
    private String selectionMusic;
    private String readyMusic;
    private String invalidMusic;

    public Background()
    {

    }

    public Background(File fileName)
    {
        super(fileName);
        this.open();
    }

    public boolean open()
    {
        try
        {
            if (binaryIO.readBinaryString().equals(FILE_HEADER))
            {
                int majorVersion = binaryIO.readBinaryInteger();
                int minorVersion = binaryIO.readBinaryInteger();
                backgroundImage = binaryIO.readBinaryString();
                backgroundMusic = binaryIO.readBinaryString();
                movingMusic = binaryIO.readBinaryString();
                selectionMusic = binaryIO.readBinaryString();
                readyMusic = binaryIO.readBinaryString();
                invalidMusic = binaryIO.readBinaryString();
            }

            inputStream.close();

            return true;
        }
        catch (CorruptAssetException e)
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
            binaryIO.setOutputStream(this.outputStream);

            binaryIO.writeBinaryString(FILE_HEADER);
            binaryIO.writeBinaryInteger(MAJOR_VERSION);
            binaryIO.writeBinaryInteger(MINOR_VERSION);
            binaryIO.writeBinaryString(backgroundImage);
            binaryIO.writeBinaryString(backgroundMusic);
            binaryIO.writeBinaryString(movingMusic);
            binaryIO.writeBinaryString(selectionMusic);
            binaryIO.writeBinaryString(readyMusic);
            binaryIO.writeBinaryString(invalidMusic);

            outputStream.close();

            return true;
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

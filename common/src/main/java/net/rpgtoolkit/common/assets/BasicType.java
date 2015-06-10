/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.common.assets;

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

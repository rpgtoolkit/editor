/*
 * See LICENSE.md in the distribution for the full license text including,
 * but not limited to, a notice of warranty and distribution rights.
 */

package net.rpgtoolkit.common.assets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Chris Hutchinson <chris@cshutchinson.com>
 */
public abstract class AssetHandle {
    
    public AssetHandle(AssetDescriptor descriptor) {
        this.descriptor = descriptor;
    }
    
    public abstract InputStream getInputStream() 
            throws IOException;
    
    public abstract OutputStream getOutputStream() 
            throws IOException;
    
    public abstract BufferedReader getReader() 
            throws IOException;
    
    public abstract BufferedWriter getWriter() 
            throws IOException;
    
    public Asset getAsset() {
        return this.asset;
    }
    
    public void setAsset(Asset asset) {
        this.asset = asset;
    }
    
    public AssetDescriptor getDescriptor() {
        return this.descriptor;
    }
    
    protected final AssetDescriptor descriptor;
    protected Asset asset;
    
}

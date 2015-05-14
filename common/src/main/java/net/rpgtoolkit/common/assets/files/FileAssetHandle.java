/*
 * See LICENSE.md in the distribution for the full license text including,
 * but not limited to, a notice of warranty and distribution rights.
 */
package net.rpgtoolkit.common.assets.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import net.rpgtoolkit.common.assets.AssetDescriptor;
import net.rpgtoolkit.common.assets.AssetHandle;

/**
 *
 * @author Chris Hutchinson <chris@cshutchinson.com>
 */
public class FileAssetHandle extends AssetHandle {
    
    public FileAssetHandle(AssetDescriptor descriptor) {
        super(descriptor);
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(getFile());
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return new FileOutputStream(getFile());
    }
    
    private File getFile() {
        return new File(this.descriptor.getURI());
    }
    
}

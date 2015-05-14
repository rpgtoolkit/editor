/*
 * See LICENSE.md in the distribution for the full license text including,
 * but not limited to, a notice of warranty and distribution rights.
 */
package net.rpgtoolkit.common.assets.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.rpgtoolkit.common.assets.AssetDescriptor;
import net.rpgtoolkit.common.assets.AssetHandle;

/**
 *
 * @author Chris Hutchinson <chris@cshutchinson.com>
 */
public class ResourceAssetHandle extends AssetHandle {

    public ResourceAssetHandle(AssetDescriptor descriptor) {
        super(descriptor);
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        final String path = descriptor.getURI().getPath();
        final InputStream in =
                ResourceAssetHandle.class.getResourceAsStream(path);
        return in;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new IOException("Internal resource assets are read-only.");
    }
    
}

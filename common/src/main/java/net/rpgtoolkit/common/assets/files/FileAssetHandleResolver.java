/*
 * See LICENSE.md in the distribution for the full license text including,
 * but not limited to, a notice of warranty and distribution rights.
 */
package net.rpgtoolkit.common.assets.files;

import net.rpgtoolkit.common.assets.AssetDescriptor;
import net.rpgtoolkit.common.assets.AssetHandle;
import net.rpgtoolkit.common.assets.AssetHandleResolver;

/**
 *
 * @author Chris Hutchinson <chris@cshutchinson.com>
 */
public class FileAssetHandleResolver 
    implements AssetHandleResolver {

    @Override
    public boolean canResolve(AssetDescriptor descriptor) {
        return descriptor.getURI().getScheme().equals("file");
    }

    @Override
    public AssetHandle resolve(AssetDescriptor descriptor) {
        return new FileAssetHandle(descriptor);
    }
    
}

/*
 * See LICENSE.md in the distribution for the full license text including,
 * but not limited to, a notice of warranty and distribution rights.
 */
package net.rpgtoolkit.common.assets;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteOrder;

import net.rpgtoolkit.common.io.BinaryReader;

/**
 *
 * @author Chris Hutchinson <chris@cshutchinson.com>
 */
public abstract class AbstractAssetSerializer 
    implements AssetSerializer {
    
    @Override
    public int priority() {
        return 0;   // Return standard priority
    }
    
    /***
     * Returns a binary reader with a buffered input stream
     * for the specified asset handle.
     * 
     * @param order byte ordering (little / big)
     * @param handle asset handle
     * @return BinaryReader instance
     * @throws IOException 
     */
    public BinaryReader getBinaryReader(ByteOrder order, AssetHandle handle)
        throws IOException {
        return new BinaryReader(order, new BufferedInputStream(
                handle.getInputStream()));
    }
    
}

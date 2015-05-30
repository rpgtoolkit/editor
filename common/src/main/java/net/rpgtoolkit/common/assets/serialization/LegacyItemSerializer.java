/*
 * See LICENSE.md in the distribution for the full license text including,
 * but not limited to, a notice of warranty and distribution rights.
 */
package net.rpgtoolkit.common.assets.serialization;

import java.io.IOException;
import java.nio.ByteOrder;

import net.rpgtoolkit.common.CorruptAssetException;
import net.rpgtoolkit.common.assets.AbstractAssetSerializer;
import net.rpgtoolkit.common.assets.AssetDescriptor;
import net.rpgtoolkit.common.assets.AssetHandle;
import net.rpgtoolkit.common.assets.Item;
import net.rpgtoolkit.common.io.BinaryReader;
import net.rpgtoolkit.common.io.Paths;

/**
 *
 * @author Chris Hutchinson <chris@cshutchinson.com>
 */
public class LegacyItemSerializer
        extends AbstractAssetSerializer {
    
    private final String HEADER_MAGIC = "RPGTLKIT ITEM";
    private final int HEADER_VERSION_MAJOR = 2;
    private final int HEADER_VERSION_MINOR = 7;

    @Override
    public boolean canSerialize(AssetDescriptor descriptor) {
        final String ext = Paths.getExtension(descriptor.getURI().getPath());
        return (ext.contains("itm"));
    }

    @Override
    public boolean canDeserialize(AssetDescriptor descriptor) {
        return canSerialize(descriptor);
    }

    @Override
    public void serialize(AssetHandle handle)
            throws IOException, CorruptAssetException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deserialize(AssetHandle handle)
            throws IOException, CorruptAssetException {

        final BinaryReader reader = this.getBinaryReader(
                ByteOrder.LITTLE_ENDIAN, handle);
        
        final String header = reader.readTerminatedString();
        final int versionMajor = reader.readInt16();
        final int versionMinor = reader.readInt16();
        
        final Item asset = new Item();
        
        handle.setAsset(asset);
        
    }

}

/// See LICENSE.md in the distribution for the full license text including,
/// but not limited to, a notice of warranty and distribution rights.

package net.rpgtoolkit.common.assets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Comparator;

import net.rpgtoolkit.common.CorruptAssetException;

/**
 * Provides an interface for serializing game assets.
 * 
 * @author Chris Hutchinson <chris@cshutchinson.com>
 */
public interface AssetSerializer {
    
    public class PriorityComparator 
        implements Comparator<AssetSerializer> {

        @Override
        public int compare(AssetSerializer t, AssetSerializer t1) {
            return (t.priority() - t1.priority());
        }
              
    };
    
    public int priority();
    
    public boolean canSerialize(final AssetDescriptor descriptor);
    
    public boolean canDeserialize(final AssetDescriptor descriptor);
    
    public void serialize(AssetHandle handle)
            throws IOException, CorruptAssetException;
    
    public void deserialize(AssetHandle handle)
            throws IOException, CorruptAssetException;
    
}

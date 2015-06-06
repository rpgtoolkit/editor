/// See LICENSE.md in the distribution for the full license text including,
/// but not limited to, a notice of warranty and distribution rights.

package net.rpgtoolkit.common.assets;

import java.io.IOException;
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
            int priorityCompare = t.priority() - t1.priority();
            if(priorityCompare == 0) {
                if(t.equals(t1)) { return 0; }
                else {
                    return t.getClass().getSimpleName()
                            .compareTo(t1.getClass().getSimpleName());
                }
            } else {
                return priorityCompare;
            }
        }
              
    };

    /**
     * Gets the priority of the serializer. Serializers sort in ascending order,
     * so serializers with lower numbers take priority over serializers with
     * higher numbers. If two serializers have the same priority, sorting
     * between them is arbitrary. If you want to guarantee that one serializer
     * comes before another, you must assign it a lower priority number.
     *
     * @return the priority of the serializer; lower numbers come first
     * (serializers are sorted in ascending order)
     */
    public int priority();
    
    public boolean canSerialize(final AssetDescriptor descriptor);
    
    public boolean canDeserialize(final AssetDescriptor descriptor);
    
    public void serialize(AssetHandle handle)
            throws IOException, CorruptAssetException;
    
    public void deserialize(AssetHandle handle)
            throws IOException, CorruptAssetException;
    
}

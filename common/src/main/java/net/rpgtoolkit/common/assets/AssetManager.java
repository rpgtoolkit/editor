/*
 * See LICENSE.md in the distribution for the full license text including,
 * but not limited to, a notice of warranty and distribution rights.
 */
package net.rpgtoolkit.common.assets;

import java.io.IOException;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import net.rpgtoolkit.common.CorruptAssetException;

/**
 *
 * @author Chris Hutchinson <chris@cshutchinson.com>
 */
public class AssetManager {
    
    // Singleton.
    private static final AssetManager instance = new AssetManager();

    public static AssetManager getInstance() {
        return instance;
    }
    
    private AssetManager() {
        this.resolvers = new ArrayList<>();
        this.serializers = new TreeSet<>(
                new AssetSerializer.PriorityComparator());
        this.assets = new HashMap<>();
    }
    
    /***
     * Returns the number of assets managed.
     * @return count of assets managed
     */
    public int getAssetCount() {
        return this.assets.size();
    }
    
    public void addAsset(Asset asset) {
        if(asset == null) { return; }
        AssetHandle handle = resolve(asset.getDescriptor());
        if(handle != null) {
            handle.setAsset(asset);
            out.println("asset set");
            this.assets.put(asset.getDescriptor(), handle);
        }
    }

    /**
     * Registers a serializer with the manager. Serializers are sorted in
     * ascending order of priority, so lower priority numbers are first.
     *
     * @param serializer
     */
    public void registerSerializer(final AssetSerializer serializer) {
        if (serializer == null) {
            throw new NullPointerException();
        }
        this.serializers.add(serializer);
    }
    
    public void registerResolver(final AssetHandleResolver resolver) {
        if (resolver == null) {
            throw new NullPointerException();
        }
        this.resolvers.add(resolver);
    }
    
    public AssetHandle serialize(AssetDescriptor descriptor) 
        throws IOException, CorruptAssetException {
        
        final AssetHandle handle;
        if (assets.containsKey(descriptor)) {
            handle = assets.get(descriptor);
        } else {
            for(AssetDescriptor d : assets.keySet()) {
                out.println(d.getURI());
            }
            throw new CorruptAssetException("No asset found for this descriptor: "
                    + descriptor.getURI());
        }
        
        if (handle != null) {
            for (AssetSerializer serializer : serializers) {
                if (serializer.canSerialize(descriptor)) {
                    serializer.serialize(handle);
                }
                break;
            }
        }
        
        return handle;
        
    }
    
    public AssetHandle deserialize(AssetDescriptor descriptor) 
        throws IOException, CorruptAssetException {
        
        if (assets.containsKey(descriptor)) {
            return assets.get(descriptor);
        }
        
        final AssetHandle handle = resolve(descriptor);
        
        if (handle != null) {
            for (AssetSerializer serializer : serializers) {
                if (serializer.canDeserialize(descriptor)) {
                    serializer.deserialize(handle);
                    if (handle.getAsset() != null) {
                        assets.put(descriptor, handle);
                    }
                    break;
                }
            }
        }
        
        return handle;
        
    }
    
    private AssetHandle resolve(final AssetDescriptor descriptor) {
        
        for (final AssetHandleResolver resolver : resolvers) {
            if (resolver.canResolve(descriptor)) {
                return resolver.resolve(descriptor);
            }
        }
        
        return null;
        
    }
    
    private final TreeSet<AssetSerializer> serializers;
    private final List<AssetHandleResolver> resolvers;
    private final Map<AssetDescriptor, AssetHandle> assets;
    
}

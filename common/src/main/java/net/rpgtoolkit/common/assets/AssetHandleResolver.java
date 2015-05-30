/*
 * See LICENSE.md in the distribution for the full license text including,
 * but not limited to, a notice of warranty and distribution rights.
 */
package net.rpgtoolkit.common.assets;

/**
 *
 * @author Chris Hutchinson <chris@cshutchinson.com>
 */
public interface AssetHandleResolver {
    
    public boolean canResolve(AssetDescriptor descriptor);
    
    public AssetHandle resolve(AssetDescriptor descriptor);
    
}

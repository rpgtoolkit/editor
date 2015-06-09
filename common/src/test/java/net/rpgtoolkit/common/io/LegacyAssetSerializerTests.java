/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/*
 * See LICENSE.md in the distribution for the full license text including,
 * but not limited to, a notice of warranty and distribution rights.
 */
package net.rpgtoolkit.common.io;

import java.io.IOException;
import java.net.URI;
import net.rpgtoolkit.common.CorruptAssetException;
import net.rpgtoolkit.common.assets.AssetDescriptor;
import net.rpgtoolkit.common.assets.AssetHandle;
import net.rpgtoolkit.common.assets.AssetSerializer;
import net.rpgtoolkit.common.assets.Item;
import net.rpgtoolkit.common.assets.resources.ResourceAssetHandle;
import net.rpgtoolkit.common.assets.serialization.LegacyItemSerializer;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Chris Hutchinson <chris@cshutchinson.com>
 */
public final class LegacyAssetSerializerTests {
        
    @Test
    public void testLegacyItemSerialization() 
            throws CorruptAssetException, IOException {
        
        final AssetSerializer serializer = new LegacyItemSerializer();
        final AssetDescriptor descriptor = new AssetDescriptor(
                "resource:///assets//legacy//test.itm");
        
        final AssetHandle handle = deserialize(serializer, descriptor);   
        final Item item = (Item) handle.getAsset();
                
    }
    
    private AssetHandle deserialize(AssetSerializer serializer, 
            AssetDescriptor descriptor) 
            throws CorruptAssetException, IOException {
        
        final AssetHandle handle = new ResourceAssetHandle(descriptor);
        
        assertNotEquals(null, serializer);
        assertNotEquals(null, handle);
        assertEquals(null, handle.getAsset());
        assertTrue(serializer.canDeserialize(descriptor));
        
        serializer.deserialize(handle);
        
        assertNotEquals(null, handle.getAsset());
        
        return handle;

    }
       
}

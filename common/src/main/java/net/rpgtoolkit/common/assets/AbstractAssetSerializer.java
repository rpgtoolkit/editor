/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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

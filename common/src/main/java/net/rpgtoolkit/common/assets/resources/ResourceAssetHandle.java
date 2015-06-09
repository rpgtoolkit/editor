/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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

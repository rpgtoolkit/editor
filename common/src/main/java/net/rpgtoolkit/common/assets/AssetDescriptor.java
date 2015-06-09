/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.common.assets;

import java.net.URI;

/**
 *
 * @author Chris Hutchinson <chris@cshutchinson.com>
 */
public class AssetDescriptor {

    public AssetDescriptor(URI uri) {
        if (uri == null) {
            throw new NullPointerException();
        }
        this.uri = uri;
        this.type = "application/octet-stream";
    }
    
    public AssetDescriptor(String uri) {
        this(URI.create(uri));
    }
      
    public String getType() {
        return this.type;
    }
    
    public URI getURI() {
        return this.uri;
    }
    
    @Override
    public boolean equals(Object rhs) {
        if (rhs == this) return true;
        if (rhs == null) return false;
        if (rhs.getClass() != this.getClass()) return false;
        return this.uri.equals(rhs);
    }
    
    @Override
    public int hashCode() {
        return uri.hashCode();
    }
    
    protected String type;
    protected URI uri;
    
}

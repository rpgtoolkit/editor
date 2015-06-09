/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.common.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Chris Hutchinson <chris@cshutchinson.com>
 */
public final class Paths {
    
    private static final Pattern PATH_EXTENSION_PATTERN = 
            Pattern.compile(".*/.*?(\\..*)");
    
    /***
     * Extracts the file extension (if present) from a file system path.
     * 
     * @param path path contents
     * @return String if extension present, empty string otherwise
     */
    public static String getExtension(String path) {
        final Matcher m = PATH_EXTENSION_PATTERN.matcher(path);
        if (m.matches()) {
           return m.group(1);
        }
        return "";
    }
    
}

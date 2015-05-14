/*
 * See LICENSE.md in the distribution for the full license text including,
 * but not limited to, a notice of warranty and distribution rights.
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

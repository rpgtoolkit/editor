/**
 * Copyright (c) 2017, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.pluginsystem;

import ro.fortsoft.pf4j.ExtensionPoint;

/**
 * A common interface for pluggable engines to implement.
 * 
 * @author Joshua Michael Daly
 */
public interface Engine extends ExtensionPoint {
    
    /**
     * Runs the project located at the specified path. An engine implementation
     * should take a copy of the project rather than running directly against
     * what the editor is using.
     * 
     * @param projectPath 
     * @throws java.lang.Exception 
     */
    public void run(String projectPath) throws Exception;
    
}

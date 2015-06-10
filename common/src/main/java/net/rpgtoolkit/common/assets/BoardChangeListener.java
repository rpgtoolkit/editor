/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.common.assets;

import java.util.EventListener;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public interface BoardChangeListener extends EventListener 
{
    /**
     * 
     * 
     * @param e
     */
    public void boardChanged(BoardChangedEvent e);
    
    /**
     * 
     * 
     * @param e 
     */
    public void boardLayerAdded(BoardChangedEvent e);
    
    /**
     * 
     * 
     * @param e 
     */
    public void boardLayerMovedUp(BoardChangedEvent e);
    
    /**
     * 
     * 
     * @param e 
     */
    public void boardLayerMovedDown(BoardChangedEvent e);
    
    /**
     * 
     * @param e 
     */
    public void boardLayerCloned(BoardChangedEvent e);
    
    /**
     * 
     * 
     * @param e 
     */
    public void boardLayerDeleted(BoardChangedEvent e);
}

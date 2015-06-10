/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import javax.swing.JInternalFrame;

public abstract class ToolkitEditorWindow extends JInternalFrame
{
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public ToolkitEditorWindow()
    {
        
    }
    
    public ToolkitEditorWindow (String title, boolean resizeable, boolean closeable,
            boolean maximizable, boolean iconifiable)
    {
        super(title, resizeable, closeable, maximizable, iconifiable);
    }
    
    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    
    
    /*
     * *************************************************************************
     * Public Abstract Methods
     * *************************************************************************
     */
    public abstract boolean save();

}

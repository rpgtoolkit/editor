/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import net.rpgtoolkit.editor.editors.TilesetCanvas;

/**
 *
 * @author Joshua Michael Daly
 */
public class TileSetPanel extends JPanel
{
    
    private TilesetCanvas tilesetCanvas;
    private final JScrollPane scrollPane;
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public TileSetPanel()
    {
        this.scrollPane = new JScrollPane();
        this.scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        
        this.setLayout(new BorderLayout());
        this.add(this.scrollPane, BorderLayout.CENTER);
    }
    
    /*
     * *************************************************************************
     * Public Getters and Setters
     * *************************************************************************
     */
    public TilesetCanvas getTilesetCanvas()
    {
        return tilesetCanvas;
    }

    public void setTilesetCanvas(TilesetCanvas tilesetCanvas)
    {
        this.tilesetCanvas = tilesetCanvas;
        this.scrollPane.setViewportView(this.tilesetCanvas);
        this.scrollPane.getViewport().revalidate();
    }
    
    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
}

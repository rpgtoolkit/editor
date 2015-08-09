/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import net.rpgtoolkit.common.assets.TileSet;
import net.rpgtoolkit.editor.editors.TileSetCanvas;
import net.rpgtoolkit.editor.ui.resources.Icons;

/**
 *
 * @author Joshua Michael Daly
 */
public class TileSetPanel extends JTabbedPane {

    private final JButton openTileSetButton;

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public TileSetPanel() {
        openTileSetButton = new JButton("Open TileSet");
        openTileSetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                MainWindow.getInstance().openFile();
            }
            
        });
        
        addTab(null, Icons.getLargeIcon("plus"), openTileSetButton);
        setTabPlacement(JTabbedPane.BOTTOM);
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    public void addTileSet(TileSet tileSet) {
        String tabName = tileSet.getName().replace(".tst", "");
        
        if (indexOfTab(tabName) < 0) {
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

            TileSetCanvas canvas = new TileSetCanvas(tileSet);
            canvas.addTileSelectionListener(
                    MainWindow.getInstance().getTileSetSelectionListener());

            scrollPane.setViewportView(canvas);
            scrollPane.getViewport().revalidate();

            addTab(tileSet.getName().replace(".tst", ""), scrollPane);
            setSelectedIndex(indexOfTab(tabName));
        }
    }

    public void addTileSets(Collection<TileSet> tileSets) {
        for (TileSet tileSet : tileSets) {
            addTileSet(tileSet);
        }
    }

    public void removeTileSets() {
        this.removeAll();
    }
}

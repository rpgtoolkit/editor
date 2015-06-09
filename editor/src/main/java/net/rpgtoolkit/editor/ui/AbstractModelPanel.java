/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.rpgtoolkit.editor.editors.BoardEditor;
import net.rpgtoolkit.editor.ui.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public abstract class AbstractModelPanel extends JPanel
{
    
    protected Object model;
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public AbstractModelPanel(Object model, int rows, int columns)
    {
        this.model = model;
        
        this.setLayout(new GridLayout(rows, columns));
        this.add(new JLabel("Name"));
        this.add(new JLabel("Value"));
    }
    
    /*
     * *************************************************************************
     * Public Getters and Setters
     * *************************************************************************
     */
    public Object getModel()
    {
        return this.model;
    }
    
    public BoardEditor getBoardEditor()
    {
        return MainWindow.getInstance().getCurrentBoardEditor();
    }
    
    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    public void updateCurrentBoardView()
    {
        BoardEditor editor = MainWindow.getInstance().getCurrentBoardEditor();
        
        if (editor != null)
        {
            editor.getBoardView().repaint();
        }
    }
    
}

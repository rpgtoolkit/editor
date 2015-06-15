/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.rpgtoolkit.editor.editors.BoardEditor;

/**
 *
 * @author Joshua Michael Daly
 */
public abstract class AbstractModelPanel extends JPanel
{
    
    protected Object model;
    
    protected GridBagConstraints constraints;
    protected GridBagConstraints constraintsRight;
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public AbstractModelPanel(Object model, int rows, int columns)
    {
        this.model = model;
        
        this.setLayout(new GridBagLayout());
        this.constraints = new GridBagConstraints();
        this.constraints.weightx = 1.0;
        this.constraints.weighty = 1.0;
        this.constraints.anchor = GridBagConstraints.NORTHWEST;
        
        this.constraintsRight = this.constraints;
        this.constraintsRight.fill = GridBagConstraints.HORIZONTAL;
        
        constraints.gridx = 0;
        constraints.gridy = 0;        
        this.add(new JLabel("Name"), this.constraints);
        
        constraints.gridx = 1;
        constraints.gridy = 0;
        this.add(new JLabel("Value"), this.constraintsRight);
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

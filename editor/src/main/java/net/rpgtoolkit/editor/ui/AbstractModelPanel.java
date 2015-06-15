/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
        this.constraints.insets = new Insets(5, 5, 5, 5);
        
        this.constraintsRight = this.constraints;
        this.constraintsRight.fill = GridBagConstraints.HORIZONTAL;
        
        Font font = new JLabel().getFont();
        font = new Font(font.getName(), Font.BOLD, font.getSize());
        
        JLabel nameLabel = new JLabel("Name");
        JLabel valueLabel = new JLabel("Value");
        nameLabel.setFont(font);
        valueLabel.setFont(font);
        
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(nameLabel, this.constraints);
        
        constraints.gridx = 1;
        constraints.gridy = 0;
        this.add(valueLabel, this.constraintsRight);
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

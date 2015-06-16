/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.rpgtoolkit.editor.editors.board.BoardLayerView;
import net.rpgtoolkit.common.assets.BoardVector;


/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public class BoardVectorPanel extends AbstractModelPanel
{
    
    private final JSpinner layerSpinner;
    private final JCheckBox isClosedCheckBox;
    private final JTextField handleTextField;
    private final JComboBox<String> tileTypeComboBox;
    
    private static final String[] TILE_TYPES = {
        "SOLID", "UNDER", "STAIRS", "WAYPOINT"
    };
    
    private int lastSpinnerLayer; // Used to ensure that the selection is valid.
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public BoardVectorPanel(BoardVector boardVector)
    {
        ///
        /// super
        ///
        super(boardVector);
        ///
        /// layerSpinner
        ///
        this.layerSpinner = new JSpinner();
        this.layerSpinner.setValue(((BoardVector)this.model).getLayer());
        this.layerSpinner.addChangeListener(new ChangeListener()
        {

            @Override
            public void stateChanged(ChangeEvent e)
            {
                BoardLayerView lastLayerView = getBoardEditor().getBoardView().
                        getLayer(((BoardVector)model).getLayer());
                
                BoardLayerView newLayerView = getBoardEditor().getBoardView().
                        getLayer((int)layerSpinner.getValue());
                
                // Make sure this is a valid move.
                if (lastLayerView != null && newLayerView != null)
                {
                    // Do the swap.
                    ((BoardVector)model).setLayer((int)layerSpinner.getValue());
                    newLayerView.getLayer().getVectors().add((BoardVector)model);
                    lastLayerView.getLayer().getVectors().remove((BoardVector)model);
                    updateCurrentBoardView();
                    
                    // Store new layer selection index.
                    lastSpinnerLayer = (int)layerSpinner.getValue();
                }
                else
                {
                    // Not a valid layer revert selection.
                    layerSpinner.setValue(lastSpinnerLayer);
                }
            }
        });
        
        // Store currently selected layer.
        this.lastSpinnerLayer = (int)this.layerSpinner.getValue();
        ///
        /// isClosedCheckBox
        ///
        this.isClosedCheckBox = new JCheckBox();
        this.isClosedCheckBox.setSelected(((BoardVector)this.model).isClosed());
        this.isClosedCheckBox.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                ((BoardVector)model).setClosed(isClosedCheckBox.isSelected());
                updateCurrentBoardView();
            }
        });
        ///
        /// handleTextField
        ///
        this.handleTextField = new JTextField();
        this.handleTextField.setText(((BoardVector)this.model).getHandle());
        this.handleTextField.addFocusListener(new FocusListener()
        {

            @Override
            public void focusGained(FocusEvent e)
            {
                
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                if (!((BoardVector)model).getHandle().
                        equals(handleTextField.getText()))
                {
                    ((BoardVector)model).setHandle(handleTextField.getText());
                }
            }
        });
        ///
        /// tileTypeComboBox
        ///
        this.tileTypeComboBox = new JComboBox<>(TILE_TYPES);
        
        switch(((BoardVector)this.model).getTileType())
        {
            case 1:
                this.tileTypeComboBox.setSelectedIndex(0);
                break;
            case 2:
                this.tileTypeComboBox.setSelectedIndex(1);
                break;
            case 8:
                this.tileTypeComboBox.setSelectedIndex(2);
                break;
            case 16:
                this.tileTypeComboBox.setSelectedIndex(3);
        }
        
        this.tileTypeComboBox.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                switch(tileTypeComboBox.getSelectedIndex())
                {
                    case 0:
                        ((BoardVector)model).setTileType(1);
                        break;
                    case 1:
                        ((BoardVector)model).setTileType(2);
                        break;
                    case 2:
                        ((BoardVector)model).setTileType(8);
                        break;
                    case 3:
                        ((BoardVector)model).setTileType(16);
                }
                
                updateCurrentBoardView();
            }
        });
        ///
        /// constraints
        ///
        this.constraints.insets = new Insets(4, 15, 0, 30);
        this.constraintsRight.insets = new Insets(0, 0, 10, 15);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 1;
        this.add(new JLabel("Handle"), this.constraints);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 2;
        this.add(new JLabel("Is Closed"), this.constraints);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 3;
        this.add(new JLabel("Layer"), this.constraints);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 4;
        this.add(new JLabel("Type"), this.constraints);
        ///
        /// constraintsRight
        ///
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 1;
        this.add(this.handleTextField, this.constraintsRight);
        
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 2;
        this.add(this.isClosedCheckBox, this.constraintsRight);
        
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 3;
        this.add(this.layerSpinner, this.constraintsRight);
        
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 4;
        this.add(this.tileTypeComboBox, this.constraintsRight);
        
    }
}

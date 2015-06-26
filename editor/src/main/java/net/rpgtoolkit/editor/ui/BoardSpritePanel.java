/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.rpgtoolkit.common.assets.BoardSprite;
import net.rpgtoolkit.editor.editors.board.BoardSpriteDialog;
import net.rpgtoolkit.editor.utilities.FileTools;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public class BoardSpritePanel extends AbstractModelPanel
{

    private final JTextField fileTextField;
    private final JButton fileButton;

    private final JTextField activationProgramTextField;
    private final JButton activationProgramButton;

    private final JTextField multiTaskingTextField;
    private final JButton multiTaskingButton;
    
    private final JSpinner xSpinner;
    private final JSpinner ySpinner;
    private final JSpinner layerSpinner;
    
    private final JComboBox typeComboBox;
    
    private final JButton variablesJButton;
    
    private static final String[] ACTIVATION_TYPES = {
        "STEP-ON", "KEYPRESS"
    };

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public BoardSpritePanel(final BoardSprite boardSprite)
    {
        ///
        /// super
        ///
        super(boardSprite);
        ///
        /// filePanel
        ///
        this.fileTextField = new JTextField(boardSprite.getFileName());
        this.fileTextField.setColumns(17);

        this.fileButton = new JButton("...");
        this.fileButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                File file = FileTools.doChooseFile("itm", "Item", "Item Files");
                
                if (file != null)
                {
                    fileTextField.setText(file.getName());
                }
            }
        });

        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filePanel.add(this.fileTextField);
        filePanel.add(this.fileButton);
        ///
        /// activationProgramPanel
        ///
        this.activationProgramTextField = new JTextField(boardSprite.
                getActivationProgram());
        this.activationProgramTextField.setColumns(17);

        this.activationProgramButton = new JButton("...");
        this.activationProgramButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                File file = FileTools.doChooseFile("prg", "Prg", "Program Files");
                
                if (file != null)
                {
                    activationProgramTextField.setText(file.getName());
                }
            }
        });

        JPanel activationProgramPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        activationProgramPanel.add(this.activationProgramTextField);
        activationProgramPanel.add(this.activationProgramButton);
        ///
        /// multiTaskingPanel
        ///
        this.multiTaskingTextField = new JTextField(boardSprite.
                getMultitaskingProgram());
        this.multiTaskingTextField.setColumns(17);

        this.multiTaskingButton = new JButton("...");
        this.multiTaskingButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                File file = FileTools.doChooseFile("prg", "Prg", "Program Files");
                
                if (file != null)
                {
                    multiTaskingTextField.setText(file.getName());
                }
            }
        });

        JPanel multiTaskingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        multiTaskingPanel.add(this.multiTaskingTextField);
        multiTaskingPanel.add(this.multiTaskingButton);
        ///
        /// xSpinner
        ///
        this.xSpinner = new JSpinner();
        this.xSpinner.setValue(((BoardSprite)this.model).getX());
        this.xSpinner.addChangeListener(new ChangeListener()
        {

            @Override
            public void stateChanged(ChangeEvent e)
            {
                
            }
            
        });
        ///
        /// ySpinner
        ///
        this.ySpinner = new JSpinner();
        this.ySpinner.setValue(((BoardSprite)this.model).getY());
        this.ySpinner.addChangeListener(new ChangeListener()
        {

            @Override
            public void stateChanged(ChangeEvent e)
            {
                
            }
            
        });
        ///
        /// layerSpinner
        ///
        this.layerSpinner = new JSpinner();
        this.layerSpinner.setValue(((BoardSprite)this.model).getLayer());
        this.layerSpinner.addChangeListener(new ChangeListener()
        {

            @Override
            public void stateChanged(ChangeEvent e)
            {
                
            }
            
        });
        ///
        /// typeComboBox
        ///
        this.typeComboBox = new JComboBox(ACTIVATION_TYPES);
        ///
        /// variablesJButton
        ///
        this.variablesJButton = new JButton("Configure");
        this.variablesJButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                BoardSpriteDialog dialog = new BoardSpriteDialog(
                        MainWindow.getInstance(), "Configure Variables", 
                        true, (BoardSprite)model);
                
                if (dialog.showDialog() == BoardSpriteDialog.APPLY)
                {
                    // Rather than update the BoardSprite from the dialog
                    // we'll centralise it and perform it here.
                    String initialVariable = dialog.getInitialVariable();
                    String initialValue = dialog.getInitialValue();
                    String finalVariable = dialog.getFinalVariable();
                    String finalValue = dialog.getFinalValue();
                    String loadingVariable = dialog.getLoadingVariable();
                    String loadingValue = dialog.getLoadingValue();
                    
                    if (!boardSprite.getInitialVariable().equals(initialVariable))
                    {
                        boardSprite.setInitialVariable(initialVariable);
                    }
                    
                    if (!boardSprite.getInitialValue().equals(initialValue))
                    {
                        boardSprite.setInitialValue(initialValue);
                    }
                    
                    if (!boardSprite.getFinalVariable().equals(finalVariable))
                    {
                        boardSprite.setFinalVariable(finalVariable);
                    }
                    
                    if (!boardSprite.getFinalValue().equals(finalValue))
                    {
                        boardSprite.setFinalValue(finalValue);
                    }
                    
                    if (!boardSprite.getLoadingVariable().equals(loadingVariable))
                    {
                        boardSprite.setLoadingVariable(loadingVariable);
                    }
                    
                    if (!boardSprite.getLoadingValue().equals(loadingValue))
                    {
                        boardSprite.setLoadingValue(loadingValue);
                    }
                }
            }
            
        });
        ///
        /// this
        ///
        this.constraints.insets = new Insets(8, 15, 0, 3);
        this.constraintsRight.insets = new Insets(0, 0, 10, 15);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 1;
        this.add(new JLabel("Item File"), this.constraints);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 2;
        this.add(new JLabel("Activation"), this.constraints);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 3;
        this.add(new JLabel("MultiTasking"), this.constraints);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 4;
        this.add(new JLabel("X"), this.constraints);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 5;
        this.add(new JLabel("Y"), this.constraints);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 6;
        this.add(new JLabel("Layer"), this.constraints);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 7;
        this.add(new JLabel("Type"), this.constraints);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 8;
        this.add(new JLabel("Variables"), this.constraints);
        
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 1;
        this.add(filePanel, this.constraintsRight);
        
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 2;
        this.add(activationProgramPanel, this.constraintsRight);
        
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 3;
        this.add(multiTaskingPanel, this.constraintsRight);
        
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 4;
        this.add(this.xSpinner, this.constraintsRight);
        
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 5;
        this.add(this.ySpinner, this.constraintsRight);
        
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 6;
        this.add(this.layerSpinner, this.constraintsRight);
        
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 7;
        this.add(this.typeComboBox, this.constraintsRight);
        
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 8;
        this.add(this.variablesJButton, this.constraintsRight);
    }
}

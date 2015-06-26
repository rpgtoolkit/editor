/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.board;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.rpgtoolkit.common.assets.BoardSprite;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public class BoardSpriteDialog extends JDialog
{
    
    public static final int CANCEL = 0;
    public static final int APPLY = 1;
    
    private int result;
    
    private final BoardSprite boardSprite;
    
    private final JTextField initialVariableTextField;
    private final JTextField initialValueTextField;
    
    private final JTextField finalVariableTextField;
    private final JTextField finalValueTextField;
    
    private final JTextField loadingVariableTextField;
    private final JTextField loadingValueTextField;
    
    private final JButton applyButton;
    private final JButton cancelButton;

    /*
     * ************************************************************************* 
     * Public Getters
     * *************************************************************************
     */
    /**
     * 
     * 
     * @return 
     */
    public String getInitialVariable()
    {
        return initialVariableTextField.getText();
    }

    /**
     * 
     * 
     * @return 
     */
    public String getInitialValue()
    {
        return initialValueTextField.getText();
    }

    /**
     * 
     * 
     * @return 
     */
    public String getFinalVariable()
    {
        return finalVariableTextField.getText();
    }

    /**
     * 
     * 
     * @return 
     */
    public String getFinalValue()
    {
        return finalValueTextField.getText();
    }

    /**
     * 
     * 
     * @return 
     */
    public String getLoadingVariable()
    {
        return loadingVariableTextField.getText();
    }

    /**
     * 
     * 
     * @return 
     */
    public String getLoadingValue()
    {
        return loadingValueTextField.getText();
    }
    
    /*
     * ************************************************************************* 
     * Public Constructors
     * *************************************************************************
     */
    /**
     * 
     * 
     * @param parentFrame
     * @param title
     * @param isModal
     * @param boardSprite 
     */
    public BoardSpriteDialog(JFrame parentFrame, String title, boolean isModal,
            BoardSprite boardSprite)
    {
        super(parentFrame, title, isModal);
        this.boardSprite = boardSprite;
        
        ///
        /// initialVariableTextField
        ///
        this.initialVariableTextField = new JTextField();
        this.initialVariableTextField.setColumns(10);
        this.initialVariableTextField.setText(
                this.boardSprite.getInitialVariable());
        ///
        /// initialValueTextField
        ///
        this.initialValueTextField = new JTextField();
        this.initialValueTextField.setColumns(10);
        this.initialValueTextField.setText(this.boardSprite.getInitialValue());
        ///
        /// finalVariableTextField
        ///
        this.finalVariableTextField = new JTextField();
        this.finalVariableTextField.setColumns(10);
        this.finalVariableTextField.setText(
                this.boardSprite.getFinalVariable());
        ///
        /// finalValueTextField
        ///
        this.finalValueTextField = new JTextField();
        this.finalValueTextField.setColumns(10);
        this.finalValueTextField.setText(this.boardSprite.getFinalValue());
        ///
        /// loadingVariableTextField
        ///
        this.loadingVariableTextField = new JTextField();
        this.loadingVariableTextField.setColumns(10);
        this.loadingVariableTextField.setText(
                this.boardSprite.getLoadingVariable());
        ///
        /// loadingValueTextField
        ///
        this.loadingValueTextField = new JTextField();
        this.loadingValueTextField.setColumns(10);
        this.loadingValueTextField.setText(this.boardSprite.getLoadingValue());
        ///
        /// applyButton
        ///
        this.applyButton = new JButton("Apply");
        this.applyButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                result = APPLY;
                dispose();
            }
            
        });
        ///
        /// cancelButton
        ///
        this.cancelButton = new JButton("Cancel");
        this.cancelButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                result = CANCEL;
                dispose();
            }
            
        });
        ///
        /// dialogPanel
        ///
        JPanel dialogPanel = new JPanel(new GridBagLayout());
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(5, 5, 5, 5);
        
        // Header Labels
        // Labels '='
        constraints.gridx = 0;
        constraints.gridy = 0;
        dialogPanel.add(new JLabel("Something"), constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 2;
        dialogPanel.add(new JLabel("Something"), constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 4;
        dialogPanel.add(new JLabel("Something"), constraints);
        
        // Variable Fields
        constraints.gridx = 0;
        constraints.gridy = 1;
        dialogPanel.add(this.initialVariableTextField, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 3;
        dialogPanel.add(this.finalVariableTextField, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 5;
        dialogPanel.add(this.loadingVariableTextField, constraints);
        
        // Labels '='
        constraints.gridx = 1;
        constraints.gridy = 1;
        dialogPanel.add(new JLabel("="), constraints);
        
        constraints.gridx = 1;
        constraints.gridy = 3;
        dialogPanel.add(new JLabel("="), constraints);
        
        constraints.gridx = 1;
        constraints.gridy = 5;
        dialogPanel.add(new JLabel("="), constraints);
        
        // Value Fields
        constraints.gridx = 2;
        constraints.gridy = 1;
        dialogPanel.add(this.initialValueTextField, constraints);
        
        constraints.gridx = 2;
        constraints.gridy = 3;
        dialogPanel.add(this.finalValueTextField, constraints);
        
        constraints.gridx = 2;
        constraints.gridy = 5;
        dialogPanel.add(this.loadingValueTextField, constraints);
        ///
        /// buttonPanel
        ///
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        buttonPanel.add(this.applyButton);
        buttonPanel.add(this.cancelButton);
        ///
        /// paddingPanel
        ///
        JPanel paddingPanel = new JPanel(new BorderLayout());
        paddingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        paddingPanel.add(dialogPanel, BorderLayout.CENTER);
        paddingPanel.add(buttonPanel, BorderLayout.SOUTH);
        ///
        /// this
        ///
        this.setResizable(false);
        this.add(paddingPanel);
    }
    
    /*
     * ************************************************************************* 
     * Public Methods
     * *************************************************************************
     */
    /**
     * 
     * 
     * @return 
     */
    public int showDialog()
    {
        this.pack();
        this.setLocationRelativeTo(this.getOwner());
        this.setVisible(true);
        
        return this.result;
    }
    
}

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
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.rpgtoolkit.common.assets.BoardSprite;

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

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public BoardSpritePanel(BoardSprite boardSprite)
    {
        ///
        /// super
        ///
        super(boardSprite);
        ///
        /// filePanel
        ///
        this.fileTextField = new JTextField(boardSprite.getFileName());
        this.fileTextField.setEnabled(false);
        this.fileTextField.setColumns(12);

        this.fileButton = new JButton("...");
        this.fileButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                File file = doChooseFile("itm", "Item", "Item Files");
                
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
        this.activationProgramTextField.setEnabled(false);
        this.activationProgramTextField.setColumns(12);

        this.activationProgramButton = new JButton("...");
        this.activationProgramButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                File file = doChooseFile("prg", "Prg", "Program Files");
                
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
        this.multiTaskingTextField.setEnabled(false);
        this.multiTaskingTextField.setColumns(12);

        this.multiTaskingButton = new JButton("...");
        this.multiTaskingButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                File file = doChooseFile("prg", "Prg", "Program Files");
                
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
        /// this
        ///
        this.constraints.insets = new Insets(8, 15, 0, 3);
        this.constraintsRight.insets = new Insets(0, 0, 10, 15);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 1;
        this.add(new JLabel("Item File"), this.constraints);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 2;
        this.add(new JLabel("Activation Program"), this.constraints);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 3;
        this.add(new JLabel("MultiTasking Program"), this.constraints);
        
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 1;
        this.add(filePanel, this.constraintsRight);
        
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 2;
        this.add(activationProgramPanel, this.constraintsRight);
        
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 3;
        this.add(multiTaskingPanel, this.constraintsRight);
    }

    /*
     * *************************************************************************
     * Private Static Methods
     * *************************************************************************
     */
    private static File doChooseFile(String extension, String directory, 
            String type)
    {
        if (MainWindow.getInstance().getActiveProject() != null)
        {
            File projectPath = new File(System.getProperty("project.path") +
                    "/" + directory);

            if (projectPath.exists())
            {
                JFileChooser fileChooser = MainWindow.getInstance().
                        getFileChooser();

                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        type, extension);
                fileChooser.setFileFilter(filter);
                fileChooser.setCurrentDirectory(projectPath);

                if (fileChooser.showOpenDialog(MainWindow.getInstance())
                        == JFileChooser.APPROVE_OPTION)
                {
                    return fileChooser.getSelectedFile();
                }
            }
        }
        
        return null;
    }

}

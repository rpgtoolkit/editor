/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.board;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.rpgtoolkit.common.assets.Board;
import net.rpgtoolkit.common.assets.BoardSprite;
import net.rpgtoolkit.editor.utilities.FileTools;

/**
 *
 *
 * @author Joshua Michael Daly
 */
public class BoardNeighboursDialog extends JDialog {

  /**
   *
   */
  public static final int CANCEL = 0;

  /**
   *
   */
  public static final int APPLY = 1;

  private int result;

  private final Board board;

  private final JTextField northNeighbourTextField;
  private final JTextField southNeighbourTextField;
  private final JTextField eastNeighbourTextField;
  private final JTextField westNeighbourTextField;
  
  private final JButton northBrowseButton;
  private final JButton southBrowseButton;
  private final JButton eastBrowseButton;
  private final JButton westBrowseButton;
  
  private final JButton applyButton;
  private final JButton cancelButton;

  public ArrayList<String> getNeighbours() {
    return new ArrayList<>(Arrays.asList(northNeighbourTextField.getText(),
            southNeighbourTextField.getText(),
            eastNeighbourTextField.getText(),
            westNeighbourTextField.getText()));
  }
  
  /**
   * 
   * @return 
   */
  public String getNorthNeighbour() {
    return northNeighbourTextField.getText();
  }
  
  /**
   * 
   * @return 
   */
  public String getSouthNeighbour() {
    return southNeighbourTextField.getText();
  }
  
  /**
   * 
   * @return 
   */
  public String getEastNeighbour() {
    return eastNeighbourTextField.getText();
  }
  
  /**
   * 
   * @return 
   */
  public String getWestNeighbour() {
    return westNeighbourTextField.getText();
  }

  /**
   *
   *
   * @param parentFrame
   * @param title
   * @param isModal
   * @param board
   */
  public BoardNeighboursDialog(JFrame parentFrame, String title, boolean isModal, Board board) {
    super(parentFrame, title, isModal);
    this.board = board;
    ArrayList<String> neighbours = board.getDirectionalLinks();

    ///
    /// northNeighbourTextField
    ///
    northNeighbourTextField = new JTextField();
    northNeighbourTextField.setColumns(10);
    northNeighbourTextField.setText(neighbours.get(0));
    ///
    /// southNeighbourTextField
    ///
    southNeighbourTextField = new JTextField();
    southNeighbourTextField.setColumns(10);
    southNeighbourTextField.setText(neighbours.get(1));
    ///
    /// eastNeighbourTextField
    ///
    eastNeighbourTextField = new JTextField();
    eastNeighbourTextField.setColumns(10);
    eastNeighbourTextField.setText(neighbours.get(2));
    ///
    /// westNeighbourTextField
    ///
    westNeighbourTextField = new JTextField();
    westNeighbourTextField.setColumns(10);
    westNeighbourTextField.setText(neighbours.get(3));
    ///
    /// northBrowseButton
    ///
    northBrowseButton = new JButton("...");
    northBrowseButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        File file = FileTools.doChooseFile("brd", "Boards", "Board Files");

        if (file != null) {
          northNeighbourTextField.setText(file.getName());
        }
      }

    });
    ///
    /// southBrowseButton
    ///
    southBrowseButton = new JButton("...");
    southBrowseButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        File file = FileTools.doChooseFile("brd", "Boards", "Board Files");

        if (file != null) {
          southNeighbourTextField.setText(file.getName());
        }
      }

    });
    ///
    /// eastBrowseButton
    ///
    eastBrowseButton = new JButton("...");
    eastBrowseButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        File file = FileTools.doChooseFile("brd", "Boards", "Board Files");

        if (file != null) {
          eastNeighbourTextField.setText(file.getName());
        }
      }

    });
    ///
    /// westBrowseButton
    ///
    westBrowseButton = new JButton("...");
    westBrowseButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        File file = FileTools.doChooseFile("brd", "Boards", "Board Files");

        if (file != null) {
          westNeighbourTextField.setText(file.getName());
        }
      }

    });
    ///
    /// applyButton
    ///
    applyButton = new JButton("Apply");
    applyButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        result = APPLY;
        dispose();
      }

    });
    ///
    /// cancelButton
    ///
    cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
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
    constraints.gridx = 0;
    constraints.gridy = 0;
    dialogPanel.add(new JLabel("North"), constraints);

    constraints.gridx = 0;
    constraints.gridy = 2;
    dialogPanel.add(new JLabel("South"), constraints);

    constraints.gridx = 0;
    constraints.gridy = 4;
    dialogPanel.add(new JLabel("East"), constraints);
    
    constraints.gridx = 0;
    constraints.gridy = 6;
    dialogPanel.add(new JLabel("West"), constraints);

    // Variable Fields
    constraints.gridx = 0;
    constraints.gridy = 1;
    dialogPanel.add(northNeighbourTextField, constraints);

    constraints.gridx = 0;
    constraints.gridy = 3;
    dialogPanel.add(southNeighbourTextField, constraints);

    constraints.gridx = 0;
    constraints.gridy = 5;
    dialogPanel.add(eastNeighbourTextField, constraints);
    
    constraints.gridx = 0;
    constraints.gridy = 7;
    dialogPanel.add(westNeighbourTextField, constraints);

    // Value Fields
    constraints.gridx = 1;
    constraints.gridy = 1;
    dialogPanel.add(northBrowseButton, constraints);

    constraints.gridx = 1;
    constraints.gridy = 3;
    dialogPanel.add(southBrowseButton, constraints);

    constraints.gridx = 1;
    constraints.gridy = 5;
    dialogPanel.add(eastBrowseButton, constraints);
    
    constraints.gridx = 1;
    constraints.gridy = 7;
    dialogPanel.add(westBrowseButton, constraints);
    ///
    /// buttonPanel
    ///
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    buttonPanel.add(applyButton);
    buttonPanel.add(cancelButton);
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
    setResizable(false);
    add(paddingPanel);
  }

  /**
   *
   *
   * @return
   */
  public int showDialog() {
    pack();
    setLocationRelativeTo(getOwner());
    setVisible(true);

    return result;
  }
}

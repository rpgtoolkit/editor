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
public class BoardSpriteDialog extends JDialog {

  /**
   *
   */
  public static final int CANCEL = 0;

  /**
   *
   */
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

  /**
   *
   *
   * @return
   */
  public String getInitialVariable() {
    return initialVariableTextField.getText();
  }

  /**
   *
   *
   * @return
   */
  public String getInitialValue() {
    return initialValueTextField.getText();
  }

  /**
   *
   *
   * @return
   */
  public String getFinalVariable() {
    return finalVariableTextField.getText();
  }

  /**
   *
   *
   * @return
   */
  public String getFinalValue() {
    return finalValueTextField.getText();
  }

  /**
   *
   *
   * @return
   */
  public String getLoadingVariable() {
    return loadingVariableTextField.getText();
  }

  /**
   *
   *
   * @return
   */
  public String getLoadingValue() {
    return loadingValueTextField.getText();
  }

  /**
   *
   *
   * @param parentFrame
   * @param title
   * @param isModal
   * @param boardSprite
   */
  public BoardSpriteDialog(JFrame parentFrame, String title, boolean isModal,
          BoardSprite boardSprite) {
    super(parentFrame, title, isModal);
    this.boardSprite = boardSprite;

    ///
    /// initialVariableTextField
    ///
    initialVariableTextField = new JTextField();
    initialVariableTextField.setColumns(10);
    initialVariableTextField.setText(
            boardSprite.getInitialVariable());
    ///
    /// initialValueTextField
    ///
    initialValueTextField = new JTextField();
    initialValueTextField.setColumns(10);
    initialValueTextField.setText(boardSprite.getInitialValue());
    ///
    /// finalVariableTextField
    ///
    finalVariableTextField = new JTextField();
    finalVariableTextField.setColumns(10);
    finalVariableTextField.setText(
            boardSprite.getFinalVariable());
    ///
    /// finalValueTextField
    ///
    finalValueTextField = new JTextField();
    finalValueTextField.setColumns(10);
    finalValueTextField.setText(boardSprite.getFinalValue());
    ///
    /// loadingVariableTextField
    ///
    loadingVariableTextField = new JTextField();
    loadingVariableTextField.setColumns(10);
    loadingVariableTextField.setText(
            boardSprite.getLoadingVariable());
    ///
    /// loadingValueTextField
    ///
    loadingValueTextField = new JTextField();
    loadingValueTextField.setColumns(10);
    loadingValueTextField.setText(boardSprite.getLoadingValue());
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
    dialogPanel.add(initialVariableTextField, constraints);

    constraints.gridx = 0;
    constraints.gridy = 3;
    dialogPanel.add(finalVariableTextField, constraints);

    constraints.gridx = 0;
    constraints.gridy = 5;
    dialogPanel.add(loadingVariableTextField, constraints);

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
    dialogPanel.add(initialValueTextField, constraints);

    constraints.gridx = 2;
    constraints.gridy = 3;
    dialogPanel.add(finalValueTextField, constraints);

    constraints.gridx = 2;
    constraints.gridy = 5;
    dialogPanel.add(loadingValueTextField, constraints);
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

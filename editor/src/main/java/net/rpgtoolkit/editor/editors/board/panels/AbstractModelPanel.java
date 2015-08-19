/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.board.panels;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import net.rpgtoolkit.editor.editors.BoardEditor;
import net.rpgtoolkit.editor.ui.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public abstract class AbstractModelPanel extends JPanel {
  
  protected static final int COLUMNS = 10;
  
  protected Object model;
  
  protected Font font;

  protected GridBagConstraints constraints;
  protected GridBagConstraints constraintsRight;

  public AbstractModelPanel(Object model) {
    this.model = model;
    
    font = new Font(new JLabel().getFont().getName(), Font.PLAIN, 10);

    setLayout(new GridBagLayout());
    constraints = new GridBagConstraints();
    constraints.anchor = GridBagConstraints.NORTHWEST;
    constraints.insets = new Insets(4, 15, 0, 0);
    
    constraintsRight = (GridBagConstraints) constraints.clone();
    constraintsRight.fill = GridBagConstraints.HORIZONTAL;
    constraintsRight.weightx = 1.0;
    constraintsRight.insets = new Insets(0, 30, 0, 4);
  }

  public Object getModel() {
    return model;
  }

  public BoardEditor getBoardEditor() {
    return MainWindow.getInstance().getCurrentBoardEditor();
  }

  public void updateCurrentBoardView() {
    BoardEditor editor = MainWindow.getInstance().getCurrentBoardEditor();

    if (editor != null) {
      editor.getBoardView().repaint();
    }
  }
  
  protected JLabel getJLabel(String text) {
    JLabel label = new JLabel(text);
    label.setVerticalAlignment(SwingConstants.CENTER);
    label.setFont(font);
    return label;
  }
  
  protected JTextField getJTextField(String text) {
    JTextField textField = new JTextField(text);
    textField.setColumns(COLUMNS);
    textField.setFont(font);
    return textField;
  }
  
  protected JButton getJButton(String text) {
    JButton button = new JButton(text);
    button.setFont(font);
    return button;
  }
  
  protected JSpinner getJSpinner(Object value) {
    JSpinner spinner = new JSpinner();
    spinner.setValue(value);
    spinner.setFont(font);
    return spinner;
  }
}

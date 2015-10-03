/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors.board.panels;

import java.awt.Font;
import java.io.File;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import net.rpgtoolkit.editor.editors.BoardEditor;
import net.rpgtoolkit.editor.ui.MainWindow;
import net.rpgtoolkit.editor.ui.listeners.PopupListFilesListener;

/**
 *
 * @author Joshua Michael Daly
 */
public abstract class AbstractModelPanel extends JPanel {

  protected static final int COLUMNS = 10;

  protected Object model;
  protected Font font;

  protected GroupLayout layout;
  protected SequentialGroup horizontalGroup;
  protected SequentialGroup verticalGroup;

  public AbstractModelPanel(Object model) {
    this.model = model;
    font = new JLabel().getFont();

    layout = new GroupLayout(this);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    horizontalGroup = layout.createSequentialGroup();
    verticalGroup = layout.createSequentialGroup();

    setLayout(layout);
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

  protected JComboBox getFileListJComboBox(File rootDirectory, String[] extensions,
          boolean recursive) {
    JComboBox comboBox = new JComboBox();
    comboBox.addPopupMenuListener(new PopupListFilesListener(
            rootDirectory, extensions, recursive, comboBox));
    comboBox.setPrototypeDisplayValue("*****************");
    comboBox.insertItemAt("", 0);
    return comboBox;
  }

}

package net.rpgtoolkit.editor.editors.board.panels;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.rpgtoolkit.common.assets.Board;
import net.rpgtoolkit.editor.editors.board.BoardNeighboursDialog;
import net.rpgtoolkit.editor.editors.board.BoardSpriteDialog;
import net.rpgtoolkit.editor.ui.MainWindow;
import net.rpgtoolkit.editor.utilities.FileTools;

/**
 *
 *
 * @author Joshua Micahel Daly
 */
public class BoardPanel extends AbstractModelPanel {

  private final JSpinner widthSpinner;
  private final JSpinner heightSpinner;

  private final JButton configureButton;

  private final JTextField musicFileTextField;
  private final JButton musicFileButton;

  private final JTextField entryProgramTextField;
  private final JButton entryProgramButton;

  public BoardPanel(final Object model) {
    ///
    /// super
    ///
    super(model);
    ///
    /// widthSpinner
    ///
    widthSpinner = getJSpinner(((Board) model).getWidth());
    widthSpinner.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {

      }

    });
    ///
    /// heightSpinner
    ///
    heightSpinner = getJSpinner(((Board) model).getWidth());
    heightSpinner.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {

      }

    });
    ///
    /// configureButton
    ///
    configureButton = getJButton("Configure");
    configureButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        BoardNeighboursDialog dialog = new BoardNeighboursDialog(MainWindow.getInstance(), 
                "Configure Neighbours", true, (Board) model);

        if (dialog.showDialog() == BoardSpriteDialog.APPLY) { 
          ((Board) model).setDirectionalLinks(dialog.getNeighbours());
        }
      }

    });
    ///
    /// musicPanel
    ///
    musicFileTextField = getJTextField(((Board) model).getBackgroundMusic());

    musicFileButton = getJButton("...");
    musicFileButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent ae) {
        File file = FileTools.doChooseFile("wav", "Media", "Audio Files");

        if (file != null) {
          musicFileTextField.setText(file.getName());
        }
      }

    });

    JPanel musicPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    musicPanel.add(musicFileTextField);
    musicPanel.add(musicFileButton);
    ///
    /// entryProgramPanel
    ///
    entryProgramTextField = getJTextField(((Board) model).getFirstRunProgram());

    entryProgramButton = getJButton("...");
    entryProgramButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent ae) {
        File file = FileTools.doChooseFile("prg", "Prg", "Program Files");

        if (file != null) {
          entryProgramTextField.setText(file.getName());
        }
      }

    });

    JPanel entryProgramPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    entryProgramPanel.add(entryProgramTextField);
    entryProgramPanel.add(entryProgramButton);
    ///
    /// this
    ///
    constraints.insets = new Insets(4, 4, 0, 30);
    //constraintsRight.insets = new Insets(0, 0, 10, 15);
    
    constraints.gridx = 0;
    constraints.gridy = 1;
    add(getJLabel("Width"), constraints);

    constraints.gridx = 0;
    constraints.gridy = 2;
    add(getJLabel("Height"), constraints);

    constraints.gridx = 0;
    constraints.gridy = 3;
    add(getJLabel("Neighbours"), constraints);

    constraints.gridx = 0;
    constraints.gridy = 4;
    add(getJLabel("Music"), constraints);

    constraints.gridx = 0;
    constraints.gridy = 5;
    add(getJLabel("Entry Program"), constraints);

    constraintsRight.gridx = 1;
    constraintsRight.gridy = 1;
    add(widthSpinner, constraintsRight);

    constraintsRight.gridx = 1;
    constraintsRight.gridy = 2;
    add(heightSpinner, constraintsRight);

    constraintsRight.gridx = 1;
    constraintsRight.gridy = 3;
    add(configureButton, constraintsRight);

    constraintsRight.gridx = 1;
    constraintsRight.gridy = 4;
    add(musicPanel, constraintsRight);

    constraintsRight.gridx = 1;
    constraintsRight.gridy = 5;
    add(entryProgramPanel, constraintsRight);
  }
}

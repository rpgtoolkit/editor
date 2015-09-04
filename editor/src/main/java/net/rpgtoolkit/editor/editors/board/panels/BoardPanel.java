package net.rpgtoolkit.editor.editors.board.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.rpgtoolkit.common.assets.Board;
import net.rpgtoolkit.editor.editors.board.BoardNeighboursDialog;
import net.rpgtoolkit.editor.editors.board.BoardSpriteDialog;
import net.rpgtoolkit.editor.ui.MainWindow;

/**
 *
 *
 * @author Joshua Micahel Daly
 */
public class BoardPanel extends AbstractModelPanel {

  private final JSpinner widthSpinner;
  private final JLabel widthLabel;
  
  private final JSpinner heightSpinner;
  private final JLabel heightLabel;

  private final JButton configureButton;
  private final JLabel configureLabel;

  private final JComboBox musicFileComboBox;
  private final JLabel musicLabel;

  private final JComboBox entryProgramComboBox;
  private final JLabel entryProgramLabel;

  public BoardPanel(final Board board) {
    ///
    /// super
    ///
    super(board);
    ///
    /// widthSpinner
    ///
    widthSpinner = getJSpinner(board.getWidth());
    widthSpinner.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {

      }

    });
    ///
    /// heightSpinner
    ///
    heightSpinner = getJSpinner(board.getWidth());
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
                "Configure Neighbours", true, board);

        if (dialog.showDialog() == BoardSpriteDialog.APPLY) { 
          ((Board) model).setDirectionalLinks(dialog.getNeighbours());
        }
      }

    });
    ///
    /// musicTextField
    ///
    File directory = new File(System.getProperty("project.path") + "Media" + File.separator);
    String[] exts = new String[] {"wav", "mp3"};
    musicFileComboBox = getFileListJComboBox(directory, exts, true);
    musicFileComboBox.setSelectedItem(board.getBackgroundMusic());
    musicFileComboBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        board.setBackgroundMusic((String)musicFileComboBox.getSelectedItem());
      }
      
    });
    ///
    /// entryProgramTextField
    ///
    directory = new File(System.getProperty("project.path") + "Prg" + File.separator);
    exts = new String[] {"prg"};
    entryProgramComboBox = getFileListJComboBox(directory, exts, true);
    entryProgramComboBox.setSelectedItem(board.getFirstRunProgram());
    entryProgramComboBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        board.setFirstRunProgram((String)entryProgramComboBox.getSelectedItem());
      }

    });
    ///
    /// this
    ///
    horizontalGroup.addGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(widthLabel = getJLabel("Width"))
                    .addComponent(heightLabel = getJLabel("Height"))
                    .addComponent(configureLabel = getJLabel("Neighbours"))
                    .addComponent(musicLabel = getJLabel("Music"))
                    .addComponent(entryProgramLabel = getJLabel("Entry Program")));
    
    horizontalGroup.addGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(widthSpinner)
                    .addComponent(heightSpinner)
                    .addComponent(configureButton)
                    .addComponent(musicFileComboBox)
                    .addComponent(entryProgramComboBox));
    
    layout.setHorizontalGroup(horizontalGroup);
    
    verticalGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
            .addComponent(widthLabel).addComponent(widthSpinner));
    
    verticalGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
            .addComponent(heightLabel).addComponent(heightSpinner));
    
    verticalGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
            .addComponent(configureLabel).addComponent(configureButton));
    
    verticalGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
            .addComponent(musicLabel).addComponent(musicFileComboBox));
    
    verticalGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
            .addComponent(entryProgramLabel).addComponent(entryProgramComboBox));
  
    layout.setVerticalGroup(verticalGroup);
  }
}

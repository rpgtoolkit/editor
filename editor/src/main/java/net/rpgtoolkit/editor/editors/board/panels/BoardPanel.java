package net.rpgtoolkit.editor.editors.board.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
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

  private final JTextField musicFileTextField;
  private final JLabel musicLabel;

  private final JTextField entryProgramTextField;
  private final JLabel entryProgramLabel;

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
    /// musicTextField
    ///
    musicFileTextField = getJTextField(((Board) model).getBackgroundMusic());
    ///
    /// entryProgramTextField
    ///
    entryProgramTextField = getJTextField(((Board) model).getFirstRunProgram());
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
                    .addComponent(musicFileTextField)
                    .addComponent(entryProgramTextField));
    
    layout.setHorizontalGroup(horizontalGroup);
    
    verticalGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
            .addComponent(widthLabel).addComponent(widthSpinner));
    
    verticalGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
            .addComponent(heightLabel).addComponent(heightSpinner));
    
    verticalGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
            .addComponent(configureLabel).addComponent(configureButton));
    
    verticalGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
            .addComponent(musicLabel).addComponent(musicFileTextField));
    
    verticalGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
            .addComponent(entryProgramLabel).addComponent(entryProgramTextField));
  
    layout.setVerticalGroup(verticalGroup);
  }
}

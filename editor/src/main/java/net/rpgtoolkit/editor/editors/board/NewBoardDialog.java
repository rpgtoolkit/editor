package net.rpgtoolkit.editor.editors.board;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import net.rpgtoolkit.editor.ui.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public class NewBoardDialog extends JDialog {

  private final JSpinner widthSpinner;
  private final JSpinner heightSpinner;

  private final JButton okButton;
  private final JButton cancelButton;

  private int[] value = null;

  /**
   *
   */
  public NewBoardDialog() {
    super(MainWindow.getInstance(),
            "New Board",
            JDialog.ModalityType.APPLICATION_MODAL);

    widthSpinner = new JSpinner(new SpinnerNumberModel(10, 3, 100, 1));
    ((JSpinner.DefaultEditor) widthSpinner.getEditor()).
            getTextField().setColumns(7);

    heightSpinner = new JSpinner(new SpinnerNumberModel(10, 3, 100, 1));
    ((JSpinner.DefaultEditor) heightSpinner.getEditor()).
            getTextField().setColumns(7);

    okButton = new JButton("OK");
    okButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        value = new int[]{(int) widthSpinner.getValue(),
          (int) heightSpinner.getValue()};
        dispose();
      }

    });

    cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }

    });

    JPanel gridPanel = new JPanel(new GridLayout(0, 2));
    gridPanel.add(new JLabel("Width", SwingConstants.CENTER));
    gridPanel.add(widthSpinner);
    gridPanel.add(new JLabel("Height", SwingConstants.CENTER));
    gridPanel.add(heightSpinner);
    gridPanel.add(okButton);
    gridPanel.add(cancelButton);

    add(gridPanel);

    setResizable(false);
    pack();
  }

  /**
   *
   * @return
   */
  public int[] getValue() {
    return value;
  }

}

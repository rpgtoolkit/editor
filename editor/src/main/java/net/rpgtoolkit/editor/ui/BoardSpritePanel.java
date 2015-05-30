package net.rpgtoolkit.editor.ui;

import java.awt.FlowLayout;
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

    private static final int GRID_ROWS = 4;
    private static final int GRID_COLS = 2;

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
        super(boardSprite, GRID_ROWS, GRID_COLS);
        ///
        /// filePanel
        ///
        this.fileTextField = new JTextField(boardSprite.getFileName());
        this.fileTextField.setEnabled(false);
        this.fileTextField.setColumns(10);

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
        this.activationProgramTextField.setColumns(10);

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
        this.multiTaskingTextField.setColumns(10);

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
        this.add(new JLabel("Item File"));
        this.add(filePanel);
        this.add(new JLabel("Activation Program"));
        this.add(activationProgramPanel);
        this.add(new JLabel("MultiTasking Program"));
        this.add(multiTaskingPanel);
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

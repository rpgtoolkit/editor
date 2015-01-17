package rpgtoolkit.editor.main.panels.properties;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import rpgtoolkit.editor.board.types.BoardSprite;

/**
 *
 * @author Joshua Michael Daly
 */
public class BoardSpritePanel extends AbstractModelPanel
{
    private static final int GRID_ROWS = 4;
    private static final int GRID_COLS = 2;
    
    private final JTextField fileTextField;
    private final JTextField activationProgramTextField;
    private final JTextField multiTaskingTextField;
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public BoardSpritePanel(BoardSprite boardSprite)
    {
        super(boardSprite, GRID_ROWS, GRID_COLS);
        
        this.fileTextField = new JTextField(boardSprite.getFileName());
        this.fileTextField.setEnabled(false);
        this.fileTextField.setColumns(10);
        
        this.activationProgramTextField = new JTextField(boardSprite.
                getActivationProgram());
        this.activationProgramTextField.setEnabled(false);
        this.activationProgramTextField.setColumns(10);
        
        this.multiTaskingTextField = new JTextField(boardSprite.
                getMultitaskingProgram());
        this.multiTaskingTextField.setEnabled(false);
        this.multiTaskingTextField.setColumns(10);
        
        this.add(new JLabel("Sprite File"));
        this.add(buildBrowsePanel(this.fileTextField));
        this.add(new JLabel("Activation Program"));
        this.add(buildBrowsePanel(this.activationProgramTextField));
        this.add(new JLabel("MultiTasking Program"));
        this.add(buildBrowsePanel(this.multiTaskingTextField));
    }
    
    /*
     * *************************************************************************
     * Private Static Methods
     * *************************************************************************
     */
    private static JPanel buildBrowsePanel(JTextField textField)
    {
        JButton fileBrowseButton = new JButton("...");
        fileBrowseButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                
            }
        });
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(textField);
        panel.add(fileBrowseButton);
        
        return panel;
    }
}

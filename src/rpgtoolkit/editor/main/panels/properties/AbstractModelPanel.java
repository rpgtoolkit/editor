package rpgtoolkit.editor.main.panels.properties;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import rpgtoolkit.editor.board.BoardEditor;
import rpgtoolkit.editor.main.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public abstract class AbstractModelPanel extends JPanel
{
    
    protected Object model;
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public AbstractModelPanel(Object model, int rows, int columns)
    {
        this.model = model;
        
        this.setLayout(new GridLayout(rows, columns));
        this.add(new JLabel("Name"));
        this.add(new JLabel("Value"));
    }
    
    /*
     * *************************************************************************
     * Public Getters and Setters
     * *************************************************************************
     */
    public Object getModel()
    {
        return this.model;
    }
    
    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    public void updateCurrentBoardView()
    {
        BoardEditor editor = MainWindow.getInstance().getCurrentBoardEditor();
        
        if (editor != null)
        {
            editor.getBoardView().repaint();
        }
    }
    
}

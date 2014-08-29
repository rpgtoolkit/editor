package rpgtoolkit.editor.main.panels.properties;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import rpgtoolkit.editor.board.types.BoardVector;

/**
 * 
 * 
 * @author Joshua Michael Daly
 */
public class BoardVectorPanel extends AbstractModelPanel
{
    
    //private final JSpinner layerSpinner;
    private final JCheckBox isClosedCheckBox;
    private final JTextField handleTextField;
    private final JComboBox<String> tileTypeComboBox;
    
    private static final String[] TILE_TYPES = {
        "SOLID", "UNDER", "STAIRS", "WAYPOINT"
    };
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public BoardVectorPanel(BoardVector boardVector)
    {
        super(boardVector, 4, 2);
        
        //this.layerSpinner = new JSpinner();
        //this.layerSpinner.setValue(((BoardVector)this.model).getLayer());
        
        this.isClosedCheckBox = new JCheckBox();
        this.isClosedCheckBox.setSelected(((BoardVector)this.model).isClosed());
        this.isClosedCheckBox.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                ((BoardVector)model).setClosed(isClosedCheckBox.isSelected());
                updateCurrentBoardView();
            }
        });
        
        this.handleTextField = new JTextField();
        this.handleTextField.setText(((BoardVector)this.model).getHandle());
        this.handleTextField.addFocusListener(new FocusListener()
        {

            @Override
            public void focusGained(FocusEvent e)
            {
                
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                if (!((BoardVector)model).getHandle().equals(handleTextField.getText()))
                {
                    ((BoardVector)model).setHandle(handleTextField.getText());
                }
            }
        });

        
        this.tileTypeComboBox = new JComboBox<>(TILE_TYPES);
        
        switch(((BoardVector)this.model).getTileType())
        {
            case 1:
                this.tileTypeComboBox.setSelectedIndex(0);
                break;
            case 2:
                this.tileTypeComboBox.setSelectedIndex(1);
                break;
            case 8:
                this.tileTypeComboBox.setSelectedIndex(2);
                break;
            case 16:
                this.tileTypeComboBox.setSelectedIndex(3);
        }
        
        this.tileTypeComboBox.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                switch(tileTypeComboBox.getSelectedIndex())
                {
                    case 0:
                        ((BoardVector)model).setTileType(1);
                        break;
                    case 1:
                        ((BoardVector)model).setTileType(2);
                        break;
                    case 2:
                        ((BoardVector)model).setTileType(8);
                        break;
                    case 3:
                        ((BoardVector)model).setTileType(16);
                }
                
                updateCurrentBoardView();
            }
        });
        
        this.add(new JLabel("Handle"));
        this.add(this.handleTextField);
        this.add(new JLabel("Is Closed"));
        this.add(this.isClosedCheckBox);
        //this.add(new JLabel("Layer"));
        //this.add(this.layerSpinner);
        this.add(new JLabel("Type"));
        this.add(this.tileTypeComboBox);
    }
}

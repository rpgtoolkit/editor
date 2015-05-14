package net.rpgtoolkit.editor.main.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.rpgtoolkit.editor.main.panels.properties.AbstractModelPanel;
import net.rpgtoolkit.editor.main.panels.properties.ModelPanelFactory;

/**
 *
 * @author Joshua Michael Daly
 */
public class PropertiesPanel extends JPanel implements ListSelectionListener
{
    private Object model;
    
    private JScrollPane propertiesScrollPane;
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    public PropertiesPanel()
    {
        this.initialize();
    }
    
    public PropertiesPanel(Object model)
    {
        this.model = model;
        this.initialize();
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
    
    public void setModel(Object model)
    {
        this.model = model;
        
        AbstractModelPanel panel = ModelPanelFactory.getModelPanel(model);
        
        // To ensure that the internal controls are not streched.
        JPanel intermediate = new JPanel(new FlowLayout());
        intermediate.add(panel);
        
        this.propertiesScrollPane.setViewportView(intermediate);
        this.propertiesScrollPane.getViewport().revalidate();
    }
    
    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        
    }
    
    /*
     * *************************************************************************
     * Private Methods
     * *************************************************************************
     */
    private void initialize()
    {
        if (this.model != null)
        {

        }
        else
        {
            
        }
        
        this.propertiesScrollPane = new JScrollPane();
        this.propertiesScrollPane.getViewport().setScrollMode(
                JViewport.SIMPLE_SCROLL_MODE);
        
        this.setLayout(new BorderLayout());
        this.add(this.propertiesScrollPane, BorderLayout.CENTER);
    }
    
}

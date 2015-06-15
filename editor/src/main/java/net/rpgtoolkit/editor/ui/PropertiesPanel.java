/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


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
        JPanel intermediate = new JPanel(new BorderLayout());
        intermediate.setBackground(Color.yellow);
        
        if (panel != null)
        {
            intermediate.add(panel, BorderLayout.NORTH);
        }
        
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

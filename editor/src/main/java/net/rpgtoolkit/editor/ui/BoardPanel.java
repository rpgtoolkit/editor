package net.rpgtoolkit.editor.ui;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.rpgtoolkit.common.assets.Board;

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
    
    public BoardPanel(Object model) {
        ///
        /// super
        ///
        super(model);
        ///
        /// widthSpinner
        ///
        widthSpinner = new JSpinner();
        widthSpinner.setValue(((Board)model).getWidth());
        widthSpinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                
            }
            
        });
        ///
        /// heightSpinner
        ///
        heightSpinner = new JSpinner();
        heightSpinner.setValue(((Board)model).getHeight());
        heightSpinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e)
            {
                
            }
            
        });
        ///
        /// configureButton
        ///
        configureButton = new JButton("Configure");
        configureButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                
            }
            
        });
        ///
        /// musicPanel
        ///
        musicFileTextField = new JTextField(((Board)model).getBackgroundMusic());
        musicFileTextField.setColumns(17);

        musicFileButton = new JButton("...");
        musicFileButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                
            }
            
        });
        
        JPanel musicPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        musicPanel.add(musicFileTextField);
        musicPanel.add(musicFileButton);
        ///
        /// entryProgramPanel
        ///
        entryProgramTextField = new JTextField(((Board)model).getFirstRunProgram());
        entryProgramTextField.setColumns(17);

        entryProgramButton = new JButton("...");
        entryProgramButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                
            }
            
        });
        
        JPanel entryProgramPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        entryProgramPanel.add(entryProgramTextField);
        entryProgramPanel.add(entryProgramButton);
        ///
        /// this
        ///
        this.constraints.insets = new Insets(8, 15, 0, 3);
        this.constraintsRight.insets = new Insets(0, 0, 10, 15);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 1;
        this.add(new JLabel("Width"), this.constraints);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 2;
        this.add(new JLabel("Height"), this.constraints);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 3;
        this.add(new JLabel("Neighbours"), this.constraints);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 4;
        this.add(new JLabel("Music"), this.constraints);
        
        this.constraints.gridx = 0;
        this.constraints.gridy = 5;
        this.add(new JLabel("Entry Program"), this.constraints);
        
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 1;
        this.add(widthSpinner, this.constraintsRight);
        
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 2;
        this.add(heightSpinner, this.constraintsRight);
        
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 3;
        this.add(configureButton, this.constraintsRight);
        
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 4;
        this.add(musicPanel, this.constraintsRight);
        
        this.constraintsRight.gridx = 1;
        this.constraintsRight.gridy = 5;
        this.add(entryProgramPanel, this.constraintsRight);
    }
    
}

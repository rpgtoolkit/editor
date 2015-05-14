package net.rpgtoolkit.editor.main.menus;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import net.rpgtoolkit.editor.main.MainWindow;
import net.rpgtoolkit.editor.main.menus.actions.ZoomInAction;
import net.rpgtoolkit.editor.main.menus.actions.ZoomOutAction;
import net.rpgtoolkit.editor.main.menus.listeners.ShowCoordinatesItemListener;
import net.rpgtoolkit.editor.main.menus.listeners.ShowGridItemListener;
import net.rpgtoolkit.editor.main.menus.listeners.ShowVectorsItemListener;

/**
 *
 * @author Joshua Michael Daly
 */
public final class ViewMenu extends JMenu
{
    private final MainWindow parent;
    
    private JMenuItem zoomInMenuItem;
    private JMenuItem zoomOutMenuItem;
    private JCheckBoxMenuItem showGridMenuItem;
    private JCheckBoxMenuItem showCoordinatesMenuItem;
    private JCheckBoxMenuItem showVectorsMenuItem;
    
    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    /**
     * 
     * @param parent 
     */
    public ViewMenu(MainWindow parent)
    {
        super("View");
        
        this.parent = parent;
        this.setMnemonic(KeyEvent.VK_V);
        
        this.configureZoomInMenuItem();
        this.configureZoomOutMenuItem();
        this.configureShowGridMenuItem();
        this.configureShowCoordinatesMenuItem();
        this.configureShowVectorsMenuItem();
        
        this.add(zoomInMenuItem);
        this.add(zoomOutMenuItem);
        this.add(new JSeparator());
        this.add(showGridMenuItem);
        this.add(showCoordinatesMenuItem);
        this.add(showVectorsMenuItem);
    }
    
    /*
     * *************************************************************************
     * Public Getters
     * *************************************************************************
     */
    public JMenuItem getZoomInMenuItem()
    {
        return zoomInMenuItem;
    }

    public JMenuItem getZoomOutMenuItem()
    {
        return zoomOutMenuItem;
    }

    public JCheckBoxMenuItem getShowGridMenuItem()
    {
        return showGridMenuItem;
    }

    public JCheckBoxMenuItem getShowCoordinatesMenuItem()
    {
        return showCoordinatesMenuItem;
    }

    public JCheckBoxMenuItem getShowVectorsMenuItem()
    {
        return showVectorsMenuItem;
    }
    
    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    /**
     * 
     */
    public void configureZoomInMenuItem()
    {
        zoomInMenuItem = new JMenuItem("Zoom In");
        zoomInMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/zoom-in.png")));
        zoomInMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_ADD, ActionEvent.CTRL_MASK));
        zoomInMenuItem.setMnemonic(KeyEvent.VK_PLUS);
        zoomInMenuItem.addActionListener(new ZoomInAction(parent));
    }
    
    /**
     * 
     */
    public void configureZoomOutMenuItem()
    {
        zoomOutMenuItem = new JMenuItem("Zoom Out");
        zoomOutMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/zoom-out.png")));
        zoomOutMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, ActionEvent.CTRL_MASK));
        zoomOutMenuItem.setMnemonic(KeyEvent.VK_MINUS);
        zoomOutMenuItem.addActionListener(new ZoomOutAction(parent));
    }
    
    /**
     * 
     */
    public void configureShowGridMenuItem()
    {
        showGridMenuItem = new JCheckBoxMenuItem("Show Grid");
        showGridMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/editor/grid.png")));
        showGridMenuItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        showGridMenuItem.setMnemonic(KeyEvent.VK_G);
        showGridMenuItem.addItemListener(new ShowGridItemListener(parent));
    }
    
    /**
     * 
     */
    public void configureShowCoordinatesMenuItem()
    {
        showCoordinatesMenuItem = new JCheckBoxMenuItem("Show Coordinates");
        //showGridMenuItem.setIcon(new ImageIcon(getClass()
        //        .getResource("/editor/grid.png")));
        //showGridMenuItem.setAccelerator(
        //        KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        //showGridMenuItem.setMnemonic(KeyEvent.VK_G);
        showCoordinatesMenuItem.addItemListener(new ShowCoordinatesItemListener(parent));
    }
    
    /**
     * 
     */
    public void configureShowVectorsMenuItem()
    {
        showVectorsMenuItem = new JCheckBoxMenuItem("Show Vectors");
        //showGridMenuItem.setIcon(new ImageIcon(getClass()
        //        .getResource("/editor/grid.png")));
        //showGridMenuItem.setAccelerator(
        //        KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        //showGridMenuItem.setMnemonic(KeyEvent.VK_G);
        showVectorsMenuItem.addItemListener(new ShowVectorsItemListener(parent));
    }
}

package rpgtoolkit.editor.main.menus;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import rpgtoolkit.editor.main.MainWindow;

/**
 *
 * @author Joshua Michael Daly
 */
public final class RunMenu extends JMenu
{
    private final MainWindow parent;
    
    private JMenuItem debugProgramMenuItem;
    private JMenuItem runProjectMenuItem;
    
    public RunMenu(MainWindow parent)
    {
        super("Run");
        
        this.parent = parent;
        this.setMnemonic(KeyEvent.VK_R);
        
        this.configureDebugProgramMenuItem();
        this.configureRunProjectMenuItem();
        
        this.add(debugProgramMenuItem);
        this.add(runProjectMenuItem);
    }
    
    public void configureDebugProgramMenuItem()
    {
        debugProgramMenuItem = new JMenuItem("Debug Program");
        debugProgramMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/rpgtoolkit/editor/resources/bug.png")));
//        debugProgramMenuItem.setAccelerator(
//                KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.ACTION_PERFORMED));
        debugProgramMenuItem.setMnemonic(KeyEvent.VK_D);
        
        debugProgramMenuItem.setEnabled(false);
    }
    
    public void configureRunProjectMenuItem()
    {
        runProjectMenuItem = new JMenuItem("Run Project");
        runProjectMenuItem.setIcon(new ImageIcon(getClass()
                .getResource("/rpgtoolkit/editor/resources/run.png")));
//        runProjectMenuItem.setAccelerator(
//                KeyStroke.getKeyStroke(KeyEvent.VK_F11, ActionEvent.ACTION_FIRST));
        runProjectMenuItem.setMnemonic(KeyEvent.VK_N);
        
        runProjectMenuItem.setEnabled(false);
    }
}

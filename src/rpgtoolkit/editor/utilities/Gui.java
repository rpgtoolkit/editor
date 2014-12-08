package rpgtoolkit.editor.utilities;

import javax.swing.GroupLayout;
import javax.swing.JPanel;

/**
 * Contains useful shortcuts and constants for building editors via Swing.
 *
 * @author Joel Moore
 */
public abstract class Gui {
    
    public static final int JTF_HEIGHT = 24;

    /**
     * Creates a GroupLayout for the specified panel and assigns it to that
     * panel. Common settings are applied: auto create gaps and auto create
     * container gaps are true.
     * @param forPanel the JPanel to use as the host for the layout; this
     * panel's layout will also be set to the created layout
     * @return the created GroupLayout
     */
    public static GroupLayout createGroupLayout(JPanel forPanel) {
        GroupLayout layout = new GroupLayout(forPanel);
        forPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        return layout;
    }
    
}

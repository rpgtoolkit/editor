package net.rpgtoolkit.editor.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

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
     *
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
    
    /**
     * Creates a JList with the specified data. Common settings are applied:
     * selection mode is single selection, layout orientation is vertical,
     * visible row count is given a negative number.
     *
     * @param dataModel the list of data to assign to the JList
     * @return a new, configured JList
     */
    public static JList createVerticalJList(ListModel dataModel) {
        JList list = new JList(dataModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        return list;
    }

    /**
     * Creates and returns a new ActionListener that removes the currently
     * selected item from a JList and from its DefaultListModel.
     *
     * @param backingList
     * @param listComponent
     * @return a new ActionListener that removes the currently selected item
     * when triggered
     */
    public static ActionListener simpleRemoveListener(
            final DefaultListModel backingList, final JList listComponent) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = listComponent.getSelectedIndex();
                if(index >= 0) {
                    backingList.remove(index);
                    if(index == backingList.size()) {
                        index--;
                    }
                    listComponent.setSelectedIndex(index);
                    listComponent.ensureIndexIsVisible(index);
                }
            }
        };
    }
    
}

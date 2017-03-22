/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import net.rpgtoolkit.editor.MainWindow;
import net.rpgtoolkit.pluginsystem.Engine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.fortsoft.pf4j.PluginManager;

/**
 *
 * @author Joshua Michael Daly
 */
public class StopAction extends AbstractAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(StopAction.class);

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            MainWindow instance = MainWindow.getInstance();
            
            PluginManager pluginManager = MainWindow.getInstance().getPluginManager();
            List<Engine> engines = pluginManager.getExtensions(Engine.class);
            // Just use the first available engine for now.
            if (engines.size() > 0) {
                engines.get(0).stop();
            }
            
            instance.getMainToolBar().getRunButton().setEnabled(true);
           instance.getMainToolBar().getStopButton().setEnabled(false);
        } catch (Exception ex) {
            LOGGER.error("Failed to stop engine.", ex);
        }
    }

}

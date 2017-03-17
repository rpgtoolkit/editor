/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import net.rpgtoolkit.editor.MainWindow;
import net.rpgtoolkit.pluginsystem.Engine;
import ro.fortsoft.pf4j.PluginManager;

/**
 *
 * @author Joshua Michael Daly
 */
public class RunAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        PluginManager pluginManager = MainWindow.getInstance().getPluginManager();
        
        List<Engine> engines = pluginManager.getExtensions(Engine.class);
        
        // Just use the first available engine for now.
        if (engines.size() > 0) {
            engines.get(0).run(System.getProperty("project.path"));
        }
    }

}

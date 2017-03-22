/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui.actions;

import com.google.common.io.Files;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import javax.swing.AbstractAction;
import net.rpgtoolkit.editor.MainWindow;
import net.rpgtoolkit.pluginsystem.Engine;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.fortsoft.pf4j.PluginManager;

/**
 *
 * @author Joshua Michael Daly
 */
public class RunAction extends AbstractAction {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RunAction.class);

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            MainWindow instance = MainWindow.getInstance();
            
            String projectName = instance.getTitle();
            
            // Make a temporary copy of the user's project for the engine to use.
            File projectOriginal = new File(System.getProperty("project.path"));
            File projectCopy = Files.createTempDir();
            FileUtils.copyDirectory(projectOriginal, projectCopy);
            
            PluginManager pluginManager = MainWindow.getInstance().getPluginManager();
            List<Engine> engines = pluginManager.getExtensions(Engine.class);
            // Just use the first available engine for now.
            if (engines.size() > 0) {
                engines.get(0).run(projectName, projectCopy);
            }
            
           instance.getMainToolBar().getRunButton().setEnabled(false);
           instance.getMainToolBar().getStopButton().setEnabled(true);
        } catch (Exception ex) {
            LOGGER.error("Failed to run engine.", ex);
        }
    }

}

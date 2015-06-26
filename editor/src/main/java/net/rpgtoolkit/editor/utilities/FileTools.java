/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.utilities;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.rpgtoolkit.editor.ui.MainWindow;
import net.rpgtoolkit.editor.ui.SingleRootFileSystemView;

/**
 *
 * @author Joshua Michael Daly
 */
public final class FileTools {
    
    public static File doChooseFile(String extension, String directory, 
            String type)
    {
        if (MainWindow.getInstance().getActiveProject() != null)
        {
            File projectPath = new File(System.getProperty("project.path") +
                    "/" + directory);

            if (projectPath.exists())
            {
                JFileChooser fileChooser = new JFileChooser(
                        new SingleRootFileSystemView(projectPath));

                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        type, extension);
                fileChooser.setFileFilter(filter);

                if (fileChooser.showOpenDialog(MainWindow.getInstance())
                        == JFileChooser.APPROVE_OPTION)
                {
                    return fileChooser.getSelectedFile();
                }
            }
        }
        
        return null;
    }
    
}

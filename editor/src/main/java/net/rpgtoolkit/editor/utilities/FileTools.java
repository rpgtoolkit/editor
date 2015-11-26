/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.utilities;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.rpgtoolkit.common.utilities.PropertiesSingleton;
import net.rpgtoolkit.editor.ui.MainWindow;
import net.rpgtoolkit.editor.ui.SingleRootFileSystemView;

/**
 *
 * @author Joshua Michael Daly
 */
public final class FileTools {

  public static boolean createDirectoryStructure(String path, String project) {
    boolean result = true;

    result &= createDirectory(path + File.separator + PropertiesSingleton.getProperty("toolkit.directory.main"));
    result &= createDirectory(path + File.separator
            + PropertiesSingleton.getProperty("toolkit.directory.game") + File.separator + project);

    String gameDirectory = PropertiesSingleton.getProperty("toolkit.directory.game") + File.separator + project;
    for (String directory : PropertiesSingleton.getDirectories()) {
      result &= createDirectory(path + File.separator + gameDirectory + File.separator + directory);
    }

    return result;
  }

  private static boolean createDirectory(String path) {
    File directory = new File(path);

    if (!directory.exists()) {
      return directory.mkdirs();
    } else {
      return true;
    }
  }

  public static File doChoosePath() {
    JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    if (fileChooser.showOpenDialog(MainWindow.getInstance()) == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getCurrentDirectory();
    }

    return null;
  }

  public static File doChooseFile(String extension, String directory, String type) {
    if (MainWindow.getInstance().getActiveProject() != null) {
      File projectPath = new File(System.getProperty("project.path")
              + "/" + directory);

      if (projectPath.exists()) {
        JFileChooser fileChooser = new JFileChooser(new SingleRootFileSystemView(projectPath));
        fileChooser.setAcceptAllFileFilterUsed(false);

        FileNameExtensionFilter filter = new FileNameExtensionFilter(type, extension);
        fileChooser.setFileFilter(filter);

        if (fileChooser.showOpenDialog(MainWindow.getInstance()) == JFileChooser.APPROVE_OPTION) {
          return fileChooser.getSelectedFile();
        }
      }
    }

    return null;
  }

}

/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui;

import java.io.File;
import java.io.IOException;
import javax.swing.JInternalFrame;
import net.rpgtoolkit.common.assets.AbstractAsset;
import net.rpgtoolkit.common.assets.AssetDescriptor;
import net.rpgtoolkit.common.assets.AssetException;
import net.rpgtoolkit.common.assets.AssetManager;
import net.rpgtoolkit.editor.utilities.EditorFileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ToolkitEditorWindow extends JInternalFrame {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(ToolkitEditorWindow.class);

  public ToolkitEditorWindow() {

  }

  public ToolkitEditorWindow(String title, boolean resizeable, boolean closeable,
          boolean maximizable, boolean iconifiable) {
    super(title, resizeable, closeable, maximizable, iconifiable);
  }

  public abstract void save() throws Exception;
  
  protected void save(AbstractAsset asset) throws Exception {
    if (asset.getDescriptor() == null) {
      File file = EditorFileManager.saveByType(asset.getClass());
      
      if (file == null) {
        return; // Save was aborted by the user.
      }
      
      asset.setDescriptor(new AssetDescriptor(file.toURI()));
      setTitle("Editing " + file.getName());
    }

    try {
      AssetManager.getInstance().serialize(
              AssetManager.getInstance().getHandle(asset));
    } catch (IOException | AssetException ex) {
      LOGGER.error("Failed to save asset=[{}].", asset, ex);
    }
  }
  
  public abstract void saveAs(File file) throws Exception;

}

/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.ui.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joshua Michael Daly
 */
public class EditorProperties {

  private static final EditorProperties INSTANCE = new EditorProperties();
  private final Properties properties = new Properties();

  private EditorProperties() {
    try (InputStream in = EditorProperties.class.
            getResourceAsStream("/editor/properties/editor.properties")) {
      properties.load(in);
    } catch (IOException ex) {
      Logger.getLogger(EditorProperties.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public static String getProperty(String key) {
    return INSTANCE.properties.getProperty(key);
  }

}

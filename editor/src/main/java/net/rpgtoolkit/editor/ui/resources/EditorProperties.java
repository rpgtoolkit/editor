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
import net.rpgtoolkit.common.assets.AbstractAsset;
import net.rpgtoolkit.common.assets.Animation;
import net.rpgtoolkit.common.assets.Board;
import net.rpgtoolkit.common.assets.Enemy;
import net.rpgtoolkit.common.assets.Item;
import net.rpgtoolkit.common.assets.Player;
import net.rpgtoolkit.common.assets.Project;
import net.rpgtoolkit.common.assets.SpecialMove;
import net.rpgtoolkit.common.assets.StatusEffect;
import net.rpgtoolkit.common.assets.TileSet;

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

  public static String getDefaultExtension(Class<? extends AbstractAsset> type) {
    if (type == Animation.class) {
      return getProperty("editor.animation.extension.default");
    } else if (type == Board.class) {
      return getProperty("editor.board.extension.default");
    } else if (type == Enemy.class) {
      return getProperty("editor.enemy.extension.default");
    } else if (type == Item.class) {
      return getProperty("editor.item.extension.default");
    } else if (type == Player.class) {
      return getProperty("editor.character.extension.default");
    } else if (type == Project.class) {
      return getProperty("editor.project.extension.default");
    } else if (type == StatusEffect.class) {
      return getProperty("editor.statuseffect.extension.default");
    } else if (type == TileSet.class) {
      return getProperty("editor.tileset.extension.default");
    } else if (type == SpecialMove.class) {
      return getProperty("editor.specialmove.extension.default");
    } else {
      return null;
    }
  }

}

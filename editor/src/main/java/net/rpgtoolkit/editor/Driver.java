/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.rpgtoolkit.common.assets.AssetManager;
import net.rpgtoolkit.common.assets.files.FileAssetHandleResolver;
import net.rpgtoolkit.common.assets.serialization.JsonAnimationSerializer;
import net.rpgtoolkit.common.assets.serialization.JsonBoardSerializer;
import net.rpgtoolkit.common.assets.serialization.JsonEnemySerializer;
import net.rpgtoolkit.common.assets.serialization.JsonItemSerializer;
import net.rpgtoolkit.common.assets.serialization.JsonPlayerSerializer;
import net.rpgtoolkit.common.assets.serialization.JsonProjectSerializer;
import net.rpgtoolkit.common.assets.serialization.JsonSpecialMoveSerializer;
import net.rpgtoolkit.common.assets.serialization.legacy.LegacyAnimatedTileSerializer;
import net.rpgtoolkit.common.assets.serialization.legacy.LegacyBackgroundSerializer;
import net.rpgtoolkit.common.assets.serialization.legacy.LegacyBoardSerializer;
import net.rpgtoolkit.common.assets.serialization.legacy.LegacyEnemySerializer;
import net.rpgtoolkit.common.assets.serialization.legacy.LegacyItemSerializer;
import net.rpgtoolkit.common.assets.serialization.legacy.LegacyPlayerSerializer;
import net.rpgtoolkit.common.assets.serialization.legacy.LegacyProjectSerializer;
import net.rpgtoolkit.common.assets.serialization.legacy.LegacySpecialMoveSerializer;
import net.rpgtoolkit.common.assets.serialization.legacy.LegacyStatusEffectSerializer;
import net.rpgtoolkit.common.assets.serialization.legacy.LegacyTileSetSerializer;

public class Driver {

  private static void registerResolvers() {
    AssetManager.getInstance().registerResolver(new FileAssetHandleResolver());
  }

  private static void registerSerializers() {
    AssetManager assetManager = AssetManager.getInstance();

    // Legacy.
    assetManager.registerSerializer(new LegacyAnimatedTileSerializer());
    assetManager.registerSerializer(new LegacyBackgroundSerializer());
    assetManager.registerSerializer(new LegacyBoardSerializer());
    assetManager.registerSerializer(new LegacyEnemySerializer());
    assetManager.registerSerializer(new LegacyItemSerializer());
    assetManager.registerSerializer(new LegacyPlayerSerializer());
    assetManager.registerSerializer(new LegacyProjectSerializer());
    assetManager.registerSerializer(new LegacySpecialMoveSerializer());
    assetManager.registerSerializer(new LegacyStatusEffectSerializer());
    assetManager.registerSerializer(new LegacyTileSetSerializer());

    // JSON.
    assetManager.registerSerializer(new JsonAnimationSerializer());
    assetManager.registerSerializer(new JsonPlayerSerializer());
    assetManager.registerSerializer(new JsonBoardSerializer());
    assetManager.registerSerializer(new JsonProjectSerializer());
    assetManager.registerSerializer(new JsonSpecialMoveSerializer());
    assetManager.registerSerializer(new JsonEnemySerializer());
    assetManager.registerSerializer(new JsonItemSerializer());
  }

  public static void main(String[] args) {
    try {
      System.out.println(System.getProperty("os.name"));
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

      registerResolvers();
      registerSerializers();
      MainWindow.getInstance().setVisible(true);
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {

    }
  }

}

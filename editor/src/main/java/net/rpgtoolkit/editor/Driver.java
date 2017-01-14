/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
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
import net.rpgtoolkit.common.assets.serialization.legacy.LegacyTileSetSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Driver {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(Driver.class);
  
  private static void logSystemInfo() {
    LOGGER.info("---------------------------- System Info ----------------------------");
    LOGGER.info("Operating System: {}", System.getProperty("os.name"));
    LOGGER.info("System Architecture: {}", System.getProperty("os.arch"));
    LOGGER.info("Available Processors (cores): {}", Runtime.getRuntime().availableProcessors());
    LOGGER.info("Free Memory (bytes): {}", Runtime.getRuntime().freeMemory());
    LOGGER.info("Total Memory (bytes): {}", Runtime.getRuntime().totalMemory());
    LOGGER.info("Max Memory (bytes): {}", Runtime.getRuntime().maxMemory());
    LOGGER.info("---------------------------------------------------------------------");
  }

  private static void registerResolvers() {
    LOGGER.debug("Registering asset resolvers.");
    
    AssetManager.getInstance().registerResolver(new FileAssetHandleResolver());
  }

  private static void registerSerializers() {
    LOGGER.debug("Registering asset serializers.");
    
    AssetManager assetManager = AssetManager.getInstance();

    // Legacy.
    assetManager.registerSerializer(new LegacyAnimatedTileSerializer());
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
      LOGGER.info("Starting the RPGToolkit Editor...");
      
      logSystemInfo();
      registerResolvers();
      registerSerializers();
      
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      
      MainWindow.getInstance().setVisible(true);
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
      LOGGER.error("Failed to start the editor!", ex);
    }
  }

}

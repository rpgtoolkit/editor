/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors;

import java.awt.Component;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameListener;
import net.rpgtoolkit.common.assets.AssetDescriptor;
import net.rpgtoolkit.common.assets.AssetException;
import net.rpgtoolkit.common.assets.AssetManager;
import net.rpgtoolkit.common.assets.BoardVector;
import net.rpgtoolkit.common.assets.Item;
import net.rpgtoolkit.common.assets.listeners.SpriteChangeListener;
import net.rpgtoolkit.editor.editors.sprite.AbstractSpriteEditor;
import net.rpgtoolkit.editor.MainWindow;
import net.rpgtoolkit.editor.utilities.EditorFileManager;

/**
 *
 * @author Joshua Michael Daly
 */
public class ItemEditor extends AbstractSpriteEditor implements InternalFrameListener, SpriteChangeListener {

  private final Item item;

  private JTextField itemName;
  private JTextField itemDescription;

  public ItemEditor(Item item) {
    super("Editing Item - " + item.toString(), item);

    this.item = item;
    this.item.addSpriteChangeListener(this);

    if (this.item.getDescriptor() == null) {
      setupNewItem();
    }

    constructWindow();
    setVisible(true);
    pack();
  }
  
  public Item getItem() {
     return item;
  }

  @Override
  public boolean save() {
    boolean success = false;

    item.setName(itemName.getText());
    item.setDescription(itemDescription.getText());

    if (item.getDescriptor() == null) {
      File file = EditorFileManager.saveByType(Item.class);
      item.setDescriptor(new AssetDescriptor(file.toURI()));
      this.setTitle("Editing Item - " + file.getName());
    }

    try {
      AssetManager.getInstance().serialize(
              AssetManager.getInstance().getHandle(item));
      success = true;
    } catch (IOException | AssetException ex) {
      Logger.getLogger(CharacterEditor.class.getName()).log(Level.SEVERE, null, ex);
    }

    return success;
  }

  @Override
  public boolean saveAs(File file) {
    item.setDescriptor(new AssetDescriptor((file.toURI())));
    setTitle("Editing Item - " + file.getName());
    return save();
  }
  
  private void setupNewItem() {
    String undefined = "Undefined";

    item.setName(undefined);
    item.setDescription(undefined);

    item.setStandardGraphics(new ArrayList<>(STANDARD_PLACE_HOLDERS));
    item.setStandingGraphics(new ArrayList<>(STANDING_PLACE_HOLDERS));
    
    BoardVector baseVector = new BoardVector();
    baseVector.addPoint(0, 0);
    baseVector.addPoint(30, 0);
    baseVector.addPoint(30, 20);
    baseVector.addPoint(0, 20);
    baseVector.setClosed(true);
    item.setBaseVector(baseVector);

    BoardVector activationVector = new BoardVector();
    activationVector.addPoint(0, 0);
    activationVector.addPoint(30, 0);
    activationVector.addPoint(30, 20);
    activationVector.addPoint(0, 20);
    activationVector.setClosed(true);
    item.setActivationVector(activationVector);

    item.setBaseVectorOffset(new Point(0, 0));
    item.setActivationVectorOffset(new Point(0, 0));

    item.setCustomGraphics(new ArrayList<String>());
    item.setCustomGraphicNames(new ArrayList<String>());
  }

  private void constructWindow() {
    addInternalFrameListener(this);

    createStatsPanel();
    createAnimationsPanel();

    build();
  }

  private void createStatsPanel() {
    List<Component> labels = new ArrayList<>();
    labels.add(new JLabel("Name"));
    labels.add(new JLabel("Description"));

    itemName = new JTextField(item.getName());
    itemName.setColumns(DEFAULT_INPUT_COLUMNS);

    itemDescription = new JTextField(item.getDescription());
    itemDescription.setColumns(DEFAULT_INPUT_COLUMNS);

    List<Component> inputs = new ArrayList<>();
    inputs.add(itemName);
    inputs.add(itemDescription);

    profileImagePath = "";

    buildStatsPanel(labels, inputs);
  }

  private void createAnimationsPanel() {
    buildAnimationsPanel();
  }

}
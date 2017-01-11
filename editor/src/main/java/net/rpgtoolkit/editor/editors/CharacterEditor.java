/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.rpgtoolkit.common.assets.AssetDescriptor;
import net.rpgtoolkit.common.assets.AssetException;
import net.rpgtoolkit.common.assets.AssetHandle;
import net.rpgtoolkit.common.assets.AssetManager;
import net.rpgtoolkit.common.assets.BoardVector;
import net.rpgtoolkit.common.assets.Player;
import net.rpgtoolkit.common.assets.PlayerSpecialMove;
import net.rpgtoolkit.common.assets.Program;
import net.rpgtoolkit.common.assets.SpecialMove;
import net.rpgtoolkit.common.io.Paths;
import net.rpgtoolkit.common.utilities.CoreProperties;
import net.rpgtoolkit.editor.editors.sprite.AbstractSpriteEditor;
import net.rpgtoolkit.editor.utilities.GuiHelper;
import net.rpgtoolkit.editor.ui.IntegerField;
import net.rpgtoolkit.editor.MainWindow;
import net.rpgtoolkit.editor.ui.WholeNumberField;
import net.rpgtoolkit.common.assets.listeners.SpriteChangeListener;
import net.rpgtoolkit.editor.utilities.EditorFileManager;

/**
 * Player Character Editor
 *
 * @author Joel Moore
 * @author Joshua Michael Daly
 */
public class CharacterEditor extends AbstractSpriteEditor implements InternalFrameListener, SpriteChangeListener {

  private final Player player; // Player file we are altering

  private final MainWindow mainWindow = MainWindow.getInstance();

  // Tabs required by the menu
  private JPanel specialMovesPanel;
  private JPanel equipmentPanel;
  private JPanel levelsPanel;

  //STATS SETTINGS
  private JTextField playerName;
  private IntegerField experience;
  private IntegerField hitPoints;
  private IntegerField maxHitPoints;
  private IntegerField specialPoints;
  private IntegerField maxSpecialPoints;
  private IntegerField fightPower;
  private IntegerField defencePower;
  private IntegerField level;

  // SPECIAL MOVES SETTINGS
  private JCheckBox usesSpecials;
  private JTextField specialsName;
  private JList sMoveList;
  private WholeNumberField sMoveExpMin;
  private WholeNumberField sMoveLvMin;
  private JTextField sMoveVarName;
  private JTextField sMoveVarVal;

  // EQUIPMENT SETTINGS
  private JCheckBox equipHead;
  private JCheckBox equipNeck;
  private JCheckBox equipHandL;
  private JCheckBox equipHandR;
  private JCheckBox equipBody;
  private JCheckBox equipLegs;
  private JList accList;
  private JTextField accName;

  // LEVELS SETTINGS
  private IntegerField initialExpReq;
  private IntegerField expReqIncrease;
  private IntegerField maxLevel;
  private IntegerField maxHPIncrease;
  private IntegerField dpIncrease;
  private IntegerField fpIncrease;
  private IntegerField maxspIncrease;
  private JTextField levelUpProgram;

  public CharacterEditor(Player player) {
    super("Editing Player Character - " + player.getName(), player);
    
    this.player = player;
    this.player.addSpriteChangeListener(this);

    if (this.player.getDescriptor() == null) {
      setupNewPlayer();
    }

    constructWindow();
    setVisible(true);
    pack();
  }

  public Player getPlayer() {
    return player;
  }

  @Override
  public boolean save() throws Exception {
    boolean success = false;

    // Get the relative portrait path.
    if (profilePanel.getFile() != null) {
      String remove = System.getProperty("project.path")
              + CoreProperties.getProperty("toolkit.directory.bitmap")
              + File.separator;
      String path = profilePanel.getFile().getAbsolutePath().replace(remove, "");
      player.setProfilePicture(path);
    }

    // Update all player variables from stats panel.
    player.setName(playerName.getText());
    player.setInitialExperience(experience.getValue());
    player.setInitialHP(hitPoints.getValue());
    player.setInitialMaxHP(maxHitPoints.getValue());
    player.setInitialMP(specialPoints.getValue());
    player.setInitialMaxMP(maxSpecialPoints.getValue());
    player.setInitialFP(fightPower.getValue());
    player.setInitialDP(defencePower.getValue());
    player.setInitialLevel(level.getValue());

    // Update all player variables from graphics panel.
    player.setIdleTimeBeforeStanding(idleTimeoutField.getValue());
    player.setFrameRate(stepRateField.getValue());

    if (player.getDescriptor() == null) {
      File file = EditorFileManager.saveByType(Player.class);
      player.setDescriptor(new AssetDescriptor(file.toURI()));
      this.setTitle("Editing Player - " + file.getName());
    }

    try {
      AssetManager.getInstance().serialize(
              AssetManager.getInstance().getHandle(player));
      success = true;
    } catch (IOException | AssetException ex) {
      Logger.getLogger(CharacterEditor.class.getName()).log(Level.SEVERE, null, ex);
    }

    return success;
  }

  /**
   *
   *
   * @param file
   * @return
   * @throws java.lang.Exception
   */
  @Override
  public boolean saveAs(File file) throws Exception {
    player.setDescriptor(new AssetDescriptor(file.toURI()));
    this.setTitle("Editing Player - " + file.getName());
    return save();
  }

  private void setupNewPlayer() {
    String undefined = "Undefined";

    player.setExpVariableName(undefined);
    player.setDpVariableName(undefined);
    player.setFpVariableName(undefined);
    player.setHpVariableName(undefined);
    player.setMaxHPVariableName(undefined);
    player.setNameVariableName(undefined);
    player.setMpVariableName(undefined);
    player.setMaxHPVariableName(undefined);
    player.setLvlVariableName(undefined);
    player.setSpecialMovesName(undefined);
    player.setProgramOnLevelUp(undefined);

    // An empty path.
    player.setProfilePicture("");

    player.setStandardGraphics(new ArrayList<>(STANDARD_PLACE_HOLDERS));
    player.setStandingGraphics(new ArrayList<>(STANDING_PLACE_HOLDERS));

    BoardVector baseVector = new BoardVector();
    baseVector.addPoint(0, 0);
    baseVector.addPoint(30, 0);
    baseVector.addPoint(30, 20);
    baseVector.addPoint(0, 20);
    baseVector.setClosed(true);
    player.setBaseVector(baseVector);

    BoardVector activationVector = new BoardVector();
    activationVector.addPoint(0, 0);
    activationVector.addPoint(30, 0);
    activationVector.addPoint(30, 20);
    activationVector.addPoint(0, 20);
    activationVector.setClosed(true);
    player.setActivationVector(activationVector);

    player.setBaseVectorOffset(new Point(0, 0));
    player.setActivationVectorOffset(new Point(0, 0));

    // Prepare the empty ArrayLists
    player.setSpecialMoveList(new ArrayList<PlayerSpecialMove>());
    player.setAccessoryNames(new ArrayList<String>());
    player.setCustomGraphics(new ArrayList<String>());
    player.setCustomGraphicNames(new ArrayList<String>());
  }

  /**
   * Builds the Swing interface
   */
  private void constructWindow() {
    addInternalFrameListener(this);

    specialMovesPanel = new JPanel();
    equipmentPanel = new JPanel();
    levelsPanel = new JPanel();

    createStatsPanel();
    createAnimationsPanel();
    
    // <editor-fold defaultstate="collapsed" desc="disabled until tk 4.1.0">
    // TODO: Disabled until TK 4.1.0
    //this.createSpecialMovesPanel();
    //this.createEquipmentPanel();
    //this.createLevelsPanel();
    
    // TODO: Disabled until TK 4.1.0
    //tabPane.addTab("Special Moves", specialMovesPanel);
    //tabPane.addTab("Equippable Items", equipmentPanel);
    
    //tabPane.addTab("Levels", levelsPanel);
    // </editor-fold>
    
    build();
  }
  
  private void createStatsPanel() {    
    List<Component> labels = new ArrayList<>();
    labels.add(new JLabel("Name"));
    labels.add(new JLabel("Experience"));
    labels.add(new JLabel("Health"));
    labels.add(new JLabel("Max Health"));
    labels.add(new JLabel("Special Power"));
    labels.add(new JLabel("Max Special Power"));
    labels.add(new JLabel("Fighting Power"));
    labels.add(new JLabel("Defence Power"));
    labels.add(new JLabel("Level"));
    
    playerName = new JTextField(player.getName());
    playerName.setColumns(DEFAULT_INPUT_COLUMNS);
    
    experience = new IntegerField(player.getInitialExperience());
    experience.setColumns(DEFAULT_INPUT_COLUMNS);
    
    hitPoints = new IntegerField(player.getInitialHP());
    hitPoints.setColumns(DEFAULT_INPUT_COLUMNS);
    
    maxHitPoints = new IntegerField(player.getInitialMaxHP());
    maxHitPoints.setColumns(DEFAULT_INPUT_COLUMNS);
    
    specialPoints = new IntegerField(player.getInitialMP());
    specialPoints.setColumns(DEFAULT_INPUT_COLUMNS);
    
    maxSpecialPoints = new IntegerField(player.getInitialMaxMP());
    maxSpecialPoints.setColumns(DEFAULT_INPUT_COLUMNS);
    
    fightPower = new IntegerField(player.getInitialFP());
    fightPower.setColumns(DEFAULT_INPUT_COLUMNS);
    
    defencePower = new IntegerField(player.getInitialDP());
    defencePower.setColumns(DEFAULT_INPUT_COLUMNS);
    
    level = new IntegerField(player.getInitialLevel());
    level.setColumns(DEFAULT_INPUT_COLUMNS);
    
    List<Component> inputs = new ArrayList<>();
    inputs.add(playerName);
    inputs.add(experience);
    inputs.add(hitPoints);
    inputs.add(maxHitPoints);
    inputs.add(specialPoints);
    inputs.add(maxSpecialPoints);
    inputs.add(fightPower);
    inputs.add(defencePower);
    inputs.add(level);

    profileImagePath = player.getProfilePicture();
    
    buildStatsPanel(labels, inputs);
  }

  private void createAnimationsPanel() {
    buildAnimationsPanel();
  }

  // <editor-fold defaultstate="collapsed" desc="disabled until tk 4.1.0">
  private void createSpecialMovesPanel() {
    // Configure components
    this.usesSpecials = new JCheckBox("Does this character use Special Moves?");

    //set usesSpecials later, after listeners can disable things in response
    final JLabel specialsNameLabel = new JLabel("In-game name of special moves");
    this.specialsName = new JTextField(this.player.getSpecialMovesName());

    final DefaultListModel specialMoves = new DefaultListModel();
    final ArrayList<PlayerSpecialMove> sMoves = this.player.getSpecialMoveList();
    for (PlayerSpecialMove sMove : sMoves) {
      String text = getSpecialMoveText(sMove.getName());
      String number = Integer.toString(specialMoves.size() + 1) + ": ";
      specialMoves.addElement(number + text);
    }

    this.sMoveList = GuiHelper.createVerticalJList(specialMoves);

    final JLabel sMoveExpMinLabel = new JLabel("Experience is at least");
    this.sMoveExpMin = new WholeNumberField(0);

    final JLabel sMoveLvMinLabel = new JLabel("Level is at least");
    this.sMoveLvMin = new WholeNumberField(0);

    final JLabel sMoveVarNameLabel = new JLabel("Or variable");
    this.sMoveVarName = new JTextField();

    final JLabel sMoveVarValLabel = new JLabel("equals");
    this.sMoveVarVal = new JTextField();

    JScrollPane sMoveListScroller = new JScrollPane(this.sMoveList);

    final JButton sMoveFindButton = new JButton("Browse");
    sMoveFindButton.setEnabled(false);
    final JButton sMoveAddButton = new JButton("Add");
    final JButton sMoveRemoveButton = new JButton("Remove");
    sMoveRemoveButton.setEnabled(false);

    final JButton sMoveAlwaysButton = new JButton("The Player Can Always Use This Move");

    // Configure listeners uses specials
    this.usesSpecials.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        sMoveList.setEnabled(usesSpecials.isSelected());
        specialsNameLabel.setEnabled(usesSpecials.isSelected());
        specialsName.setEnabled(usesSpecials.isSelected());
        sMoveAddButton.setEnabled(usesSpecials.isSelected());

        if (usesSpecials.isSelected() == true && sMoveList.getSelectedIndex() != -1) {
          sMoveFindButton.setEnabled(true);
          sMoveRemoveButton.setEnabled(true);
        } else {
          sMoveFindButton.setEnabled(false);
          sMoveRemoveButton.setEnabled(false);
        }

        sMoveExpMinLabel.setEnabled(usesSpecials.isSelected());
        sMoveExpMin.setEnabled(usesSpecials.isSelected());
        sMoveLvMinLabel.setEnabled(usesSpecials.isSelected());
        sMoveLvMin.setEnabled(usesSpecials.isSelected());
        sMoveVarNameLabel.setEnabled(usesSpecials.isSelected());
        sMoveVarName.setEnabled(usesSpecials.isSelected());
        sMoveVarValLabel.setEnabled(usesSpecials.isSelected());
        sMoveVarVal.setEnabled(usesSpecials.isSelected());
        sMoveAlwaysButton.setEnabled(usesSpecials.isSelected());
      }
    });

    //change selection
    this.sMoveList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
          if (sMoveList.getSelectedIndex() == -1) {
            sMoveAlwaysButton.doClick();
            sMoveFindButton.setEnabled(false);
            sMoveRemoveButton.setEnabled(false);
            sMoveExpMinLabel.setEnabled(false);
            sMoveExpMin.setEnabled(false);
            sMoveLvMinLabel.setEnabled(false);
            sMoveLvMin.setEnabled(false);
            sMoveVarNameLabel.setEnabled(false);
            sMoveVarName.setEnabled(false);
            sMoveVarValLabel.setEnabled(false);
            sMoveVarVal.setEnabled(false);
            sMoveAlwaysButton.setEnabled(false);
          } else {
            int index = sMoveList.getSelectedIndex();
            PlayerSpecialMove move = sMoves.get(index);
            sMoveFindButton.setEnabled(true);
            sMoveRemoveButton.setEnabled(true);
            sMoveExpMin.setValue(move.getMinExperience());
            sMoveExpMinLabel.setEnabled(true);
            sMoveExpMin.setEnabled(true);
            sMoveLvMin.setValue(move.getMinLevel());
            sMoveLvMinLabel.setEnabled(true);
            sMoveLvMin.setEnabled(true);
            sMoveVarName.setText(move.getConditionVariable());
            sMoveVarNameLabel.setEnabled(true);
            sMoveVarName.setEnabled(true);
            sMoveVarVal.setText(move.getConditionVariableTest());
            sMoveVarValLabel.setEnabled(true);
            sMoveVarVal.setEnabled(true);
            sMoveAlwaysButton.setEnabled(true);
          }
        }
      }
    });

    //check/uncheck uses specials now that the listeners exist
    this.usesSpecials.setSelected(true);
    if (this.player.getHasSpecialMoves() == false) {
      this.usesSpecials.doClick();
    }

    //browse button
    sMoveFindButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int index = sMoveList.getSelectedIndex();
        if (index >= 0) {
          String loc = browseSpecialMove();
          if (loc != null) {
            SpecialMove sMove = loadSpecialMove(loc);
            PlayerSpecialMove pMove = new PlayerSpecialMove(sMove.getName(), 0, 0, "", "");
            sMoves.set(index, pMove);
            specialMoves.set(index, Integer.toString(index + 1) + ": " + getSpecialMoveText(loc));
            sMoveExpMin.setValue(pMove.getMinExperience());
            sMoveLvMin.setValue(pMove.getMinLevel());
            sMoveVarName.setText(pMove.getConditionVariable());
            sMoveVarVal.setText(pMove.getConditionVariableTest());
          }
        }
      }
    });

    //add button
    sMoveAddButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //insert after current slot
        int index = sMoveList.getSelectedIndex() + 1;
        String loc = browseSpecialMove();

        if (loc != null) {
          SpecialMove move = loadSpecialMove(loc);
          sMoves.add(index, new PlayerSpecialMove(move.getName(), 0, 0, "", ""));
          specialMoves.add(index, Integer.toString(index + 1)
                  + ": " + getSpecialMoveText(loc));
        }

        //select the added move
        sMoveList.setSelectedIndex(index);
        sMoveList.ensureIndexIsVisible(index);
      }
    });

    //remove button
    sMoveRemoveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int index = sMoveList.getSelectedIndex();
        if (index >= 0) {
          sMoves.remove(index);
        }
        GuiHelper.simpleRemoveListener(specialMoves, sMoveList).actionPerformed(e);
      }
    });

    //move list (the one backing the JList, not the one on the Player)
    specialMoves.addListDataListener(new ListDataListener() {

      @Override
      public void intervalAdded(ListDataEvent e) {
        updateNumberLabels(e.getIndex0() + 1);
      }

      @Override
      public void intervalRemoved(ListDataEvent e) {
        updateNumberLabels(e.getIndex0());
      }

      private void updateNumberLabels(int startingIndex) {
        for (int i = startingIndex; i < specialMoves.getSize(); i++) {
          String text = specialMoves.get(i).toString().replaceFirst("\\d+: ", "");
          specialMoves.set(i, Integer.toString(i + 1) + ": " + text);
        }
      }

      @Override
      public void contentsChanged(ListDataEvent e) {
        //no effect
      }
    });

    //player special move properties
    sMoveExpMin.addPropertyChangeListener("value", new PropertyChangeListener() {

      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        int index = sMoveList.getSelectedIndex();
        if (index != -1) {
          sMoves.get(index).setMinExperience(sMoveExpMin.getValue());
        }
      }
    });

    sMoveLvMin.addPropertyChangeListener("value", new PropertyChangeListener() {

      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        int index = sMoveList.getSelectedIndex();
        if (index != -1) {
          sMoves.get(index).setMinLevel(sMoveLvMin.getValue());
        }
      }
    });

    sMoveVarName.getDocument().addDocumentListener(new DocumentListener() {

      @Override
      public void insertUpdate(DocumentEvent e) {
        updateVar();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        updateVar();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
      }

      private void updateVar() {
        int index = sMoveList.getSelectedIndex();
        if (index != -1) {
          sMoves.get(index).setConditionVariable(sMoveVarName.getText());
        }
      }
    });

    sMoveVarVal.getDocument().addDocumentListener(new DocumentListener() {

      @Override
      public void insertUpdate(DocumentEvent e) {
        updateVar();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        updateVar();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
      }

      private void updateVar() {
        int index = sMoveList.getSelectedIndex();
        if (index != -1) {
          sMoves.get(index).setConditionVariableTest(sMoveVarVal.getText());
        }
      }
    });

    //always button
    sMoveAlwaysButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        sMoveExpMin.setValue(0);
        sMoveLvMin.setValue(0);
        sMoveVarName.setText("");
        sMoveVarVal.setText("");
      }
    });

    // Configure the necessary Panels
    JPanel settingsPanel = new JPanel();
    settingsPanel.setBorder(BorderFactory.createTitledBorder(
            this.defaultEtchedBorder, "Settings"));

    JPanel sMovePanel = new JPanel();
    sMovePanel.setBorder(BorderFactory.createTitledBorder(
            this.defaultEtchedBorder, "Special Moves the Player Can Use"));

    JPanel conditionsPanel = new JPanel();
    conditionsPanel.setBorder(BorderFactory.createTitledBorder(
            this.defaultEtchedBorder, "The Player Can Use This Special Move If"));

    // Create Layout for Top Level Panel
    GroupLayout layout = GuiHelper.createGroupLayout(this.specialMovesPanel);

    // Configure Layouts for Second Level Panels
    GroupLayout settingsLayout = GuiHelper.createGroupLayout(settingsPanel);
    GroupLayout sMoveLayout = GuiHelper.createGroupLayout(sMovePanel);
    GroupLayout conditionsLayout = GuiHelper.createGroupLayout(conditionsPanel);

    // Configure the SETTINGS PANEL layout
    settingsLayout.setHorizontalGroup(settingsLayout.createParallelGroup()
            .addComponent(this.usesSpecials)
            .addGroup(settingsLayout.createSequentialGroup()
                    .addComponent(specialsNameLabel)
                    .addComponent(this.specialsName))
    );

    settingsLayout.setVerticalGroup(settingsLayout.createSequentialGroup()
            .addComponent(this.usesSpecials)
            .addGroup(settingsLayout.createParallelGroup()
                    .addComponent(specialsNameLabel)
                    .addComponent(this.specialsName))
    );

    settingsLayout.linkSize(SwingConstants.VERTICAL, specialsNameLabel, this.specialsName);

    // Configure the SMOVE PANEL layout
    sMoveLayout.setHorizontalGroup(sMoveLayout.createSequentialGroup()
            .addComponent(sMoveListScroller)
            .addGroup(sMoveLayout.createParallelGroup()
                    .addComponent(sMoveFindButton)
                    .addComponent(sMoveAddButton)
                    .addComponent(sMoveRemoveButton))
    );

    sMoveLayout.setVerticalGroup(sMoveLayout.createParallelGroup()
            .addComponent(sMoveListScroller)
            .addGroup(sMoveLayout.createSequentialGroup()
                    .addComponent(sMoveFindButton)
                    .addComponent(sMoveAddButton)
                    .addComponent(sMoveRemoveButton))
    );

    // Configure the CONDITIONS PANEL layout
    conditionsLayout.setHorizontalGroup(conditionsLayout.createParallelGroup()
            .addGroup(conditionsLayout.createSequentialGroup()
                    .addComponent(sMoveExpMinLabel)
                    .addComponent(this.sMoveExpMin)
            ).addGroup(conditionsLayout.createSequentialGroup()
                    .addComponent(sMoveLvMinLabel)
                    .addComponent(this.sMoveLvMin)
            ).addGroup(conditionsLayout.createSequentialGroup()
                    .addComponent(sMoveVarNameLabel)
                    .addComponent(this.sMoveVarName)
                    .addComponent(sMoveVarValLabel)
                    .addComponent(this.sMoveVarVal))
            .addComponent(sMoveAlwaysButton)
    );

    conditionsLayout.setVerticalGroup(conditionsLayout.createSequentialGroup()
            .addGroup(conditionsLayout.createParallelGroup()
                    .addComponent(sMoveExpMinLabel)
                    .addComponent(this.sMoveExpMin)
            ).addGroup(conditionsLayout.createParallelGroup()
                    .addComponent(sMoveLvMinLabel)
                    .addComponent(this.sMoveLvMin)
            ).addGroup(conditionsLayout.createParallelGroup()
                    .addComponent(sMoveVarNameLabel)
                    .addComponent(this.sMoveVarName)
                    .addComponent(sMoveVarValLabel)
                    .addComponent(this.sMoveVarVal))
            .addComponent(sMoveAlwaysButton)
    );

    conditionsLayout.linkSize(SwingConstants.HORIZONTAL, sMoveExpMinLabel, sMoveLvMinLabel);
    conditionsLayout.linkSize(SwingConstants.VERTICAL,
            sMoveExpMinLabel, sMoveLvMinLabel, sMoveVarNameLabel, sMoveVarValLabel,
            this.sMoveExpMin, this.sMoveLvMin, this.sMoveVarName, this.sMoveVarVal);

    // Configure the Top Level Panel layout
    layout.setHorizontalGroup(layout.createParallelGroup()
            .addComponent(settingsPanel)
            .addComponent(sMovePanel)
            .addComponent(conditionsPanel)
    );

    layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(settingsPanel)
            .addComponent(sMovePanel)
            .addComponent(conditionsPanel)
    );
  }

  private void createEquipmentPanel() {
    this.equipHead = new JCheckBox("Head");
    this.equipNeck = new JCheckBox("Neck Accessory");
    this.equipHandL = new JCheckBox("Left Hand");
    this.equipHandR = new JCheckBox("Right Hand");
    this.equipBody = new JCheckBox("Body Armour");
    this.equipLegs = new JCheckBox("Legs");

    // Start at 1 for head (not sure what slot 0 is)
    boolean[] armorSlots = this.player.getArmourTypes();
    this.equipHead.setSelected(armorSlots[1]);
    this.equipNeck.setSelected(armorSlots[2]);
    this.equipHandL.setSelected(armorSlots[3]);
    this.equipHandR.setSelected(armorSlots[4]);
    this.equipBody.setSelected(armorSlots[5]);
    this.equipLegs.setSelected(armorSlots[6]);

    final DefaultListModel accessories = new DefaultListModel();
    ArrayList<String> accNames = this.player.getAccessoryNames();
    for (String n : accNames) {
      accessories.addElement(n);
    }

    this.accList = GuiHelper.createVerticalJList(accessories);
    final JLabel accNameLabel = new JLabel("Slot Name");
    this.accName = new JTextField();
    if (accNames.isEmpty() == false) {
      this.accName.setText(accNames.get(0));
    }

    JPanel standardEquipPanel = new JPanel();
    standardEquipPanel.setBorder(BorderFactory.createTitledBorder(
            this.defaultEtchedBorder, "Standard Equipment Can Be Equipped On"));

    JPanel accessoriesPanel = new JPanel();
    accessoriesPanel.setBorder(BorderFactory.createTitledBorder(
            this.defaultEtchedBorder, "Additional Accessory Slots"));

    // Configure Layouts
    GroupLayout layout = GuiHelper.createGroupLayout(this.equipmentPanel);

    GroupLayout standardEquipLayout = GuiHelper.createGroupLayout(standardEquipPanel);
    GroupLayout accessoriesLayout = GuiHelper.createGroupLayout(accessoriesPanel);

    standardEquipLayout.setHorizontalGroup(standardEquipLayout.createParallelGroup()
            .addComponent(this.equipHead)
            .addComponent(this.equipNeck)
            .addComponent(this.equipHandL)
            .addComponent(this.equipHandR)
            .addComponent(this.equipBody)
            .addComponent(this.equipLegs));

    standardEquipLayout.setVerticalGroup(standardEquipLayout.createSequentialGroup()
            .addComponent(this.equipHead)
            .addComponent(this.equipNeck)
            .addComponent(this.equipHandL)
            .addComponent(this.equipHandR)
            .addComponent(this.equipBody)
            .addComponent(this.equipLegs));

    accessoriesLayout.setHorizontalGroup(accessoriesLayout.createParallelGroup()
            .addComponent(standardEquipPanel)
            .addGroup(accessoriesLayout.createSequentialGroup()
                    .addComponent(accList)
                    .addComponent(accNameLabel)
                    .addComponent(this.accName)));

    accessoriesLayout.setVerticalGroup(accessoriesLayout.createSequentialGroup()
            .addComponent(standardEquipPanel)
            .addGroup(accessoriesLayout.createParallelGroup()
                    .addComponent(accList)
                    .addComponent(accNameLabel)
                    .addComponent(this.accName)));

    accessoriesLayout.linkSize(SwingConstants.VERTICAL, accNameLabel, this.accName);

    //layout.linkSize(SwingConstants.HORIZONTAL, standardEquipPanel, accessoriesPanel);
    layout.setHorizontalGroup(layout.createParallelGroup()
            .addComponent(standardEquipPanel)
            .addComponent(accessoriesPanel)
    );

    layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(standardEquipPanel)
            .addComponent(accessoriesPanel)
    );
  }

  private void createLevelsPanel() {
    JLabel experienceAwardedLabel = new JLabel("Experience Gained");
    this.initialExpReq = new IntegerField(this.player.getLevelType());

    JLabel goldAwardedLabel = new JLabel("GP Earned");
    this.expReqIncrease = new IntegerField(this.player.getExpIncreaseFactor());

    JLabel levelUpProgramLavel = new JLabel("Level up RPGCode");
    this.levelUpProgram = new JTextField(this.player.getProgramOnLevelUp());
    JButton levelUpProgramFindButton = new JButton("Browse");

    JPanel rewardsSubPanel = new JPanel();
    rewardsSubPanel.setBorder(BorderFactory.createTitledBorder(
            this.defaultEtchedBorder, "Rewards for Defeating Enemy"));

    // Configure listeners
    levelUpProgramFindButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String loc = EditorFileManager.browseByTypeRelative(Program.class);
        if (loc != null) {
          levelUpProgram.setText(loc);
        }
      }
    });

    // Configure Layouts
    GroupLayout layout = GuiHelper.createGroupLayout(this.levelsPanel);

    GroupLayout rewardsPanelLayout = GuiHelper.createGroupLayout(rewardsSubPanel);

    rewardsPanelLayout.setHorizontalGroup(rewardsPanelLayout.createParallelGroup()
            .addGroup(rewardsPanelLayout.createSequentialGroup()
                    .addComponent(experienceAwardedLabel)
                    .addComponent(this.initialExpReq))
            .addGroup(rewardsPanelLayout.createSequentialGroup()
                    .addComponent(goldAwardedLabel)
                    .addComponent(this.expReqIncrease))
            .addComponent(levelUpProgramLavel)
            .addGroup(rewardsPanelLayout.createSequentialGroup()
                    .addComponent(this.levelUpProgram)
                    .addComponent(levelUpProgramFindButton))
    );

    rewardsPanelLayout.setVerticalGroup(rewardsPanelLayout.createSequentialGroup()
            .addGroup(rewardsPanelLayout.createParallelGroup()
                    .addComponent(experienceAwardedLabel)
                    .addComponent(this.initialExpReq))
            .addGroup(rewardsPanelLayout.createParallelGroup()
                    .addComponent(goldAwardedLabel)
                    .addComponent(this.expReqIncrease))
            .addComponent(levelUpProgramLavel)
            .addGroup(rewardsPanelLayout.createParallelGroup()
                    .addComponent(this.levelUpProgram)
                    .addComponent(levelUpProgramFindButton))
    );

    rewardsPanelLayout.linkSize(SwingConstants.HORIZONTAL,
            experienceAwardedLabel,
            goldAwardedLabel
    );
    rewardsPanelLayout.linkSize(SwingConstants.VERTICAL,
            experienceAwardedLabel, this.initialExpReq,
            goldAwardedLabel, this.expReqIncrease, levelUpProgramLavel,
            this.levelUpProgram, levelUpProgramFindButton
    );

    layout.setHorizontalGroup(layout.createParallelGroup()
            .addComponent(rewardsSubPanel)
    );

    layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(rewardsSubPanel)
    );
  }

  private ActionListener varDefaultListener(
          final JTextField varField, final String varKey, final char type) {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        varField.setText(
                playerName.getText().replace(" ", "_")
                + "[" + varKey + "]" + type
        );
      }
    };
  }

  private String browseSpecialMove() {
    return EditorFileManager.browseByTypeRelative(SpecialMove.class);
  }

  private String getSpecialMoveText(String loc) {
    SpecialMove move = loadSpecialMove(loc);
    String text;
    if (move != null) {
      text = move.getName() + " (" + loc + ")";
    } else {
      text = "Error reading file named: " + loc;
    }
    return text;
  }

  private SpecialMove loadSpecialMove(String loc) {
    if (Paths.extension("/" + loc).contains("spc")) {
      File f = EditorFileManager.getPath(
              EditorFileManager.getTypeSubdirectory(SpecialMove.class)
              + File.separator + loc);
      if (f.canRead()) {
        try {
          AssetHandle handle = AssetManager.getInstance().deserialize(new AssetDescriptor(f.toURI()));
          return (SpecialMove) handle.getAsset();
        } catch (IOException | AssetException ex) {
          Logger.getLogger(CharacterEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
    return null;
  }
  //</editor-fold>

}

/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.rpgtoolkit.common.assets.Animation;
import net.rpgtoolkit.common.assets.AssetDescriptor;
import net.rpgtoolkit.common.assets.AssetException;
import net.rpgtoolkit.common.assets.AssetHandle;
import net.rpgtoolkit.common.assets.AssetManager;
import net.rpgtoolkit.common.assets.Player;
import net.rpgtoolkit.common.assets.PlayerSpecialMove;
import net.rpgtoolkit.common.assets.Program;
import net.rpgtoolkit.common.assets.SpecialMove;
import net.rpgtoolkit.common.io.Paths;
import net.rpgtoolkit.common.utilities.PropertiesSingleton;
import net.rpgtoolkit.editor.editors.character.AnimationsTableModel;
import net.rpgtoolkit.editor.ui.AnimatedPanel;
import net.rpgtoolkit.editor.editors.character.ProfilePanel;
import net.rpgtoolkit.editor.ui.DoubleField;
import net.rpgtoolkit.editor.ui.Gui;
import net.rpgtoolkit.editor.ui.IntegerField;
import net.rpgtoolkit.editor.ui.MainWindow;
import net.rpgtoolkit.editor.ui.ToolkitEditorWindow;
import net.rpgtoolkit.editor.ui.WholeNumberField;
import net.rpgtoolkit.editor.ui.resources.Icons;

/**
 * Player Character Editor
 *
 * @author Joel Moore
 * @author Joshua Michael Daly
 */
public class CharacterEditor extends ToolkitEditorWindow implements InternalFrameListener {

  private final Player player; // Player file we are altering

  private final MainWindow mainWindow = MainWindow.getInstance();

  // Tabs required by the menu
  private JPanel statsPanel;
  private JPanel animationsPanel;
  private JPanel specialMovesPanel;
  private JPanel equipmentPanel;
  private JPanel levelsPanel;

  private final Border defaultEtchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

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
  private JTextField playerNameVar;
  private JTextField experienceVar;
  private JTextField hitPointsVar;
  private JTextField maxHitPointsVar;
  private JTextField specialPointsVar;
  private JTextField maxSpecialPointsVar;
  private JTextField fightPowerVar;
  private JTextField defencePowerVar;
  private JTextField levelVar;
  private ProfilePanel profilePanel;

  // GRAPHICS SETTINGS
  private AnimatedPanel animatedPanel;
  private JTable animationsTable;
  private AnimationsTableModel animationsTableModel;
  private Animation selectedAnim;
  private DoubleField idleTimeoutField;
  private DoubleField stepRateField;

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

  public CharacterEditor(Player character) {
    super("Editing Player Character - " + character.getName(), true, true, true, true);
    player = character;

    constructWindow();
    setVisible(true);
    pack();
  }

  @Override
  public boolean save() {
    // Get the relative portrait path.
    if (profilePanel.getFile() != null) {
      String remove = System.getProperty("project.path")
              + PropertiesSingleton.getProperty("toolkit.directory.bitmap")
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
    player.setNameVariableName(playerNameVar.getText());
    player.setExpVariableName(experienceVar.getText());
    player.setHpVariableName(hitPointsVar.getText());
    player.setMaxMPVariableName(hitPointsVar.getText());
    player.setMpVariableName(specialPointsVar.getText());
    player.setMaxHPVariableName(maxSpecialPointsVar.getText());
    player.setFpVariableName(fightPowerVar.getText());
    player.setDpVariableName(defencePowerVar.getText());
    player.setLvlVariableName(levelVar.getText());

    // Update all player variables from graphics panel.
    player.setIdleTimeBeforeStanding(idleTimeoutField.getValue());
    player.setFrameRate(stepRateField.getValue());

    return player.saveBinary();
  }

  /**
   *
   *
   * @param file
   * @return
   */
  @Override
  public boolean saveAs(File file) {
    player.setFile(file);

    return save();
  }

  public void gracefulClose() {
    player.removePlayerChangeListener(animationsTableModel);
  }

  public void setWindowParent(MainWindow parent) {

  }

  @Override
  public void internalFrameOpened(InternalFrameEvent e) {

  }

  @Override
  public void internalFrameClosing(InternalFrameEvent e) {

  }

  @Override
  public void internalFrameClosed(InternalFrameEvent e) {
    this.gracefulClose();
  }

  @Override
  public void internalFrameIconified(InternalFrameEvent e) {

  }

  @Override
  public void internalFrameDeiconified(InternalFrameEvent e) {

  }

  @Override
  public void internalFrameActivated(InternalFrameEvent e) {

  }

  @Override
  public void internalFrameDeactivated(InternalFrameEvent e) {

  }

  /**
   * Builds the Swing interface
   */
  private void constructWindow() {
    addInternalFrameListener(this);

    JTabbedPane tabPane = new JTabbedPane();
    statsPanel = new JPanel();
    animationsPanel = new JPanel();
    specialMovesPanel = new JPanel();
    equipmentPanel = new JPanel();
    levelsPanel = new JPanel();

    createStatsPanel();
    createAnimationsPanel();
    //this.createSpecialMovesPanel();
    //this.createEquipmentPanel();

    tabPane.addTab("Stats and Portrait", statsPanel);
    tabPane.addTab("Animations", animationsPanel);
    //tabPane.addTab("Special Moves", specialMovesPanel);
    //tabPane.addTab("Equippable Items", equipmentPanel);

    // TODO: Decide what do with the LevelsPanel.
    //this.createLevelsPanel();
    //tabPane.addTab("Levels", levelsPanel);
    
    add(tabPane);
  }

  private void createStatsPanel() {
    // Configure Class scope components
    playerName = new JTextField(player.getName());
    experience = new IntegerField(player.getInitialExperience());
    hitPoints = new IntegerField(player.getInitialHP());
    maxHitPoints = new IntegerField(player.getInitialMaxHP());
    specialPoints = new IntegerField(player.getInitialMP());
    maxSpecialPoints = new IntegerField(player.getInitialMaxMP());
    fightPower = new IntegerField(player.getInitialFP());
    defencePower = new IntegerField(player.getInitialDP());
    level = new IntegerField(player.getInitialLevel());
    playerNameVar = new JTextField(player.getNameVariableName());
    experienceVar = new JTextField(player.getExpVariableName());
    hitPointsVar = new JTextField(player.getHpVariableName());
    maxHitPointsVar = new JTextField(player.getMaxHPVariableName());
    specialPointsVar = new JTextField(player.getMpVariableName());
    maxSpecialPointsVar = new JTextField(player.getMaxMPVariableName());
    fightPowerVar = new JTextField(player.getFpVariableName());
    defencePowerVar = new JTextField(player.getDpVariableName());
    levelVar = new JTextField(player.getLvlVariableName());

    JLabel playerNameLabel = new JLabel("Name");
    JLabel experienceLabel = new JLabel("Experience");
    JLabel hitPointsLabel = new JLabel("Health");
    JLabel maxHitPointsLabel = new JLabel("Max Health");
    JLabel specialPointsLabel = new JLabel("Special Power");
    JLabel maxSpecialPointsLabel = new JLabel("Max Special Power");
    JLabel fightPowerLabel = new JLabel("Fighting Power");
    JLabel defencePowerLabel = new JLabel("Defence Power");
    JLabel levelLabel = new JLabel("Level");
    JLabel playerNameVarLabel = new JLabel("Name");
    JLabel experienceVarLabel = new JLabel("Experience");
    JLabel hitPointsVarLabel = new JLabel("Health");
    JLabel maxHitPointsVarLabel = new JLabel("Max Health");
    JLabel specialPointsVarLabel = new JLabel("Special Power");
    JLabel maxSpecialPointsVarLabel = new JLabel("Max Special Power");
    JLabel fightPowerVarLabel = new JLabel("Fighting Power");
    JLabel defencePowerVarLabel = new JLabel("Defence Power");
    JLabel levelVarLabel = new JLabel("Level");
    JButton defaultNameBtn = new JButton("Default");
    JButton defaultExperienceBtn = new JButton("Default");
    JButton defaultHitPointsBtn = new JButton("Default");
    JButton defaultMaxHitPointsBtn = new JButton("Default");
    JButton defaultSpecialPointsBtn = new JButton("Default");
    JButton defaultMaxSpecialPointsBtn = new JButton("Default");
    JButton defaultFightPowerBtn = new JButton("Default");
    JButton defaultDefencePowerBtn = new JButton("Default");
    JButton defaultLevelBtn = new JButton("Default");

    defaultNameBtn.addActionListener(
            varDefaultListener(playerNameVar, "name", '$'));
    defaultExperienceBtn.addActionListener(
            varDefaultListener(experienceVar, "experience", '!'));
    defaultHitPointsBtn.addActionListener(
            varDefaultListener(hitPointsVar, "health", '!'));
    defaultMaxHitPointsBtn.addActionListener(
            varDefaultListener(maxHitPointsVar, "maxhealth", '!'));
    defaultSpecialPointsBtn.addActionListener(
            varDefaultListener(specialPointsVar, "smpower", '!'));
    defaultMaxSpecialPointsBtn.addActionListener(
            varDefaultListener(maxSpecialPointsVar, "maxsm", '!'));
    defaultFightPowerBtn.addActionListener(
            varDefaultListener(fightPowerVar, "fight", '!'));
    defaultDefencePowerBtn.addActionListener(
            varDefaultListener(defencePowerVar, "defence", '!'));
    defaultLevelBtn.addActionListener(
            varDefaultListener(levelVar, "level", '!'));

    // Configure the necessary Panels
    JPanel statsEditPanel = new JPanel();
    statsEditPanel.setBorder(BorderFactory.createTitledBorder(
            defaultEtchedBorder, "Starting Stats"));
    JPanel variablesPanel = new JPanel();
    variablesPanel.setBorder(BorderFactory.createTitledBorder(
            defaultEtchedBorder, "Controlling Variables"));

    // Create Layout for top level panel
    GroupLayout layout = Gui.createGroupLayout(statsPanel);

    // Create Layouts for second level panels
    GroupLayout statsLayout = Gui.createGroupLayout(statsEditPanel);
    GroupLayout variablesLayout = Gui.createGroupLayout(variablesPanel);

    // Configure the STATS EDIT PANEL layout
    // <editor-fold defaultstate="collapsed" desc="group layouts">
    statsLayout.setHorizontalGroup(statsLayout.createParallelGroup()
            .addGroup(statsLayout.createSequentialGroup()
                    .addComponent(playerNameLabel)
                    .addComponent(this.playerName))
            .addGroup(statsLayout.createSequentialGroup()
                    .addComponent(experienceLabel)
                    .addComponent(this.experience))
            .addGroup(statsLayout.createSequentialGroup()
                    .addComponent(hitPointsLabel)
                    .addComponent(this.hitPoints))
            .addGroup(statsLayout.createSequentialGroup()
                    .addComponent(maxHitPointsLabel)
                    .addComponent(this.maxHitPoints))
            .addGroup(statsLayout.createSequentialGroup()
                    .addComponent(specialPointsLabel)
                    .addComponent(this.specialPoints))
            .addGroup(statsLayout.createSequentialGroup()
                    .addComponent(maxSpecialPointsLabel)
                    .addComponent(this.maxSpecialPoints))
            .addGroup(statsLayout.createSequentialGroup()
                    .addComponent(fightPowerLabel)
                    .addComponent(this.fightPower))
            .addGroup(statsLayout.createSequentialGroup()
                    .addComponent(defencePowerLabel)
                    .addComponent(this.defencePower))
            .addGroup(statsLayout.createSequentialGroup()
                    .addComponent(levelLabel)
                    .addComponent(this.level))
    );

    statsLayout.linkSize(SwingConstants.HORIZONTAL,
            playerNameLabel,
            experienceLabel,
            hitPointsLabel,
            maxHitPointsLabel,
            specialPointsLabel,
            maxSpecialPointsLabel,
            fightPowerLabel,
            defencePowerLabel,
            levelLabel);
    statsLayout.linkSize(SwingConstants.VERTICAL,
            playerNameLabel,
            experienceLabel,
            hitPointsLabel,
            maxHitPointsLabel,
            specialPointsLabel,
            maxSpecialPointsLabel,
            fightPowerLabel,
            defencePowerLabel,
            levelLabel,
            this.playerName,
            this.experience,
            this.hitPoints,
            this.maxHitPoints,
            this.specialPoints,
            this.maxSpecialPoints,
            this.fightPower,
            this.defencePower,
            this.level);

    statsLayout.setVerticalGroup(statsLayout.createSequentialGroup()
            .addGroup(statsLayout.createParallelGroup()
                    .addComponent(playerNameLabel)
                    .addComponent(this.playerName, Gui.JTF_HEIGHT,
                            Gui.JTF_HEIGHT, Gui.JTF_HEIGHT))
            .addGroup(statsLayout.createParallelGroup()
                    .addComponent(experienceLabel)
                    .addComponent(this.experience))
            .addGroup(statsLayout.createParallelGroup()
                    .addComponent(hitPointsLabel)
                    .addComponent(this.hitPoints))
            .addGroup(statsLayout.createParallelGroup()
                    .addComponent(maxHitPointsLabel)
                    .addComponent(this.maxHitPoints))
            .addGroup(statsLayout.createParallelGroup()
                    .addComponent(specialPointsLabel)
                    .addComponent(this.specialPoints))
            .addGroup(statsLayout.createParallelGroup()
                    .addComponent(maxSpecialPointsLabel)
                    .addComponent(this.maxSpecialPoints))
            .addGroup(statsLayout.createParallelGroup()
                    .addComponent(fightPowerLabel)
                    .addComponent(this.fightPower))
            .addGroup(statsLayout.createParallelGroup()
                    .addComponent(defencePowerLabel)
                    .addComponent(this.defencePower))
            .addGroup(statsLayout.createParallelGroup()
                    .addComponent(levelLabel)
                    .addComponent(this.level))
    );

    // Configure the VARIABLES PANEL layout
    variablesLayout.setHorizontalGroup(variablesLayout.createParallelGroup()
            .addGroup(variablesLayout.createSequentialGroup()
                    .addComponent(playerNameVarLabel)
                    .addComponent(this.playerNameVar)
                    .addComponent(defaultNameBtn))
            .addGroup(variablesLayout.createSequentialGroup()
                    .addComponent(experienceVarLabel)
                    .addComponent(this.experienceVar)
                    .addComponent(defaultExperienceBtn))
            .addGroup(variablesLayout.createSequentialGroup()
                    .addComponent(hitPointsVarLabel)
                    .addComponent(this.hitPointsVar)
                    .addComponent(defaultHitPointsBtn))
            .addGroup(variablesLayout.createSequentialGroup()
                    .addComponent(maxHitPointsVarLabel)
                    .addComponent(this.maxHitPointsVar)
                    .addComponent(defaultMaxHitPointsBtn))
            .addGroup(variablesLayout.createSequentialGroup()
                    .addComponent(specialPointsVarLabel)
                    .addComponent(this.specialPointsVar)
                    .addComponent(defaultSpecialPointsBtn))
            .addGroup(variablesLayout.createSequentialGroup()
                    .addComponent(maxSpecialPointsVarLabel)
                    .addComponent(this.maxSpecialPointsVar)
                    .addComponent(defaultMaxSpecialPointsBtn))
            .addGroup(variablesLayout.createSequentialGroup()
                    .addComponent(fightPowerVarLabel)
                    .addComponent(this.fightPowerVar)
                    .addComponent(defaultFightPowerBtn))
            .addGroup(variablesLayout.createSequentialGroup()
                    .addComponent(defencePowerVarLabel)
                    .addComponent(this.defencePowerVar)
                    .addComponent(defaultDefencePowerBtn))
            .addGroup(variablesLayout.createSequentialGroup()
                    .addComponent(levelVarLabel)
                    .addComponent(this.levelVar)
                    .addComponent(defaultLevelBtn))
    );

    variablesLayout.linkSize(SwingConstants.HORIZONTAL,
            playerNameVarLabel,
            experienceVarLabel,
            hitPointsVarLabel,
            maxHitPointsVarLabel,
            specialPointsVarLabel,
            maxSpecialPointsVarLabel,
            fightPowerVarLabel,
            defencePowerVarLabel,
            levelVarLabel);
    variablesLayout.linkSize(SwingConstants.VERTICAL,
            playerNameVarLabel,
            experienceVarLabel,
            hitPointsVarLabel,
            maxHitPointsVarLabel,
            specialPointsVarLabel,
            maxSpecialPointsVarLabel,
            fightPowerVarLabel,
            defencePowerVarLabel,
            levelVarLabel,
            this.playerNameVar,
            this.experienceVar,
            this.hitPointsVar,
            this.maxHitPointsVar,
            this.specialPointsVar,
            this.maxSpecialPointsVar,
            this.fightPowerVar,
            this.defencePowerVar,
            this.levelVar);

    variablesLayout.setVerticalGroup(variablesLayout.createSequentialGroup()
            .addGroup(variablesLayout.createParallelGroup()
                    .addComponent(playerNameVarLabel)
                    .addComponent(this.playerNameVar, Gui.JTF_HEIGHT,
                            Gui.JTF_HEIGHT, Gui.JTF_HEIGHT)
                    .addComponent(defaultNameBtn))
            .addGroup(variablesLayout.createParallelGroup()
                    .addComponent(experienceVarLabel)
                    .addComponent(this.experienceVar)
                    .addComponent(defaultExperienceBtn))
            .addGroup(variablesLayout.createParallelGroup()
                    .addComponent(hitPointsVarLabel)
                    .addComponent(this.hitPointsVar)
                    .addComponent(defaultHitPointsBtn))
            .addGroup(variablesLayout.createParallelGroup()
                    .addComponent(maxHitPointsVarLabel)
                    .addComponent(this.maxHitPointsVar)
                    .addComponent(defaultMaxHitPointsBtn))
            .addGroup(variablesLayout.createParallelGroup()
                    .addComponent(specialPointsVarLabel)
                    .addComponent(this.specialPointsVar)
                    .addComponent(defaultSpecialPointsBtn))
            .addGroup(variablesLayout.createParallelGroup()
                    .addComponent(maxSpecialPointsVarLabel)
                    .addComponent(this.maxSpecialPointsVar)
                    .addComponent(defaultMaxSpecialPointsBtn))
            .addGroup(variablesLayout.createParallelGroup()
                    .addComponent(fightPowerVarLabel)
                    .addComponent(this.fightPowerVar)
                    .addComponent(defaultFightPowerBtn))
            .addGroup(variablesLayout.createParallelGroup()
                    .addComponent(defencePowerVarLabel)
                    .addComponent(this.defencePowerVar)
                    .addComponent(defaultDefencePowerBtn))
            .addGroup(variablesLayout.createParallelGroup()
                    .addComponent(levelVarLabel)
                    .addComponent(this.levelVar)
                    .addComponent(defaultLevelBtn))
    );
    // </editor-fold>

    JPanel configPanel = new JPanel(new BorderLayout());
    configPanel.add(statsEditPanel, BorderLayout.NORTH);
    configPanel.add(variablesPanel, BorderLayout.SOUTH);

    profilePanel = new ProfilePanel();
    if (!player.getProfilePicture().isEmpty()) {
      profilePanel.addImage(new File(
              System.getProperty("project.path")
              + PropertiesSingleton.getProperty("toolkit.directory.bitmap")
              + File.separator
              + player.getProfilePicture()));
    }

    // Configure STATS PANEL layout
    layout.setHorizontalGroup(
            layout.createSequentialGroup()
            .addComponent(profilePanel)
            .addComponent(configPanel)
    );

    layout.linkSize(SwingConstants.VERTICAL, profilePanel, configPanel);

    layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(profilePanel)
                    .addComponent(configPanel))
    );
  }

  private void createAnimationsPanel() {
    // Configure Class scope components
    animationsTableModel = new AnimationsTableModel(player);

    animationsTable = new JTable(animationsTableModel);
    animationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    player.addPlayerChangeListener(animationsTableModel);

    // Configure function Scope Components
    JScrollPane animationScrollPane = new JScrollPane(animationsTable);

    animatedPanel = new AnimatedPanel();

    final JButton addButton = new JButton();
    addButton.setIcon(Icons.getSmallIcon("new"));

    final JButton browseButton = new JButton();
    browseButton.setIcon(Icons.getSmallIcon("open"));
    browseButton.setEnabled(false);

    final JButton removeButton = new JButton();
    removeButton.setIcon(Icons.getSmallIcon("delete"));
    removeButton.setEnabled(false);

    animationsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
          int row = animationsTable.getSelectedRow();
          if (row == -1) {
            animatedPanel.setAnimation(selectedAnim);
            browseButton.setEnabled(false);
            removeButton.setEnabled(false);
          } else {
            String path;
            if (row < AnimationsTableModel.STANDARD_GRAPHICS.length) {
              path = player.getStandardGraphics().get(row);
            } else {
              path = player.getCustomGraphics().get(row - AnimationsTableModel.STANDARD_GRAPHICS.length);
            }

            updateAnimation(path);
            browseButton.setEnabled(true);

            if (row < AnimationsTableModel.STANDARD_GRAPHICS.length) {
              removeButton.setEnabled(false); // Cannot remove default graphics.
            } else {
              removeButton.setEnabled(true);
            }
          }
        }
      }
    });

    browseButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int row = animationsTable.getSelectedRow();
        if (row < 0) {
          return;
        }
        
        if (row < player.getStandingGraphics().size()) {
            Object[] options = {"Active Animation", "Idle Animation", "Cancel"};
            int result = JOptionPane.showOptionDialog(
                    mainWindow,
                    "Select Animation type to update.",
                    "Update Animation",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            
            if (result != 2) { // Cancel
                String path = mainWindow.browseByTypeRelative(Animation.class);
                if (path != null) {
                    switch (result) { // Active Animation
                        case 0:
                            player.updateStandardGraphics(row, path);
                            break;
                        case 1: // Idle Animation
                            player.updateStandingGraphics(row, path);
                            break;
                    }
                }
            }
        } else {
            String path = mainWindow.browseByTypeRelative(Animation.class);
            if (path != null) {
                int customIndex = row - AnimationsTableModel.STANDARD_GRAPHICS.length;
                player.updateCustomGraphics(customIndex, path);
                updateAnimation(path);
            }
        }
      }
    });

    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String name = (String) JOptionPane.showInputDialog(
                animationsPanel,
                "Enter the handle for the new sprite:",
                "Add Enemy Graphic",
                JOptionPane.PLAIN_MESSAGE);

        if (name == null || name.isEmpty()) {
          return;
        }

        player.getCustomGraphicNames().add(name);
        player.addCustomGraphics("");

        animationsTable.scrollRectToVisible(animationsTable.getCellRect(
                animationsTable.getRowCount() - 1, 0, true));
      }
    });

    removeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int row = animationsTable.getSelectedRow();
        if (row >= 0) {
          if (row < AnimationsTableModel.STANDARD_GRAPHICS.length) {
            if (selectedAnim != null) {
              player.getStandardGraphics().set(row, "");
            }
          } else if (row < AnimationsTableModel.STANDARD_GRAPHICS.length
                  + player.getCustomGraphicNames().size()) {
            int customIndex = row - AnimationsTableModel.STANDARD_GRAPHICS.length;
            player.getCustomGraphicNames().remove(customIndex);
            player.removeCustomGraphics(customIndex);

            if (row > 0) {
              if (row == animationsTableModel.getRowCount()) {
                row--;
              }

              animationsTable.scrollRectToVisible(animationsTable.getCellRect(row, 0, true));
            }
          }
        }
      }
    });

    // Configure the necessary Panels
    JPanel configurationPanel = new JPanel();
    configurationPanel.add(addButton);
    configurationPanel.add(browseButton);
    configurationPanel.add(removeButton);

    configurationPanel.add(new JLabel("Idle Timeout: "));
    idleTimeoutField = new DoubleField(player.getIdleTimeBeforeStanding());
    configurationPanel.add(idleTimeoutField);

    configurationPanel.add(new JLabel("Step Rate: "));
    stepRateField = new DoubleField(player.getFrameRate());
    configurationPanel.add(stepRateField);

    // Fix the size of this panel to stop the JTable growing beyond the Window.
    JPanel southPanel = new JPanel(new BorderLayout()) {
      @Override
      public Dimension getPreferredSize() {
        return new Dimension(getParent().getWidth() - 25,
                this.getParent().getHeight() - AnimatedPanel.HEIGHT);
      }

      @Override
      public Dimension getMaximumSize() {
        return new Dimension(getParent().getWidth() - 25,
                this.getParent().getHeight() - AnimatedPanel.HEIGHT);
      }

      @Override
      public Dimension getMinimumSize() {
        return new Dimension(getParent().getWidth() - 25,
                200);
      }
    };
    southPanel.add(animationScrollPane, BorderLayout.CENTER);
    southPanel.add(configurationPanel, BorderLayout.SOUTH);

    // Create Layout for Top Level Panel
    GroupLayout layout = Gui.createGroupLayout(animationsPanel);

    // Configure the GRAPHICS PANEL layout
    layout.setHorizontalGroup(layout.createParallelGroup()
            .addComponent(animatedPanel)
            .addComponent(southPanel)
    );

    layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(animatedPanel)
            .addComponent(southPanel)
    );
  }

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

    this.sMoveList = Gui.createVerticalJList(specialMoves);

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
        Gui.simpleRemoveListener(specialMoves, sMoveList).actionPerformed(e);
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
    GroupLayout layout = Gui.createGroupLayout(this.specialMovesPanel);

    // Configure Layouts for Second Level Panels
    GroupLayout settingsLayout = Gui.createGroupLayout(settingsPanel);
    GroupLayout sMoveLayout = Gui.createGroupLayout(sMovePanel);
    GroupLayout conditionsLayout = Gui.createGroupLayout(conditionsPanel);

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

    this.accList = Gui.createVerticalJList(accessories);
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
    GroupLayout layout = Gui.createGroupLayout(this.equipmentPanel);

    GroupLayout standardEquipLayout = Gui.createGroupLayout(standardEquipPanel);
    GroupLayout accessoriesLayout = Gui.createGroupLayout(accessoriesPanel);

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
        String loc = mainWindow.browseByTypeRelative(Program.class);
        if (loc != null) {
          levelUpProgram.setText(loc);
        }
      }
    });

    // Configure Layouts
    GroupLayout layout = Gui.createGroupLayout(this.levelsPanel);

    GroupLayout rewardsPanelLayout = Gui.createGroupLayout(rewardsSubPanel);

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
    return mainWindow.browseByTypeRelative(SpecialMove.class);
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
      File f = mainWindow.getPath(
              mainWindow.getTypeSubdirectory(SpecialMove.class)
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

  private void updateAnimation(String path) {
    if (!path.isEmpty()) {
      File file = mainWindow.getPath(
              mainWindow.getTypeSubdirectory(Animation.class)
              + File.separator + path);
      if (file.canRead()) {
        selectedAnim = new Animation(file);

        if (selectedAnim != null && selectedAnim.getFrameCount() > 0) {
          animatedPanel.setAnimation(selectedAnim);
        }
      }
    } else {
      selectedAnim = null;
      animatedPanel.setAnimation(selectedAnim);
    }
  }

}

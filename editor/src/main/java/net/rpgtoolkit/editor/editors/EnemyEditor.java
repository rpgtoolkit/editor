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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
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
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.rpgtoolkit.common.assets.AssetDescriptor;
import net.rpgtoolkit.common.assets.AssetException;
import net.rpgtoolkit.common.assets.AssetHandle;
import net.rpgtoolkit.common.assets.AssetManager;
import net.rpgtoolkit.common.assets.BoardVector;
import net.rpgtoolkit.common.assets.Enemy;
import net.rpgtoolkit.common.assets.Program;
import net.rpgtoolkit.common.assets.SpecialMove;
import net.rpgtoolkit.common.io.Paths;
import net.rpgtoolkit.editor.editors.sprite.AbstractSpriteEditor;
import net.rpgtoolkit.editor.MainWindow;
import net.rpgtoolkit.editor.utilities.EditorFileManager;
import net.rpgtoolkit.editor.utilities.GuiHelper;
import net.rpgtoolkit.editor.ui.IntegerField;
import net.rpgtoolkit.editor.ui.WholeNumberField;

/**
 * Enemy editor
 *
 * @author Joel Moore
 * @author Joshua Michael Daly
 */
public class EnemyEditor extends AbstractSpriteEditor implements InternalFrameListener {

  private final Enemy enemy;

  private final MainWindow mainWindow = MainWindow.getInstance();

  // Tabs required by the menu
  private JPanel specialMovesPanel;
  private JPanel tacticsPanel;
  private JPanel rewardsPanel;

  //BASIC SETTINGS
  private JTextField enemyName;
  private WholeNumberField maxHitPoints;
  private WholeNumberField maxSpecialPoints;
  private WholeNumberField fightPower;
  private WholeNumberField defencePower;
  private JCheckBox canRunAway; //NOTE: actions set up in old Joel Moore commits
  private JTextField runAwayProgram;
  private WholeNumberField critOnEnemy;
  private WholeNumberField critOnPlayer;

  // SPECIAL MOVES SETTINGS
  private JList sMoveList;
  private JList strengthList;
  private JList weaknessList;

  // TACTICS SETTINGS
  private JSlider aiLevel;
  private JCheckBox useRPGCodeTactics;
  private JTextField tacticsProgram;

  // REWARDS SETTINGS
  private IntegerField experienceAwarded;
  private IntegerField goldAwarded;
  private JTextField victoryProgram;

  /*
   * *************************************************************************
   * Public Constructors
   * *************************************************************************
   */

  /**
   * Opens an existing enemy
   *
   * @param theEnemy Enemy to edit
   */
  public EnemyEditor(Enemy theEnemy) {
    super("Editing Enemy - " + theEnemy.toString(), theEnemy);

    this.enemy = theEnemy;
    
    if (this.enemy.getDescriptor() == null) {
      setupNewEnemy();
    }

    this.constructWindow();
    this.setVisible(true);
    pack();
  }

  /*
   * *************************************************************************
   * Public Methods
   * *************************************************************************
   */
  @Override
  public boolean save() throws Exception {
    boolean success = false;
    
    //Update all enemy variables from the stats panel
    enemy.setName(enemyName.getText());
    enemy.setMaxHitPoints(maxHitPoints.getValue());
    enemy.setMaxMagicPoints(maxSpecialPoints.getValue());
    enemy.setFightPower(fightPower.getValue());
    enemy.setDefencePower(defencePower.getValue());
    enemy.setExperienceAwarded(experienceAwarded.getValue());
    enemy.setGoldAwarded(goldAwarded.getValue());
    
    // Update all enemy variables from graphics panel.
    enemy.setIdleTimeBeforeStanding(idleTimeoutField.getValue());
    enemy.setFrameRate(stepRateField.getValue());
    
    if (enemy.getDescriptor() == null) {
      File file = EditorFileManager.saveByType(Enemy.class);
      enemy.setDescriptor(new AssetDescriptor(file.toURI()));
      this.setTitle("Editing Enemy - " + file.getName());
    }

    try {
      AssetManager.getInstance().serialize(
              AssetManager.getInstance().getHandle(enemy));
      success = true;
    } catch (IOException | AssetException ex) {
      Logger.getLogger(EnemyEditor.class.getName()).log(Level.SEVERE, null, ex);
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
    enemy.setDescriptor(new AssetDescriptor(file.toURI()));
    this.setTitle("Editing Enemy - " + file.getName());
    return save();
  }

  /*
   * *************************************************************************
   * Private Methods
   * *************************************************************************
   */
  private void setupNewEnemy() {
    enemy.setStandardGraphics(new ArrayList<>(STANDARD_PLACE_HOLDERS));
    enemy.setStandingGraphics(new ArrayList<>(STANDING_PLACE_HOLDERS));
    
    BoardVector baseVector = new BoardVector();
    enemy.setBaseVector(baseVector);
    
    BoardVector activationVector = new BoardVector();
    enemy.setActivationVector(activationVector);
    
    enemy.setBaseVectorOffset(new Point(0, 0));
    enemy.setActivationVectorOffset(new Point(0, 0));
  }
  
  /**
   * Builds the Swing interface
   */
  private void constructWindow() {
    this.addInternalFrameListener(this);
    
    this.specialMovesPanel = new JPanel();
    this.tacticsPanel = new JPanel();
    this.rewardsPanel = new JPanel();

    this.createStatsPanel();
    this.createGraphicsPanel();
    this.createSpecialMovesPanel();
    this.createTacticsPanel();
    this.createRewardsPanel();
    
    // <editor-fold defaultstate="collapsed" desc="disabled until tk 4.1.0">
    //tabPane.addTab("Special Moves", this.specialMovesPanel);
    //tabPane.addTab("Tactics", this.tacticsPanel);
    //tabPane.addTab("Rewards", this.rewardsPanel);
    // </editor-fold>
   
    build();
  }

  private void createStatsPanel() {
    List<Component> labels = new ArrayList<>();
    labels.add(new JLabel("Name"));
    labels.add(new JLabel("Health"));
    labels.add(new JLabel("Special Power"));
    labels.add(new JLabel("Fighting Power"));
    labels.add(new JLabel("Defence Power"));
    labels.add(new JLabel("Experience Reward"));
    labels.add(new JLabel("Gold Reward"));
    
    // Configure Class scope components
    enemyName = new JTextField(enemy.getName());
    enemyName.setColumns(DEFAULT_INPUT_COLUMNS);
    
    maxHitPoints = new WholeNumberField(enemy.getMaxHitPoints());
    maxHitPoints.setColumns(DEFAULT_INPUT_COLUMNS);
    
    maxSpecialPoints = new WholeNumberField(enemy.getMaxMagicPoints());
    maxSpecialPoints.setColumns(DEFAULT_INPUT_COLUMNS);
    
    fightPower = new WholeNumberField(enemy.getFightPower());
    fightPower.setColumns(DEFAULT_INPUT_COLUMNS);
    
    defencePower = new WholeNumberField(enemy.getDefencePower());
    defencePower.setColumns(DEFAULT_INPUT_COLUMNS);
    
    experienceAwarded = new IntegerField((int)enemy.getExperienceAwarded());
    experienceAwarded.setColumns(DEFAULT_INPUT_COLUMNS);
    
    goldAwarded = new IntegerField((int)enemy.getGoldAwarded());
    goldAwarded.setColumns(DEFAULT_INPUT_COLUMNS);
    
    List<Component> inputs = new ArrayList<>();
    inputs.add(enemyName);
    inputs.add(maxHitPoints);
    inputs.add(maxSpecialPoints);
    inputs.add(fightPower);
    inputs.add(defencePower);
    inputs.add(experienceAwarded);
    inputs.add(goldAwarded);
    
    profileImagePath = "";
    
    buildStatsPanel(labels, inputs);
  }

  private void createGraphicsPanel() {
    buildAnimationsPanel();
  }

  // <editor-fold defaultstate="collapsed" desc="disabled until tk 4.1.0">
  private void createSpecialMovesPanel() {
    // Configure Class scope components
    final DefaultListModel specialMoves = new DefaultListModel();
    final ArrayList<String> sMoveLocs = this.enemy.getSpecialMoves();
    for (String loc : sMoveLocs) {
      String text = getSpecialMoveText(loc);
      specialMoves.addElement(text);
    }
    final DefaultListModel strengths = new DefaultListModel();
    final ArrayList<String> strengthLocs = this.enemy.getStrengths();
    for (String loc : strengthLocs) {
      String text = getSpecialMoveText(loc);
      strengths.addElement(text);
    }
    final DefaultListModel weaknesses = new DefaultListModel();
    final ArrayList<String> weaknessLocs = this.enemy.getWeaknesses();
    for (String loc : weaknessLocs) {
      String text = getSpecialMoveText(loc);
      weaknesses.addElement(text);
    }

    this.sMoveList = GuiHelper.createVerticalJList(specialMoves);
    this.strengthList = GuiHelper.createVerticalJList(strengths);
    this.weaknessList = GuiHelper.createVerticalJList(weaknesses);

    // Configure function Scope Components
    JScrollPane sMoveListScroller = new JScrollPane(this.sMoveList);
    JScrollPane strengthListScroller = new JScrollPane(this.strengthList);
    JScrollPane weaknessListScroller = new JScrollPane(this.weaknessList);

    JButton sMoveFindButton = new JButton("Browse");
    JButton sMoveAddButton = new JButton("Add");
    final JButton sMoveRemoveButton = new JButton("Remove");
    sMoveRemoveButton.setEnabled(false);

    JButton strengthFindButton = new JButton("Browse");
    JButton strengthAddButton = new JButton("Add");
    final JButton strengthRemoveButton = new JButton("Remove");
    strengthRemoveButton.setEnabled(false);

    JButton weaknessFindButton = new JButton("Browse");
    JButton weaknessAddButton = new JButton("Add");
    final JButton weaknessRemoveButton = new JButton("Remove");
    weaknessRemoveButton.setEnabled(false);

        // Configure listeners
    //change selection
    this.sMoveList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
          if (sMoveList.getSelectedIndex() == -1) {
            sMoveRemoveButton.setEnabled(false);
          } else {
            sMoveRemoveButton.setEnabled(true);
          }
        }
      }
    });
    this.strengthList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
          if (strengthList.getSelectedIndex() == -1) {
            strengthRemoveButton.setEnabled(false);
          } else {
            strengthRemoveButton.setEnabled(true);
          }
        }
      }
    });
    this.weaknessList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
          if (weaknessList.getSelectedIndex() == -1) {
            weaknessRemoveButton.setEnabled(false);
          } else {
            weaknessRemoveButton.setEnabled(true);
          }
        }
      }
    });

    //browse buttons
    sMoveFindButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int index = sMoveList.getSelectedIndex();
        if (index >= 0) {
          String loc = browseSpecialMove();
          if (loc != null) {
            specialMoves.set(index, getSpecialMoveText(loc));
          }
        }
      }
    });
    strengthFindButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int index = strengthList.getSelectedIndex();
        if (index >= 0) {
          String loc = browseSpecialMove();
          if (loc != null) {
            strengths.set(index, getSpecialMoveText(loc));
          }
        }
      }
    });
    weaknessFindButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int index = weaknessList.getSelectedIndex();
        if (index >= 0) {
          String loc = browseSpecialMove();
          if (loc != null) {
            weaknesses.set(index, getSpecialMoveText(loc));
          }
        }
      }
    });

    //add buttons
    sMoveAddButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //insert after current slot
        int index = sMoveList.getSelectedIndex() + 1;
        String loc = browseSpecialMove();
        if (loc != null) {
          specialMoves.add(index, getSpecialMoveText(loc));
        }
        //select the added move
        sMoveList.setSelectedIndex(index);
        sMoveList.ensureIndexIsVisible(index);
      }
    });
    strengthAddButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //insert after current slot
        int index = strengthList.getSelectedIndex() + 1;
        String loc = browseSpecialMove();
        if (loc != null) {
          strengths.add(index, getSpecialMoveText(loc));
        }
        //select the added move
        strengthList.setSelectedIndex(index);
        strengthList.ensureIndexIsVisible(index);
      }
    });
    weaknessAddButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //insert after current slot
        int index = weaknessList.getSelectedIndex() + 1;
        String loc = browseSpecialMove();
        if (loc != null) {
          weaknesses.add(index, getSpecialMoveText(loc));
        }
        //select the added move
        weaknessList.setSelectedIndex(index);
        weaknessList.ensureIndexIsVisible(index);
      }
    });

    //remove buttons
    sMoveRemoveButton.addActionListener(GuiHelper.simpleRemoveListener(specialMoves, sMoveList)
    );
    strengthRemoveButton.addActionListener(GuiHelper.simpleRemoveListener(strengths, strengthList)
    );
    weaknessRemoveButton.addActionListener(GuiHelper.simpleRemoveListener(weaknesses, weaknessList)
    );

    // Configure the necessary Panels
    JPanel sMovePanel = new JPanel();
    sMovePanel.setBorder(BorderFactory.createTitledBorder(
            this.defaultEtchedBorder, "Special Move List"));
    JPanel strengthPanel = new JPanel();
    strengthPanel.setBorder(BorderFactory.createTitledBorder(
            this.defaultEtchedBorder, "Strength List"));
    JPanel weaknessPanel = new JPanel();
    weaknessPanel.setBorder(BorderFactory.createTitledBorder(
            this.defaultEtchedBorder, "Weakness List"));

    // Create Layout for Top Level Panel
    GroupLayout layout = GuiHelper.createGroupLayout(this.specialMovesPanel);

    // Configure Layouts for Second Level Panels
    GroupLayout sMoveLayout = GuiHelper.createGroupLayout(sMovePanel);
    GroupLayout strengthLayout = GuiHelper.createGroupLayout(strengthPanel);
    GroupLayout weaknessLayout = GuiHelper.createGroupLayout(weaknessPanel);

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

    // Configure the STRENGTH PANEL layout
    strengthLayout.setHorizontalGroup(strengthLayout.createSequentialGroup()
            .addComponent(strengthListScroller)
            .addGroup(strengthLayout.createParallelGroup()
                    .addComponent(strengthFindButton)
                    .addComponent(strengthAddButton)
                    .addComponent(strengthRemoveButton))
    );

    strengthLayout.setVerticalGroup(strengthLayout.createParallelGroup()
            .addComponent(strengthListScroller)
            .addGroup(strengthLayout.createSequentialGroup()
                    .addComponent(strengthFindButton)
                    .addComponent(strengthAddButton)
                    .addComponent(strengthRemoveButton))
    );

    // Configure the WEAKNESS PANEL layout
    weaknessLayout.setHorizontalGroup(weaknessLayout.createSequentialGroup()
            .addComponent(weaknessListScroller)
            .addGroup(weaknessLayout.createParallelGroup()
                    .addComponent(weaknessFindButton)
                    .addComponent(weaknessAddButton)
                    .addComponent(weaknessRemoveButton))
    );

    weaknessLayout.setVerticalGroup(weaknessLayout.createParallelGroup()
            .addComponent(weaknessListScroller)
            .addGroup(weaknessLayout.createSequentialGroup()
                    .addComponent(weaknessFindButton)
                    .addComponent(weaknessAddButton)
                    .addComponent(weaknessRemoveButton))
    );

    // Configure the SPECIAL MOVE PANEL layout
    layout.setHorizontalGroup(layout.createParallelGroup()
            .addComponent(sMovePanel)
            .addComponent(strengthPanel)
            .addComponent(weaknessPanel)
    );

    layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(sMovePanel)
            .addComponent(strengthPanel)
            .addComponent(weaknessPanel)
    );
  }

  private void createTacticsPanel() {
    this.aiLevel = new JSlider(0, 3, this.enemy.getAiLevel());
    this.aiLevel.setMajorTickSpacing(1);
    this.aiLevel.setPaintTicks(true);
    Hashtable<Integer, JLabel> aiLabels = new Hashtable<>();
    aiLabels.put(0, new JLabel("Low"));
    aiLabels.put(1, new JLabel("Medium"));
    aiLabels.put(2, new JLabel("High"));
    aiLabels.put(3, new JLabel("Very High"));
    this.aiLevel.setLabelTable(aiLabels);
    this.aiLevel.setPaintLabels(true);

    this.useRPGCodeTactics = new JCheckBox("Use RPGCode-guided tactics");
    this.useRPGCodeTactics.setSelected(this.enemy.useRPGCodeTatics());
    this.aiLevel.setEnabled(!(this.useRPGCodeTactics.isSelected()));

    final JLabel tacticsProgramLabel = new JLabel("Program to run");
    this.tacticsProgram = new JTextField(this.enemy.getTacticsFile());
    final JButton tacticsProgramFindButton = new JButton("Browse");
    tacticsProgramLabel.setEnabled(this.useRPGCodeTactics.isSelected());
    this.tacticsProgram.setEnabled(this.useRPGCodeTactics.isSelected());
    tacticsProgramFindButton.setEnabled(this.useRPGCodeTactics.isSelected());

    JPanel battleTacticsPanel = new JPanel();
    battleTacticsPanel.setBorder(BorderFactory.createTitledBorder(
            this.defaultEtchedBorder, "Battle Tactics"));

    JPanel aiLevelPanel = new JPanel();
    aiLevelPanel.setBorder(BorderFactory.createTitledBorder(
            this.defaultEtchedBorder, "Artificial Intelligence Level (Internal Algorithm)"));

        // Configure listeners
    //tactics program checkbox disable built-in ai level
    this.useRPGCodeTactics.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        aiLevel.setEnabled(!(useRPGCodeTactics.isSelected()));
        tacticsProgramLabel.setEnabled(useRPGCodeTactics.isSelected());
        tacticsProgram.setEnabled(useRPGCodeTactics.isSelected());
        tacticsProgramFindButton.setEnabled(useRPGCodeTactics.isSelected());
      }
    });

    //browse for tactics program
    tacticsProgramFindButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String loc = EditorFileManager.browseByTypeRelative(Program.class);
        if (loc != null) {
          tacticsProgram.setText(loc);
        }
      }
    });

    // Configure Layouts
    GroupLayout layout = GuiHelper.createGroupLayout(this.tacticsPanel);

    GroupLayout battleTacticsLayout = GuiHelper.createGroupLayout(battleTacticsPanel);
    GroupLayout aiLevelLayout = GuiHelper.createGroupLayout(aiLevelPanel);

    aiLevelLayout.setHorizontalGroup(aiLevelLayout.createSequentialGroup()
            .addComponent(this.aiLevel)
    );

    aiLevelLayout.setVerticalGroup(aiLevelLayout.createParallelGroup()
            .addComponent(this.aiLevel)
    );

    battleTacticsLayout.setHorizontalGroup(battleTacticsLayout.createParallelGroup()
            .addComponent(aiLevelPanel)
            .addComponent(this.useRPGCodeTactics)
            .addGroup(battleTacticsLayout.createSequentialGroup()
                    .addComponent(tacticsProgramLabel)
                    .addComponent(this.tacticsProgram)
                    .addComponent(tacticsProgramFindButton))
    );

    battleTacticsLayout.setVerticalGroup(battleTacticsLayout.createSequentialGroup()
            .addComponent(aiLevelPanel)
            .addComponent(this.useRPGCodeTactics)
            .addGroup(battleTacticsLayout.createParallelGroup()
                    .addComponent(tacticsProgramLabel)
                    .addComponent(this.tacticsProgram)
                    .addComponent(tacticsProgramFindButton))
    );

    battleTacticsLayout.linkSize(SwingConstants.VERTICAL,
            tacticsProgramLabel, this.tacticsProgram, tacticsProgramFindButton
    );

    layout.setHorizontalGroup(layout.createParallelGroup()
            .addComponent(battleTacticsPanel)
    );

    layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(battleTacticsPanel)
    );
  }

  private void createRewardsPanel() {
    JLabel experienceAwardedLabel = new JLabel("Experience Gained");
    this.experienceAwarded = new IntegerField((int)this.enemy.getExperienceAwarded());

    JLabel goldAwardedLabel = new JLabel("GP Earned");
    this.goldAwarded = new IntegerField((int)this.enemy.getGoldAwarded());

    JLabel victoryProgramLabel = new JLabel("Program to run upon defeating enemy");
    this.victoryProgram = new JTextField(this.enemy.getBeatEnemyProgram());
    JButton victoryProgramFindButton = new JButton("Browse");

    JPanel rewardsSubPanel = new JPanel();
    rewardsSubPanel.setBorder(BorderFactory.createTitledBorder(
            this.defaultEtchedBorder, "Rewards for Defeating Enemy"));

    // Configure listeners
    victoryProgramFindButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String loc = EditorFileManager.browseByTypeRelative(Program.class);
        if (loc != null) {
          victoryProgram.setText(loc);
        }
      }
    });

    // Configure Layouts
    GroupLayout layout = GuiHelper.createGroupLayout(this.rewardsPanel);

    GroupLayout rewardsPanelLayout = GuiHelper.createGroupLayout(rewardsSubPanel);

    rewardsPanelLayout.setHorizontalGroup(rewardsPanelLayout.createParallelGroup()
            .addGroup(rewardsPanelLayout.createSequentialGroup()
                    .addComponent(experienceAwardedLabel)
                    .addComponent(this.experienceAwarded))
            .addGroup(rewardsPanelLayout.createSequentialGroup()
                    .addComponent(goldAwardedLabel)
                    .addComponent(this.goldAwarded))
            .addComponent(victoryProgramLabel)
            .addGroup(rewardsPanelLayout.createSequentialGroup()
                    .addComponent(this.victoryProgram)
                    .addComponent(victoryProgramFindButton))
    );

    rewardsPanelLayout.setVerticalGroup(rewardsPanelLayout.createSequentialGroup()
            .addGroup(rewardsPanelLayout.createParallelGroup()
                    .addComponent(experienceAwardedLabel)
                    .addComponent(this.experienceAwarded))
            .addGroup(rewardsPanelLayout.createParallelGroup()
                    .addComponent(goldAwardedLabel)
                    .addComponent(this.goldAwarded))
            .addComponent(victoryProgramLabel)
            .addGroup(rewardsPanelLayout.createParallelGroup()
                    .addComponent(this.victoryProgram)
                    .addComponent(victoryProgramFindButton))
    );

    rewardsPanelLayout.linkSize(SwingConstants.HORIZONTAL,
            experienceAwardedLabel,
            goldAwardedLabel
    );
    rewardsPanelLayout.linkSize(SwingConstants.VERTICAL,
            experienceAwardedLabel, this.experienceAwarded,
            goldAwardedLabel, this.goldAwarded, victoryProgramLabel,
            this.victoryProgram, victoryProgramFindButton
    );

    layout.setHorizontalGroup(layout.createParallelGroup()
            .addComponent(rewardsSubPanel)
    );

    layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(rewardsSubPanel)
    );
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
    if(Paths.extension("/"+loc).contains("spc")) {
      File f = EditorFileManager.getPath(
          EditorFileManager.getTypeSubdirectory(SpecialMove.class)
              + File.separator + loc);
      if(f.canRead()) {
        try {
          AssetHandle handle = AssetManager.getInstance().deserialize(
              new AssetDescriptor(f.toURI()));
          return (SpecialMove)handle.getAsset();
        } catch(IOException | AssetException ex) {
          Logger.getLogger(CharacterEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
    return null;
  }
  // </editor-fold>
  
}

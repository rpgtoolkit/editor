/**
 * Copyright (c) 2015, rpgtoolkit.net <help@rpgtoolkit.net>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.rpgtoolkit.editor.editors;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import net.rpgtoolkit.common.assets.AssetDescriptor;
import net.rpgtoolkit.common.assets.Board;
import net.rpgtoolkit.common.assets.Player;
import net.rpgtoolkit.common.assets.Program;
import net.rpgtoolkit.common.assets.Project;
import net.rpgtoolkit.editor.ui.ToolkitEditorWindow;
import net.rpgtoolkit.editor.ui.resources.Icons;
import net.rpgtoolkit.editor.utilities.EditorFileManager;
import net.rpgtoolkit.editor.utilities.GuiHelper;

/**
 * Project File editor
 *
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class ProjectEditor extends ToolkitEditorWindow implements InternalFrameListener {

  private final Project project; // Project file we are altering

  // Tabs required by the menu
  private JPanel projectSettingsPanel;
  private JPanel codePanel;
  private JPanel graphicsPanel;

  // Components required for saving/loading data
  private final Border defaultEtchedBorder = BorderFactory.
          createEtchedBorder(EtchedBorder.LOWERED);

  //PROJECT SETTINGS
  private JTextField projectName;

  // STARTUP SETTINGS
  private JTextField initialBoard;
  private JTextField initialChar;

  // CODE SETTINGS
  private JTextField runTimeProgram;
  private JTextField startupProgram;
  private JTextField gameOverProgram;
  private JTextField runTimeKey;
  private JTextField menuKey;

  // GRAPHICS SETTINGS
  private JCheckBox showFPS;
  private JCheckBox drawBoardVectors;
  private JCheckBox fullScreen;
  private JRadioButton sixByFour;
  private JRadioButton eightBySix;
  private JRadioButton tenBySeven;
  private JRadioButton customRes;
  private JTextField customResWidth;
  private JTextField customResHeight;

  /*
   * *************************************************************************
   * Public Constructors
   * *************************************************************************
   */
  /**
   * Opens an existing project
   *
   * @param project Project file to open (.gam)
   */
  public ProjectEditor(Project project) {
    super(project.getGameTitle(), true, true, true, true, Icons.getIcon("project"));

    this.project = project;

    setSize(555, 530);
    constructWindow();
    setVisible(true);
  }

  /*
   * *************************************************************************
   * Public Methods
   * *************************************************************************
   */
  @Override
  public void save() throws Exception {
    if (sixByFour.isSelected()) {
      project.setResolutionWidth(640);
      project.setResolutionHeight(480);
      project.setMainResolution(0);
    } else if (eightBySix.isSelected()) {
      project.setResolutionWidth(800);
      project.setResolutionHeight(600);
      project.setMainResolution(1);
    } else if (tenBySeven.isSelected()) {
      project.setResolutionWidth(1024);
      project.setResolutionHeight(768);
      project.setMainResolution(2);
    } else {
      project.setResolutionWidth(Integer.parseInt(customResWidth.getText()));
      project.setResolutionHeight(Integer.parseInt(customResHeight.getText()));
      project.setMainResolution(3);
    }
    
    save(project);
  }

  /**
   *
   *
   * @param file
   * @throws java.lang.Exception
   */
  @Override
  public void saveAs(File file) throws Exception {
    project.setDescriptor(new AssetDescriptor(file.toURI()));
    this.setTitle("Editing Project - " + file.getName());
    save();
  }

  public void gracefulClose() {

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

  /*
   * *************************************************************************
   * Private Methods
   * *************************************************************************
   */
  /**
   * Builds the Swing interface
   */
  private void constructWindow() {
    this.addInternalFrameListener(this);

    // Builds the components needed to display the Project status.
    JTabbedPane tabPane = new JTabbedPane();

    this.projectSettingsPanel = new JPanel();
    this.codePanel = new JPanel();
    this.graphicsPanel = new JPanel();

    this.createProjectSettingsPanel();
    this.createCodePanel();
    this.createGraphicsPanel();

    tabPane.addTab("Project Settings", this.projectSettingsPanel);
    tabPane.addTab("RPG Code", this.codePanel);
    tabPane.addTab("Graphics", this.graphicsPanel);

    add(tabPane);
    pack();
  }

  private void createProjectSettingsPanel() {
    // Configure Class scope components
    projectName = new JTextField(this.project.getGameTitle());
    projectName.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void changedUpdate(DocumentEvent e) {
      }

      @Override
      public void insertUpdate(DocumentEvent e) {
        project.setGameTitle(projectName.getText());
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        project.setGameTitle(projectName.getText());
      }
    });


    // Configure function Scope Components
    JLabel projectNameLabel = new JLabel("Project Name");

    // Configure the necessary Panels
    JPanel projectInfoPanel = new JPanel();
    projectInfoPanel.setBorder(BorderFactory.createTitledBorder(
            this.defaultEtchedBorder, "Project Information"));

    GroupLayout layout = GuiHelper.createGroupLayout(projectSettingsPanel);
    GroupLayout projectInfoLayout = GuiHelper.createGroupLayout(projectInfoPanel);

    // Configure the PROJECT INFO PANEL layout
    projectInfoLayout.setHorizontalGroup(projectInfoLayout.createSequentialGroup()
            .addComponent(projectNameLabel)
            .addComponent(this.projectName)
    );

    projectInfoLayout.setVerticalGroup(projectInfoLayout.
            createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(projectNameLabel)
            .addComponent(this.projectName, GuiHelper.JTF_HEIGHT,
                    GuiHelper.JTF_HEIGHT, GuiHelper.JTF_HEIGHT)
    );
    
    initialBoard = new JTextField();
    initialBoard.setEditable(false);
    initialBoard.setText(project.getInitBoard());

    initialChar = new JTextField();
    initialChar.setEditable(false);
    initialChar.setText(project.getInitChar());
    
    JLabel initialBoardLabel = new JLabel("Initial Board");
    JButton initialBoardButton = new JButton("Browse");
    initialBoardButton.addActionListener((ActionEvent e) -> {
      File file = EditorFileManager.browseByType(Board.class);
      
      if (file != null) {
        String fileName = file.getName();
        initialBoard.setText(fileName);
        project.setInitBoard(fileName);
      }
    });
    
    JLabel initialCharLabel = new JLabel("Initial Character");
    JButton initialCharButton = new JButton("Browse");
    initialCharButton.addActionListener((ActionEvent e) -> {
      File file = EditorFileManager.browseByType(Player.class);
      
      if (file != null) {
        String fileName = file.getName();
        initialChar.setText(fileName);
        project.setInitChar(fileName);
      }
    });
    
    JLabel blankBoardNote = new JLabel("You may leave the initial board "
            + "blank if you wish");

    // Configure the necessary Panels
    JPanel conditionsPanel = new JPanel();
    conditionsPanel.setBorder(BorderFactory.createTitledBorder(
            defaultEtchedBorder, "Startup Settings"));

    GroupLayout conditionsLayout = GuiHelper.createGroupLayout(conditionsPanel);

    conditionsLayout.setHorizontalGroup(conditionsLayout.createParallelGroup()
            .addGroup(conditionsLayout.createSequentialGroup()
                    .addComponent(initialBoardLabel, 75, 75, 75)
                    .addComponent(initialBoard)
                    .addComponent(initialBoardButton))
            .addGroup(conditionsLayout.createSequentialGroup()
                    .addComponent(initialCharLabel)
                    .addComponent(initialChar)
                    .addComponent(initialCharButton))
            .addComponent(blankBoardNote)
    );

    conditionsLayout.linkSize(SwingConstants.HORIZONTAL, initialBoardLabel,
            initialCharLabel);
    conditionsLayout.linkSize(SwingConstants.VERTICAL, initialBoard,
            initialChar);

    conditionsLayout.setVerticalGroup(conditionsLayout.createSequentialGroup()
            .addGroup(conditionsLayout.createParallelGroup()
                    .addComponent(initialBoardLabel)
                    .addComponent(initialBoard, GuiHelper.JTF_HEIGHT,
                            GuiHelper.JTF_HEIGHT, GuiHelper.JTF_HEIGHT)
                    .addComponent(initialBoardButton))
            .addGroup(conditionsLayout.createParallelGroup()
                    .addComponent(initialCharLabel)
                    .addComponent(initialChar)
                    .addComponent(initialCharButton))
            .addComponent(blankBoardNote)
    );

    // Configure PROJECT SETTINGS PANEL layout
    layout.setHorizontalGroup(layout.createParallelGroup()
            .addComponent(projectInfoPanel, 515, 515, 515)
            .addComponent(conditionsPanel, 515, 515, 515)
    );

    layout.linkSize(SwingConstants.HORIZONTAL, projectInfoPanel, conditionsPanel);

    layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(projectInfoPanel)
            .addComponent(conditionsPanel)
    );
  }

  private void createCodePanel() {
    // Configure Class scope components
    runTimeProgram = new JTextField();
    runTimeProgram.setEditable(false);
    runTimeProgram.setText(project.getRunTime());

    startupProgram = new JTextField();
    startupProgram.setEditable(false);
    startupProgram.setText(project.getStartupPrg());

    gameOverProgram = new JTextField();
    gameOverProgram.setEditable(false);
    gameOverProgram.setText(project.getGameOverProgram());

    runTimeKey = new JTextField();
    runTimeKey.setText(String.valueOf(project.getRunKey()));
    runTimeKey.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void changedUpdate(DocumentEvent e) {
      }

      @Override
      public void insertUpdate(DocumentEvent e) {
        project.setRunKey(Integer.parseInt(runTimeKey.getText()));
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        project.setRunKey(Integer.parseInt(runTimeKey.getText()));
      }
    });

    menuKey = new JTextField();
    menuKey.setText(String.valueOf(project.getMenuKey()));
    menuKey.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void changedUpdate(DocumentEvent e) {
      }

      @Override
      public void insertUpdate(DocumentEvent e) {
        project.setMenuKey(Integer.parseInt(menuKey.getText()));
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        project.setMenuKey(Integer.parseInt(menuKey.getText()));
      }
    });

    // Configure Function scope components
    JLabel runTimeProgramLabel = new JLabel("Run Time Program");
    JLabel startupProgramLabel = new JLabel("Startup Program");
    JLabel gameOverProgramLabel = new JLabel("Game Over Program");
    
    JButton runTimeProgramButton = new JButton("Browse");
    runTimeProgramButton.addActionListener((ActionEvent e) -> {
      File file = EditorFileManager.browseByType(Program.class);

      if (file != null) {
        String fileName = file.getName();
        runTimeProgram.setText(fileName);
        project.setRunTime(fileName);
      }
    });
    
    JButton startupProgramButton = new JButton("Browse");
    startupProgramButton.addActionListener((ActionEvent e) -> {
      File file = EditorFileManager.browseByType(Program.class);

      if (file != null) {
        String fileName = file.getName();
        startupProgram.setText(fileName);
        project.setStartupPrg(fileName);
      }
    });
    
    JButton gameOverProgramButton = new JButton("Browse");
    gameOverProgramButton.addActionListener((ActionEvent e) -> {
      File file = EditorFileManager.browseByType(Program.class);

      if (file != null) {
        String fileName = file.getName();
        gameOverProgram.setText(file.getName());
        project.setGameOverProgram(fileName);
      }
    });
    
    JLabel runTimeKeyLabel = new JLabel("Run Time");
    JLabel menuKeyLabel = new JLabel("Display Menu");

    // Configure Panels
    JPanel programPanel = new JPanel();
    programPanel.setBorder(BorderFactory.createTitledBorder(
            this.defaultEtchedBorder, "Programs"));
    JPanel keysPanel = new JPanel();
    keysPanel.setBorder(BorderFactory.createTitledBorder(
            this.defaultEtchedBorder, "Keys"));

    // Configure Layouts
    GroupLayout layout = GuiHelper.createGroupLayout(this.codePanel);
    GroupLayout programPanelLayout = GuiHelper.createGroupLayout(programPanel);
    GroupLayout keysPanelLayout = GuiHelper.createGroupLayout(keysPanel);

    programPanelLayout.setHorizontalGroup(programPanelLayout.createParallelGroup()
            .addGroup(programPanelLayout.createSequentialGroup()
                    .addComponent(runTimeProgramLabel)
                    .addComponent(this.runTimeProgram)
                    .addComponent(runTimeProgramButton))
            .addGroup(programPanelLayout.createSequentialGroup()
                    .addComponent(startupProgramLabel)
                    .addComponent(this.startupProgram)
                    .addComponent(startupProgramButton))
            .addGroup(programPanelLayout.createSequentialGroup()
                    .addComponent(gameOverProgramLabel)
                    .addComponent(this.gameOverProgram)
                    .addComponent(gameOverProgramButton))
    );

    programPanelLayout.linkSize(SwingConstants.VERTICAL, this.gameOverProgram,
            this.startupProgram, this.runTimeProgram);
    programPanelLayout.linkSize(SwingConstants.HORIZONTAL,
            gameOverProgramLabel, startupProgramLabel, runTimeProgramLabel);

    programPanelLayout.setVerticalGroup(programPanelLayout.createSequentialGroup()
            .addGroup(programPanelLayout.createParallelGroup()
                    .addComponent(runTimeProgramLabel)
                    .addComponent(this.runTimeProgram, GuiHelper.JTF_HEIGHT,
                            GuiHelper.JTF_HEIGHT, GuiHelper.JTF_HEIGHT)
                    .addComponent(runTimeProgramButton))
            .addGroup(programPanelLayout.createParallelGroup()
                    .addComponent(startupProgramLabel)
                    .addComponent(this.startupProgram)
                    .addComponent(startupProgramButton))
            .addGroup(programPanelLayout.createParallelGroup()
                    .addComponent(gameOverProgramLabel)
                    .addComponent(this.gameOverProgram)
                    .addComponent(gameOverProgramButton))
    );

    keysPanelLayout.setHorizontalGroup(keysPanelLayout.createParallelGroup()
            .addGroup(keysPanelLayout.createSequentialGroup()
                    .addComponent(runTimeKeyLabel)
                    .addComponent(this.runTimeKey, 50, 50, 50))
            .addGroup(keysPanelLayout.createSequentialGroup()
                    .addComponent(menuKeyLabel)
                    .addComponent(this.menuKey))
            .addGroup(keysPanelLayout.createSequentialGroup())
    );

    keysPanelLayout.linkSize(SwingConstants.VERTICAL, this.runTimeKey,
            this.menuKey);
    keysPanelLayout.linkSize(SwingConstants.HORIZONTAL, this.runTimeKey,
            this.menuKey);
    keysPanelLayout.linkSize(SwingConstants.HORIZONTAL, runTimeKeyLabel,
            menuKeyLabel);

    keysPanelLayout.setVerticalGroup(keysPanelLayout.createSequentialGroup()
            .addGroup(keysPanelLayout.createParallelGroup()
                    .addComponent(runTimeKeyLabel)
                    .addComponent(this.runTimeKey, GuiHelper.JTF_HEIGHT,
                            GuiHelper.JTF_HEIGHT, GuiHelper.JTF_HEIGHT))
            .addGroup(keysPanelLayout.createParallelGroup()
                    .addComponent(menuKeyLabel)
                    .addComponent(this.menuKey))
            .addGroup(keysPanelLayout.createParallelGroup())
    );

    layout.setHorizontalGroup(layout.createParallelGroup()
            .addComponent(programPanel, 515, 515, 515)
            .addComponent(keysPanel)
    );

    layout.linkSize(SwingConstants.HORIZONTAL, programPanel, keysPanel);

    layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(programPanel)
            .addComponent(keysPanel)
    );

  }

  private void createGraphicsPanel() {
    fullScreen = new JCheckBox("Full Screen Mode?");
    fullScreen.setSelected(project.getFullscreenMode());
    fullScreen.addActionListener((ActionEvent e) -> {
      project.setExtendToFullScreen(fullScreen.isSelected());
    });

    sixByFour = new JRadioButton("640 x 480");
    eightBySix = new JRadioButton("800 x 600");
    tenBySeven = new JRadioButton("1024 x 768");
    customRes = new JRadioButton("Custom");
    customResWidth = new JTextField(Long.toString(this.project.getResolutionWidth()));
    customResHeight = new JTextField(Long.toString(this.project.getResolutionHeight()));

    showFPS = new JCheckBox();
    showFPS.setSelected(project.getDisplayFPSInTitle());
    showFPS.addActionListener((ActionEvent e) -> {
      project.setDisplayFPSInTitle(showFPS.isSelected());
    });

    drawBoardVectors = new JCheckBox();
    drawBoardVectors.setSelected(project.getDrawVectors());
    drawBoardVectors.addActionListener((ActionEvent e) -> {
      project.setDrawVectors(drawBoardVectors.isSelected());
    });
    
    ButtonGroup resolutionGroup = new ButtonGroup();
    resolutionGroup.add(this.sixByFour);
    resolutionGroup.add(this.eightBySix);
    resolutionGroup.add(this.tenBySeven);
    resolutionGroup.add(this.customRes);

    switch (project.getResolutionMode()) {
      case 0:
        this.sixByFour.setSelected(true);
        break;
      case 1:
        this.eightBySix.setSelected(true);
        break;
      case 2:
        this.tenBySeven.setSelected(true);
        break;
      case 3:
        this.customRes.setSelected(true);
        break;
    }

    JLabel customResWarningLabel = new JLabel("Please note that not all "
            + "video cards support all resolutions");
    JLabel customResX = new JLabel("x");
    JLabel customResY = new JLabel("y");
    JLabel showFPSLabel = new JLabel("Show FPS?");
    JLabel drawBoardVectorsLabel = new JLabel("Draw Board Vectors?");

    JPanel screenPanel = new JPanel();
    screenPanel.setBorder(BorderFactory.createTitledBorder(
            this.defaultEtchedBorder, "Screen"));
    JPanel miscPanel = new JPanel();
    miscPanel.setBorder(BorderFactory.createTitledBorder(
            this.defaultEtchedBorder, "Miscellaneous"));

    JPanel resolutionPanel = new JPanel();
    resolutionPanel.setBorder(BorderFactory.createTitledBorder(
            this.defaultEtchedBorder, "Resolution"));
    JPanel customResolutionPanel = new JPanel();
    customResolutionPanel.setBorder(BorderFactory.createTitledBorder(
            this.defaultEtchedBorder, "Custom Resolution"));

    // Create Layout for Top Level Panel
    GroupLayout layout = GuiHelper.createGroupLayout(this.graphicsPanel);
    
    // Configure Layouts for Second Level Panels
    GroupLayout resolutionLayout = GuiHelper.createGroupLayout(resolutionPanel);
    GroupLayout screenLayout = GuiHelper.createGroupLayout(screenPanel);
    GroupLayout miscLayout = GuiHelper.createGroupLayout(miscPanel);
    GroupLayout customResLayout = GuiHelper.createGroupLayout(customResolutionPanel);

    resolutionLayout.setHorizontalGroup(resolutionLayout.createParallelGroup()
            .addComponent(this.sixByFour)
            .addComponent(this.eightBySix)
            .addComponent(this.tenBySeven)
            .addComponent(this.customRes)
            .addComponent(this.fullScreen)
    );

    resolutionLayout.setVerticalGroup(resolutionLayout.createSequentialGroup()
            .addComponent(this.sixByFour)
            .addComponent(this.eightBySix)
            .addComponent(this.tenBySeven)
            .addComponent(this.customRes)
            .addComponent(this.fullScreen)
    );

    screenLayout.setHorizontalGroup(screenLayout.createParallelGroup()
            .addGroup(screenLayout.createSequentialGroup()
                    .addComponent(resolutionPanel, 150,
                            GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(customResolutionPanel)
    );

    screenLayout.linkSize(SwingConstants.VERTICAL, resolutionPanel);

    screenLayout.setVerticalGroup(screenLayout.createSequentialGroup()
            .addGroup(screenLayout.createParallelGroup()
                    .addComponent(resolutionPanel))
            .addComponent(customResolutionPanel)
            .addGap(15)
    );

    customResLayout.setHorizontalGroup(customResLayout.createParallelGroup()
            .addComponent(customResWarningLabel)
            .addGroup(customResLayout.createSequentialGroup()
                    .addComponent(customResX)
                    .addComponent(this.customResWidth)
                    .addComponent(customResY)
                    .addComponent(this.customResHeight))
    );

    customResLayout.linkSize(SwingConstants.VERTICAL, this.customResWidth,
            this.customResHeight);

    customResLayout.setVerticalGroup(customResLayout.createSequentialGroup()
            .addComponent(customResWarningLabel)
            .addGroup(customResLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(customResX)
                    .addComponent(this.customResWidth, GuiHelper.JTF_HEIGHT,
                            GuiHelper.JTF_HEIGHT, GuiHelper.JTF_HEIGHT)
                    .addComponent(customResY)
                    .addComponent(this.customResHeight))
    );

    miscLayout.setHorizontalGroup(miscLayout.createParallelGroup()
            .addGroup(miscLayout.createSequentialGroup()
                    .addComponent(this.showFPS)
                    .addComponent(showFPSLabel))
            .addGroup(miscLayout.createSequentialGroup()
                    .addComponent(this.drawBoardVectors)
                    .addComponent(drawBoardVectorsLabel))
    );

    miscLayout.setVerticalGroup(miscLayout.createSequentialGroup()
            .addGroup(miscLayout.createParallelGroup()
                    .addComponent(this.showFPS)
                    .addComponent(showFPSLabel))
            .addGroup(miscLayout.createParallelGroup()
                    .addComponent(this.drawBoardVectors)
                    .addComponent(drawBoardVectorsLabel))
    );

    layout.setHorizontalGroup(layout.createParallelGroup()
            .addComponent(screenPanel, 515, 515, 515)
            .addComponent(miscPanel)
    );

    layout.linkSize(SwingConstants.HORIZONTAL, screenPanel, miscPanel);

    layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(screenPanel)
            .addComponent(miscPanel)
    );
  }
  
}

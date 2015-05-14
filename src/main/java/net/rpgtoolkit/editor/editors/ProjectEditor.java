package net.rpgtoolkit.editor.editors;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import net.rpgtoolkit.common.Background;
import net.rpgtoolkit.common.Project;
import net.rpgtoolkit.editor.ui.MainWindow;
import net.rpgtoolkit.editor.ui.ToolkitEditorWindow;
import net.rpgtoolkit.editor.ui.Gui;

/**
 * Project File editor
 *
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class ProjectEditor extends ToolkitEditorWindow implements InternalFrameListener
{

    // Key Values for project settings

    private final int KEY_LEFT_UP = 0;
    private final int KEY_UP = 1;
    private final int KEY_RIGHT_UP = 2;
    private final int KEY_LEFT = 3;
    private final int KEY_RIGHT = 4;
    private final int KEY_LEFT_DOWN = 5;
    private final int KEY_DOWN = 6;
    private final int KEY_RIGHT_DOWN = 7;

    private MainWindow parent;

    private JFileChooser openProject;
    private Background testObject;
    private Project project; // Project file we are altering

    // Tabs required by the menu
    private JPanel projectSettingsPanel;
    private JPanel startupInfoPanel;
    private JPanel codePanel;
    private JPanel fightingPanel;
    private JPanel graphicsPanel;

    // Components required for saving/loading data
    private final Border defaultEtchedBorder = BorderFactory.
            createEtchedBorder(EtchedBorder.LOWERED);

    //PROJECT SETTINGS
    private JTextField projectName;
    private JCheckBox enableJoystick;
    private JTextField cursorMoveSound;
    private JTextField cursorSelectSound;
    private JTextField cursorCancelSound;
    private JCheckBox useKeyboard;
    private JCheckBox useMouse;
    private JCheckBox allowDiagonalMove;
    private JTextField movementKeys[];

    // STARTUP SETTINGS
    private JTextField initialBoard;
    private JTextField initialChar;
    private JSlider charSpeed;
    private JRadioButton pixelMovement;
    private JRadioButton tileMovement;
    private JCheckBox pushInPixels;
    private JComboBox pathfinding;

    // CODE SETTINGS
    private JTextField runTimeProgram;
    private JTextField startupProgram;
    private JTextField gameOverProgram;
    private JTextField runTimeKey;
    private JTextField menuKey;
    private JTextField generalKey;

    // FIGHTING SETTINGS
    private JCheckBox enableFight;

    // GRAPHICS SETTINGS
    private JCheckBox showFPS;
    private JCheckBox drawBoardVectors;
    private JCheckBox drawSpriteVectors;
    private JCheckBox drawActivePlayerPath;
    private JCheckBox drawActivePlayerDestination;
    private JRadioButton sixteenBit;
    private JRadioButton twentyFourBit;
    private JRadioButton thirtyTwoBit;
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
     * Create a new blank Project
     * <p/>
     * May in the future load up a wizard to help users create a new project
     */
    public ProjectEditor()
    {
        super("New Project", true, true, true, true);

        this.project = new Project();
        this.setVisible(true);
    }

    /**
     * Opens an existing project
     *
     * @param fileName Project file to open (.gam)
     */
    public ProjectEditor(Project fileName)
    {
        super(fileName.getGameTitle(), true, true, true, true);

        this.project = fileName;

        this.setSize(555, 530);
        this.constructWindow();
        this.setVisible(true);
    }

    /*
     * *************************************************************************
     * Public Methods
     * *************************************************************************
     */
    @Override
    public boolean save()
    {
        return project.save();
    }

    public void gracefulClose()
    {

    }

    public void setWindowParent(MainWindow parent)
    {
        this.parent = parent;
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e)
    {

    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e)
    {

    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e)
    {
        this.gracefulClose();
    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e)
    {

    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e)
    {

    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e)
    {

    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e)
    {

    }

    /*
     * *************************************************************************
     * Private Methods
     * *************************************************************************
     */
    /**
     * Builds the Swing interface
     */
    private void constructWindow()
    {
        this.addInternalFrameListener(this);
        
        // Builds the components needed to display the Project status.
        JTabbedPane tabPane = new JTabbedPane();

        this.projectSettingsPanel = new JPanel();
        this.startupInfoPanel = new JPanel();
        this.codePanel = new JPanel();
        this.fightingPanel = new JPanel();
        this.graphicsPanel = new JPanel();

        this.createProjectSettingsPanel();
        this.createStartupInfoPanel();
        this.createCodePanel();
        this.createFightPanel();
        this.createGraphicsPanel();

        tabPane.addTab("Project Settings", this.projectSettingsPanel);
        tabPane.addTab("Startup Info", this.startupInfoPanel);
        tabPane.addTab("RPG Code", this.codePanel);
        tabPane.addTab("Battle System", this.fightingPanel);
        tabPane.addTab("Graphics", this.graphicsPanel);

        this.add(tabPane);
        // this.setJMenuBar(new ProjectEditorMenu(this.parent));
    }

    private void createProjectSettingsPanel()
    {
        // Configure Class scope components
        this.projectName = new JTextField(this.project.getGameTitle());
        this.enableJoystick = new JCheckBox();
        this.enableJoystick.setEnabled(this.project.getJoystickStatus());
        this.cursorMoveSound = new JTextField(this.project.getCursorMoveSound());
        this.cursorSelectSound = new JTextField(this.project.getCursorSelectSound());
        this.cursorCancelSound = new JTextField(this.project.getCursorCancelSound());
        this.useKeyboard = new JCheckBox();
        this.useMouse = new JCheckBox();
        this.allowDiagonalMove = new JCheckBox();
        
        this.movementKeys = new JTextField[8];
        for (int i = 0; i < this.movementKeys.length; i++)
        {
            movementKeys[i] = new JTextField();
        }

        // Configure function Scope Components
        JLabel projectNameLabel = new JLabel("Project Name");
        JLabel enableJoystickLabel = new JLabel("Enable Joystick?");
        JLabel cursorMoveSoundLabel = new JLabel("Move");
        JLabel cursorSelectSoundLabel = new JLabel("Select");
        JLabel cursorCancelSoundLabel = new JLabel("Cancel");
        JButton cursorMoveSoundButton = new JButton("Browse");
        JButton cursorSelectSoundButton = new JButton("Browse");
        JButton cursorCancelSoundButton = new JButton("Browse");
        JLabel useKeyboardLabel = new JLabel("Use Keyboard?");
        JLabel useMouseLabel = new JLabel("Use Mouse?");
        JLabel allowDiagonalMoveLabel = new JLabel("Allow Diagonal Movement?");

        // Configure the necessary Panels
        JPanel projectInfoPanel = new JPanel();
        projectInfoPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Project Information"));
        JPanel cursorSoundsPanel = new JPanel();
        cursorSoundsPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Cursor Sounds"));
        JPanel controlsPanel = new JPanel();
        controlsPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Control Options"));
        JPanel keysPanel = new JPanel();
        keysPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Change Keys"));

        GroupLayout layout = Gui.createGroupLayout(this.projectSettingsPanel);
        GroupLayout projectInfoLayout = Gui.createGroupLayout(projectInfoPanel);
        GroupLayout cursorSoundsLayout = Gui.createGroupLayout(cursorSoundsPanel);
        GroupLayout controlsLayout = Gui.createGroupLayout(controlsPanel);
        GroupLayout keysLayout = Gui.createGroupLayout(keysPanel);

        // Configure the PROJECT INFO PANEL layout
        projectInfoLayout.setHorizontalGroup(projectInfoLayout.createSequentialGroup()
                .addComponent(projectNameLabel)
                .addComponent(this.projectName)
        );

        projectInfoLayout.setVerticalGroup(projectInfoLayout.
                createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(projectNameLabel)
                .addComponent(this.projectName, Gui.JTF_HEIGHT, 
                        Gui.JTF_HEIGHT, Gui.JTF_HEIGHT)
        );

        // Configure the CURSOR SOUNDS PANEL layout
        cursorSoundsLayout.setHorizontalGroup(cursorSoundsLayout.createParallelGroup()
                .addGroup(cursorSoundsLayout.createSequentialGroup()
                        .addComponent(cursorMoveSoundLabel, 50, 50, 50)
                        .addComponent(this.cursorMoveSound)
                        .addComponent(cursorMoveSoundButton))
                .addGroup(cursorSoundsLayout.createSequentialGroup()
                        .addComponent(cursorSelectSoundLabel)
                        .addComponent(this.cursorSelectSound)
                        .addComponent(cursorSelectSoundButton))
                .addGroup(cursorSoundsLayout.createSequentialGroup()
                        .addComponent(cursorCancelSoundLabel)
                        .addComponent(this.cursorCancelSound)
                        .addComponent(cursorCancelSoundButton))
        );

        cursorSoundsLayout.linkSize(SwingConstants.HORIZONTAL, 
                cursorMoveSoundLabel, 
                cursorSelectSoundLabel, 
                cursorCancelSoundLabel);
        cursorSoundsLayout.linkSize(SwingConstants.VERTICAL, 
                this.cursorMoveSound, 
                this.cursorCancelSound, 
                this.cursorSelectSound);

        cursorSoundsLayout.setVerticalGroup(cursorSoundsLayout.createSequentialGroup()
                .addGroup(cursorSoundsLayout.createParallelGroup()
                        .addComponent(cursorMoveSoundLabel)
                        .addComponent(this.cursorMoveSound, Gui.JTF_HEIGHT, 
                                Gui.JTF_HEIGHT, Gui.JTF_HEIGHT)
                        .addComponent(cursorMoveSoundButton))
                .addGroup(cursorSoundsLayout.createParallelGroup()
                        .addComponent(cursorSelectSoundLabel)
                        .addComponent(this.cursorSelectSound)
                        .addComponent(cursorSelectSoundButton))
                .addGroup(cursorSoundsLayout.createParallelGroup()
                        .addComponent(cursorCancelSoundLabel)
                        .addComponent(this.cursorCancelSound)
                        .addComponent(cursorCancelSoundButton))
        );

        // Configure CONTROL SETTINGS PANEL layout
        controlsLayout.setHorizontalGroup(controlsLayout.createParallelGroup()
                .addGroup(controlsLayout.createSequentialGroup()
                        .addComponent(this.useKeyboard)
                        .addComponent(useKeyboardLabel))
                .addGroup(controlsLayout.createSequentialGroup()
                        .addComponent(this.useMouse)
                        .addComponent(useMouseLabel))
                .addGroup(controlsLayout.createSequentialGroup()
                        .addComponent(this.allowDiagonalMove)
                        .addComponent(allowDiagonalMoveLabel))
                .addGroup(controlsLayout.createSequentialGroup()
                        .addComponent(this.enableJoystick)
                        .addComponent(enableJoystickLabel))
        );

        controlsLayout.setVerticalGroup(controlsLayout.createSequentialGroup()
                .addGroup(controlsLayout.createParallelGroup()
                        .addComponent(this.useKeyboard)
                        .addComponent(useKeyboardLabel))
                .addGroup(controlsLayout.createParallelGroup()
                        .addComponent(this.useMouse)
                        .addComponent(useMouseLabel))
                .addGroup(controlsLayout.createParallelGroup()
                        .addComponent(this.allowDiagonalMove)
                        .addComponent(allowDiagonalMoveLabel))
                .addGroup(controlsLayout.createParallelGroup()
                        .addComponent(this.enableJoystick)
                        .addComponent(enableJoystickLabel))
        );

        // Configure KEY REBINDING PANEL layout
        keysLayout.setHorizontalGroup(keysLayout.createParallelGroup()
                .addGroup(keysLayout.createSequentialGroup()
                        .addComponent(this.movementKeys[0], 40, 40, 40)
                        .addComponent(this.movementKeys[1])
                        .addComponent(this.movementKeys[2]))
                .addGroup(keysLayout.createSequentialGroup()
                        .addComponent(this.movementKeys[3])
                        .addGap(52)
                        .addComponent(this.movementKeys[4]))
                .addGroup(keysLayout.createSequentialGroup()
                        .addComponent(this.movementKeys[5])
                        .addComponent(this.movementKeys[6])
                        .addComponent(this.movementKeys[7]))
        );

        keysLayout.linkSize(this.movementKeys);

        keysLayout.setVerticalGroup(keysLayout.createSequentialGroup()
                .addGroup(keysLayout.createParallelGroup()
                        .addComponent(this.movementKeys[0], Gui.JTF_HEIGHT, 
                                Gui.JTF_HEIGHT, Gui.JTF_HEIGHT)
                        .addComponent(this.movementKeys[1])
                        .addComponent(this.movementKeys[2]))
                .addGroup(keysLayout.createParallelGroup()
                        .addComponent(this.movementKeys[3])
                        .addComponent(this.movementKeys[4]))
                .addGroup(keysLayout.createParallelGroup()
                        .addComponent(this.movementKeys[5])
                        .addComponent(this.movementKeys[6])
                        .addComponent(this.movementKeys[7]))
        );

        // Configure PROJECT SETTINGS PANEL layout
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(projectInfoPanel, 515, 515, 515)
                .addComponent(cursorSoundsPanel)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(controlsPanel, 255, 255, 255)
                        .addComponent(keysPanel))
        );

        layout.linkSize(SwingConstants.HORIZONTAL, projectInfoPanel, cursorSoundsPanel);
        layout.linkSize(SwingConstants.HORIZONTAL, controlsPanel, keysPanel);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(projectInfoPanel)
                .addComponent(cursorSoundsPanel)
                .addGroup(layout.createParallelGroup()
                        .addComponent(controlsPanel)
                        .addComponent(keysPanel))
        );
    }

    private void createStartupInfoPanel()
    {
        this.initialBoard = new JTextField();
        this.initialChar = new JTextField();
        this.charSpeed = new JSlider();
        this.charSpeed.setMaximum(3);
        this.charSpeed.setMinimum(-3);
        this.charSpeed.setMajorTickSpacing(1);
        this.charSpeed.setMinorTickSpacing(1);
        this.charSpeed.setPaintLabels(true);
        this.charSpeed.setValue(0);
        this.pixelMovement = new JRadioButton();
        this.tileMovement = new JRadioButton();
        this.pushInPixels = new JCheckBox();
        this.pathfinding = new JComboBox();
        this.pathfinding.addItem("Axial");
        this.pathfinding.addItem("Diagonal");
        this.pathfinding.addItem("Vector");
        this.pathfinding.setEditable(false);
        this.pathfinding.setSelectedIndex(2);

        JLabel initialBoardLabel = new JLabel("Initial Board");
        JButton initialBoardButton = new JButton("Browse");
        JLabel initialCharLabel = new JLabel("Initial Character");
        JButton initialCharButton = new JButton("Browse");
        JLabel blankBoardNote = new JLabel("You may leave the initial board "
                + "blank if you wish");
        JLabel charSpeedLabel = new JLabel("Adjust game speed");
        JLabel charSpeedNote = new JLabel("This is a secondary option, leave at "
                + "zero for default character speeds");
        JLabel movementLabel = new JLabel("Movement Style");
        JLabel pixelMovementLabel = new JLabel("Per Pixel");
        JLabel tileMovementLabel = new JLabel("Per Tile");
        JLabel pushInPixelsLabel = new JLabel("Push[] in pixel increments");
        JButton mouseCursorButton = new JButton("Mouse Cursor");
        JLabel pathFindingLabel = new JLabel("Default Path Finding Mode");

        // Configure the necessary Panels
        JPanel conditionsPanel = new JPanel();
        conditionsPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Startup Settings"));
        JPanel miscPanel = new JPanel();
        miscPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Miscellaneous Settings"));

        GroupLayout layout = Gui.createGroupLayout(this.startupInfoPanel);
        GroupLayout conditionsLayout = Gui.createGroupLayout(conditionsPanel);
        GroupLayout miscLayout = Gui.createGroupLayout(miscPanel);

        conditionsLayout.setHorizontalGroup(conditionsLayout.createParallelGroup()
                .addGroup(conditionsLayout.createSequentialGroup()
                        .addComponent(initialBoardLabel, 75, 75, 75)
                        .addComponent(this.initialBoard)
                        .addComponent(initialBoardButton))
                .addGroup(conditionsLayout.createSequentialGroup()
                        .addComponent(initialCharLabel)
                        .addComponent(this.initialChar)
                        .addComponent(initialCharButton))
                .addComponent(blankBoardNote)
        );

        conditionsLayout.linkSize(SwingConstants.HORIZONTAL, initialBoardLabel, 
                initialCharLabel);
        conditionsLayout.linkSize(SwingConstants.VERTICAL, this.initialBoard, 
                this.initialChar);

        conditionsLayout.setVerticalGroup(conditionsLayout.createSequentialGroup()
                .addGroup(conditionsLayout.createParallelGroup()
                        .addComponent(initialBoardLabel)
                        .addComponent(this.initialBoard, Gui.JTF_HEIGHT, 
                                Gui.JTF_HEIGHT, Gui.JTF_HEIGHT)
                        .addComponent(initialBoardButton))
                .addGroup(conditionsLayout.createParallelGroup()
                        .addComponent(initialCharLabel)
                        .addComponent(this.initialChar)
                        .addComponent(initialCharButton))
                .addComponent(blankBoardNote)
        );

        miscLayout.setHorizontalGroup(miscLayout.createParallelGroup()
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(charSpeedLabel)
                        .addComponent(this.charSpeed))
                .addComponent(charSpeedNote)
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(movementLabel)
                        .addComponent(this.pixelMovement)
                        .addComponent(pixelMovementLabel)
                        .addComponent(this.tileMovement)
                        .addComponent(tileMovementLabel))
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(this.pushInPixels)
                        .addComponent(pushInPixelsLabel))
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(mouseCursorButton)
                        .addComponent(pathFindingLabel)
                        .addComponent(this.pathfinding))
        );

        miscLayout.setVerticalGroup(miscLayout.createSequentialGroup()
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(charSpeedLabel)
                        .addComponent(this.charSpeed))
                .addComponent(charSpeedNote)
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(movementLabel)
                        .addComponent(this.pixelMovement)
                        .addComponent(pixelMovementLabel)
                        .addComponent(this.tileMovement)
                        .addComponent(tileMovementLabel))
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(this.pushInPixels)
                        .addComponent(pushInPixelsLabel))
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(mouseCursorButton)
                        .addComponent(pathFindingLabel)
                        .addComponent(this.pathfinding, 22, 22, 22))
        );

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(conditionsPanel, 515, 515, 515)
                .addComponent(miscPanel)
        );

        layout.linkSize(SwingConstants.HORIZONTAL, conditionsPanel, miscPanel);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(conditionsPanel)
                .addComponent(miscPanel)
        );
    }

    private void createCodePanel()
    {
        // Configure Class scope components
        this.runTimeProgram = new JTextField();
        this.startupProgram = new JTextField();
        this.gameOverProgram = new JTextField();
        this.runTimeKey = new JTextField();
        this.menuKey = new JTextField();
        this.generalKey = new JTextField();

        // Configure Function scope components
        JLabel runTimeProgramLabel = new JLabel("Run Time Program");
        JLabel startupProgramLabel = new JLabel("Startup Program");
        JLabel gameOverProgramLabel = new JLabel("Game Over Program");
        JButton runTimeProgramButton = new JButton("Browse");
        JButton startupProgramButton = new JButton("Browse");
        JButton gameOverProgramButton = new JButton("Browse");
        JLabel runTimeKeyLabel = new JLabel("Run Time");
        JLabel menuKeyLabel = new JLabel("Display Menu");
        JLabel generalKeyLabel = new JLabel("General Key");
        JButton moreKeysButton = new JButton("More");

        // Configure Panels
        JPanel programPanel = new JPanel();
        programPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Programs"));
        JPanel keysPanel = new JPanel();
        keysPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Keys"));

        // Configure Layouts
        GroupLayout layout = Gui.createGroupLayout(this.codePanel);
        GroupLayout programPanelLayout = Gui.createGroupLayout(programPanel);
        GroupLayout keysPanelLayout = Gui.createGroupLayout(keysPanel);

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
                        .addComponent(this.runTimeProgram, Gui.JTF_HEIGHT, 
                                Gui.JTF_HEIGHT, Gui.JTF_HEIGHT)
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
                .addGroup(keysPanelLayout.createSequentialGroup()
                        .addComponent(generalKeyLabel)
                        .addComponent(this.generalKey))
                .addComponent(moreKeysButton)
        );

        keysPanelLayout.linkSize(SwingConstants.VERTICAL, this.runTimeKey, 
                this.menuKey, this.generalKey);
        keysPanelLayout.linkSize(SwingConstants.HORIZONTAL, this.runTimeKey, 
                this.menuKey, this.generalKey);
        keysPanelLayout.linkSize(SwingConstants.HORIZONTAL, runTimeKeyLabel, 
                menuKeyLabel, generalKeyLabel);

        keysPanelLayout.setVerticalGroup(keysPanelLayout.createSequentialGroup()
                .addGroup(keysPanelLayout.createParallelGroup()
                        .addComponent(runTimeKeyLabel)
                        .addComponent(this.runTimeKey, Gui.JTF_HEIGHT, 
                                Gui.JTF_HEIGHT, Gui.JTF_HEIGHT))
                .addGroup(keysPanelLayout.createParallelGroup()
                        .addComponent(menuKeyLabel)
                        .addComponent(this.menuKey))
                .addGroup(keysPanelLayout.createParallelGroup()
                        .addComponent(generalKeyLabel)
                        .addComponent(this.generalKey))
                .addComponent(moreKeysButton)
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

    private void createFightPanel()
    {
        this.enableFight = new JCheckBox();

        JLabel enableFightLabel = new JLabel("Enable Battle System?");
        JButton configureFight = new JButton("Configure");

        JPanel fightControlPanel = new JPanel();
        fightControlPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Configuration"));

        // Configure Layouts
        GroupLayout layout = Gui.createGroupLayout(this.fightingPanel);
        GroupLayout fightControlPanelLayout = Gui.createGroupLayout(fightControlPanel);

        fightControlPanelLayout.setHorizontalGroup(fightControlPanelLayout.createParallelGroup()
                .addGroup(fightControlPanelLayout.createSequentialGroup()
                        .addComponent(this.enableFight)
                        .addComponent(enableFightLabel))
                .addComponent(configureFight)
        );

        fightControlPanelLayout.setVerticalGroup(fightControlPanelLayout.createSequentialGroup()
                .addGroup(fightControlPanelLayout.createParallelGroup()
                        .addComponent(this.enableFight)
                        .addComponent(enableFightLabel))
                .addComponent(configureFight)
        );

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(fightControlPanel, 515, 515, 515)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(fightControlPanel)
        );
    }

    private void createGraphicsPanel()
    {

        this.sixteenBit = new JRadioButton("16 bit");
        this.twentyFourBit = new JRadioButton("24 bit");
        this.thirtyTwoBit = new JRadioButton("32 bit");
        this.fullScreen = new JCheckBox("Full Screen Mode?");
        this.fullScreen.setSelected(this.project.getFullscreenMode());
        this.sixByFour = new JRadioButton("640 x 480");
        this.eightBySix = new JRadioButton("800 x 600");
        this.tenBySeven = new JRadioButton("1024 x 768");
        this.customRes = new JRadioButton("Custom");
        this.customResWidth = new JTextField(Long.toString(this.project.getResolutionWidth()));
        this.customResHeight = new JTextField(Long.toString(this.project.getResolutionHeight()));
        this.showFPS = new JCheckBox();
        this.drawBoardVectors = new JCheckBox();
        this.drawSpriteVectors = new JCheckBox();
        this.drawActivePlayerPath = new JCheckBox();
        this.drawActivePlayerDestination = new JCheckBox();

        ButtonGroup depthGroup = new ButtonGroup();
        depthGroup.add(this.sixteenBit);
        depthGroup.add(this.twentyFourBit);
        depthGroup.add(this.thirtyTwoBit);

        switch (this.project.getColourDepth())
        {
            case 0:
                this.sixteenBit.setSelected(true);
                break;
            case 1:
                this.twentyFourBit.setSelected(true);
                break;
            case 2:
                this.thirtyTwoBit.setSelected(true);
                break;
        }

        ButtonGroup resolutionGroup = new ButtonGroup();
        resolutionGroup.add(this.sixByFour);
        resolutionGroup.add(this.eightBySix);
        resolutionGroup.add(this.tenBySeven);
        resolutionGroup.add(this.customRes);

        switch (this.project.getResolutionMode())
        {
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
        JLabel drawSpriteVectorsLabel = new JLabel("Draw Sprite Vectors?");
        JLabel drawActivePlayerPathLabel = new JLabel("Draw Active Player Path?");
        JLabel drawActivePlayerDestinationLabel = new JLabel("Draw Active Player Destination");

        JPanel screenPanel = new JPanel();
        screenPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Screen"));
        JPanel miscPanel = new JPanel();
        miscPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Miscellaneous"));

        JPanel colorDepthPanel = new JPanel();
        colorDepthPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Color Depth"));
        JPanel resolutionPanel = new JPanel();
        resolutionPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Resolution"));
        JPanel customResolutionPanel = new JPanel();
        customResolutionPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Custom Resolution"));

        // Create Layout for Top Level Panel
        GroupLayout layout = Gui.createGroupLayout(this.graphicsPanel);
        // Configure Layouts for Second Level Panels
        GroupLayout colorDepthLayout = Gui.createGroupLayout(colorDepthPanel);
        GroupLayout resolutionLayout = Gui.createGroupLayout(resolutionPanel);
        GroupLayout screenLayout = Gui.createGroupLayout(screenPanel);
        GroupLayout miscLayout = Gui.createGroupLayout(miscPanel);
        GroupLayout customResLayout = Gui.createGroupLayout(customResolutionPanel);

        colorDepthLayout.setHorizontalGroup(colorDepthLayout.createParallelGroup()
                .addComponent(this.sixteenBit)
                .addComponent(this.twentyFourBit)
                .addComponent(this.thirtyTwoBit)
        );

        colorDepthLayout.setVerticalGroup(colorDepthLayout.createSequentialGroup()
                .addComponent(this.sixteenBit)
                .addComponent(this.twentyFourBit)
                .addComponent(this.thirtyTwoBit)
        );

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
                        .addComponent(colorDepthPanel, 150, 
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(resolutionPanel, 150, 
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addComponent(customResolutionPanel)
        );

        screenLayout.linkSize(SwingConstants.VERTICAL, colorDepthPanel, resolutionPanel);

        screenLayout.setVerticalGroup(screenLayout.createSequentialGroup()
                .addGroup(screenLayout.createParallelGroup()
                        .addComponent(colorDepthPanel)
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
                        .addComponent(this.customResWidth, Gui.JTF_HEIGHT, 
                                Gui.JTF_HEIGHT, Gui.JTF_HEIGHT)
                        .addComponent(customResY)
                        .addComponent(this.customResHeight))
        );

        miscLayout.setHorizontalGroup(miscLayout.createParallelGroup()
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(this.showFPS)
                        .addComponent(showFPSLabel))
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(this.drawBoardVectors)
                        .addComponent(drawBoardVectorsLabel)
                        .addComponent(this.drawActivePlayerPath)
                        .addComponent(drawActivePlayerPathLabel))
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(this.drawSpriteVectors)
                        .addComponent(drawSpriteVectorsLabel)
                        .addComponent(this.drawActivePlayerDestination)
                        .addComponent(drawActivePlayerDestinationLabel))
        );

        miscLayout.setVerticalGroup(miscLayout.createSequentialGroup()
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(this.showFPS)
                        .addComponent(showFPSLabel))
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(this.drawBoardVectors)
                        .addComponent(drawBoardVectorsLabel)
                        .addComponent(this.drawActivePlayerPath)
                        .addComponent(drawActivePlayerPathLabel))
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(this.drawSpriteVectors)
                        .addComponent(drawSpriteVectorsLabel)
                        .addComponent(this.drawActivePlayerDestination)
                        .addComponent(drawActivePlayerDestinationLabel))
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

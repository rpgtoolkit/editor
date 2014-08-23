package rpgtoolkit.editor.project;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import rpgtoolkit.common.io.types.Background;
import rpgtoolkit.common.io.types.Project;
import rpgtoolkit.editor.main.MainWindow;
import rpgtoolkit.editor.main.ToolkitEditorWindow;

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
    private final int JTF_HEIGHT = 24;

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
    private final Border defaultEtchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

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

    /**
     * Create a new blank Project
     * <p/>
     * May in the future load up a wizard to help users create a new project
     */
    public ProjectEditor()
    {
        super("New Project", true, true, true, true);

        project = new Project();
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

    /**
     * Builds the Swing interface
     */
    private void constructWindow()
    {
        this.addInternalFrameListener(this);
        // Builds the components needed to display the Project status
        JTabbedPane tabPane = new JTabbedPane();

        projectSettingsPanel = new JPanel();
        startupInfoPanel = new JPanel();
        codePanel = new JPanel();
        fightingPanel = new JPanel();
        graphicsPanel = new JPanel();

        createProjectSettingsPanel();
        createStartupInfoPanel();
        createCodePanel();
        createFightPanel();
        createGraphicsPanel();

        tabPane.addTab("Project Settings", projectSettingsPanel);
        tabPane.addTab("Startup Info", startupInfoPanel);
        tabPane.addTab("RPG Code", codePanel);
        tabPane.addTab("Battle System", fightingPanel);
        tabPane.addTab("Graphics", graphicsPanel);

        this.add(tabPane);
        // this.setJMenuBar(new ProjectEditorMenu(this.parent));
    }

    private void createProjectSettingsPanel()
    {
        // Configure Class scope components
        projectName = new JTextField(project.getGameTitle());
        enableJoystick = new JCheckBox();
        enableJoystick.setEnabled(project.getJoystickStatus());
        cursorMoveSound = new JTextField(project.getCursorMoveSound());
        cursorSelectSound = new JTextField(project.getCursorSelectSound());
        cursorCancelSound = new JTextField(project.getCursorCancelSound());
        useKeyboard = new JCheckBox();
        useMouse = new JCheckBox();
        allowDiagonalMove = new JCheckBox();
        movementKeys = new JTextField[8];
        for (int i = 0; i < movementKeys.length; i++)
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
        projectInfoPanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Project Information"));
        JPanel cursorSoundsPanel = new JPanel();
        cursorSoundsPanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Cursor Sounds"));
        JPanel controlsPanel = new JPanel();
        controlsPanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Control Options"));
        JPanel keysPanel = new JPanel();
        keysPanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Change Keys"));

        // Create Layout for top level panel
        GroupLayout layout = new GroupLayout(projectSettingsPanel);
        projectSettingsPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Create Layouts for second level panels
        GroupLayout projectInfoLayout = new GroupLayout(projectInfoPanel);
        projectInfoPanel.setLayout(projectInfoLayout);
        projectInfoLayout.setAutoCreateGaps(true);
        projectInfoLayout.setAutoCreateContainerGaps(true);

        GroupLayout cursorSoundsLayout = new GroupLayout(cursorSoundsPanel);
        cursorSoundsPanel.setLayout(cursorSoundsLayout);
        cursorSoundsLayout.setAutoCreateGaps(true);
        cursorSoundsLayout.setAutoCreateContainerGaps(true);

        GroupLayout controlsLayout = new GroupLayout(controlsPanel);
        controlsPanel.setLayout(controlsLayout);
        controlsLayout.setAutoCreateGaps(true);
        controlsLayout.setAutoCreateContainerGaps(true);

        GroupLayout keysLayout = new GroupLayout(keysPanel);
        keysPanel.setLayout(keysLayout);
        keysLayout.setAutoCreateGaps(true);
        keysLayout.setAutoCreateContainerGaps(true);

        // Configure the PROJECT INFO PANEL layout
        projectInfoLayout.setHorizontalGroup(projectInfoLayout.createSequentialGroup()
                .addComponent(projectNameLabel)
                .addComponent(projectName)
        );

        projectInfoLayout.setVerticalGroup(projectInfoLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(projectNameLabel)
                .addComponent(projectName, JTF_HEIGHT, JTF_HEIGHT, JTF_HEIGHT)
        );

        // Configure the CURSOR SOUNDS PANEL layout
        cursorSoundsLayout.setHorizontalGroup(cursorSoundsLayout.createParallelGroup()
                .addGroup(cursorSoundsLayout.createSequentialGroup()
                        .addComponent(cursorMoveSoundLabel, 50, 50, 50)
                        .addComponent(cursorMoveSound)
                        .addComponent(cursorMoveSoundButton))
                .addGroup(cursorSoundsLayout.createSequentialGroup()
                        .addComponent(cursorSelectSoundLabel)
                        .addComponent(cursorSelectSound)
                        .addComponent(cursorSelectSoundButton))
                .addGroup(cursorSoundsLayout.createSequentialGroup()
                        .addComponent(cursorCancelSoundLabel)
                        .addComponent(cursorCancelSound)
                        .addComponent(cursorCancelSoundButton))
        );

        cursorSoundsLayout.linkSize(SwingConstants.HORIZONTAL, cursorMoveSoundLabel, cursorSelectSoundLabel, cursorCancelSoundLabel);
        cursorSoundsLayout.linkSize(SwingConstants.VERTICAL, cursorMoveSound, cursorCancelSound, cursorSelectSound);

        cursorSoundsLayout.setVerticalGroup(cursorSoundsLayout.createSequentialGroup()
                .addGroup(cursorSoundsLayout.createParallelGroup()
                        .addComponent(cursorMoveSoundLabel)
                        .addComponent(cursorMoveSound, JTF_HEIGHT, JTF_HEIGHT, JTF_HEIGHT)
                        .addComponent(cursorMoveSoundButton))
                .addGroup(cursorSoundsLayout.createParallelGroup()
                        .addComponent(cursorSelectSoundLabel)
                        .addComponent(cursorSelectSound)
                        .addComponent(cursorSelectSoundButton))
                .addGroup(cursorSoundsLayout.createParallelGroup()
                        .addComponent(cursorCancelSoundLabel)
                        .addComponent(cursorCancelSound)
                        .addComponent(cursorCancelSoundButton))
        );

        // Configure CONTROL SETTINGS PANEL layout
        controlsLayout.setHorizontalGroup(controlsLayout.createParallelGroup()
                .addGroup(controlsLayout.createSequentialGroup()
                        .addComponent(useKeyboard)
                        .addComponent(useKeyboardLabel))
                .addGroup(controlsLayout.createSequentialGroup()
                        .addComponent(useMouse)
                        .addComponent(useMouseLabel))
                .addGroup(controlsLayout.createSequentialGroup()
                        .addComponent(allowDiagonalMove)
                        .addComponent(allowDiagonalMoveLabel))
                .addGroup(controlsLayout.createSequentialGroup()
                        .addComponent(enableJoystick)
                        .addComponent(enableJoystickLabel))
        );

        controlsLayout.setVerticalGroup(controlsLayout.createSequentialGroup()
                .addGroup(controlsLayout.createParallelGroup()
                        .addComponent(useKeyboard)
                        .addComponent(useKeyboardLabel))
                .addGroup(controlsLayout.createParallelGroup()
                        .addComponent(useMouse)
                        .addComponent(useMouseLabel))
                .addGroup(controlsLayout.createParallelGroup()
                        .addComponent(allowDiagonalMove)
                        .addComponent(allowDiagonalMoveLabel))
                .addGroup(controlsLayout.createParallelGroup()
                        .addComponent(enableJoystick)
                        .addComponent(enableJoystickLabel))
        );

        // Configure KEY REBINDING PANEL layout
        keysLayout.setHorizontalGroup(keysLayout.createParallelGroup()
                .addGroup(keysLayout.createSequentialGroup()
                        .addComponent(movementKeys[0], 40, 40, 40)
                        .addComponent(movementKeys[1])
                        .addComponent(movementKeys[2]))
                .addGroup(keysLayout.createSequentialGroup()
                        .addComponent(movementKeys[3])
                        .addGap(52)
                        .addComponent(movementKeys[4]))
                .addGroup(keysLayout.createSequentialGroup()
                        .addComponent(movementKeys[5])
                        .addComponent(movementKeys[6])
                        .addComponent(movementKeys[7]))
        );

        keysLayout.linkSize(movementKeys);

        keysLayout.setVerticalGroup(keysLayout.createSequentialGroup()
                .addGroup(keysLayout.createParallelGroup()
                        .addComponent(movementKeys[0], JTF_HEIGHT, JTF_HEIGHT, JTF_HEIGHT)
                        .addComponent(movementKeys[1])
                        .addComponent(movementKeys[2]))
                .addGroup(keysLayout.createParallelGroup()
                        .addComponent(movementKeys[3])
                        .addComponent(movementKeys[4]))
                .addGroup(keysLayout.createParallelGroup()
                        .addComponent(movementKeys[5])
                        .addComponent(movementKeys[6])
                        .addComponent(movementKeys[7]))
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
        initialBoard = new JTextField();
        initialChar = new JTextField();
        charSpeed = new JSlider();
        charSpeed.setMaximum(3);
        charSpeed.setMinimum(-3);
        charSpeed.setMajorTickSpacing(1);
        charSpeed.setMinorTickSpacing(1);
        charSpeed.setPaintLabels(true);
        charSpeed.setValue(0);
        pixelMovement = new JRadioButton();
        tileMovement = new JRadioButton();
        pushInPixels = new JCheckBox();
        pathfinding = new JComboBox();
        pathfinding.addItem("Axial");
        pathfinding.addItem("Diagonal");
        pathfinding.addItem("Vector");
        pathfinding.setEditable(false);
        pathfinding.setSelectedIndex(2);

        JLabel initialBoardLabel = new JLabel("Initial Board");
        JButton initialBoardButton = new JButton("Browse");
        JLabel initialCharLabel = new JLabel("Initial Character");
        JButton initialCharButton = new JButton("Browse");
        JLabel blankBoardNote = new JLabel("You may leave the initial board blank if you wish");
        JLabel charSpeedLabel = new JLabel("Adjust game speed");
        JLabel charSpeedNote = new JLabel("This is a secondary option, leave at zero for default character speeds");
        JLabel movementLabel = new JLabel("Movement Style");
        JLabel pixelMovementLabel = new JLabel("Per Pixel");
        JLabel tileMovementLabel = new JLabel("Per Tile");
        JLabel pushInPixelsLabel = new JLabel("Push[] in pixel increments");
        JButton mouseCursorButton = new JButton("Mouse Cursor");
        JLabel pathFindingLabel = new JLabel("Default Path Finding Mode");

        // Configure the necessary Panels
        JPanel conditionsPanel = new JPanel();
        conditionsPanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Startup Settings"));
        JPanel miscPanel = new JPanel();
        miscPanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Miscellaneous Settings"));

        GroupLayout layout = new GroupLayout(startupInfoPanel);
        startupInfoPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout conditionsLayout = new GroupLayout(conditionsPanel);
        conditionsPanel.setLayout(conditionsLayout);
        conditionsLayout.setAutoCreateGaps(true);
        conditionsLayout.setAutoCreateContainerGaps(true);

        GroupLayout miscLayout = new GroupLayout(miscPanel);
        miscPanel.setLayout(miscLayout);
        miscLayout.setAutoCreateGaps(true);
        miscLayout.setAutoCreateContainerGaps(true);

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

        conditionsLayout.linkSize(SwingConstants.HORIZONTAL, initialBoardLabel, initialCharLabel);
        conditionsLayout.linkSize(SwingConstants.VERTICAL, initialBoard, initialChar);

        conditionsLayout.setVerticalGroup(conditionsLayout.createSequentialGroup()
                .addGroup(conditionsLayout.createParallelGroup()
                        .addComponent(initialBoardLabel)
                        .addComponent(initialBoard, JTF_HEIGHT, JTF_HEIGHT, JTF_HEIGHT)
                        .addComponent(initialBoardButton))
                .addGroup(conditionsLayout.createParallelGroup()
                        .addComponent(initialCharLabel)
                        .addComponent(initialChar)
                        .addComponent(initialCharButton))
                .addComponent(blankBoardNote)
        );

        miscLayout.setHorizontalGroup(miscLayout.createParallelGroup()
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(charSpeedLabel)
                        .addComponent(charSpeed))
                .addComponent(charSpeedNote)
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(movementLabel)
                        .addComponent(pixelMovement)
                        .addComponent(pixelMovementLabel)
                        .addComponent(tileMovement)
                        .addComponent(tileMovementLabel))
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(pushInPixels)
                        .addComponent(pushInPixelsLabel))
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(mouseCursorButton)
                        .addComponent(pathFindingLabel)
                        .addComponent(pathfinding))
        );

        miscLayout.setVerticalGroup(miscLayout.createSequentialGroup()
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(charSpeedLabel)
                        .addComponent(charSpeed))
                .addComponent(charSpeedNote)
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(movementLabel)
                        .addComponent(pixelMovement)
                        .addComponent(pixelMovementLabel)
                        .addComponent(tileMovement)
                        .addComponent(tileMovementLabel))
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(pushInPixels)
                        .addComponent(pushInPixelsLabel))
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(mouseCursorButton)
                        .addComponent(pathFindingLabel)
                        .addComponent(pathfinding, 22, 22, 22))
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
        runTimeProgram = new JTextField();
        startupProgram = new JTextField();
        gameOverProgram = new JTextField();
        runTimeKey = new JTextField();
        menuKey = new JTextField();
        generalKey = new JTextField();

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
        programPanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Programs"));
        JPanel keysPanel = new JPanel();
        keysPanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Keys"));

        // Configure Layouts
        GroupLayout layout = new GroupLayout(codePanel);
        codePanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout programPanelLayout = new GroupLayout(programPanel);
        programPanel.setLayout(programPanelLayout);
        programPanelLayout.setAutoCreateGaps(true);
        programPanelLayout.setAutoCreateContainerGaps(true);

        GroupLayout keysPanelLayout = new GroupLayout(keysPanel);
        keysPanel.setLayout(keysPanelLayout);
        keysPanelLayout.setAutoCreateGaps(true);
        keysPanelLayout.setAutoCreateContainerGaps(true);

        programPanelLayout.setHorizontalGroup(programPanelLayout.createParallelGroup()
                .addGroup(programPanelLayout.createSequentialGroup()
                        .addComponent(runTimeProgramLabel)
                        .addComponent(runTimeProgram)
                        .addComponent(runTimeProgramButton))
                .addGroup(programPanelLayout.createSequentialGroup()
                        .addComponent(startupProgramLabel)
                        .addComponent(startupProgram)
                        .addComponent(startupProgramButton))
                .addGroup(programPanelLayout.createSequentialGroup()
                        .addComponent(gameOverProgramLabel)
                        .addComponent(gameOverProgram)
                        .addComponent(gameOverProgramButton))
        );

        programPanelLayout.linkSize(SwingConstants.VERTICAL, gameOverProgram, startupProgram, runTimeProgram);
        programPanelLayout.linkSize(SwingConstants.HORIZONTAL, gameOverProgramLabel, startupProgramLabel, runTimeProgramLabel);

        programPanelLayout.setVerticalGroup(programPanelLayout.createSequentialGroup()
                .addGroup(programPanelLayout.createParallelGroup()
                        .addComponent(runTimeProgramLabel)
                        .addComponent(runTimeProgram, JTF_HEIGHT, JTF_HEIGHT, JTF_HEIGHT)
                        .addComponent(runTimeProgramButton))
                .addGroup(programPanelLayout.createParallelGroup()
                        .addComponent(startupProgramLabel)
                        .addComponent(startupProgram)
                        .addComponent(startupProgramButton))
                .addGroup(programPanelLayout.createParallelGroup()
                        .addComponent(gameOverProgramLabel)
                        .addComponent(gameOverProgram)
                        .addComponent(gameOverProgramButton))
        );

        keysPanelLayout.setHorizontalGroup(keysPanelLayout.createParallelGroup()
                .addGroup(keysPanelLayout.createSequentialGroup()
                        .addComponent(runTimeKeyLabel)
                        .addComponent(runTimeKey, 50, 50, 50))
                .addGroup(keysPanelLayout.createSequentialGroup()
                        .addComponent(menuKeyLabel)
                        .addComponent(menuKey))
                .addGroup(keysPanelLayout.createSequentialGroup()
                        .addComponent(generalKeyLabel)
                        .addComponent(generalKey))
                .addComponent(moreKeysButton)
        );

        keysPanelLayout.linkSize(SwingConstants.VERTICAL, runTimeKey, menuKey, generalKey);
        keysPanelLayout.linkSize(SwingConstants.HORIZONTAL, runTimeKey, menuKey, generalKey);
        keysPanelLayout.linkSize(SwingConstants.HORIZONTAL, runTimeKeyLabel, menuKeyLabel, generalKeyLabel);

        keysPanelLayout.setVerticalGroup(keysPanelLayout.createSequentialGroup()
                .addGroup(keysPanelLayout.createParallelGroup()
                        .addComponent(runTimeKeyLabel)
                        .addComponent(runTimeKey, JTF_HEIGHT, JTF_HEIGHT, JTF_HEIGHT))
                .addGroup(keysPanelLayout.createParallelGroup()
                        .addComponent(menuKeyLabel)
                        .addComponent(menuKey))
                .addGroup(keysPanelLayout.createParallelGroup()
                        .addComponent(generalKeyLabel)
                        .addComponent(generalKey))
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
        enableFight = new JCheckBox();

        JLabel enableFightLabel = new JLabel("Enable Battle System?");
        JButton configureFight = new JButton("Configure");

        JPanel fightControlPanel = new JPanel();
        fightControlPanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Configuration"));

        // Configure Layouts
        GroupLayout layout = new GroupLayout(fightingPanel);
        fightingPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout fightControlPanelLayout = new GroupLayout(fightControlPanel);
        fightControlPanel.setLayout(fightControlPanelLayout);
        fightControlPanelLayout.setAutoCreateGaps(true);
        fightControlPanelLayout.setAutoCreateContainerGaps(true);

        fightControlPanelLayout.setHorizontalGroup(fightControlPanelLayout.createParallelGroup()
                .addGroup(fightControlPanelLayout.createSequentialGroup()
                        .addComponent(enableFight)
                        .addComponent(enableFightLabel))
                .addComponent(configureFight)
        );

        fightControlPanelLayout.setVerticalGroup(fightControlPanelLayout.createSequentialGroup()
                .addGroup(fightControlPanelLayout.createParallelGroup()
                        .addComponent(enableFight)
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

        sixteenBit = new JRadioButton("16 bit");
        twentyFourBit = new JRadioButton("24 bit");
        thirtyTwoBit = new JRadioButton("32 bit");
        fullScreen = new JCheckBox("Full Screen Mode?");
        fullScreen.setSelected(project.getFullscreenMode());
        sixByFour = new JRadioButton("640 x 480");
        eightBySix = new JRadioButton("800 x 600");
        tenBySeven = new JRadioButton("1024 x 768");
        customRes = new JRadioButton("Custom");
        customResWidth = new JTextField(Long.toString(project.getResolutionWidth()));
        customResHeight = new JTextField(Long.toString(project.getResolutionHeight()));
        showFPS = new JCheckBox();
        drawBoardVectors = new JCheckBox();
        drawSpriteVectors = new JCheckBox();
        drawActivePlayerPath = new JCheckBox();
        drawActivePlayerDestination = new JCheckBox();

        ButtonGroup depthGroup = new ButtonGroup();
        depthGroup.add(sixteenBit);
        depthGroup.add(twentyFourBit);
        depthGroup.add(thirtyTwoBit);

        switch (project.getColourDepth())
        {
            case 0:
                sixteenBit.setSelected(true);
                break;
            case 1:
                twentyFourBit.setSelected(true);
                break;
            case 2:
                thirtyTwoBit.setSelected(true);
                break;
        }

        ButtonGroup resolutionGroup = new ButtonGroup();
        resolutionGroup.add(sixByFour);
        resolutionGroup.add(eightBySix);
        resolutionGroup.add(tenBySeven);
        resolutionGroup.add(customRes);

        switch (project.getResolutionMode())
        {
            case 0:
                sixByFour.setSelected(true);
                break;
            case 1:
                eightBySix.setSelected(true);
                break;
            case 2:
                tenBySeven.setSelected(true);
                break;
            case 3:
                customRes.setSelected(true);
                break;
        }

        JLabel customResWarningLabel = new JLabel("Please note that not all video cards support all resolutions");
        JLabel customResX = new JLabel("x");
        JLabel customResY = new JLabel("y");
        JLabel showFPSLabel = new JLabel("Show FPS?");
        JLabel drawBoardVectorsLabel = new JLabel("Draw Board Vectors?");
        JLabel drawSpriteVectorsLabel = new JLabel("Draw Sprite Vectors?");
        JLabel drawActivePlayerPathLabel = new JLabel("Draw Active Player Path?");
        JLabel drawActivePlayerDestinationLabel = new JLabel("Draw Active Player Destination");

        JPanel screenPanel = new JPanel();
        screenPanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Screen"));
        JPanel miscPanel = new JPanel();
        miscPanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Miscellaneous"));

        JPanel colorDepthPanel = new JPanel();
        colorDepthPanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Color Depth"));
        JPanel resolutionPanel = new JPanel();
        resolutionPanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Resolution"));
        JPanel customResolutionPanel = new JPanel();
        customResolutionPanel.setBorder(BorderFactory.createTitledBorder(defaultEtchedBorder, "Custom Resolution"));

        // Create Layout for Top Level Panel
        GroupLayout layout = new GroupLayout(graphicsPanel);
        graphicsPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Configure Layouts for Second Level Panels
        GroupLayout colorDepthLayout = new GroupLayout(colorDepthPanel);
        colorDepthPanel.setLayout(colorDepthLayout);
        colorDepthLayout.setAutoCreateGaps(true);
        colorDepthLayout.setAutoCreateContainerGaps(true);

        GroupLayout resolutionLayout = new GroupLayout(resolutionPanel);
        resolutionPanel.setLayout(resolutionLayout);
        resolutionLayout.setAutoCreateGaps(true);
        resolutionLayout.setAutoCreateContainerGaps(true);

        GroupLayout screenLayout = new GroupLayout(screenPanel);
        screenPanel.setLayout(screenLayout);
        screenLayout.setAutoCreateGaps(true);
        screenLayout.setAutoCreateContainerGaps(true);

        GroupLayout miscLayout = new GroupLayout(miscPanel);
        miscPanel.setLayout(miscLayout);
        miscLayout.setAutoCreateGaps(true);
        miscLayout.setAutoCreateContainerGaps(true);

        GroupLayout customResLayout = new GroupLayout(customResolutionPanel);
        customResolutionPanel.setLayout(customResLayout);
        customResLayout.setAutoCreateGaps(true);
        customResLayout.setAutoCreateContainerGaps(true);

        colorDepthLayout.setHorizontalGroup(colorDepthLayout.createParallelGroup()
                .addComponent(sixteenBit)
                .addComponent(twentyFourBit)
                .addComponent(thirtyTwoBit)
        );

        colorDepthLayout.setVerticalGroup(colorDepthLayout.createSequentialGroup()
                .addComponent(sixteenBit)
                .addComponent(twentyFourBit)
                .addComponent(thirtyTwoBit)
        );

        resolutionLayout.setHorizontalGroup(resolutionLayout.createParallelGroup()
                .addComponent(sixByFour)
                .addComponent(eightBySix)
                .addComponent(tenBySeven)
                .addComponent(customRes)
                .addComponent(fullScreen)
        );

        resolutionLayout.setVerticalGroup(resolutionLayout.createSequentialGroup()
                .addComponent(sixByFour)
                .addComponent(eightBySix)
                .addComponent(tenBySeven)
                .addComponent(customRes)
                .addComponent(fullScreen)
        );

        screenLayout.setHorizontalGroup(screenLayout.createParallelGroup()
                .addGroup(screenLayout.createSequentialGroup()
                        .addComponent(colorDepthPanel, 150, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(resolutionPanel, 150, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(customResWidth)
                        .addComponent(customResY)
                        .addComponent(customResHeight))
        );

        customResLayout.linkSize(SwingConstants.VERTICAL, customResWidth, customResHeight);

        customResLayout.setVerticalGroup(customResLayout.createSequentialGroup()
                .addComponent(customResWarningLabel)
                .addGroup(customResLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(customResX)
                        .addComponent(customResWidth, JTF_HEIGHT, JTF_HEIGHT, JTF_HEIGHT)
                        .addComponent(customResY)
                        .addComponent(customResHeight))
        );

        miscLayout.setHorizontalGroup(miscLayout.createParallelGroup()
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(showFPS)
                        .addComponent(showFPSLabel))
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(drawBoardVectors)
                        .addComponent(drawBoardVectorsLabel)
                        .addComponent(drawActivePlayerPath)
                        .addComponent(drawActivePlayerPathLabel))
                .addGroup(miscLayout.createSequentialGroup()
                        .addComponent(drawSpriteVectors)
                        .addComponent(drawSpriteVectorsLabel)
                        .addComponent(drawActivePlayerDestination)
                        .addComponent(drawActivePlayerDestinationLabel))
        );

        miscLayout.setVerticalGroup(miscLayout.createSequentialGroup()
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(showFPS)
                        .addComponent(showFPSLabel))
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(drawBoardVectors)
                        .addComponent(drawBoardVectorsLabel)
                        .addComponent(drawActivePlayerPath)
                        .addComponent(drawActivePlayerPathLabel))
                .addGroup(miscLayout.createParallelGroup()
                        .addComponent(drawSpriteVectors)
                        .addComponent(drawSpriteVectorsLabel)
                        .addComponent(drawActivePlayerDestination)
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

    public void internalFrameOpened(InternalFrameEvent e)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void internalFrameClosing(InternalFrameEvent e)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void internalFrameClosed(InternalFrameEvent e)
    {
        this.gracefulClose();
    }

    public void internalFrameIconified(InternalFrameEvent e)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void internalFrameDeiconified(InternalFrameEvent e)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void internalFrameActivated(InternalFrameEvent e)
    {

    }

    public void internalFrameDeactivated(InternalFrameEvent e)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

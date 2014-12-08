package rpgtoolkit.editor.enemy;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import rpgtoolkit.common.io.types.Enemy;
import rpgtoolkit.editor.main.MainWindow;
import rpgtoolkit.editor.main.ToolkitEditorWindow;
import rpgtoolkit.editor.utilities.Gui;
import rpgtoolkit.editor.utilities.WholeNumberField;

/**
 * Project File editor
 *
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 * @author Joel Moore
 */
public class EnemyEditor extends ToolkitEditorWindow implements InternalFrameListener
{

    private JFileChooser openEnemy;
    private final Enemy enemy; // Project file we are altering

    // Tabs required by the menu
    private JPanel basicSettingsPanel;
    private JPanel graphicsPanel;
    private JPanel specialMovesPanel;
    private JPanel tacticsPanel;
    private JPanel rewardsPanel;

    private final Border defaultEtchedBorder = BorderFactory.
            createEtchedBorder(EtchedBorder.LOWERED);
    
    //BASIC SETTINGS
    private JTextField enemyName;
    private WholeNumberField maxHitPoints;
    private WholeNumberField maxSpecialPoints;
    private WholeNumberField fightPower;
    private WholeNumberField defencePower;
    private JCheckBox canRunAway;
    private JTextField runAwayProgram;
    private WholeNumberField critOnEnemy;
    private WholeNumberField critOnPlayer;

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

    // SPECIAL MOVES SETTINGS
    private JTextField initialBoard;
    private JTextField initialChar;
    private JSlider charSpeed;
    private JRadioButton pixelMovement;
    private JRadioButton tileMovement;
    private JCheckBox pushInPixels;
    private JComboBox pathfinding;

    // TACTICS SETTINGS
    private JTextField runTimeProgram;
    private JTextField startupProgram;
    private JTextField gameOverProgram;
    private JTextField runTimeKey;
    private JTextField menuKey;
    private JTextField generalKey;

    // REWARDS SETTINGS
    private WholeNumberField experienceAwarded;
    private WholeNumberField goldAwarded;
    private JTextField victoryProgram;

    /*
     * *************************************************************************
     * Public Constructors
     * *************************************************************************
     */
    /**
     * Create a new blank Enemy
     */
    public EnemyEditor()
    {
        super("New Enemy", true, true, true, true);

        this.enemy = new Enemy();
        this.setVisible(true);
    }

    /**
     * Opens an existing project
     *
     * @param theEnemy Enemy to edit
     */
    public EnemyEditor(Enemy theEnemy)
    {
        super("Editing Enemy - " + theEnemy.toString(), true, true, true, true);

        this.enemy = theEnemy;

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
        return enemy.save();
    }

    public void gracefulClose()
    {

    }

    public void setWindowParent(MainWindow parent)
    {

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
        
        // Builds the components needed to display the Enemy status.
        JTabbedPane tabPane = new JTabbedPane();

        this.basicSettingsPanel = new JPanel();
        this.graphicsPanel = new JPanel();
        this.specialMovesPanel = new JPanel();
        this.tacticsPanel = new JPanel();
        this.rewardsPanel = new JPanel();

        this.createBasicSettingsPanel();
        this.createGraphicsPanel();
        this.createSpecialMovesPanel();
        this.createTacticsPanel();
        this.createRewardsPanel();

        tabPane.addTab("Basic Settings", this.basicSettingsPanel);
        tabPane.addTab("Graphics", this.graphicsPanel);
        tabPane.addTab("Special Moves", this.specialMovesPanel);
        tabPane.addTab("Tactics", this.tacticsPanel);
        tabPane.addTab("Rewards", this.rewardsPanel);

        this.add(tabPane);
        // this.setJMenuBar(new ProjectEditorMenu(this.parent));
    }

    private void createBasicSettingsPanel()
    {
        // Configure Class scope components
        this.enemyName = new JTextField(this.enemy.getName());
        this.maxHitPoints = new WholeNumberField(this.enemy.getMaxHitPoints());
        this.maxSpecialPoints = new WholeNumberField(this.enemy.getMaxMagicPoints());
        this.fightPower = new WholeNumberField(this.enemy.getFightPower());
        this.defencePower = new WholeNumberField(this.enemy.getDefencePower());
        this.canRunAway = new JCheckBox();
        this.canRunAway.setEnabled(this.enemy.canRunAway());
        this.runAwayProgram = new JTextField(this.enemy.getRunAwayProgram());
        this.critOnEnemy = new WholeNumberField(this.enemy.getSneakChance());
        this.critOnPlayer = new WholeNumberField(this.enemy.getSurpriseChance());

        // Configure function Scope Components
        JLabel enemyNameLabel = new JLabel("Name");
        JLabel maxHitPointsLabel = new JLabel("Max Health Points");
        JLabel maxSpecialPointsLabel = new JLabel("Special Move Power");
        JLabel fightPowerLabel = new JLabel("Fighting Power");
        JLabel defencePowerLabel = new JLabel("DefencePower");
        JLabel canRunAwayLabel = new JLabel("Player can run from this enemy");
        JLabel runAwayProgramLabel = new JLabel("Program to run when player runs away");
        JButton runAwayProgramButton = new JButton("Browse");
        JLabel critOnEnemyLabel = new JLabel("Chances of a critical hit on the enemy: (1 in)");
        JLabel critOnPlayerLabel = new JLabel("Chances of a critical hit on the player: (1 in)");

        // Configure the necessary Panels
        JPanel basicInfoPanel = new JPanel();
        basicInfoPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Basic Information"));
        JPanel fightingConditionsPanel = new JPanel();
        fightingConditionsPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Fighting Conditions"));

        // Create Layout for top level panel
        GroupLayout layout = Gui.createGroupLayout(this.basicSettingsPanel);

        // Create Layouts for second level panels
        GroupLayout basicInfoLayout = Gui.createGroupLayout(basicInfoPanel);

        GroupLayout fightingConditionsLayout = Gui.createGroupLayout(fightingConditionsPanel);

        // Configure the BASIC INFO PANEL layout
        basicInfoLayout.setHorizontalGroup(basicInfoLayout.createParallelGroup()
                .addGroup(basicInfoLayout.createSequentialGroup()
                        .addComponent(enemyNameLabel)
                        .addComponent(this.enemyName))
                .addGroup(basicInfoLayout.createSequentialGroup()
                        .addComponent(maxHitPointsLabel)
                        .addComponent(this.maxHitPoints))
                .addGroup(basicInfoLayout.createSequentialGroup()
                        .addComponent(maxSpecialPointsLabel)
                        .addComponent(this.maxSpecialPoints))
                .addGroup(basicInfoLayout.createSequentialGroup()
                        .addComponent(fightPowerLabel)
                        .addComponent(this.fightPower))
                .addGroup(basicInfoLayout.createSequentialGroup()
                        .addComponent(defencePowerLabel)
                        .addComponent(this.defencePower))
        );

        basicInfoLayout.linkSize(SwingConstants.HORIZONTAL,
                enemyNameLabel,
                maxHitPointsLabel,
                maxSpecialPointsLabel,
                fightPowerLabel,
                defencePowerLabel);
        basicInfoLayout.linkSize(SwingConstants.VERTICAL,
                this.enemyName,
                this.maxHitPoints,
                this.maxSpecialPoints,
                this.fightPower,
                this.defencePower);

        basicInfoLayout.setVerticalGroup(basicInfoLayout.createSequentialGroup()
                .addGroup(basicInfoLayout.createParallelGroup()
                        .addComponent(enemyNameLabel)
                        .addComponent(this.enemyName, Gui.JTF_HEIGHT,
                                Gui.JTF_HEIGHT, Gui.JTF_HEIGHT))
                .addGroup(basicInfoLayout.createParallelGroup()
                        .addComponent(maxHitPointsLabel)
                        .addComponent(this.maxHitPoints))
                .addGroup(basicInfoLayout.createParallelGroup()
                        .addComponent(maxSpecialPointsLabel)
                        .addComponent(this.maxSpecialPoints))
                .addGroup(basicInfoLayout.createParallelGroup()
                        .addComponent(fightPowerLabel)
                        .addComponent(this.fightPower))
                .addGroup(basicInfoLayout.createParallelGroup()
                        .addComponent(defencePowerLabel)
                        .addComponent(this.defencePower))
        );

        // Configure the FIGHTING CONDITIONS PANEL layout
        fightingConditionsLayout.setHorizontalGroup(fightingConditionsLayout.createParallelGroup()
                .addGroup(fightingConditionsLayout.createSequentialGroup()
                        .addComponent(this.canRunAway)
                        .addComponent(canRunAwayLabel))
                .addGroup(fightingConditionsLayout.createSequentialGroup()
                        .addGap(16) //TODO: surely there's a good way to get the width + padding of a checkbox?
                        .addComponent(runAwayProgramLabel)
                        .addComponent(this.runAwayProgram)
                        .addComponent(runAwayProgramButton))
                .addGroup(fightingConditionsLayout.createSequentialGroup()
                        .addComponent(critOnEnemyLabel)
                        .addComponent(this.critOnEnemy))
                .addGroup(fightingConditionsLayout.createSequentialGroup()
                        .addComponent(critOnPlayerLabel)
                        .addComponent(this.critOnPlayer))
        );

        fightingConditionsLayout.linkSize(SwingConstants.HORIZONTAL, 
                critOnEnemyLabel, 
                critOnPlayerLabel);
        fightingConditionsLayout.linkSize(SwingConstants.VERTICAL, 
                this.runAwayProgram, 
                this.critOnEnemy, 
                this.critOnPlayer);

        fightingConditionsLayout.setVerticalGroup(fightingConditionsLayout.createSequentialGroup()
                .addGroup(fightingConditionsLayout.createParallelGroup()
                        .addComponent(this.canRunAway)
                        .addComponent(canRunAwayLabel))
                .addGroup(fightingConditionsLayout.createParallelGroup()
                        .addComponent(runAwayProgramLabel)
                        .addComponent(this.runAwayProgram, Gui.JTF_HEIGHT, 
                                Gui.JTF_HEIGHT, Gui.JTF_HEIGHT)
                        .addComponent(runAwayProgramButton))
                .addGroup(fightingConditionsLayout.createParallelGroup()
                        .addComponent(critOnEnemyLabel)
                        .addComponent(this.critOnEnemy))
                .addGroup(fightingConditionsLayout.createParallelGroup()
                        .addComponent(critOnPlayerLabel)
                        .addComponent(this.critOnPlayer))
        );

        // Configure BASIC SETTINGS PANEL layout
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(basicInfoPanel, 515, 515, 515)
                .addComponent(fightingConditionsPanel)
        );

        layout.linkSize(SwingConstants.HORIZONTAL, basicInfoPanel, fightingConditionsPanel);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(basicInfoPanel)
                .addComponent(fightingConditionsPanel)
        );
    }

    private void createSpecialMovesPanel()
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

        GroupLayout layout = new GroupLayout(this.specialMovesPanel);
        this.specialMovesPanel.setLayout(layout);
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

    private void createTacticsPanel()
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
        GroupLayout layout = new GroupLayout(this.tacticsPanel);
        this.tacticsPanel.setLayout(layout);
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

    private void createRewardsPanel()
    {
        this.experienceAwarded = new WholeNumberField(this.enemy.getExperienceAwarded());

        JLabel enableFightLabel = new JLabel("Enable Battle System?");
        JButton configureFight = new JButton("Configure");

        JPanel fightControlPanel = new JPanel();
        fightControlPanel.setBorder(BorderFactory.createTitledBorder(
                this.defaultEtchedBorder, "Configuration"));

        // Configure Layouts
        GroupLayout layout = new GroupLayout(this.rewardsPanel);
        this.rewardsPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout fightControlPanelLayout = new GroupLayout(fightControlPanel);
        fightControlPanel.setLayout(fightControlPanelLayout);
        fightControlPanelLayout.setAutoCreateGaps(true);
        fightControlPanelLayout.setAutoCreateContainerGaps(true);

        fightControlPanelLayout.setHorizontalGroup(fightControlPanelLayout.createParallelGroup()
                .addGroup(fightControlPanelLayout.createSequentialGroup()
                        .addComponent(this.experienceAwarded)
                        .addComponent(enableFightLabel))
                .addComponent(configureFight)
        );

        fightControlPanelLayout.setVerticalGroup(fightControlPanelLayout.createSequentialGroup()
                .addGroup(fightControlPanelLayout.createParallelGroup()
                        .addComponent(this.experienceAwarded)
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
        this.fullScreen.setSelected(true);
        this.sixByFour = new JRadioButton("640 x 480");
        this.eightBySix = new JRadioButton("800 x 600");
        this.tenBySeven = new JRadioButton("1024 x 768");
        this.customRes = new JRadioButton("Custom");
        this.customResWidth = new JTextField(Long.toString(100));
        this.customResHeight = new JTextField(Long.toString(50));
        this.showFPS = new JCheckBox();
        this.drawBoardVectors = new JCheckBox();
        this.drawSpriteVectors = new JCheckBox();
        this.drawActivePlayerPath = new JCheckBox();
        this.drawActivePlayerDestination = new JCheckBox();

        ButtonGroup depthGroup = new ButtonGroup();
        depthGroup.add(this.sixteenBit);
        depthGroup.add(this.twentyFourBit);
        depthGroup.add(this.thirtyTwoBit);

        ButtonGroup resolutionGroup = new ButtonGroup();
        resolutionGroup.add(this.sixByFour);
        resolutionGroup.add(this.eightBySix);
        resolutionGroup.add(this.tenBySeven);
        resolutionGroup.add(this.customRes);

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
        GroupLayout layout = new GroupLayout(this.graphicsPanel);
        this.graphicsPanel.setLayout(layout);
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

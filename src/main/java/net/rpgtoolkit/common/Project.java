package net.rpgtoolkit.common;

import net.rpgtoolkit.common.EnemySkillPair;
import net.rpgtoolkit.editor.CorruptFileException;

import java.io.*;
import java.util.ArrayList;
import net.rpgtoolkit.common.RunTimeKey;

/**
 * This class manages the GAM file type for the RPG Toolkit
 *
 * @author Geoff Wilson
 * @version 0.1
 */
public class Project extends BasicType
{
    // Some useful constants
    private final String DEFAULT_MENU_PLUGIN = "tk3menu.dll";
    private final String DEFAULT_FIGHT_PLUGIN = "tk3fight.dll";
    private final String FILE_HEADER = "RPGTLKIT MAIN";
    private final String DEFAULT_MOUSE_CURSOR = "TK DEFAULT";
    private final int MAJOR_VERSION = 2;
    private final int MINOR_VERSION = 9;

    // Project Variables
    private String projectPath;
    private String gameTitle;
    private int mainScreenType;
    private int extendToFullScreen;
    private int mainResolution;
    private int mainDisableProtectReg;
    private String languageFile;
    private Program startupPrg;
    private Board initBoard;
    private Player initChar;
    private String runTime;
    private int runKey;
    private int menuKey;
    private int key;
    private ArrayList<RunTimeKey> runTimeArray;
    private String menuPlugin;
    private String fightPlugin;
    private int fightingEnabled;
    private ArrayList<EnemySkillPair> enemyArray;
    private int fightType;
    private long fightChance;
    private int useCustomBattleSystem;
    private String battleSystemProgram;
    private String gameOverProgram;
    private String buttonGraphic;
    private String windowGraphic;
    private ArrayList<String> pluginArray;
    private int useDayNight;
    private int dayNightType;
    private long dayLenghtInMins;
    private String cursorMoveSound;
    private String cursorSelectSound;
    private String cursorCancelSound;
    private int enableJoyStick;
    private int colordepth;
    private int gameSpeed;
    private int usePixelBasedMovement;
    private String mouseCursor;
    private int hotSpotX;
    private int hotSpotY;
    private long transpcolor;
    private long resolutionWidth;
    private long resolutionHeight;
    private int displayFPSInTitle;

    // Variables Specific to 3.1
    private int pathfindingAlgo;
    private long drawVectors;
    private long pathColor;
    private long movementControls;
    private ArrayList<Integer> movementKeys;

    /**
     * Creates a new project file
     */
    public Project()
    {

    }

    /**
     * Opens a project from an existing file
     *
     * @param file Project (.GAM) file to open
     */
    public Project(File file)
    {
        super(file);
        this.open();
    }

    private boolean open()
    {
        try
        {
            // Prepare objects be we begin
            runTimeArray = new ArrayList<>();
            enemyArray = new ArrayList<>();
            pluginArray = new ArrayList<>();

            // First thing we must do is check the header
            if (binaryIO.readBinaryString().equals(FILE_HEADER)) // Valid file we can proceede
            {
                // get file version number (normally 2.9)
                int majorVersion = binaryIO.readBinaryInteger(); // should be 2
                int minorVersion = binaryIO.readBinaryInteger(); // should be 9

                // File spec seems to have two useless read calls here
                inputStream.skip(2); // Skip ahead 2 bytes
                binaryIO.readBinaryString(); // "NOCODE" skipped

                projectPath = binaryIO.readBinaryString();
                
                // appends a leading slash to the games project folder
                projectPath = "/" + projectPath.toLowerCase().replace("\\", "/");
                System.setProperty("project.path", 
                        System.getProperty("user.dir") + projectPath);

                gameTitle = binaryIO.readBinaryString();
                mainScreenType = binaryIO.readBinaryInteger();
                extendToFullScreen = binaryIO.readBinaryInteger();
                mainResolution = binaryIO.readBinaryInteger();

                if (minorVersion < 3)
                {
                    inputStream.skip(2); // Skip over the old parallax value
                }

                mainDisableProtectReg = binaryIO.readBinaryInteger();
                languageFile = binaryIO.readBinaryString();

                String startupPrgString = binaryIO.readBinaryString();
                startupPrg = new Program(System.getProperty("project.path") + "/Prg/" + startupPrgString);
                String initBoardString = binaryIO.readBinaryString();
                initBoard = new Board(new File(System.getProperty("project.path") + "/Boards/" + initBoardString));
                String initCharString = binaryIO.readBinaryString();
                initChar = new Player(new File(System.getProperty("project.path") + "/Chrs/" + initCharString));

                runTime = binaryIO.readBinaryString();
                runKey = binaryIO.readBinaryInteger();
                menuKey = binaryIO.readBinaryInteger();
                key = binaryIO.readBinaryInteger();

                // Read in array of run time keys
                int numberOfKeys = binaryIO.readBinaryInteger();
                for (int i = 0; i < numberOfKeys + 1; i++)
                {
                    int runTimeKey = binaryIO.readBinaryInteger();
                    String runTimeProgram = binaryIO.readBinaryString();
                    RunTimeKey newKey = new RunTimeKey(runTimeKey, runTimeProgram);
                    runTimeArray.add(newKey);
                }

                // Deal with lack of plugin data in main file
                if (minorVersion >= 3)
                {
                    menuPlugin = binaryIO.readBinaryString();
                    fightPlugin = binaryIO.readBinaryString();
                }
                else
                {
                    inputStream.skip(8); // Junk Data to be skipped
                    menuPlugin = DEFAULT_MENU_PLUGIN;
                    fightPlugin = DEFAULT_FIGHT_PLUGIN;
                }

                fightingEnabled = binaryIO.readBinaryInteger();
                int numberOfEnemies = binaryIO.readBinaryInteger(); // Pointless!
                for (int i = 0; i < numberOfEnemies + 1; i++)
                {
                    String enemy = binaryIO.readBinaryString();
                    int skill = binaryIO.readBinaryInteger();
                    EnemySkillPair newEnemy = new EnemySkillPair(enemy, skill);
                    enemyArray.add(newEnemy);
                }

                fightType = binaryIO.readBinaryInteger();
                fightChance = binaryIO.readBinaryLong();
                useCustomBattleSystem = binaryIO.readBinaryInteger();
                battleSystemProgram = binaryIO.readBinaryString();

                if (minorVersion <= 2)
                {
                    inputStream.skip(2); // Old fight style option
                }

                gameOverProgram = binaryIO.readBinaryString();

                buttonGraphic = binaryIO.readBinaryString();
                windowGraphic = binaryIO.readBinaryString();

                int pluginCount = binaryIO.readBinaryInteger();
                if (minorVersion <= 2)
                {
                    for (int i = 0; i < pluginCount + 1; i++)
                    {
                        int readin = binaryIO.readBinaryInteger();
                        if (readin == 1)
                        {
                            pluginArray.add("tkplug" + i + ".dll");
                        }
                    }
                }
                else
                {
                    for (int i = 0; i < pluginCount + 1; i++)
                    {
                        pluginArray.add(binaryIO.readBinaryString());
                    }
                }

                useDayNight = binaryIO.readBinaryInteger();
                dayNightType = binaryIO.readBinaryInteger();
                dayLenghtInMins = binaryIO.readBinaryLong();

                if (minorVersion >= 3)
                {
                    cursorMoveSound = binaryIO.readBinaryString();
                    cursorSelectSound = binaryIO.readBinaryString();
                    cursorCancelSound = binaryIO.readBinaryString();
                    enableJoyStick = inputStream.read();
                    colordepth = inputStream.read();
                }

                if (minorVersion >= 4)
                {
                    gameSpeed = inputStream.read();
                    usePixelBasedMovement = inputStream.read();
                }
                else
                {
                    gameSpeed = 0;
                }

                if (minorVersion <= 7)
                {
                    // Does something with gamespeed
                }

                if (minorVersion <= 5)
                {
                    if (minorVersion == 5)
                    {
                        if (inputStream.read() == 1)
                        {
                            mouseCursor = DEFAULT_MOUSE_CURSOR;
                        }
                        else
                        {
                            mouseCursor = "";
                        }
                    }
                    else
                    {
                        mouseCursor = DEFAULT_MOUSE_CURSOR;
                    }
                    hotSpotX = 0;
                    hotSpotY = 0;
                }
                else
                {
                    mouseCursor = binaryIO.readBinaryString();
                    hotSpotX = inputStream.read();
                    hotSpotY = inputStream.read();
                    transpcolor = binaryIO.readBinaryLong();
                }

                if (mouseCursor.equals(DEFAULT_MOUSE_CURSOR))
                {
                    transpcolor = 255;
                }

                if (minorVersion >= 7)
                {
                    resolutionWidth = binaryIO.readBinaryLong();
                    resolutionHeight = binaryIO.readBinaryLong();
                }

                if (minorVersion >= 8)
                {
                    displayFPSInTitle = inputStream.read();
                }

                if (minorVersion >= 9)
                {
                    pathfindingAlgo = binaryIO.readBinaryInteger();
                    drawVectors = binaryIO.readBinaryLong();
                    pathColor = binaryIO.readBinaryLong();
                    movementControls = binaryIO.readBinaryLong();

                    movementKeys = new ArrayList();

                    for (int i = 0; i < 8; i++)
                    {

                        movementKeys.add(binaryIO.readBinaryInteger());
                    }
                }

            }

            // Release the file
            this.inputStream.close();
            this.binaryIO.closeInput();

            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (CorruptFileException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

    }

    public boolean save()
    {
        try
        {
            outputStream = new FileOutputStream(this.file);
            binaryIO.setOutputStream(outputStream);

            binaryIO.writeBinaryString(FILE_HEADER);
            binaryIO.writeBinaryInteger(MAJOR_VERSION);
            binaryIO.writeBinaryInteger(MINOR_VERSION);
            binaryIO.writeBinaryInteger(1);
            binaryIO.writeBinaryString("NOCODE");
            binaryIO.writeBinaryString(projectPath);
            binaryIO.writeBinaryString(gameTitle);
            binaryIO.writeBinaryInteger(mainScreenType);
            binaryIO.writeBinaryInteger(extendToFullScreen);
            binaryIO.writeBinaryInteger(mainResolution);
            binaryIO.writeBinaryInteger(mainDisableProtectReg);
            binaryIO.writeBinaryString(languageFile);
            //binaryIO.writeBinaryString(startupPrg.);
            //binaryIO.writeBinaryString(initBoard);
            //binaryIO.writeBinaryString(initChar);
            binaryIO.writeBinaryString(runTime);
            binaryIO.writeBinaryInteger(runKey);
            binaryIO.writeBinaryInteger(menuKey);
            binaryIO.writeBinaryInteger(key);
            binaryIO.writeBinaryInteger(50);
            for (int i = 0; i < 51; i++)
            {
                binaryIO.writeBinaryInteger(runTimeArray.get(i).getKey());
                binaryIO.writeBinaryString(runTimeArray.get(i).getProgram());
            }
            binaryIO.writeBinaryString(menuPlugin);
            binaryIO.writeBinaryString(fightPlugin);
            binaryIO.writeBinaryInteger(fightingEnabled);
            binaryIO.writeBinaryInteger(500);
            for (int i = 0; i < 501; i++)
            {
                binaryIO.writeBinaryString(enemyArray.get(i).getEnemy());
                binaryIO.writeBinaryInteger(enemyArray.get(i).getSkill());
            }
            binaryIO.writeBinaryInteger(fightType);
            binaryIO.writeBinaryLong(fightChance);
            binaryIO.writeBinaryInteger(useCustomBattleSystem);
            binaryIO.writeBinaryString(battleSystemProgram);
            binaryIO.writeBinaryString(gameOverProgram);
            binaryIO.writeBinaryString(buttonGraphic);
            binaryIO.writeBinaryString(windowGraphic);
            binaryIO.writeBinaryInteger(pluginArray.size() - 1);
            for (String aPluginArray : pluginArray)
            {
                binaryIO.writeBinaryString(aPluginArray);
            }
            binaryIO.writeBinaryInteger(useDayNight);
            binaryIO.writeBinaryInteger(dayNightType);
            binaryIO.writeBinaryLong(dayLenghtInMins);
            binaryIO.writeBinaryString(cursorMoveSound);
            binaryIO.writeBinaryString(cursorSelectSound);
            binaryIO.writeBinaryString(cursorCancelSound);
            outputStream.write(enableJoyStick);
            outputStream.write(colordepth);
            outputStream.write(gameSpeed);
            outputStream.write(usePixelBasedMovement);
            binaryIO.writeBinaryString(mouseCursor);
            outputStream.write(hotSpotX);
            outputStream.write(hotSpotY);
            binaryIO.writeBinaryLong(transpcolor);
            binaryIO.writeBinaryLong(resolutionWidth);
            binaryIO.writeBinaryLong(resolutionHeight);
            outputStream.write(displayFPSInTitle);
            binaryIO.writeBinaryInteger(pathfindingAlgo);
            binaryIO.writeBinaryLong(drawVectors);
            binaryIO.writeBinaryLong(pathColor);
            binaryIO.writeBinaryLong(movementControls);
            for (Integer aKeyCode : movementKeys)
            {
                binaryIO.writeBinaryInteger(aKeyCode);
            }
            outputStream.close();
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAs(File fileName)
    {
        this.file = fileName;
        return this.save();
    }

    public long getResolutionHeight()
    {
        return resolutionHeight;
    }

    public void setResolutionHeight(long newHeight)
    {
        resolutionHeight = newHeight;
    }

    public long getResolutionWidth()
    {
        return resolutionWidth;
    }

    public void setResolutionWidth(long newWidth)
    {
        resolutionWidth = newWidth;
    }

    public String getCursorMoveSound()
    {
        return cursorMoveSound;
    }

    public String getCursorSelectSound()
    {
        return cursorSelectSound;
    }

    public String getCursorCancelSound()
    {
        return cursorCancelSound;
    }

    public String getProjectPath()
    {
        return this.projectPath;
    }

    public void setProjectPath(String projectPath)
    {
        this.projectPath = projectPath;
    }

    public String getGameTitle()
    {
        return this.gameTitle;
    }

    public void setGameTitle(String gameTitle)
    {
        this.gameTitle = gameTitle;
    }

    public int getMainScreenType()
    {
        return mainScreenType;
    }

    public void setMainScreenType(int mainScreenType)
    {
        this.mainScreenType = mainScreenType;
    }

    public boolean getFullscreenMode()
    {
        return extendToFullScreen == 1;
    }

    public void setExtendToFullScreen(int extendToFullScreen)
    {
        this.extendToFullScreen = extendToFullScreen;
    }

    public int getResolutionMode()
    {
        return mainResolution;
    }

    public int getColourDepth()
    {
        return colordepth;
    }

    public boolean getJoystickStatus()
    {
        return enableJoyStick == 1;
    }

    public Board getInitBoard()
    {
        return initBoard;
    }

    public Player getInitChar()
    {
        return initChar;
    }

    public Program getStartupPrg()
    {
        return startupPrg;
    }
}


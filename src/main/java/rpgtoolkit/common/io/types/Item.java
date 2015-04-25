package rpgtoolkit.common.io.types;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import rpgtoolkit.common.utilities.BinaryIO;
import rpgtoolkit.editor.board.types.BoardVector;
import rpgtoolkit.editor.exceptions.CorruptFileException;

public class Item extends BasicType
{
    //Constants
    private final String FILE_HEADER = "RPGTLKIT ITEM";
    private final int MAJOR_VERSION = 2;
    private final int MINOR_VERSION = 7;
    private final int USER_CHAR_COUNT = 51;
    private final int EQUIP_COUNT = 8;
    private final int ANIMATION_STANDARD_COUNT = 10;
    private final int ANIMATION_STANDING_COUNT = 8;
    private final int ITEM_WALK_S = 0;
    private final int ITEM_WALK_N = 1;
    private final int ITEM_WALK_E = 2;
    private final int ITEM_WALK_W = 3;
    private final int ITEM_WALK_NW = 4;
    private final int ITEM_WALK_NE = 5;
    private final int ITEM_WALK_SW = 6;
    private final int ITEM_WALK_SE = 7;
    private final int ITEM_REST = 8;

    //Item Variables
    private String name;
    private String description;
    //DEV: should these bytes used in place of booleans be booleans?
    private byte isEquippable;
    private byte isMenuDriven;
    private byte isBoardDriven;
    private byte isBattleDriven;
    private byte usersSpecified;
    private ArrayList<String> userChar;
    private long buyPrice;
    private long sellPrice;
    private byte isKeyItem;
    private ArrayList<Byte> equipLocation;
    private String accessory;
    private long equipHP;
    private long equipDP;
    private long equipFP;
    private long equipSMP;
    private String equipProgram;
    private String removeProgram;
    private long menuHP;
    private long menuSMP;
    private String menuProgram;
    private long fightHP;
    private long fightSMP;
    private String fightProgram;
    private String fightAnimation;
    private String boardMultitaskProgram;
    private String boardPickUpProgram;
    private byte isWide;                            //0 = 32x32; 1 = 64x32

    private ArrayList<String> standardAnimationFiles;
    private ArrayList<Animation> standardAnimations;
    private ArrayList<String> animationCustom;
    private ArrayList<String> animationCustomHandle;
    private ArrayList<String> animationStanding;

    // Active animation for the engine
    private Animation activeAnimation;
    private int frameNumber;
    private int frameCount;
    private Timer animationTimer;

    private double idleTime;
    private double speed;

    //private long loopSpeed;   //present in VB code, but never used
    private BoardVector vectorBase;
    private BoardVector vectorActivate;

    //CONSTRUCTOR
    public Item()
    {
        // New Item
        resetAll();
    }

    public Item(File fileName)
    {
        super(fileName);
        this.open();
    }

    //set up variables
    private void resetAll()
    {
        name = "";
        description = "";
        speed = 0.05;
        idleTime = 3;
        isEquippable = 0;
        isMenuDriven = 0;
        isBoardDriven = 0;
        isBattleDriven = 0;
        usersSpecified = 0;
        userChar = new ArrayList<>(USER_CHAR_COUNT);
        for (int i = 0; i != USER_CHAR_COUNT; i++)
        {
            userChar.add("");
        }
        buyPrice = 0;
        sellPrice = 0;
        isKeyItem = 0;
        equipLocation = new ArrayList<>(EQUIP_COUNT);
        for (int i = 0; i != EQUIP_COUNT; i++)
        {
            Byte tempByte = 0;
            equipLocation.add(tempByte);
        }
        accessory = "";
        equipHP = 0;
        equipDP = 0;
        equipFP = 0;
        equipSMP = 0;
        equipProgram = "";
        removeProgram = "";
        menuHP = 0;
        menuSMP = 0;
        menuProgram = "";
        fightHP = 0;
        fightSMP = 0;
        fightProgram = "";
        fightAnimation = "";
        boardMultitaskProgram = "";
        boardPickUpProgram = "";
        isWide = 1;
        standardAnimationFiles = new ArrayList<>(ANIMATION_STANDARD_COUNT);
        for (int i = 0; i != ANIMATION_STANDARD_COUNT; i++)
        {
            standardAnimationFiles.add("");
        }
        animationStanding = new ArrayList<>(ANIMATION_STANDING_COUNT);
        for (int i = 0; i != ANIMATION_STANDING_COUNT; i++)
        {
            animationStanding.add("");
        }
        animationCustom = new ArrayList<>(5);
        animationCustomHandle = new ArrayList<>(5);
        for (int i = 0; i != 5; i++)
        {
            animationCustom.add("");
            animationCustomHandle.add("");
        }
        vectorBase = makeDefaultSpriteVector(true, false);
        vectorActivate = makeDefaultSpriteVector(false, false);
    }

    private BoardVector makeDefaultSpriteVector(boolean isCollisionVector, boolean isIsometric)
    {
        BoardVector toReturn = new BoardVector();
        if (isCollisionVector)
        {
            if (isIsometric)
            {
                toReturn.addPoint(-15, 0);
                toReturn.addPoint(0, 7);
                toReturn.addPoint(15, 0);
                toReturn.addPoint(0, -7);
            }
            else
            {
                //toReturn.setTileType(TT_SOLID);   //WARNING: needs to work when tiletypes exist
                toReturn.addPoint(-15, -15);
                toReturn.addPoint(15, -15);
                toReturn.addPoint(15, 0);
                toReturn.addPoint(-15, 0);
            }
        }
        else
        {
            if (isIsometric)
            {
                toReturn.addPoint(-31, 0);
                toReturn.addPoint(0, 15);
                toReturn.addPoint(31, 0);
                toReturn.addPoint(0, -15);
            }
            else
            {
                //toReturn.setTileType(TT_SOLID);   //WARNING: needs to work when tiletypes exist
                toReturn.addPoint(-24, -24);
                toReturn.addPoint(24, -24);
                toReturn.addPoint(24, 8);
                toReturn.addPoint(-24, 8);
            }
        }
        return (toReturn);
    }

    //ACCESS METHODS
    public String getName()
    {
        return (name);
    }

    public String getDescription()
    {
        return (description);
    }

    public void setName(String newName)
    {
        name = newName;
    }

    public void setDescription(String newDesc)
    {
        description = newDesc;
    }

    public boolean open()
    {
        resetAll();
        try
        {
            //setup
            inputStream = new FileInputStream(this.file);
            binaryIO = new BinaryIO(inputStream);
            animationCustom = new ArrayList<>();
            animationCustomHandle = new ArrayList<>();
            //checking for the correct filetype
            String fileHeader = binaryIO.readBinaryString();
            if (fileHeader.equals(FILE_HEADER))
            {
                //version info
                int majorVersion = binaryIO.readBinaryInteger();
                int minorVersion = binaryIO.readBinaryInteger();
                //general data
                name = binaryIO.readBinaryString();
                description = binaryIO.readBinaryString();
                isEquippable = binaryIO.readBinaryByte();
                isMenuDriven = binaryIO.readBinaryByte();
                isBoardDriven = binaryIO.readBinaryByte();
                isBattleDriven = binaryIO.readBinaryByte();
                usersSpecified = binaryIO.readBinaryByte();
                for (int i = 0; i != USER_CHAR_COUNT; i++)
                {
                    userChar.set(i, binaryIO.readBinaryString());
                }
                if (minorVersion >= 3)
                {
                    buyPrice = binaryIO.readBinaryLong();
                    sellPrice = binaryIO.readBinaryLong();
                }
                else
                {
                    buyPrice = binaryIO.readBinaryInteger();
                    sellPrice = binaryIO.readBinaryInteger();
                }
                isKeyItem = binaryIO.readBinaryByte();
                //equipable information
                for (int i = 0; i != EQUIP_COUNT; i++)
                {
                    equipLocation.set(i, binaryIO.readBinaryByte());
                }
                accessory = binaryIO.readBinaryString();
                if (minorVersion >= 3)
                {
                    equipHP = binaryIO.readBinaryLong();
                    equipDP = binaryIO.readBinaryLong();
                    equipFP = binaryIO.readBinaryLong();
                    equipSMP = binaryIO.readBinaryLong();
                    equipProgram = binaryIO.readBinaryString();
                    removeProgram = binaryIO.readBinaryString();
                    //menu-driven information
                    menuHP = binaryIO.readBinaryLong();
                    menuSMP = binaryIO.readBinaryLong();
                    menuProgram = binaryIO.readBinaryString();
                    //battle-driven information
                    fightHP = binaryIO.readBinaryLong();
                    fightSMP = binaryIO.readBinaryLong();
                    fightProgram = binaryIO.readBinaryString();
                    fightAnimation = binaryIO.readBinaryString();
                }
                else
                {
                    equipHP = binaryIO.readBinaryInteger();
                    equipDP = binaryIO.readBinaryInteger();
                    equipFP = binaryIO.readBinaryInteger();
                    equipSMP = binaryIO.readBinaryInteger();
                    equipProgram = binaryIO.readBinaryString();
                    removeProgram = binaryIO.readBinaryString();
                    //menu-driven information
                    menuHP = binaryIO.readBinaryInteger();
                    menuSMP = binaryIO.readBinaryInteger();
                    menuProgram = binaryIO.readBinaryString();
                    //battle-driven information
                    fightHP = binaryIO.readBinaryInteger();
                    fightSMP = binaryIO.readBinaryInteger();
                    fightProgram = binaryIO.readBinaryString();
                    fightAnimation = binaryIO.readBinaryString();
                }
                //board-driven information
                boardMultitaskProgram = binaryIO.readBinaryString();
                boardPickUpProgram = binaryIO.readBinaryString();
                //graphical standard information
                isWide = binaryIO.readBinaryByte();
                if (minorVersion >= 4)   //tk3 item
                {
                    for (int i = 0; i != ANIMATION_STANDARD_COUNT; i++)
                    {
                        standardAnimationFiles.set(i, binaryIO.readBinaryString());
                    }
                    if (minorVersion >= 5)
                    {
                        for (int i = 0; i != ANIMATION_STANDING_COUNT; i++)
                        {
                            animationStanding.set(i, binaryIO.readBinaryString());
                        }
                        //speed information
                        speed = binaryIO.readBinaryDouble();
                        idleTime = binaryIO.readBinaryDouble();
                    }
                    if (minorVersion < 6)
                    {
                        //handling the archaic rest graphic
                        animationStanding.set(ITEM_WALK_S, standardAnimationFiles.get(ITEM_REST));
                        standardAnimationFiles.set(ITEM_REST, "");
                    }
                    long tempMaxArrayVal;
                    tempMaxArrayVal = binaryIO.readBinaryLong() + 1;   //future animationCustomHandle.size()
                    for (long i = 0; i != tempMaxArrayVal; i++)
                    {
                        animationCustom.add(binaryIO.readBinaryString());
                        animationCustomHandle.add(binaryIO.readBinaryString());
                    }
                    //vector information
                    if (minorVersion >= 7) //modern item--uses vectors
                    {
                        BoardVector tempV;
                        long tempX;
                        long tempY;
                        int vectorCount = binaryIO.readBinaryInteger() + 1;
                        for (int i = 0; i != vectorCount; i++)
                        {
                            tempV = new BoardVector();
                            tempMaxArrayVal = binaryIO.readBinaryInteger() + 1;
                            for (int j = 0; j != tempMaxArrayVal; j++)
                            {
                                tempX = binaryIO.readBinaryLong();
                                tempY = binaryIO.readBinaryLong();
                                tempV.addPoint(tempX, tempY);
                            }
                            if (i == 0)
                            {
                                vectorBase = tempV;
                            }
                            else
                            {
                                vectorActivate = tempV;
                            }
                        }
                    }
                }
                else  //old tk2 item (minorVersion < 4)
                {
                    //convert graphics to animations, etc.
                    //WARNING: this part needs to be filled out
                }
            }
            
            inputStream.close();
            
            return true;
        }
        catch (CorruptFileException | IOException e)
        {
            System.err.println(e.toString());
            return false;
        }
    }

    public boolean save()   //WARNING: hasn't been tested yet
    {
        try
        {
            outputStream = new FileOutputStream(this.file);
            binaryIO.setOutputStream(outputStream);
            //header
            binaryIO.writeBinaryString(FILE_HEADER);
            binaryIO.writeBinaryInteger(MAJOR_VERSION);
            binaryIO.writeBinaryInteger(MINOR_VERSION);
            //general data
            binaryIO.writeBinaryString(name);
            binaryIO.writeBinaryString(description);
            binaryIO.writeBinaryByte(isEquippable);
            binaryIO.writeBinaryByte(isMenuDriven);
            binaryIO.writeBinaryByte(isBoardDriven);
            binaryIO.writeBinaryByte(isBattleDriven);
            binaryIO.writeBinaryByte(usersSpecified);
            for (int i = 0; i != USER_CHAR_COUNT; i++)
            {
                binaryIO.writeBinaryString(userChar.get(i));
            }
            binaryIO.writeBinaryLong(buyPrice);
            binaryIO.writeBinaryLong(sellPrice);
            binaryIO.writeBinaryByte(isKeyItem);
            //equippable information
            for (int i = 0; i != EQUIP_COUNT; i++)
            {
                binaryIO.writeBinaryByte(equipLocation.get(i));
            }
            binaryIO.writeBinaryString(accessory);
            binaryIO.writeBinaryLong(equipHP);
            binaryIO.writeBinaryLong(equipDP);
            binaryIO.writeBinaryLong(equipFP);
            binaryIO.writeBinaryLong(equipSMP);
            binaryIO.writeBinaryString(equipProgram);
            binaryIO.writeBinaryString(removeProgram);
            //menu-driven information
            binaryIO.writeBinaryLong(menuHP);
            binaryIO.writeBinaryLong(menuSMP);
            binaryIO.writeBinaryString(menuProgram);
            //battle-driven information
            binaryIO.writeBinaryLong(fightHP);
            binaryIO.writeBinaryLong(fightSMP);
            binaryIO.writeBinaryString(fightProgram);
            binaryIO.writeBinaryString(fightAnimation);
            //board-driven information
            binaryIO.writeBinaryString(boardMultitaskProgram);
            binaryIO.writeBinaryString(boardPickUpProgram);
            //graphical standard information
            binaryIO.writeBinaryByte(isWide);
            for (int i = 0; i != ANIMATION_STANDARD_COUNT; i++)
            {
                binaryIO.writeBinaryString(standardAnimationFiles.get(i));
            }
            for (int i = 0; i != ANIMATION_STANDING_COUNT; i++)
            {
                binaryIO.writeBinaryString(animationStanding.get(i));
            }
            //speed information
            binaryIO.writeBinaryDouble(speed);
            binaryIO.writeBinaryDouble(idleTime);
            binaryIO.writeBinaryLong(animationCustomHandle.size() - 1);
            for (int i = 0; i != animationCustomHandle.size(); i++)
            {
                binaryIO.writeBinaryString(animationCustom.get(i));
                binaryIO.writeBinaryString(animationCustomHandle.get(i));
            }
            //vectors
            binaryIO.writeBinaryInteger(1);
            binaryIO.writeBinaryInteger(vectorBase.getPointCount() - 1);
            for (int i = 0; i != vectorBase.getPointCount(); i++)
            {
                binaryIO.writeBinaryLong(vectorBase.getPointX(i));
                binaryIO.writeBinaryLong(vectorBase.getPointY(i));
            }
            binaryIO.writeBinaryInteger(vectorActivate.getPointCount() - 1);
            for (int i = 0; i != vectorActivate.getPointCount(); i++)
            {
                binaryIO.writeBinaryLong(vectorActivate.getPointX(i));
                binaryIO.writeBinaryLong(vectorActivate.getPointY(i));
            }
            //ending
            outputStream.close();
            return (true);
        }
        catch (IOException e)
        {
            System.err.println(e.toString());
            return false;
        }
    }

    public boolean saveAs(File fileName)
    {
        this.file = fileName;
        return this.save();
    }

    public ArrayList<String> getStandardAnimationFiles()
    {
        return standardAnimationFiles;
    }

    /**
     * Loads the animations into memory, used in the engine, this is not called
     * during load as it would use too much memory to load every animation into memory for
     * all players/enemies/items at once.
     * <p/>
     * This should be called before rendering/gameplay takes place to avoid hindering performance
     */
    public void loadAnimations()
    {
        standardAnimations = new ArrayList<>();
        for (String anmFile : standardAnimationFiles)
        {
            if (!anmFile.equals(""))
            {
                anmFile = anmFile.replace("\\", "/");
                Animation a = new Animation(new File(System.getProperty(
                        "project.path") + "/Misc/" + anmFile));
                standardAnimations.add(a);
            }
        }
        animationTimer = new Timer();
        animationTimer.schedule(new AnimationTimer(), 0, 1000 / 5);
    }

    public void setActiveAnimation(int id)
    {
        activeAnimation = standardAnimations.get(id);
        frameNumber = 0;
        frameCount = (int) activeAnimation.getFrameCount();
    }

    public BufferedImage getAnimationFrame()
    {
        return activeAnimation.getFrame(frameNumber).getFrameImage();
    }
    
    public BufferedImage getAnimationFrame(int id)
    {
        return activeAnimation.getFrame(id).getFrameImage();
    }

    private class AnimationTimer extends TimerTask
    {
        public AnimationTimer()
        {
            super();
        }

        @Override
        public void run()
        {
            if (frameNumber == (frameCount - 1))
            {
                frameNumber = 0;
            }
            else
            {
                frameNumber++;
            }
        }
    }
}

package rpgtoolkit.common.io.types;

//import uk.co.tkce.engine.Texture;
//import uk.co.tkce.engine.TextureLoader;
import rpgtoolkit.common.editor.types.PlayerSpecialMove;
import rpgtoolkit.editor.board.types.BoardVector;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import rpgtoolkit.editor.exceptions.CorruptFileException;

public class Player extends BasicType
{
    // Constants
    private final String FILE_HEADER = "RPGTLKIT CHAR";
    private final int MAJOR_VERSION = 2;
    private final int MINOR_VERSION = 8;

    public static final int DIRECTION_NORTH = 1;
    public static final int DIRECTION_EAST = 2;
    public static final int DIRECTION_SOUTH = 0;
    public static final int DIRECTION_WEST = 3;

    private String name;
    private String expVariableName;
    private String dpVariableName;
    private String fpVariableName;
    private String hpVariableName;
    private String maxHPVariableName;
    private String nameVariableName;
    private String mpVariableName;
    private String maxMPVariableName;
    private String lvlVariableName;
    private long initialExperience;
    private long initialHP;
    private long initialMaxHP;
    private long initialDP;
    private long initialFP;
    private long initialMP;
    private long initialMaxMP;
    private long initialLevel;
    private String profilePicture;
    private ArrayList<PlayerSpecialMove> specialMoveList;
    private String specialMoveName;
    private byte hasSepcialMoves;
    private ArrayList<String> accessoryName;
    private byte armourType[] = new byte[7];
    private long levelType;
    private int expIncreaseFactor;
    private long maxLevel;
    private int percentHPIncrease;
    private int percentMPIncrease;
    private int percentDPIncrease;
    private int percentFPIncrease;
    private String programOnLevelUp;
    private byte levelUpType;
    private byte characterSize;

    // Graphics Variables
    private ArrayList<String> standardGraphics; // 13 Values, S,N,E,W,NW,NE,SW,SE,Att,Def,Spec,Die,Rst
    private ArrayList<Animation> standardGraphicsAnimations;

    // Active animation for the engine
    private Animation activeAnimation;
    private int frameNumber;
    private int frameCount;
    private Timer animationTimer;

    private ArrayList<String> customGraphics;
    private ArrayList<String> customGraphicNames;
    private ArrayList<String> standingGraphics;
    private double idleTimeBeforeStanding;
    private double frameRate;
    private long loopSpeed;
    private BoardVector baseVector;
    private BoardVector activationVector;
    private Area baseArea;

    // Engine Variables
    private int currentXLocation;
    private int currentYLocation;
    private int vectorCorrectionX;
    private int vectorCorrectionY;

    /**
     * Opens a project from an existing file
     *
     * @param file Character (.tem) file to open
     */
    public Player(File file)
    {
        super(file);
        System.out.println("Loading Player: " + this.file);
        this.open();
    }

    /**
     * Creates a new player (for editor use)
     */
    public Player()
    {

    }

    public String getName()
    {
        return name;
    }

    public BoardVector getBaseVector()
    {
        return baseVector;
    }

    public BoardVector getActivationVector()
    {
        return activationVector;
    }

    private boolean open()
    {
        try
        {
            // Prepare the ArrayLists
            specialMoveList = new ArrayList<>();
            accessoryName = new ArrayList<>();
            standardGraphics = new ArrayList<>();
            customGraphics = new ArrayList<>();
            customGraphicNames = new ArrayList<>();
            standingGraphics = new ArrayList<>();
            baseVector = new BoardVector();
            activationVector = new BoardVector();

            if (binaryIO.readBinaryString().equals(FILE_HEADER)) // Valid header
            {
                int majorVersion = binaryIO.readBinaryInteger();
                int minorVersion = binaryIO.readBinaryInteger();

                if (majorVersion == MAJOR_VERSION) // Valid Major Version
                {
                    name = binaryIO.readBinaryString();
                    expVariableName = binaryIO.readBinaryString();
                    dpVariableName = binaryIO.readBinaryString();
                    fpVariableName = binaryIO.readBinaryString();
                    hpVariableName = binaryIO.readBinaryString();
                    maxHPVariableName = binaryIO.readBinaryString();
                    nameVariableName = binaryIO.readBinaryString();
                    mpVariableName = binaryIO.readBinaryString();
                    maxMPVariableName = binaryIO.readBinaryString();
                    lvlVariableName = binaryIO.readBinaryString();
                    initialExperience = binaryIO.readBinaryLong();
                    initialHP = binaryIO.readBinaryLong();
                    initialMaxHP = binaryIO.readBinaryLong();
                    initialDP = binaryIO.readBinaryLong();
                    initialFP = binaryIO.readBinaryLong();
                    initialMP = binaryIO.readBinaryLong();
                    initialMaxMP = binaryIO.readBinaryLong();
                    initialLevel = binaryIO.readBinaryLong();
                    profilePicture = binaryIO.readBinaryString();
                    for (int i = 0; i < 201; i++)
                    {
                        String name = binaryIO.readBinaryString();
                        long minExp = binaryIO.readBinaryLong();
                        long minLevel = binaryIO.readBinaryLong();
                        String cVar = binaryIO.readBinaryString();
                        String cVarTest = binaryIO.readBinaryString();

                        PlayerSpecialMove newSpecialMove = new PlayerSpecialMove(name, minExp, minLevel, cVar, cVarTest);
                        specialMoveList.add(newSpecialMove);
                    }
                    specialMoveName = binaryIO.readBinaryString();
                    hasSepcialMoves = binaryIO.readBinaryByte();
                    for (int i = 0; i < 11; i++)
                    {
                        accessoryName.add(binaryIO.readBinaryString());
                    }
                    for (int i = 0; i < 7; i++)
                    {
                        armourType[i] = binaryIO.readBinaryByte();
                    }
                    if (majorVersion == 3)
                    {
                        levelType = binaryIO.readBinaryByte();
                    }
                    else
                    {
                        levelType = binaryIO.readBinaryLong();
                    }
                    expIncreaseFactor = binaryIO.readBinaryInteger();
                    maxLevel = binaryIO.readBinaryLong();
                    percentHPIncrease = binaryIO.readBinaryInteger();
                    percentDPIncrease = binaryIO.readBinaryInteger();
                    percentFPIncrease = binaryIO.readBinaryInteger();
                    percentMPIncrease = binaryIO.readBinaryInteger();
                    programOnLevelUp = binaryIO.readBinaryString();
                    levelUpType = binaryIO.readBinaryByte();
                    characterSize = binaryIO.readBinaryByte();

                    if (minorVersion > 4)
                    {
                        for (int i = 0; i < 14; i++)
                        {
                            String fileName = binaryIO.readBinaryString();
                            fileName = fileName.replace("\\", "/");
                            standardGraphics.add(fileName);
                        }

                        if (minorVersion > 5)
                        {
                            for (int i = 0; i < 8; i++)
                            {
                                standingGraphics.add(binaryIO.readBinaryString());
                            }
                        }

                        if (minorVersion > 6)
                        {
                            idleTimeBeforeStanding = binaryIO.readBinaryDouble();
                            frameRate = binaryIO.readBinaryDouble();
                        }
                        else
                        {
                            idleTimeBeforeStanding = 3;
                            frameRate = 0.05;
                        }

                        long animationCount = binaryIO.readBinaryLong();
                        for (int i = 0; i < animationCount + 1; i++)
                        {
                            customGraphics.add(binaryIO.readBinaryString());
                            customGraphicNames.add(binaryIO.readBinaryString());

                        }

                        if (minorVersion > 7)
                        {
                            int collisionCount = binaryIO.readBinaryInteger();
                            for (int i = 0; i < collisionCount + 1; i++)
                            {
                                int pointCount = binaryIO.readBinaryInteger();
                                BoardVector tempVect = new BoardVector();

                                for (int j = 0; j < pointCount + 1; j++)
                                {
                                    long x = binaryIO.readBinaryLong();
                                    long y = binaryIO.readBinaryLong();
                                    tempVect.addPoint(x, y);
                                }

                                if (i == 0)
                                {
                                    baseVector = tempVect;
                                }
                                else
                                {
                                    activationVector = tempVect;
                                }
                            }
                        }
                    }
                }
            }

            inputStream.close();

            this.save();

            return true;
        }
        catch (CorruptFileException e)
        {
            System.out.println("File " + file + " is not a supported character file");
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
            binaryIO.writeBinaryString(name);
            binaryIO.writeBinaryString(expVariableName);
            binaryIO.writeBinaryString(dpVariableName);
            binaryIO.writeBinaryString(fpVariableName);
            binaryIO.writeBinaryString(hpVariableName);
            binaryIO.writeBinaryString(maxHPVariableName);
            binaryIO.writeBinaryString(nameVariableName);
            binaryIO.writeBinaryString(mpVariableName);
            binaryIO.writeBinaryString(maxMPVariableName);
            binaryIO.writeBinaryString(lvlVariableName);
            binaryIO.writeBinaryLong(initialExperience);
            binaryIO.writeBinaryLong(initialHP);
            binaryIO.writeBinaryLong(initialMaxHP);
            binaryIO.writeBinaryLong(initialDP);
            binaryIO.writeBinaryLong(initialFP);
            binaryIO.writeBinaryLong(initialMP);
            binaryIO.writeBinaryLong(initialMaxMP);
            binaryIO.writeBinaryLong(initialLevel);
            binaryIO.writeBinaryString(profilePicture);
            for (int i = 0; i < 201; i++)
            {
                PlayerSpecialMove specialMove = specialMoveList.get(i);
                binaryIO.writeBinaryString(specialMove.getName());
                binaryIO.writeBinaryLong(specialMove.getMinExperiance());
                binaryIO.writeBinaryLong(specialMove.getMinLevel());
                binaryIO.writeBinaryString(specialMove.getConditionVariable());
                binaryIO.writeBinaryString(specialMove.getConditionVariableTest());
            }
            binaryIO.writeBinaryString(specialMoveName);
            binaryIO.writeBinaryByte(hasSepcialMoves);
            for (int i = 0; i < 11; i++)
            {
                binaryIO.writeBinaryString(accessoryName.get(i));
            }
            for (int i = 0; i < 7; i++)
            {
                binaryIO.writeBinaryByte(armourType[i]);
            }
            binaryIO.writeBinaryLong(levelType);
            binaryIO.writeBinaryInteger(expIncreaseFactor);
            binaryIO.writeBinaryLong(maxLevel);
            binaryIO.writeBinaryInteger(percentHPIncrease);
            binaryIO.writeBinaryInteger(percentDPIncrease);
            binaryIO.writeBinaryInteger(percentFPIncrease);
            binaryIO.writeBinaryInteger(percentMPIncrease);
            binaryIO.writeBinaryString(programOnLevelUp);
            binaryIO.writeBinaryByte(levelUpType);
            binaryIO.writeBinaryByte(characterSize);
            for (String standardGraphic : standardGraphics)
            {
                binaryIO.writeBinaryString(standardGraphic);
            }
            for (String standingGraphic : standingGraphics)
            {
                binaryIO.writeBinaryString(standingGraphic);
            }
            binaryIO.writeBinaryDouble(idleTimeBeforeStanding);
            binaryIO.writeBinaryDouble(frameRate);
            binaryIO.writeBinaryLong(customGraphicNames.size() - 1);
            for (int i = 0; i < customGraphicNames.size(); i++)
            {
                binaryIO.writeBinaryString(customGraphics.get(i));
                binaryIO.writeBinaryString(customGraphicNames.get(i));
            }
            binaryIO.writeBinaryInteger(1);
            binaryIO.writeBinaryInteger(baseVector.getPointCount() - 1);
            for (int i = 0; i < baseVector.getPointCount(); i++)
            {
                binaryIO.writeBinaryLong(baseVector.getPointX(i));
                binaryIO.writeBinaryLong(baseVector.getPointY(i));
            }
            binaryIO.writeBinaryInteger(activationVector.getPointCount() - 1);
            for (int i = 0; i < activationVector.getPointCount(); i++)
            {
                binaryIO.writeBinaryLong(activationVector.getPointX(i));
                binaryIO.writeBinaryLong(activationVector.getPointY(i));
            }

            outputStream.close();
            return true;
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

    /**
     * Loads the animations into memory, used in the engine, this is not called
     * during load as it would use too much memory to load every animation into memory for
     * all players/enemies/items at once.
     * <p/>
     * This should be called before rendering/game play takes place to avoid hindering performance
     */
    public void loadAnimations()
    {
        System.out.println("Loading Animations for " + this.name);
        standardGraphicsAnimations = new ArrayList<>();
        for (String anmFile : standardGraphics)
        {
            if (!anmFile.equals(""))
            {
                Animation a = new Animation(new File(System.getProperty("project.path") + "/misc/" + anmFile));
                standardGraphicsAnimations.add(a);
            }
        }
        animationTimer = new Timer();
        animationTimer.schedule(new AnimationTimer(), 0, 1000 / 6);
    }

    public void setActiveAnimation(int id)
    {
        activeAnimation = standardGraphicsAnimations.get(id);
        frameNumber = 0;
        frameCount = (int) activeAnimation.getFrameCount();
    }

    public BufferedImage getAnimationFrame()
    {
        return activeAnimation.getFrame(frameNumber).getFrameImage();
    }

//    public void loadTextures(TextureLoader loader)
//    {
//        for (Animation a : standardGraphicsAnimations)
//        {
//            for (int i = 0; i < a.getFrameCount(); i++)
//            {
//                a.getFrame(i).loadTexture(loader);
//            }
//        }
//    }

//    public void render()
//    {
//        glPushMatrix();
//
//        getFrameAsGLTexture().bind();
//        float width = getFrameAsGLTexture().getImageWidth();
//        float height = getFrameAsGLTexture().getImageHeight();
//        glTranslatef(currentXLocation, currentYLocation, 0);
//
//        // draw a quad textured to match the sprite
//        glBegin(GL_QUADS);
//        {
//            glTexCoord2f(0, 0);
//            glVertex2f(0, 0);
//
//            glTexCoord2f(0, getFrameAsGLTexture().getHeight());
//            glVertex2f(0, height);
//
//            glTexCoord2f(getFrameAsGLTexture().getWidth(), getFrameAsGLTexture().getHeight());
//            glVertex2f(width, height);
//
//            glTexCoord2f(getFrameAsGLTexture().getWidth(), 0);
//            glVertex2f(width, 0);
//        }
//        glEnd();
//
//        glPopMatrix();
//    }
//
//    public Texture getFrameAsGLTexture()
//    {
//        return activeAnimation.getFrame(frameNumber).getGlTexture();
//    }

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

    public void preparePhysics(int width, int height)
    {
        vectorCorrectionX = width / 2;
        vectorCorrectionY = height;

        currentXLocation = 1;
        currentYLocation = 1;
    }

    public int getVectorCorrectionX()
    {
        return vectorCorrectionX;
    }

    public int getVectorCorrectionY()
    {
        return vectorCorrectionY;
    }

    public Area getCollisionArea(int correctX, int correctY, int shiftX, int shiftY)
    {
        Polygon collisionPoly = new Polygon();

        for (Point point : baseVector.getPoints())
        {
            collisionPoly.addPoint(point.x + correctX + (currentXLocation + shiftX), point.y + correctY + (currentYLocation + shiftY));
        }
        baseArea = new Area(collisionPoly);
        return baseArea;
    }

    public int getXLocation()
    {
        return currentXLocation;
    }

    public int getYLocation()
    {
        return currentYLocation;
    }

    public void setXLocation(int newXLocation)
    {
        this.currentXLocation = newXLocation;
    }

    public void setYLocation(int newYLocation)
    {
        this.currentYLocation = newYLocation;
    }

    public void adjustX(int adjustment)
    {
        currentXLocation += adjustment;
    }

    public void adjustY(int adjustment)
    {
        currentYLocation += adjustment;
    }
}

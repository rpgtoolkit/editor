package rpgtoolkit.common.io.types;

import rpgtoolkit.editor.exceptions.CorruptFileException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Enemy extends BasicType
{
    // Constants
    private final String FILE_HEADER = "RPGTLKIT ENEMY";
    private final int MAJOR_VERSION = 2;
    private final int MINOR_VERSION = 1;

    // Enemry Variables
    private String name;
    private long hitPoints;
    private long magicPoints;
    private long fightPower;
    private long defencePower;
    private byte canRunAway;
    private int sneakChance;
    private int surpriseChance;
    private ArrayList<String> specialMove;
    private ArrayList<String> weakness;
    private ArrayList<String> strength;
    private byte aiLevel;
    private byte useRPGCodeTatics;
    private String taticsFile;
    private long experianceAwarded;
    private long goldAwarded;
    private String beatEnemyProgram;
    private String runAwayProgram;
    private ArrayList<String> standardGraphics;
    private ArrayList<String> customizedGraphics;
    private ArrayList<String> customizedGraphicsNames;
    private long maxHitPoints;
    private long maxMagicPoints;
    private ArrayList<String> statusEffects;

    public Enemy()
    {

    }

    public Enemy(File fileName)
    {
        super(fileName);
        this.open();
    }

    public boolean open()
    {
        try
        {
            specialMove = new ArrayList<String>();
            weakness = new ArrayList<String>();
            strength = new ArrayList<String>();
            standardGraphics = new ArrayList<String>();
            customizedGraphics = new ArrayList<String>();
            customizedGraphicsNames = new ArrayList<String>();

            if (binaryIO.readBinaryString().equals(FILE_HEADER))
            {
                int majorVersion = binaryIO.readBinaryInteger();
                int minorVersion = binaryIO.readBinaryInteger();

                name = binaryIO.readBinaryString();
                hitPoints = binaryIO.readBinaryLong();
                magicPoints = binaryIO.readBinaryLong();
                fightPower = binaryIO.readBinaryLong();
                defencePower = binaryIO.readBinaryLong();
                canRunAway = (byte) inputStream.read();
                sneakChance = binaryIO.readBinaryInteger();
                surpriseChance = binaryIO.readBinaryInteger();
                int specialMoveCount = binaryIO.readBinaryInteger();
                for (int i = 0; i < specialMoveCount + 1; i++)
                {
                    specialMove.add(binaryIO.readBinaryString());
                }
                int weaknessCount = binaryIO.readBinaryInteger();
                for (int i = 0; i < weaknessCount + 1; i++)
                {
                    weakness.add(binaryIO.readBinaryString());
                }
                int strengthCount = binaryIO.readBinaryInteger();
                for (int i = 0; i < strengthCount + 1; i++)
                {
                    strength.add(binaryIO.readBinaryString());
                }
                aiLevel = (byte) inputStream.read();
                useRPGCodeTatics = (byte) inputStream.read();
                taticsFile = binaryIO.readBinaryString();
                experianceAwarded = binaryIO.readBinaryLong();
                goldAwarded = binaryIO.readBinaryLong();
                beatEnemyProgram = binaryIO.readBinaryString();
                runAwayProgram = binaryIO.readBinaryString();
                int graphicsCount = binaryIO.readBinaryInteger();
                for (int i = 0; i < graphicsCount; i++)
                {
                    standardGraphics.add(binaryIO.readBinaryString().replace("\\", "/"));
                }

                binaryIO.readBinaryString(); // skip one extra string in the file
                long customGraphicsCount = binaryIO.readBinaryLong(); // TK saves as a long, not a int, so need to read the correct value.

                for (int i = 0; i < customGraphicsCount; i++)
                {
                    customizedGraphics.add(binaryIO.readBinaryString());
                    customizedGraphicsNames.add(binaryIO.readBinaryString());
                }
            }

            maxHitPoints = hitPoints;
            maxMagicPoints = magicPoints;

            inputStream.close();

            return true;
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
            binaryIO.writeBinaryString(FILE_HEADER);
            binaryIO.writeBinaryInteger(MAJOR_VERSION);
            binaryIO.writeBinaryInteger(MINOR_VERSION);
            binaryIO.writeBinaryString(name);
            binaryIO.writeBinaryLong(hitPoints);
            binaryIO.writeBinaryLong(magicPoints);
            binaryIO.writeBinaryLong(fightPower);
            binaryIO.writeBinaryLong(defencePower);
            outputStream.write(canRunAway);
            binaryIO.writeBinaryInteger(sneakChance);
            binaryIO.writeBinaryInteger(surpriseChance);
            binaryIO.writeBinaryInteger(specialMove.size());
            for (String aSpecialMove : specialMove)
            {
                binaryIO.writeBinaryString(aSpecialMove);
            }
            binaryIO.writeBinaryInteger(weakness.size());
            for (String weaknes : weakness)
            {
                binaryIO.writeBinaryString(weaknes);
            }
            binaryIO.writeBinaryInteger(strength.size());
            for (String aStrength : strength)
            {
                binaryIO.writeBinaryString(aStrength);
            }
            outputStream.write(aiLevel);
            outputStream.write(useRPGCodeTatics);
            binaryIO.writeBinaryString(taticsFile);
            binaryIO.writeBinaryLong(experianceAwarded);
            binaryIO.writeBinaryLong(goldAwarded);
            binaryIO.writeBinaryString(beatEnemyProgram);
            binaryIO.writeBinaryString(runAwayProgram);
            binaryIO.writeBinaryInteger(standardGraphics.size());
            for (String standardGraphic : standardGraphics)
            {
                binaryIO.writeBinaryString(standardGraphic);
            }
            binaryIO.writeBinaryInteger(customizedGraphics.size());
            for (int i = 0; i < customizedGraphics.size(); i++)
            {
                binaryIO.writeBinaryString(customizedGraphics.get(i));
                binaryIO.writeBinaryString(customizedGraphicsNames.get(i));
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
}

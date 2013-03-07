package rpgtoolkit.common.io.types;

import rpgtoolkit.editor.exceptions.CorruptFileException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SpecialMove extends BasicType
{
    // Constants
    private final String FILE_HEADER = "RPGTLKIT SPLMOVE";
    private final int MAJOR_VERSION = 2;
    private final int MINOR_VERSION = 2;

    private String name;
    private long fightPower;
    private long mpCost;
    private String rpgcodeProgram;
    private long mpDrainedFromTarget;
    private byte canUseInBattle; // Should be a bool
    private byte canUseInMenu;   // Should be a bool :'(
    private String associatedStatusEffect;
    private String associatedAnimation;
    private String description;

    public SpecialMove()
    {

    }

    public SpecialMove(File file)
    {
        super(file);
        this.open();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getFightPower()
    {
        return fightPower;
    }

    public void setFightPower(long fightPower)
    {
        this.fightPower = fightPower;
    }

    public long getMpCost()
    {
        return mpCost;
    }

    public void setMpCost(long mpCost)
    {
        this.mpCost = mpCost;
    }

    public String getRpgcodeProgram()
    {
        return rpgcodeProgram;
    }

    public void setRpgcodeProgram(String rpgcodeProgram)
    {
        this.rpgcodeProgram = rpgcodeProgram;
    }

    public long getMpDrainedFromTarget()
    {
        return mpDrainedFromTarget;
    }

    public void setMpDrainedFromTarget(long mpDrainedFromTarget)
    {
        this.mpDrainedFromTarget = mpDrainedFromTarget;
    }

    public byte getCanUseInBattle()
    {
        return canUseInBattle;
    }

    public void setCanUseInBattle(byte canUseInBattle)
    {
        this.canUseInBattle = canUseInBattle;
    }

    public byte getCanUseInMenu()
    {
        return canUseInMenu;
    }

    public void setCanUseInMenu(byte canUseInMenu)
    {
        this.canUseInMenu = canUseInMenu;
    }

    public String getAssociatedStatusEffect()
    {
        return associatedStatusEffect;
    }

    public void setAssociatedStatusEffect(String associatedStatusEffect)
    {
        this.associatedStatusEffect = associatedStatusEffect;
    }

    public String getAssociatedAnimation()
    {
        return associatedAnimation;
    }

    public void setAssociatedAnimation(String associatedAnimation)
    {
        this.associatedAnimation = associatedAnimation;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean open()
    {
        try
        {
            if (binaryIO.readBinaryString().equals(FILE_HEADER)) // Valid Status File
            {
                int majorVersion = binaryIO.readBinaryInteger();
                int minorVersion = binaryIO.readBinaryInteger();

                if (majorVersion == MAJOR_VERSION)
                {
                    name = binaryIO.readBinaryString();
                    fightPower = binaryIO.readBinaryLong();
                    mpCost = binaryIO.readBinaryLong();
                    rpgcodeProgram = binaryIO.readBinaryString();
                    mpDrainedFromTarget = binaryIO.readBinaryLong();
                    canUseInBattle = binaryIO.readBinaryByte();
                    canUseInMenu = binaryIO.readBinaryByte();
                    associatedStatusEffect = binaryIO.readBinaryString();
                    associatedAnimation = binaryIO.readBinaryString();
                    description = binaryIO.readBinaryString();
                }
            }

            inputStream.close();

            return true;
        }
        catch (CorruptFileException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;

        }
    }

    public boolean save()
    {
        try
        {
            outputStream = new FileOutputStream(this.fileName);
            binaryIO.setOutputStream(outputStream);

            binaryIO.writeBinaryString(FILE_HEADER);
            binaryIO.writeBinaryInteger(MAJOR_VERSION);
            binaryIO.writeBinaryInteger(MINOR_VERSION);
            binaryIO.writeBinaryString(name);
            binaryIO.writeBinaryLong(fightPower);
            binaryIO.writeBinaryLong(mpCost);
            binaryIO.writeBinaryString(rpgcodeProgram);
            binaryIO.writeBinaryLong(mpDrainedFromTarget);
            binaryIO.writeBinaryByte(canUseInBattle);
            binaryIO.writeBinaryByte(canUseInMenu);
            binaryIO.writeBinaryString(associatedStatusEffect);
            binaryIO.writeBinaryString(associatedAnimation);
            binaryIO.writeBinaryString(description);

            outputStream.close();
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
    }

    public boolean saveAs(File fileName)
    {
        this.fileName = fileName;
        return this.save();
    }
}

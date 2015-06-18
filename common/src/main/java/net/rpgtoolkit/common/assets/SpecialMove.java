package net.rpgtoolkit.common.assets;

import net.rpgtoolkit.common.CorruptAssetException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.System.out;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpecialMove extends BasicType implements Asset
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
//        AssetManager.getInstance().addAsset(this);
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

    public boolean getCanUseInBattle()
    {
        return canUseInBattle == 1;
    }

    public void setCanUseInBattle(boolean canUseInBattle)
    {
        if(canUseInBattle == true) { this.canUseInBattle = 1; }
        else { this.canUseInBattle = 0; }
    }

    public boolean getCanUseInMenu()
    {
        return canUseInMenu == 1;
    }

    public void setCanUseInMenu(boolean canUseInMenu)
    {
        if(canUseInMenu == true) { this.canUseInMenu = 1; }
        else { this.canUseInMenu = 0; }
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
//            AssetManager.getInstance().deserialize(getDescriptor());

//            final SpecialMove smove = (SpecialMove) handle.getAsset();
            
//            JSONObject json = JSON.load(this.file);
//            if(json == null) { return this.openBinary(); } //falback to binary
//            this.harvestJSON(json);
            this.inputStream.close(); //not using binary
            return true;
        }
        catch (IOException e)// | CorruptAssetException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
    }

    public boolean save()
    {
        //convert to new format without overwriting old file
        if(this.file.getName().endsWith(".spc")) {
            this.file = new File(this.file.getPath() + ".json");
        }
        try {
            AssetManager.getInstance().serialize(
                    AssetManager.getInstance().getHandle(this));
            return true;
        } catch(IOException | CorruptAssetException ex) {
            Logger.getLogger(SpecialMove.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean saveAs(File fileName)
    {
        this.file = fileName;
        return this.save();
    }
    
    public boolean openBinary()
    {
        out.println("Attempting to open file as binary.");
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
        catch (CorruptAssetException e)
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

    public boolean saveBinary()
    {
        try
        {
            outputStream = new FileOutputStream(this.file);
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

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public AssetDescriptor getDescriptor() {
        return new AssetDescriptor(this.getFile().toURI());
    }

    @Override
    public void reset() {
        this.name = "";
        this.description = "";
        this.mpCost = 0;
        this.fightPower = 0;
        this.rpgcodeProgram = "";
        this.mpDrainedFromTarget = 0;
        this.associatedStatusEffect = "";
        this.associatedAnimation = "";
        this.setCanUseInBattle(false);
        this.setCanUseInMenu(false);
    }
}

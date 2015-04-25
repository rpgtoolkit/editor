package rpgtoolkit.common.io.types;

import rpgtoolkit.editor.exceptions.CorruptFileException;

import java.io.File;
import java.io.IOException;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;

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
    private ArrayList<String> specialMoves;
    private ArrayList<String> weaknesses;
    private ArrayList<String> strengths;
    private byte aiLevel;
    private byte useRPGCodeTatics;
    private String tacticsFile;
    private long experienceAwarded;
    private long goldAwarded;
    private String beatEnemyProgram;
    private String runAwayProgram;
    private ArrayList<String> standardGraphics;
    private final ArrayList<String> standardGraphicsNames = new ArrayList<String>(
            Arrays.asList("Rest", "Attack", "Defend", "Special Move", "Die"));
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
        System.out.println("Loading Enemy " + fileName);
        this.open();
    }

    public boolean open()
    {
        try
        {
            specialMoves = new ArrayList<String>();
            weaknesses = new ArrayList<String>();
            strengths = new ArrayList<String>();
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
                    specialMoves.add(binaryIO.readBinaryString());
                }
                int weaknessCount = binaryIO.readBinaryInteger();
                for (int i = 0; i < weaknessCount + 1; i++)
                {
                    weaknesses.add(binaryIO.readBinaryString());
                }
                int strengthCount = binaryIO.readBinaryInteger();
                for (int i = 0; i < strengthCount + 1; i++)
                {
                    strengths.add(binaryIO.readBinaryString());
                }
                aiLevel = (byte) inputStream.read();
                useRPGCodeTatics = (byte) inputStream.read();
                tacticsFile = binaryIO.readBinaryString();
                experienceAwarded = binaryIO.readBinaryLong();
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
                //TODO: This appears to read 5 custom graphics even when there are fewer than 5, and occasionally read extra blank ones when there are more than 5
//                out.println(customGraphicsCount);

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
            binaryIO.writeBinaryInteger(specialMoves.size());
            for (String aSpecialMove : specialMoves)
            {
                binaryIO.writeBinaryString(aSpecialMove);
            }
            binaryIO.writeBinaryInteger(weaknesses.size());
            for (String weaknes : weaknesses)
            {
                binaryIO.writeBinaryString(weaknes);
            }
            binaryIO.writeBinaryInteger(strengths.size());
            for (String aStrength : strengths)
            {
                binaryIO.writeBinaryString(aStrength);
            }
            outputStream.write(aiLevel);
            outputStream.write(useRPGCodeTatics);
            binaryIO.writeBinaryString(tacticsFile);
            binaryIO.writeBinaryLong(experienceAwarded);
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

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the hitPoints
     */
    public long getHitPoints() {
        return hitPoints;
    }

    /**
     * @param hitPoints the hitPoints to set
     */
    public void setHitPoints(long hitPoints) {
        this.hitPoints = hitPoints;
    }

    /**
     * @return the magicPoints
     */
    public long getMagicPoints() {
        return magicPoints;
    }

    /**
     * @param magicPoints the magicPoints to set
     */
    public void setMagicPoints(long magicPoints) {
        this.magicPoints = magicPoints;
    }

    /**
     * @return the fightPower
     */
    public long getFightPower() {
        return fightPower;
    }

    /**
     * @param fightPower the fightPower to set
     */
    public void setFightPower(long fightPower) {
        this.fightPower = fightPower;
    }

    /**
     * @return the defencePower
     */
    public long getDefencePower() {
        return defencePower;
    }

    /**
     * @param defencePower the defencePower to set
     */
    public void setDefencePower(long defencePower) {
        this.defencePower = defencePower;
    }

    /**
     * @return the canRunAway
     */
    public boolean canRunAway() {
        return canRunAway == 1;
    }

    /**
     * @param canRunAway the canRunAway to set
     */
    public void canRunAway(boolean canRunAway) {
        if(canRunAway == true) { this.canRunAway = 1; }
        else { this.canRunAway = 0; }
    }

    /**
     * @return the sneakChance
     */
    public int getSneakChance() {
        return sneakChance;
    }

    /**
     * @param sneakChance the sneakChance to set
     */
    public void setSneakChance(int sneakChance) {
        this.sneakChance = sneakChance;
    }

    /**
     * @return the surpriseChance
     */
    public int getSurpriseChance() {
        return surpriseChance;
    }

    /**
     * @param surpriseChance the surpriseChance to set
     */
    public void setSurpriseChance(int surpriseChance) {
        this.surpriseChance = surpriseChance;
    }

    /**
     * @return the specialMoves
     */
    public ArrayList<String> getSpecialMoves() {
        return specialMoves;
    }

    /**
     * @return the weaknesses
     */
    public ArrayList<String> getWeaknesses() {
        return weaknesses;
    }

    /**
     * @return the strengths
     */
    public ArrayList<String> getStrengths() {
        return strengths;
    }

    /**
     * @return the aiLevel
     */
    public byte getAiLevel() {
        return aiLevel;
    }

    /**
     * @param aiLevel the aiLevel to set
     */
    public void setAiLevel(byte aiLevel) {
        this.aiLevel = aiLevel;
    }

    /**
     * @return the useRPGCodeTatics
     */
    public boolean useRPGCodeTatics() {
        return useRPGCodeTatics == 1;
    }

    /**
     * @param useRPGCodeTatics the useRPGCodeTatics to set
     */
    public void useRPGCodeTatics(boolean useRPGCodeTatics) {
        if(useRPGCodeTatics == true) { this.useRPGCodeTatics = 1; }
        else { this.useRPGCodeTatics = 0; }
    }

    /**
     * @return the tacticsFile
     */
    public String getTacticsFile() {
        return tacticsFile;
    }

    /**
     * @param tacticsFile the tacticsFile to set
     */
    public void setTacticsFile(String tacticsFile) {
        this.tacticsFile = tacticsFile;
    }

    /**
     * @return the experienceAwarded
     */
    public long getExperienceAwarded() {
        return experienceAwarded;
    }

    /**
     * @param experienceAwarded the experienceAwarded to set
     */
    public void setExperienceAwarded(long experienceAwarded) {
        this.experienceAwarded = experienceAwarded;
    }

    /**
     * @return the goldAwarded
     */
    public long getGoldAwarded() {
        return goldAwarded;
    }

    /**
     * @param goldAwarded the goldAwarded to set
     */
    public void setGoldAwarded(long goldAwarded) {
        this.goldAwarded = goldAwarded;
    }

    /**
     * @return the beatEnemyProgram
     */
    public String getBeatEnemyProgram() {
        return beatEnemyProgram;
    }

    /**
     * @param beatEnemyProgram the beatEnemyProgram to set
     */
    public void setBeatEnemyProgram(String beatEnemyProgram) {
        this.beatEnemyProgram = beatEnemyProgram;
    }

    /**
     * @return the runAwayProgram
     */
    public String getRunAwayProgram() {
        return runAwayProgram;
    }

    /**
     * @param runAwayProgram the runAwayProgram to set
     */
    public void setRunAwayProgram(String runAwayProgram) {
        this.runAwayProgram = runAwayProgram;
    }

    /**
     * @return the standardGraphics
     */
    public ArrayList<String> getStandardGraphics() {
        return standardGraphics;
    }

    /**
     * @return the standardGraphicsNames
     */
    public ArrayList<String> getStandardGraphicsNames() {
        return standardGraphicsNames;
    }

    /**
     * @return the customizedGraphics
     */
    public ArrayList<String> getCustomizedGraphics() {
        return customizedGraphics;
    }

    /**
     * @return the customizedGraphicsNames
     */
    public ArrayList<String> getCustomizedGraphicsNames() {
        return customizedGraphicsNames;
    }

    /**
     * @return the maxHitPoints
     */
    public long getMaxHitPoints() {
        return maxHitPoints;
    }

    /**
     * @param maxHitPoints the maxHitPoints to set
     */
    public void setMaxHitPoints(long maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
    }

    /**
     * @return the maxMagicPoints
     */
    public long getMaxMagicPoints() {
        return maxMagicPoints;
    }

    /**
     * @param maxMagicPoints the maxMagicPoints to set
     */
    public void setMaxMagicPoints(long maxMagicPoints) {
        this.maxMagicPoints = maxMagicPoints;
    }

    /**
     * @return the statusEffects
     */
    public ArrayList<String> getStatusEffects() {
        return statusEffects;
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
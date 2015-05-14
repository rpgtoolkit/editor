package net.rpgtoolkit.common;

/**
 * 
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class PlayerSpecialMove
{
    private String name;
    private long minExperience;
    private long minLevel;
    private String conditionVariable;
    private String conditionVariableTest;

    public PlayerSpecialMove(String name, long minExperience, long minLevel, String cVar, String cVarTest)
    {
        this.name = name;
        this.minExperience = minExperience;
        this.minLevel = minLevel;
        this.conditionVariable = cVar;
        this.conditionVariableTest = cVarTest;
    }

    public String getName()
    {
        return this.name;
    }

    public long getMinExperience()
    {
        return this.minExperience;
    }

    public long getMinLevel()
    {
        return this.minLevel;
    }

    public String getConditionVariable()
    {
        return this.conditionVariable;
    }

    public String getConditionVariableTest()
    {
        return this.conditionVariableTest;
    }
}

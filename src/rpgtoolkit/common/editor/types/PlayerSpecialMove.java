package rpgtoolkit.common.editor.types;

/**
 * 
 * @author Geoff Wilson
 * @author Joshua Michael Daly
 */
public class PlayerSpecialMove
{
    private String name;
    private long minExperiance;
    private long minLevel;
    private String conditionVariable;
    private String conditionVariableTest;

    public PlayerSpecialMove(String name, long minExperiacne, long minLevel, String cVar, String cVarTest)
    {
        this.name = name;
        this.minExperiance = minExperiacne;
        this.minLevel = minLevel;
        this.conditionVariable = cVar;
        this.conditionVariableTest = cVarTest;
    }

    public String getName()
    {
        return this.name;
    }

    public long getMinExperiance()
    {
        return this.minExperiance;
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


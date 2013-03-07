package rpgtoolkit.editor.board.types;

import rpgtoolkit.common.io.types.BasicType;

public class BoardProgram extends BasicType
{
    private long layer;
    private String graphic;
    private String fileName;
    private long activate;
    private String initialVariable;
    private String finalVariable;
    private String initialValue;
    private String finalValue;
    private long activationType;

    private BoardVector vector;
    private long distanceRepeat;

    public BoardProgram()
    {
        super();
    }

    public long getLayer()
    {
        return layer;
    }

    public String getGraphic()
    {
        return graphic;
    }

    public String getFileName()
    {
        return fileName;
    }

    public long getActivate()
    {
        return activate;
    }

    public String getInitialVariable()
    {
        return initialVariable;
    }

    public String getFinalVariable()
    {
        return finalVariable;
    }

    public String getInitialValue()
    {
        return initialValue;
    }

    public String getFinalValue()
    {
        return finalValue;
    }

    public long getActivationType()
    {
        return activationType;
    }

    public BoardVector getVector()
    {
        return vector;
    }

    public long getDistanceRepeat()
    {
        return distanceRepeat;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public void setLayer(long layer)
    {
        this.layer = layer;
    }

    public void setGraphic(String graphic)
    {
        this.graphic = graphic;
    }

    public void setActivate(long activate)
    {
        this.activate = activate;
    }

    public void setInitialVariable(String initialVariable)
    {
        this.initialVariable = initialVariable;
    }

    public void setFinalVariable(String finalVariable)
    {
        this.finalVariable = finalVariable;
    }

    public void setInitialValue(String initialValue)
    {
        this.initialValue = initialValue;
    }

    public void setFinalValue(String finalValue)
    {
        this.finalValue = finalValue;
    }

    public void setActivationType(long activationType)
    {
        this.activationType = activationType;
    }

    public void setVector(BoardVector vector)
    {
        this.vector = vector;
    }

    public void setDistanceRepeat(long distanceRepeat)
    {
        this.distanceRepeat = distanceRepeat;
    }
}

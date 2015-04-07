package rpgtoolkit.editor.board.types;

import rpgtoolkit.common.io.types.BasicType;

public class BoardProgram extends BasicType implements Cloneable
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

    /*
     * ************************************************************************* 
     * Public Constructors
     * *************************************************************************
     */
    
    public BoardProgram()
    {
        super();
    }

    /*
     * ************************************************************************* 
     * Public Getters and Setters
     * *************************************************************************
     */
    
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
    
    /*
     * ************************************************************************* 
     * Public Methods
     * *************************************************************************
     */

    /**
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        super.clone();
        
        BoardProgram clone = new BoardProgram();
        clone.activate = this.activate;
        clone.activationType = this.activationType;
        clone.distanceRepeat = this.distanceRepeat;
        clone.fileName = this.fileName;
        clone.finalValue = this.finalValue;
        clone.finalVariable = this.finalVariable;
        clone.graphic = this.graphic;
        clone.initialValue = this.initialValue;
        clone.initialVariable = this.initialVariable;
        clone.layer = this.layer;
        clone.vector = (BoardVector)this.vector.clone();
        
        return clone;
    }
}

package zrx.simulate.basicDataContainer;

abstract public class BeamParameter {

    public String beamParameterType;
    public boolean alreadySetBooleam;

    abstract public boolean isAlreadySet();

    abstract public void printToInformationTextArea();

    abstract public void clear();
}

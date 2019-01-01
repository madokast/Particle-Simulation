package zrx.simulate.basicDataContainer;

public class BeamParameterTwissGauss extends BeamParameter {
    private static BeamParameterTwissGauss beamParameterTwissGauss;
    public static BeamParameterTwissGauss getInstance()
    {
        if(beamParameterTwissGauss==null)
            beamParameterTwissGauss = new BeamParameterTwissGauss();

        return beamParameterTwissGauss;
    }


    public BeamParameterTwiss beamParameterTwiss;

    @Override
    public void printToInformationTextArea() {
        //
    }

    @Override
    public void clear() {
        //
    }

    @Override
    public boolean isAlreadySet() {
        return false;
    }
}

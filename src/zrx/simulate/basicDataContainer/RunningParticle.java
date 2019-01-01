package zrx.simulate.basicDataContainer;

import zrx.simulate.basicDataStructure.PositionVector;
import zrx.simulate.basicDataStructure.VelocityVector;

public class RunningParticle{
    public static String particleType;
    public static double staticMass;
    public static double chargeQuantity;

    public PositionVector positionVector;
    public VelocityVector velocityVector;
    public double KineticEnergyMeV;


    public static double stepLength;
    public static double totalDistance;

    public int number;
    public double time;
    public double distance;
}
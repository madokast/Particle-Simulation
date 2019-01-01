package zrx.simulate.basicDataStructure;

public class MagneticFieldValue {
    public MagneticVector magneticVector;
    public PositionVector positionVector;

    public MagneticFieldValue(PositionVector positionVector,MagneticVector magneticVector) {
        this.magneticVector = new MagneticVector(magneticVector);
        this.positionVector = new PositionVector(positionVector);
    }

    public void print()
    {
        System.out.println("MagneticFieldValue: ");
        magneticVector.print();
        positionVector.print();
    }
}

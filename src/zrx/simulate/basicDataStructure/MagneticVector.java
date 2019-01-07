package zrx.simulate.basicDataStructure;

public class MagneticVector extends VectorDouble {
    public MagneticVector(double x, double y, double z) {
        super(x, y, z);
    }

    public MagneticVector(MagneticVector m)
    {
        this(m.x,m.y,m.z);
    }

    public MagneticVector(TriNumberDouble v)
    {
        this(v.x,v.y,v.z);
    }

    @Override
    public void print() {
        System.out.print("MagneticVector: ");
        super.print();
    }
}

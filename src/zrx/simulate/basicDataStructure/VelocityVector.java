package zrx.simulate.basicDataStructure;

public class VelocityVector extends VectorDouble {
    public VelocityVector(double x,double y,double z)
    {
        super(x,y,z);
    }

    public VelocityVector(VelocityVector v)
    {
        this(v.x,v.y,v.z);
    }

    @Override
    public void print() {
        System.out.print("VelocityVector: ");
        super.print();
    }
}

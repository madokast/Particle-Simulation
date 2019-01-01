package zrx.simulate.basicDataStructure;

public class PositionVector extends VectorDouble {
    public PositionVector(double x, double y, double z) {
        super(x, y, z);
    }

    public PositionVector(PositionVector p)
    {
        this(p.x,p.y,p.z);
    }

    @Override
    public void print() {
        System.out.print("PositionVector: ");
        super.print();
    }
}

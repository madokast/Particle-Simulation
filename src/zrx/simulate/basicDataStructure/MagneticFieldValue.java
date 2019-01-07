package zrx.simulate.basicDataStructure;

public class MagneticFieldValue {
    public MagneticVector m;
    public PositionVector p;

    public MagneticFieldValue(PositionVector p, MagneticVector m) {
        this.m = new MagneticVector(m);
        this.p = new PositionVector(p);
    }

    public void print()
    {
        System.out.println("MagneticFieldValue: ");
        m.print();
        p.print();
    }
}

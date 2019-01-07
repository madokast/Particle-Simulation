package zrx.simulate.basicDataStructure;

public class PositionVector extends VectorDouble {
    public PositionVector(double x, double y, double z) {
        super(x, y, z);
    }

    public PositionVector(PositionVector p)
    {
        this(p.x,p.y,p.z);
    }

    public PositionVector(TriNumberDouble v)
    {
        this(v.x,v.y,v.z);
    }

    public void addSelf(TriNumberDouble d)
    {
        this.x+=d.x;
        this.y+=d.y;
        this.z+=d.z;
    }

    @Override
    public void print() {
        System.out.print("PositionVector: ");
        super.print();
    }
}

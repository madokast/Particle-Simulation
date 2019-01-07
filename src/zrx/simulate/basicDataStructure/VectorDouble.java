package zrx.simulate.basicDataStructure;

public class VectorDouble extends TriNumberDouble {
    public VectorDouble(double x,double y,double z)
    {
        super(x,y,z);
    }

    public VectorDouble(final VelocityVector v)
    {
        this(v.x,v.y,v.z);
    }

    public VectorDouble(final TriNumberDouble v)
    {
        this(v.x,v.y,v.z);
    }

    public void NormalizeSelf()
    {
        double length = this.length();

        this.x = this.x/length;
        this.y = this.y/length;
        this.z = this.z/length;
    }
}

//public class TriNumberDouble {
//    public double x;
//    public double y;
//    public double z;

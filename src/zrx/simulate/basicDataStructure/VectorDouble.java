package zrx.simulate.basicDataStructure;

public class VectorDouble extends TriNumberDouble {
    public VectorDouble(double x,double y,double z)
    {
        super(x,y,z);
    }

    public VectorDouble(VelocityVector v)
    {
        this(v.x,v.y,v.z);
    }

    public double length() { return Math.sqrt(x*x+y*y+z*z); }

    public void NormalizeSelf()
    {
        double length = this.length();

        this.x = this.x/length;
        this.y = this.y/length;
        this.z = this.z/length;
    }

    public static VectorDouble scalarMultipVct(double scalar,VectorDouble vct)
    {
        return new VectorDouble(scalar*vct.x,scalar*vct.y,scalar*vct.z);
    }
}

//public class TriNumberDouble {
//    public double x;
//    public double y;
//    public double z;

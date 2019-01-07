package zrx.simulate.basicDataStructure;

import zrx.gui.realPlot.DirectionXYZ2D;
import zrx.gui.setParticle.ErrorDialog;

public class TriNumberDouble {
    public double x;
    public double y;
    public double z;

    public TriNumberDouble(double x,double y,double z)
    {
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public TriNumberDouble(TriNumberDouble t)
    {
        this(t.x,t.y,t.z);
    }

    public static TriNumberDouble mTommNew(final TriNumberDouble t)
    {
        return new TriNumberDouble(t.x*1000,t.y*1000,t.z*1000);
    }


    public void print()
    {
        System.out.printf("%e\t%e\t%e\n",x,y,z);
    }

    @Override
    public String toString() {
        return "x="+x+"  y="+y+"  z="+z;
    }

    @Override
    public int hashCode() {
        long lx = Double.doubleToLongBits(x);
        long ly = Double.doubleToLongBits(y);
        long lz = Double.doubleToLongBits(z);
        return (int)(lx^(lx>>>32))+(int)(ly^(ly>>>32))+(int)(lz^(lz>>>32));
    }

    //投影 三维 投影到二维空间
    public BiNumberDouble projection(DirectionXYZ2D directionXYZ2D)
    {
        switch (directionXYZ2D)
        {
            case XY:
                return new BiNumberDouble(this.x,this.y);
                //break;//unreachable
            case XZ:
                return new BiNumberDouble(this.x,this.z);
            case YX:
                return new BiNumberDouble(this.y,this.x);
            case YZ:
                return new BiNumberDouble(this.y,this.z);
            case ZX:
                return new BiNumberDouble(this.z,this.x);
            case ZY:
                return new BiNumberDouble(this.z,this.y);
        }

        ErrorDialog.getInstance().setErrorInformationToLabelAndVisible(
                "Error in [public BiNumberDouble projection(DirectionXYZ2D directionXYZ2D)]."+
                        "Maybe a nullPointException");
        return new BiNumberDouble(0.0,0.0);
    }

    public static TriNumberDouble trinumberAddTrinumber(TriNumberDouble a,TriNumberDouble b)
    {
        return new TriNumberDouble(
                a.x+b.x,
                a.y+b.y,
                a.z+b.z);
    }

    public static TriNumberDouble trinumberAdds(TriNumberDouble...triNumberDoubles)
    {
        TriNumberDouble ans = new TriNumberDouble(0,0,0);
        for(TriNumberDouble t:triNumberDoubles)
        {
            ans.x+=t.x;
            ans.y+=t.y;
            ans.z+=t.z;
        }

        return ans;
    }

    //矢量标乘
    public static TriNumberDouble scalarMultipleTriNumberNew(double k, TriNumberDouble a)
    {
        return new TriNumberDouble(
                k*a.x,
                k*a.y,
                k*a.z);
    }

    public static double dotProduct(TriNumberDouble a,TriNumberDouble b)
    {
        return a.x*b.x+
                a.y*b.y+
                a.z*b.z;
    }

    public static TriNumberDouble crossProduct(TriNumberDouble a,TriNumberDouble b)
    {
        return new TriNumberDouble(
                a.y*b.z - a.z*b.y,
                -a.x*b.z + a.z*b.x,
                a.x*b.y - a.y*b.x
        );
    }

    public static TriNumberDouble trinumberSubtractTrinumber(TriNumberDouble a,TriNumberDouble b)
    {
        return new TriNumberDouble(
                a.x - b.x,
                a.y - b.y,
                a.z - b.z
        );
    }

    public double length()
    {
        return Math.sqrt(x*x+y*y+z*z);
    }

    public void NormalizeSelf()
    {
        double length = this.length();

        this.x = this.x/length;
        this.y = this.y/length;
        this.z = this.z/length;
    }

    public static TriNumberDouble normalizeTrinumberNew(final TriNumberDouble v)
    {
        double length = v.length();

        return new TriNumberDouble(
                v.x/length,
                v.y/length,
                v.z/length
        );
    }


}
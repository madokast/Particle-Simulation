package zrx.simulate.basicDataStructure;

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
}

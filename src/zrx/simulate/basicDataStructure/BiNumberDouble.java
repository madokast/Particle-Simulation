package zrx.simulate.basicDataStructure;

public class BiNumberDouble {
    public double x;
    public double y;

    //单位变换 m 到 mm ，返回新的点，和原来的无关
    public static BiNumberDouble mTOmm(final BiNumberDouble m)
    {
        return new BiNumberDouble(m.x*1000,m.y*1000);
    }

    public static void mTOmmSelf(BiNumberDouble biNumberDouble)
    {
        biNumberDouble.x*=1000;
        biNumberDouble.y*=1000;
    }

    public static void ArrMtoMMSelf(BiNumberDouble[] biNumberDoubles)
    {
        for(int i=0;i<biNumberDoubles.length;i++)
        {
            BiNumberDouble.mTOmmSelf(biNumberDoubles[i]);
        }
    }

    ///二维矢量点 距离
    public static double distance(BiNumberDouble a,BiNumberDouble b)
    {
        return Math.sqrt((a.x - b.x)*(a.x - b.x) + (a.y - b.y)*(a.y - b.y));
    }

    public BiNumberDouble() {}

    public BiNumberDouble(double x,double y)
    {
        this.x=x;
        this.y=y;
    }

    public void print()
    {
        System.out.printf("%e\t%e\n",x,y);
    }

    @Override
    public String toString() {
        return "x="+x+"  y="+y;
    }

    @Override
    public int hashCode() {
        long lx = Double.doubleToLongBits(x);
        long ly = Double.doubleToLongBits(y);
        return (int)(lx^(lx>>>32))+(int)(ly^(ly>>>32));
    }
}

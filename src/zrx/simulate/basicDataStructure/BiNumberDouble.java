package zrx.simulate.basicDataStructure;

import zrx.gui.setParticle.ErrorDialog;
import zrx.simulate.tool.SpecialNumber;

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

    //二维矢量点 距离
    public static double distance(BiNumberDouble a,BiNumberDouble b)
    {
        return Math.sqrt((a.x - b.x)*(a.x - b.x) + (a.y - b.y)*(a.y - b.y));
    }

    //旋转逆时针
    public static BiNumberDouble rotateNew(double phi,BiNumberDouble a)
    {
        // | cos  -sin ||x|
        // | sin   cos ||y|

        return new BiNumberDouble(
                Math.cos(phi)*a.x-Math.sin(phi)*a.y,
                Math.sin(phi)*a.x+Math.cos(phi)*a.y
        );
    }

    public static BiNumberDouble binumberAddBinumberNew(BiNumberDouble a,BiNumberDouble b)
    {
        return new BiNumberDouble(a.x+b.x,a.y+b.y);
    }

    public static BiNumberDouble scalarMultipleNew(double k,BiNumberDouble a)
    {
        return new BiNumberDouble(k*a.x,k*a.y);
    }

    public static BiNumberDouble normalizeNew(BiNumberDouble a)
    {
        double length = a.length();

        if(length==0.0)
        {
            ErrorDialog.getInstance().setErrorInformationToLabelAndVisible(
                    "/0 error in [public static BiNumberDouble normalizeNew(BiNumberDouble a)]");
            return new BiNumberDouble(0.0,0.0);
        }

        return new BiNumberDouble(a.x/length,a.y/length);
    }

    public double length()
    {
        return Math.sqrt(x*x+y*y);
    }

    public static BiNumberDouble reverseNew(BiNumberDouble a)
    {
        return new BiNumberDouble(-a.x,-a.y);
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

    //二维向量和X轴的夹角 0-pi (旋转角类似)
    public static double getRotateAngleByBiNumber(final BiNumberDouble b)
    {
        double x = b.x;
        double y = b.y;

        if(y<0)
        {
           x*=-1;
           y*=-1;
        }

        if(Math.abs(x)< SpecialNumber.MINrealNonnegative)
        {
            if(x>=0)
                return 0.0;
            else
                return Math.PI;
        }
        else if(Math.abs(y)<SpecialNumber.MINrealNonnegative)
        {
            return Math.PI/2.0;
        }
        else
        {
            if(x>0)
                return Math.atan(y/x);
                //return Math.atan(x/y);让您们好好看看我犯了什么错，和空气斗智斗勇一个多小时，气炸了
            else
                return Math.atan(y/x)+Math.PI;
                //return Math.atan(x/y)+Math.PI;
        }
    }

    public static Matrix22 getRotateMatrixByBiNumber(final BiNumberDouble b)
    {
        return Matrix22.rotateMatrix22(getRotateAngleByBiNumber(b));
    }
}

package zrx.simulate.tool;

import zrx.simulate.basicDataStructure.BiNumberDouble;

public class Ellipse {
    //椭圆方程Ax^2+Bxy+Cy^2=D
    public double A;
    public double B;
    public double C;
    public double D;

    public Ellipse(double a, double b, double c, double d) {
        A = a;
        B = b;
        C = c;
        D = d;
    }

    //椭圆圆周上均匀分布的点，数目num
    public static BiNumberDouble[] pointAtEllipseEdge(Ellipse ellipse,int num)
    {
        BiNumberDouble[] biNumberDoubles = new BiNumberDouble[num];

        double circumference = CircumferenceOfEllipse(ellipse);//椭圆周长

        for(int i=0;i<num;i++)
        {
            biNumberDoubles[i]=pointAtEllipseEdgeAnticlockwise(ellipse,circumference*i/num);
        }

        return biNumberDoubles;
    }

    //椭圆周长
    private static double CircumferenceOfEllipse(Ellipse ellipse)
    {
        double circumference = 0.0;
        BiNumberDouble p = new BiNumberDouble();
        BiNumberDouble q = new BiNumberDouble();

        for(int i=0, n=10000;i<n;i++) {
            p = pointAtEllipseEdgeOrientTH(ellipse, 2 * Math.PI * i / n);
            q = pointAtEllipseEdgeOrientTH(ellipse, 2 * Math.PI * (i + 1) / n);
            circumference += BiNumberDouble.distance(p, q);
        }

        return circumference;

        //double peri = 0.0;
        //	struct divct p, q;
        //	int i;
        //	int n = 10000;
        //	for (i = 0; i < n; i++) {
        //		p = point_ellip_orient(A, B, C, D, 2 * pi *i / n);
        //		q = point_ellip_orient(A, B, C, D, 2 * pi* (i + 1) / n);
        //		peri += distence_divct(p, q);
        //	}
        //
        //	return peri;
    }

    //以椭圆圆周上的点(x=0,y)出发，逆时针运动，在圆周上运行distance长度后的点，返回该点
    private static BiNumberDouble pointAtEllipseEdgeAnticlockwise(Ellipse ellipse,double distance)
    {
        BiNumberDouble p = new BiNumberDouble();
        BiNumberDouble q = new BiNumberDouble();

        double length=0.0;
        for(int i=0, n=10000;i<n;i++)
        {
            p=pointAtEllipseEdgeOrientTH(ellipse,2 * Math.PI *i / n);
            q=pointAtEllipseEdgeOrientTH(ellipse,2 * Math.PI *(i+1) / n);
            length+=BiNumberDouble.distance(p,q);

            if(length>distance)
                break;
        }

        return p;
    }

    //原点出发，方向th弧度的射线和椭圆Ax^2+Bxy+Cy^2=D的交点
    private static BiNumberDouble pointAtEllipseEdgeOrientTH(Ellipse ellipse,double th)
    {
        BiNumberDouble d = new BiNumberDouble();

        ////将弧度th限定在0~2pi
        while(th<0)
            th+=2*Math.PI;
        while(th>2*Math.PI)
            th-=2*Math.PI;

        if (Math.abs(th) < SpecialNumber.MINrealNonnegative || Math.abs(th - 2 * Math.PI)<SpecialNumber.MINrealNonnegative) {
            d.x = Math.sqrt(ellipse.D / ellipse.A);
            d.y = 0;
        }
        if (Math.abs(th - Math.PI) < SpecialNumber.MINrealNonnegative) {
            d.x = -Math.sqrt(ellipse.D / ellipse.A);
            d.y = 0;
        }
        //临界问题
        double t;
        if (th > 0 && th < Math.PI) {
            t = 1 / Math.tan(th);
            d.y = Math.sqrt(ellipse.D / (ellipse.A * t*t + ellipse.B * t + ellipse.C));
            d.x = t * d.y;
            //printf("\ntest\n");
            //printf_divct(d);
        }
        if (th > Math.PI&&th < 2 * Math.PI) {
            th -= Math.PI;
            t = 1 / Math.tan(th);
            d.y = -Math.sqrt(ellipse.D / (ellipse.A * t*t + ellipse.B * t + ellipse.C));
            d.x = t * d.y;
        }
        //射线——负号问题，象限问题

        return d;
    }
}

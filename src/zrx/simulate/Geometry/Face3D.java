package zrx.simulate.Geometry;

import zrx.simulate.basicDataStructure.TriNumberDouble;

//import javax.transaction.TransactionRequiredException;

public class Face3D {
    //Ax+By+Cz+D=0 (参数,A,B,C,D是描述平面空间特征的常数)
    private double A;
    private double B;
    private double C;
    private double D;

    //不允許隨隨便便得到平面方程
    private Face3D(double a, double b, double c, double d) {
        A = a;
        B = b;
        C = c;
        D = d;
    }

    //由過平面的一點，和法向得到平面方向
    public static Face3D getFace(TriNumberDouble position,TriNumberDouble velocity)
    {
        //一般式Ax+By+Cz+D=0，平面的法向量为(A,B,C)

        double A=velocity.x;
        double B=velocity.y;
        double C=velocity.z;
        double D=-(A*position.x+B*position.y+C*position.z);

        return new Face3D(A,B,C,D);
    }

    //过两点的直线和面的交点
    public TriNumberDouble crossPoint(TriNumberDouble a,TriNumberDouble b)
    {
        double av = pointAT(a);
        double bv = pointAT(b);

        TriNumberDouble ab = TriNumberDouble.trinumberSubtractTrinumber(b,a);//向量a->b

        return new TriNumberDouble(
                TriNumberDouble.trinumberAddTrinumber(a,
                        TriNumberDouble.scalarMultipleTriNumberNew(Math.abs(av)/(Math.abs(av)+Math.abs(bv)),
                                ab))
        );
    }

    //如果ab两点处于面两边
    //那么返回ab两点直线和面的交点
    //否则返回null
    public TriNumberDouble ifBetweenGetCrossNew(TriNumberDouble a,TriNumberDouble b)
    {
        double av = pointAT(a);
        double bv = pointAT(b);

        if(av==0)
            return new TriNumberDouble(a);

        if(bv==0)
            return new TriNumberDouble(b);

        if(av*bv<0)
        {
            return crossPoint(a,b);

            //x =
            //-(D*ax - D*bx + B*ax*by - B*ay*bx + C*ax*bz - C*az*bx)/(A*ax - A*bx + B*ay - B*by + C*az - C*bz)
            //y =
            //-(D*ay - D*by - A*ax*by + A*ay*bx + C*ay*bz - C*az*by)/(A*ax - A*bx + B*ay - B*by + C*az - C*bz)
            //z =
            //-(D*az - D*bz - A*ax*bz + A*az*bx - B*ay*bz + B*az*by)/(A*ax - A*bx + B*ay - B*by + C*az - C*bz)

            //先放弃
            //-(D*a.x - D*b.x + B*a.x*b.y - B*a.y*b.x + C*a.x*b.z - C*a.z*b.x)/
            //                            (A*a.x - A*b.x + B*a.y - B*b.y + C*a.z - C*b.z),
            //                    -(D*a.y - D*b.y - A*a.x*b.y + A*a.y*b.x + C*a.y*b.z - C*a.z*b.y)/
            //                            (A*a.x - A*b.x + B*a.y - B*b.y + C*a.z - C*b.z),
            //                    -(D*a.z - D*b.z - A*a.x*b.z + A*a.z*b.x - B*a.y*b.z + B*a.z*b.y)/
            //                            (A*a.x - A*b.x + B*a.y - B*b.y + C*a.z - C*b.z)

            //另外一个方法
            //有所求点 ans = a + k*(ab向量)
            //设 da  db  分别为  点a  点b  到面的距离
            //那么 k = da/(da+db)
            //
            //应该如此

        }
        else
            return null;
    }

    private boolean isPointsBetween(TriNumberDouble a,TriNumberDouble b)
    {
        if(pointAT(a)*pointAT(b)<0)
            return true;
        else
            return false;
    }

    //計算Ax+By+Cz+D的值
    public double pointAT(TriNumberDouble point)
    {
        return A*point.x+B*point.y+C*point.z+D;
    }

    @Override
    public String toString() {
        return "Face3D{" +
                  A +
                "x+" + B +
                "y+" + C +
                "z+" + D + "=0"+
                '}';
    }

    public boolean inFront(TriNumberDouble a)
    {
        if(pointAT(a)<0)
            return true;
        else
            return false;
    }
}

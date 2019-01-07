package zrx.simulate.basicDataStructure;

public class Matrix22 {
    // |a b|11 12
    // |c d|21 22
    public double a11;
    public double b12;
    public double c21;
    public double d22;

    public Matrix22(double a11, double b12, double c21, double d22) {
        this.a11 = a11;
        this.b12 = b12;
        this.c21 = c21;
        this.d22 = d22;
    }

    //逆时针旋转矩阵
    public static Matrix22 rotateMatrix22(double phi)
    {
        return new Matrix22(
                Math.cos(phi),        -Math.sin(phi),
                Math.sin(phi),         Math.cos(phi)
        );
    }

    //二维向量乘二维矩阵
    public static BiNumberDouble biNumberDoubleMultiMatrix(BiNumberDouble a,Matrix22 m)
    {
        return new BiNumberDouble(
                m.a11*a.x+m.b12*a.y,
                m.c21*a.x+m.d22*a.y
        );
    }
}

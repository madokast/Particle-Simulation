package zrx.simulate.basicDataStructure;

public class AccelerateVector extends VectorDouble {
    public AccelerateVector(double x, double y, double z) {
        super(x, y, z);
    }

    public AccelerateVector(TriNumberDouble v)
    {
        this(v.x,v.y,v.z);
    }

    //原本是個函數
    //struct vct accelerate(double e, double m, struct vct v, struct vct b)//加速度矢量a=(e/m)v×b
    //{
    //	struct vct vb;//v×b
    //	struct vct a;//加速度
    //
    //	vb = cross_product(v, b);
    //	a = scalar_multip_vct(e / m, vb);
    //
    //	return a;
    //}
    public AccelerateVector(double e,double m,VelocityVector v,MagneticVector b)
    {
        this(TriNumberDouble.scalarMultipleTriNumberNew(
                e/m,
                TriNumberDouble.crossProduct(v,b)
        ));
    }
}

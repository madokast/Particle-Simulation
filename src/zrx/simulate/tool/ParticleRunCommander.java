package zrx.simulate.tool;

import zrx.gui.realPlot.DiretionXYZ1D;
import zrx.gui.realPlot.PlotRealSpace;
import zrx.gui.informationWindow.InformationTextArea;
import zrx.simulate.advancedDataContainer.Beam;
import zrx.simulate.advancedDataContainer.DetectorWay;
import zrx.simulate.advancedDataContainer.DetectorsSet;
import zrx.simulate.advancedDataContainer.ReferredParticleTrack;
import zrx.simulate.basicDataContainer.ImportedMagnet;
import zrx.simulate.basicDataContainer.ReferredParticle;
import zrx.simulate.basicDataContainer.RunningParticle;
import zrx.simulate.basicDataStructure.*;

public class ParticleRunCommander {
    //以下重要東西由运动主函数寻找，顺便可以得知相关数据是否存在
    //三方向點數
    public static int DISX=0;
    public static int DISY=0;
    public static int DISZ=0;

    //三方向點間距
    public static double GAPX=0.0;
    public static double GAPY=0.0;
    public static double GAPZ=0.0;

    //三方向初始位置--或者説最小點
    public static double INIX=0.0;
    public static double INIY=0.0;
    public static double INIZ=0.0;

    //以下重要内容由simulate告知
    //運動步長
    public static double STEP_DS=0.0;//單位m，即0.5mm
    public static double e=0.0;//電荷信息，一次性跑的粒子電荷一定都相同（動質量可能不同）

    public static double MAXDISTANCE=0.0;

    //跑出磁場，默认为false
    public static boolean outof = false;


    //拉格朗日插值法 4個點
    //from C src
    public static double lagrangInterpole(double x,
                                          double x0, double y0,
                                          double x1, double y1,
                                          double x2, double y2,
                                          double x3, double y3)
    {
        if (Math.abs(x - x0) <= SpecialNumber.MINrealNonnegative)
            return y0;

        if (Math.abs(x - x1) <= SpecialNumber.MINrealNonnegative)
            return y1;

        if (Math.abs(x - x2) <= SpecialNumber.MINrealNonnegative)
            return y2;

        if (Math.abs(x - x3) <= SpecialNumber.MINrealNonnegative)
            return y3;

        double t0, t1, t2, t3, tt;
        t0 = (x - x1)*(x - x2)*(x - x3)*y0 / ((x0 - x1)*(x0 - x2)*(x0 - x3));
        t1 = (x - x0)*(x - x2)*(x - x3)*y1 / ((x1 - x0)*(x1 - x2)*(x1 - x3));
        t2 = (x - x0)*(x - x1)*(x - x3)*y2 / ((x2 - x0)*(x2 - x1)*(x2 - x3));
        t3 = (x - x0)*(x - x1)*(x - x2)*y3 / ((x3 - x0)*(x3 - x1)*(x3 - x2));
        //printf("\n*lagrange_interpolation%le %le %le %le*\n", t0, t1, t2, t3);

        tt=t0 + t1 + t2 + t3;

        if (Double.isNaN(tt)) {
            //System.out.println("*error in lagrange_interpolation_isnan*\n");
            return -1.0;
        }//错误判断

        //printf("\n%le %le %le %le %le %le %le %le %le\n", x, x0, y0, x1, y1, x2, y2, x3, y3);
        //printf("\n*%le*\n", t0 + t1 + t2 + t3);

        return tt;
    }

    //拉格朗日磁场插值m=f(p,(pi,m1)),i=0123.i—确定自变量X1 Y2 Z3
    public static MagneticFieldValue lagrInterplMag(PositionVector p, MagneticFieldValue m0,
                                                    MagneticFieldValue m1, MagneticFieldValue m2,
                                                    MagneticFieldValue m3, DiretionXYZ1D d)
    {
        //MagneticFieldValue magneticFieldValue = new MagneticFieldValue();
        switch (d)
        {
            case X:
                return new MagneticFieldValue(p, new MagneticVector(
                        lagrangInterpole(p.x,
                                m0.p.x,m0.m.x,
                                m1.p.x,m1.m.x,
                                m2.p.x,m2.m.x,
                                m3.p.x,m3.m.x),
                        lagrangInterpole(p.x,
                                m0.p.x,m0.m.y,
                                m1.p.x,m1.m.y,
                                m2.p.x,m2.m.y,
                                m3.p.x,m3.m.y),
                        lagrangInterpole(p.x,
                                m0.p.x,m0.m.z,
                                m1.p.x,m1.m.z,
                                m2.p.x,m2.m.z,
                                m3.p.x,m3.m.z)
                ));
            case Y:
                return new MagneticFieldValue(p, new MagneticVector(
                        lagrangInterpole(p.y,
                                m0.p.y,m0.m.x,
                                m1.p.y,m1.m.x,
                                m2.p.y,m2.m.x,
                                m3.p.y,m3.m.x),
                        lagrangInterpole(p.y,
                                m0.p.y,m0.m.y,
                                m1.p.y,m1.m.y,
                                m2.p.y,m2.m.y,
                                m3.p.y,m3.m.y),
                        lagrangInterpole(p.y,
                                m0.p.y,m0.m.z,
                                m1.p.y,m1.m.z,
                                m2.p.y,m2.m.z,
                                m3.p.y,m3.m.z)
                ));
            case Z:
                return new MagneticFieldValue(p, new MagneticVector(
                        lagrangInterpole(p.z,
                                m0.p.z,m0.m.x,
                                m1.p.z,m1.m.x,
                                m2.p.z,m2.m.x,
                                m3.p.z,m3.m.x),
                        lagrangInterpole(p.z,
                                m0.p.z,m0.m.y,
                                m1.p.z,m1.m.y,
                                m2.p.z,m2.m.y,
                                m3.p.z,m3.m.y),
                        lagrangInterpole(p.z,
                                m0.p.z,m0.m.z,
                                m1.p.z,m1.m.z,
                                m2.p.z,m2.m.z,
                                m3.p.z,m3.m.z)
                ));
                default:
                    InformationTextArea.getInstance().append(
                            "*error in public static MagneticFieldValue lagrInterplMag*\n");
                    return null;
        }
    }

    //由某一确定点的磁场编号获得这一点的磁场
    public static MagneticFieldValue num2mag(int k)
    {
        if(k<0||k>= ImportedMagnet.imX.length)
        {
            //System.out.println("*error in num2mag with" + k + "*\n");
            return null;
        }

        MagneticVector mV = new MagneticVector(ImportedMagnet.imX[k], ImportedMagnet.imY[k], ImportedMagnet.imZ[k]);

        int stepX,stepY,stepZ;
        stepX = (int)(k / (DISY*DISZ));
        stepY = (int)((k - stepX * (DISY*DISZ)) / DISZ);
        stepZ = (int)((k - stepX * (DISY*DISZ)) % DISZ);//算出偏移基点的步数

        PositionVector pV = new PositionVector(
                INIX+stepX*GAPX,
                INIY+stepY*GAPY,
                INIZ+stepZ*GAPZ);

        return new MagneticFieldValue(pV,mV);
    }

    //上函数的逆函数。磁场得到编号
    public static int mag2num(MagneticFieldValue m)
    {
        int stepX,stepY,stepZ;

        stepX=(int)Math.round((m.p.x-INIX+SpecialNumber.MINrealNonnegative)/GAPX);
        stepY=(int)Math.round((m.p.y-INIY+SpecialNumber.MINrealNonnegative)/GAPY);
        stepZ=(int)Math.round((m.p.z-INIZ+SpecialNumber.MINrealNonnegative)/GAPZ);
        //又是这个错误。2019年1月3日20点51分

        return stepX*DISY*DISZ+
                stepY*DISZ+
                stepZ;
    }

    //任意一点，得到离它最近的已知磁场点
    public static MagneticFieldValue nearestIM(TriNumberDouble p)
    {
        int stepX,stepY,stepZ;

        stepX=(int)Math.floor((p.x-INIX+SpecialNumber.MINrealNonnegative)/GAPX);
        stepY=(int)Math.floor((p.y-INIY+SpecialNumber.MINrealNonnegative)/GAPY);
        stepZ=(int)Math.floor((p.z-INIZ+SpecialNumber.MINrealNonnegative)/GAPZ);
        //此处曾写错，全部写成了INIX，记一笔2019年1月3日20点25分

        return num2mag(stepX*DISY*DISZ+
                stepY*DISZ+
                stepZ);
    }

    //磁场移动，数到数
    public static int magDispN2N(int n, int x, int y, int z)
    {
        return n + x * DISY*DISZ + y * DISZ + z;
    }

    //磁场移动，磁场到磁场
    public static MagneticFieldValue magDispM2M(MagneticFieldValue m,int x, int y, int z)
    {
        int num = mag2num(m);

        num = magDispN2N(num,x,y,z);

        if(num<0||num>DISX*DISY*DISZ)
        {
            //System.out.println("*particle run out of magnet. num ="+num+"*");
            outof=true;
            return  m;
        }

        return num2mag(num);
    }

    //获得当地磁场
    public static MagneticFieldValue locaMag(TriNumberDouble l)
    {
        //struct magnet t;//返回場

        //最近場
        MagneticFieldValue nr = nearestIM(l);

        //		struct magnet m1;
        //		struct magnet m2;
        //		struct magnet m3;
        //		struct magnet m4;//初级
        //
        //		struct magnet mm1;
        //		struct magnet mm2;
        //		struct magnet mm3;
        //		struct magnet mm4;//高级
        //
        //		struct magnet mmm1;
        //		struct magnet mmm2;
        //		struct magnet mmm3;
        //		struct magnet mmm4;//高高级

        //struct vct v;//位置，很重要！！前代寫的錯誤！！

        //x為最後方向，研究yz平面.第二層，nr所在的那一層
        //第2行，nr所在的那一行
        MagneticFieldValue m1 = magDispM2M(nr,0,0,-1);
        MagneticFieldValue m2 = nr; //magDispM2M(nr,0,0,0);不移動
        MagneticFieldValue m3 = magDispM2M(nr,0,0,1);
        MagneticFieldValue m4 = magDispM2M(nr,0,0,2);
        PositionVector v = new PositionVector(m1.p.x,m1.p.y,l.z);
        MagneticFieldValue mm2 = lagrInterplMag(v,m1,m2,m3,m4,DiretionXYZ1D.Z);

        //第1行，nr所在的那一行的下一行
        m1 = magDispM2M(nr,0,-1,-1);
        m2 = magDispM2M(nr,0,-1,0);
        m3 = magDispM2M(nr,0,-1,1);
        m4 = magDispM2M(nr,0,-1,2);
        v = new PositionVector(m1.p.x,m1.p.y,l.z);
        MagneticFieldValue mm1 = lagrInterplMag(v,m1,m2,m3,m4,DiretionXYZ1D.Z);

        //第3行，nr所在的那一行的上一行
        m1 = magDispM2M(nr,0,1,-1);
        m2 = magDispM2M(nr,0,1,0);
        m3 = magDispM2M(nr,0,1,1);
        m4 = magDispM2M(nr,0,1,2);
        v = new PositionVector(m1.p.x,m1.p.y,l.z);
        MagneticFieldValue mm3 = lagrInterplMag(v,m1,m2,m3,m4,DiretionXYZ1D.Z);

        //第4行，nr所在的那一行的上2行
        m1 = magDispM2M(nr,0,2,-1);
        m2 = magDispM2M(nr,0,2,0);
        m3 = magDispM2M(nr,0,2,1);
        m4 = magDispM2M(nr,0,2,2);
        v = new PositionVector(m1.p.x,m1.p.y,l.z);
        MagneticFieldValue mm4 = lagrInterplMag(v,m1,m2,m3,m4,DiretionXYZ1D.Z);

        //第二層的結合！y方向
        v = new PositionVector(mm1.p.x,l.y,mm1.p.z);
        MagneticFieldValue mmm2 = lagrInterplMag(v,mm1,mm2,mm3,mm4,DiretionXYZ1D.Y);

        //x為最後方向，研究yz平面.第1層，nr所在的那一層的下一層
        //第2行，nr所在的那一行
        m1 = magDispM2M(nr,-1,0,-1);
        m2 = magDispM2M(nr,-1,0,0);
        m3 = magDispM2M(nr,-1,0,1);
        m4 = magDispM2M(nr,-1,0,2);
        v = new PositionVector(m1.p.x,m1.p.y,l.z);
        mm2 = lagrInterplMag(v,m1,m2,m3,m4,DiretionXYZ1D.Z);

        //第1行，nr所在的那一行的下一行
        m1 = magDispM2M(nr,-1,-1,-1);
        m2 = magDispM2M(nr,-1,-1,0);
        m3 = magDispM2M(nr,-1,-1,1);
        m4 = magDispM2M(nr,-1,-1,2);
        v = new PositionVector(m1.p.x,m1.p.y,l.z);
        mm1 = lagrInterplMag(v,m1,m2,m3,m4,DiretionXYZ1D.Z);

        //第3行，nr所在的那一行的上一行
        m1 = magDispM2M(nr,-1,1,-1);
        m2 = magDispM2M(nr,-1,1,0);
        m3 = magDispM2M(nr,-1,1,1);
        m4 = magDispM2M(nr,-1,1,2);
        v = new PositionVector(m1.p.x,m1.p.y,l.z);
        mm3 = lagrInterplMag(v,m1,m2,m3,m4,DiretionXYZ1D.Z);

        //第4行，nr所在的那一行的上2行
        m1 = magDispM2M(nr,-1,2,-1);
        m2 = magDispM2M(nr,-1,2,0);
        m3 = magDispM2M(nr,-1,2,1);
        m4 = magDispM2M(nr,-1,2,2);
        v = new PositionVector(m1.p.x,m1.p.y,l.z);
        mm4 = lagrInterplMag(v,m1,m2,m3,m4,DiretionXYZ1D.Z);

        //第1層的結合！y方向
        v = new PositionVector(mm1.p.x,l.y,mm1.p.z);
        MagneticFieldValue mmm1 = lagrInterplMag(v,mm1,mm2,mm3,mm4,DiretionXYZ1D.Y);

        //x為最後方向，研究yz平面.第3層，nr所在的那一層的上一層
        //第2行，nr所在的那一行
        m1 = magDispM2M(nr,1,0,-1);
        m2 = magDispM2M(nr,1,0,0);
        m3 = magDispM2M(nr,1,0,1);
        m4 = magDispM2M(nr,1,0,2);
        v = new PositionVector(m1.p.x,m1.p.y,l.z);
        mm2 = lagrInterplMag(v,m1,m2,m3,m4,DiretionXYZ1D.Z);

        //第1行，nr所在的那一行的下一行
        m1 = magDispM2M(nr,1,-1,-1);
        m2 = magDispM2M(nr,1,-1,0);
        m3 = magDispM2M(nr,1,-1,1);
        m4 = magDispM2M(nr,1,-1,2);
        v = new PositionVector(m1.p.x,m1.p.y,l.z);
        mm1 = lagrInterplMag(v,m1,m2,m3,m4,DiretionXYZ1D.Z);

        //第3行，nr所在的那一行的上一行
        m1 = magDispM2M(nr,1,1,-1);
        m2 = magDispM2M(nr,1,1,0);
        m3 = magDispM2M(nr,1,1,1);
        m4 = magDispM2M(nr,1,1,2);
        v = new PositionVector(m1.p.x,m1.p.y,l.z);
        mm3 = lagrInterplMag(v,m1,m2,m3,m4,DiretionXYZ1D.Z);

        //第4行，nr所在的那一行的上2行
        m1 = magDispM2M(nr,1,2,-1);
        m2 = magDispM2M(nr,1,2,0);
        m3 = magDispM2M(nr,1,2,1);
        m4 = magDispM2M(nr,1,2,2);
        v = new PositionVector(m1.p.x,m1.p.y,l.z);
        mm4 = lagrInterplMag(v,m1,m2,m3,m4,DiretionXYZ1D.Z);

        //第3層的結合！y方向
        v = new PositionVector(mm1.p.x,l.y,mm1.p.z);
        MagneticFieldValue mmm3 = lagrInterplMag(v,mm1,mm2,mm3,mm4,DiretionXYZ1D.Y);

        //x為最後方向，研究yz平面.第4層，nr所在的那一層的上2層
        //第2行，nr所在的那一行
        m1 = magDispM2M(nr,2,0,-1);
        m2 = magDispM2M(nr,2,0,0);
        m3 = magDispM2M(nr,2,0,1);
        m4 = magDispM2M(nr,2,0,2);
        v = new PositionVector(m1.p.x,m1.p.y,l.z);
        mm2 = lagrInterplMag(v,m1,m2,m3,m4,DiretionXYZ1D.Z);

        //第1行，nr所在的那一行的下一行
        m1 = magDispM2M(nr,2,-1,-1);
        m2 = magDispM2M(nr,2,-1,0);
        m3 = magDispM2M(nr,2,-1,1);
        m4 = magDispM2M(nr,2,-1,2);
        v = new PositionVector(m1.p.x,m1.p.y,l.z);
        mm1 = lagrInterplMag(v,m1,m2,m3,m4,DiretionXYZ1D.Z);

        //第3行，nr所在的那一行的上一行
        m1 = magDispM2M(nr,2,1,-1);
        m2 = magDispM2M(nr,2,1,0);
        m3 = magDispM2M(nr,2,1,1);
        m4 = magDispM2M(nr,2,1,2);
        v = new PositionVector(m1.p.x,m1.p.y,l.z);
        mm3 = lagrInterplMag(v,m1,m2,m3,m4,DiretionXYZ1D.Z);

        //第4行，nr所在的那一行的上2行
        m1 = magDispM2M(nr,2,2,-1);
        m2 = magDispM2M(nr,2,2,0);
        m3 = magDispM2M(nr,2,2,1);
        m4 = magDispM2M(nr,2,2,2);
        v = new PositionVector(m1.p.x,m1.p.y,l.z);
        mm4 = lagrInterplMag(v,m1,m2,m3,m4,DiretionXYZ1D.Z);

        //第二層的結合！y方向
        v = new PositionVector(mm1.p.x,l.y,mm1.p.z);
        MagneticFieldValue mmm4 = lagrInterplMag(v,mm1,mm2,mm3,mm4,DiretionXYZ1D.Y);

        //64元集合！！X方向
        v = new PositionVector(l.x,mmm4.p.y,mmm3.p.z);
        return lagrInterplMag(v,mmm1,mmm2,mmm3,mmm4,DiretionXYZ1D.X);

        //毫無把握得把代碼轉移到這裏了
        //純看天命
    }

    //等ds迭代的尤格库塔法 p只提供质量电荷信息 v提供粒子速度 由l得到磁场 gi=ai/vi i为xyz三方向
    //改成動質量 電荷量-不需要-全局變量 速度 位置
    public static TriNumberDouble rungeDs(double rMass, TriNumberDouble v,TriNumberDouble l)
    {
        MagneticFieldValue m = locaMag(l);

        //ds法要用到(1/v)，防止v==0
        if (Math.abs(v.x) < SpecialNumber.MINrealNonnegative)
            v.x = SpecialNumber.MINrealNonnegative;
        if (Math.abs(v.y) < SpecialNumber.MINrealNonnegative)
            v.y = SpecialNumber.MINrealNonnegative;
        if (Math.abs(v.z) < SpecialNumber.MINrealNonnegative)
            v.z = SpecialNumber.MINrealNonnegative;

        //struct vct xi, yi, zi;
        //	xi.x = 1; xi.y = 0; xi.z = 0;
        //	yi.x = 0; yi.y = 1; yi.z = 0;
        //	zi.x = 0; zi.y = 0; zi.z = 1;

        //以前寫的是什麽鬼？？
        //struct vct g;
        //	g.x = (1 / v.x)*dot_product(
        //	scalar_multip_vct(p.nt.e / p.nt.m, cross_product(v, m.m)), xi);
        //	g.y = (1 / v.y)*dot_product(scalar_multip_vct(p.nt.e / p.nt.m, cross_product(v, m.m)), yi);
        //	g.z = (1 / v.z)*dot_product(scalar_multip_vct(p.nt.e / p.nt.m, cross_product(v, m.m)), zi);

        return new TriNumberDouble(
                (1/v.x)*TriNumberDouble.scalarMultipleTriNumberNew(e/rMass,
                        TriNumberDouble.crossProduct(v,m.m)).x,
                (1/v.y)*TriNumberDouble.scalarMultipleTriNumberNew(e/rMass,
                        TriNumberDouble.crossProduct(v,m.m)).y,
                (1/v.z)*TriNumberDouble.scalarMultipleTriNumberNew(e/rMass,
                        TriNumberDouble.crossProduct(v,m.m)).z
        );

    }

    public static TriNumberDouble rungeAve(TriNumberDouble a1,TriNumberDouble a2,TriNumberDouble a3,TriNumberDouble a4)
    {
        TriNumberDouble ans = TriNumberDouble.trinumberAdds(a1,
                a2,a2,
                a3,a3,
                a4);

        return TriNumberDouble.scalarMultipleTriNumberNew(1.0/6,ans);
    }

    //读入粒子，运动到下一位置，等步长法
    //runge-kutta迭代法
    //原名  particle_moving_ds
    public static void runDsSelf(RunningParticle p)
    {
        //ds矢量化
        TriNumberDouble ds = TriNumberDouble.scalarMultipleTriNumberNew(STEP_DS/p.speed,p.v);


        //ds的三方向
        //優化
        //double dx = TriNumberDouble.dotProduct(
        //                TriNumberDouble.scalarMultipleTriNumberNew(STEP_DS/p.speed,p.v),
        //                xi);
        //        double dy = TriNumberDouble.dotProduct(
        //                TriNumberDouble.scalarMultipleTriNumberNew(STEP_DS/p.speed,p.v),
        //                yi);
        //        double dz = TriNumberDouble.dotProduct(
        //                TriNumberDouble.scalarMultipleTriNumberNew(STEP_DS/p.speed,p.v),
        //                zi);
        //我也不知道爲什麽要提取出來

        //struct vct g, g1, g2, g3, g4;
        //	struct vct dv, dv1, dv2, dv3;
        //	struct vct ds1, ds2, ds3, ds4;
        TriNumberDouble g1 = rungeDs(p.rMass,p.v,p.p);
        //速度变化
        TriNumberDouble dv1 = new TriNumberDouble(
                g1.x*ds.x,
                g1.y*ds.y,
                g1.z*ds.z);

        //g2 = g_runge_kutta_ds(p, vct_add_vct(p.v, scalar_multip_vct(0.5, dv1)), vct_add_vct(p.l, scalar_multip_vct(0.5, ds)));
        TriNumberDouble g2 = rungeDs(p.rMass,
                TriNumberDouble.trinumberAddTrinumber(p.v,
                        TriNumberDouble.scalarMultipleTriNumberNew(0.5,dv1)),
                TriNumberDouble.trinumberAddTrinumber(p.p,
                        TriNumberDouble.scalarMultipleTriNumberNew(0.5,ds)));
        //速度变化
        TriNumberDouble dv2 = new TriNumberDouble(
                g2.x*ds.x,
                g2.y*ds.y,
                g2.z*ds.z
        );

        TriNumberDouble g3 = rungeDs(p.rMass,
                TriNumberDouble.trinumberAddTrinumber(p.v,
                        TriNumberDouble.scalarMultipleTriNumberNew(0.5,dv2)),
                TriNumberDouble.trinumberAddTrinumber(p.p,
                        TriNumberDouble.scalarMultipleTriNumberNew(0.5,ds)));
        //速度变化
        TriNumberDouble dv3 = new TriNumberDouble(
                g3.x*ds.x,
                g3.y*ds.y,
                g3.z*ds.z
        );

        TriNumberDouble g4 = rungeDs(p.rMass,
                TriNumberDouble.trinumberAddTrinumber(p.v,
                        TriNumberDouble.scalarMultipleTriNumberNew(1.0,dv3)),
                TriNumberDouble.trinumberAddTrinumber(p.p,
                        TriNumberDouble.scalarMultipleTriNumberNew(1.0,ds)));

        TriNumberDouble g = rungeAve(g1,g2,g3,g4);

        //速度变化
        TriNumberDouble dv = new TriNumberDouble(
                g.x*ds.x,
                g.y*ds.y,
                g.z*ds.z
        );

        //p.v = vct_add_vct(p.v, dv);
        //	p.l = vct_add_vct(p.l, ds);
        //	p.s += inte_ds;
        //	p.t += inte_ds / p.speed;//运动
        //	p.step += 1;//步数加1

        p.v.addSelf(dv);//速度變化。加速加速
        p.p.addSelf(ds);//位置變化。加速加速
        p.distance+=STEP_DS;//路程
        //時間
        //路程。不存在！

    }

    public static void runDtSelf(RunningParticle p)
    {
        //runge-kutta迭代法
        //struct vct a;
        //struct vct a1;
        //struct vct a2;
        //struct vct a3;
        //struct vct a4;//加速度

        //struct magnet m;//磁场

        double dt = STEP_DS/p.speed;

        //m = local_mag(p.l);
        MagneticFieldValue m = locaMag(p.p);

        //m = nearest_origin(p.l);//測試用
        //a1 = scalar_multip_vct(p.nt.e / p.nt.m, cross_product(p.v, m.m));
        //a2 = scalar_multip_vct(p.nt.e / p.nt.m, cross_product(vct_add_vct(p.v, scalar_multip_vct(dt / 2, a1)), m.m));
        //a3 = scalar_multip_vct(p.nt.e / p.nt.m, cross_product(vct_add_vct(p.v, scalar_multip_vct(dt / 2, a2)), m.m));
        //a4 = scalar_multip_vct(p.nt.e / p.nt.m, cross_product(vct_add_vct(p.v, scalar_multip_vct(dt / 1, a3)), m.m));//均值前体

        TriNumberDouble a1 = TriNumberDouble.scalarMultipleTriNumberNew(
                e/p.rMass,TriNumberDouble.crossProduct(p.v,m.m)
        );
        TriNumberDouble a2 = TriNumberDouble.scalarMultipleTriNumberNew(
                e/p.rMass,TriNumberDouble.crossProduct(
                        TriNumberDouble.trinumberAddTrinumber(p.v,TriNumberDouble.scalarMultipleTriNumberNew(dt/2,a1)),m.m)
        );
        TriNumberDouble a3 = TriNumberDouble.scalarMultipleTriNumberNew(
                e/p.rMass,TriNumberDouble.crossProduct(
                        TriNumberDouble.trinumberAddTrinumber(p.v,TriNumberDouble.scalarMultipleTriNumberNew(dt/2,a2)),m.m)
        );
        TriNumberDouble a4 = TriNumberDouble.scalarMultipleTriNumberNew(
                e/p.rMass,TriNumberDouble.crossProduct(
                        TriNumberDouble.trinumberAddTrinumber(p.v,TriNumberDouble.scalarMultipleTriNumberNew(dt/1,a3)),m.m)
        );


        //a = runge_kutta_averge(a1, a2, a3, a4);//均值

        TriNumberDouble a = rungeAve(a1, a2, a3, a4);

        //p.v = vct_add_vct(p.v, scalar_multip_vct(dt, a));
        //p.l = vct_add_vct(p.l, scalar_multip_vct(dt, p.v));
        //p.s += dt * p.speed;
        //p.t += dt;//运动
        //p.step += 1;//步数加1

        //p.v = new VelocityVector(TriNumberDouble.trinumberAddTrinumber(p.v,
        //                TriNumberDouble.scalarMultipleTriNumberNew(dt,a)));
        //        p.p = new PositionVector(TriNumberDouble.trinumberAddTrinumber(p.p,
        //                TriNumberDouble.scalarMultipleTriNumberNew(dt,p.v)));
        //        p.distance+=STEP_DS;


        p.v.addSelf(TriNumberDouble.scalarMultipleTriNumberNew(dt,a));//速度變化。加速加速
        p.p.addSelf(TriNumberDouble.scalarMultipleTriNumberNew(dt,p.v));//位置變化。加速加速
        p.distance+=STEP_DS;//路程

        //printf_vct(a);

        //return p;
    }

    //跑粒子，输出信息，记录轨迹，刷新实空间图像
    //需要接受RunDialog得到的步长信息
    public static void referredParticleRun(double stepLength, double maxDistance)
        throws Exception
    {
        //一。拿到步长，传参
        STEP_DS=stepLength;
        if(STEP_DS<=0)
            throw new Exception("Error:step length <= 0");

        //设置最大步長，查錯，
        MAXDISTANCE=maxDistance;
        if(maxDistance<0)
            throw new Exception("Error:Maximum distance < 0");

        //二。查看磁场是否存在，若存在，拿到磁场信息
        if(ImportedMagnet.isEmpty())
            throw new Exception("Field data not found");

        ParticleRunCommander.DISX=ImportedMagnet.pointTriNumber.x;
        ParticleRunCommander.DISY=ImportedMagnet.pointTriNumber.y;
        ParticleRunCommander.DISZ=ImportedMagnet.pointTriNumber.z;

        ParticleRunCommander.GAPX=ImportedMagnet.gapTriNumber.x;
        ParticleRunCommander.GAPY=ImportedMagnet.gapTriNumber.y;
        ParticleRunCommander.GAPZ=ImportedMagnet.gapTriNumber.z;

        ParticleRunCommander.INIX=ImportedMagnet.positionVectorMin.x;
        ParticleRunCommander.INIY=ImportedMagnet.positionVectorMin.y;
        ParticleRunCommander.INIZ=ImportedMagnet.positionVectorMin.z;

        //三。查看参考粒子是否存在，拿到电荷信息和对应的running型粒子
        //为什么要有running型，因为这个型更轻便
        if(!ReferredParticle.getInstance().isAlreadySet())
            throw new Exception("Referred particle not found");

        e=ReferredParticle.getInstance().chargeQuantity;
        RunningParticle rp = ReferredParticle.getInstance().makeRuningParticle();

        //System.out.println("rp = " + rp);

        //四。初始化轨迹类，用于记录轨迹
        ReferredParticleTrack tr = ReferredParticleTrack.getInstance();
        tr.initialize((int)(maxDistance/STEP_DS+1));

        //默认粒子在磁场里面
        outof=false;//这里可以写一个方法，检查粒子是否在磁场里面
        if(outof)
            throw new Exception("The referred particle is out of magnetic field");

        //五。打印开始仿真的信息
        InformationTextArea.getInstance().append(FormatPrint.StringsIntoPanel(
                "Starting referred particle simulation",
                "Step length="+stepLength*1000+"mm",
                "Maximum distance="+maxDistance*1000+"mm"
        ));

        //六。开始run！！
        //需要记录详细的轨迹信息（因为是参考粒子，需要用来设定sampler
        int num=0;
        while(outof==false&&(rp.distance+SpecialNumber.MINrealNonnegative)<MAXDISTANCE)
        {
            //if(num++%100==0)
            //{
            //    System.out.printf("%d\t" +
            //                    "%e\t%e\t%e\t" +
            //                    "%e\t%e\t%e\t" +
            //                    "%e\t%e\t%e\n",0
            //            ,rp.p.x*1000,rp.p.y*1000,rp.p.z*1000,
            //            rp.v.x*1000,rp.v.y*1000,rp.v.z*1000,
            //            rp.distance*1000,rp.distance/rp.speed,70.0);
            //}

            //System.out.println(rp);
            //跑之前加入轨迹，所以有初始位置的轨迹，数组下标0--也就是初始位置，数组下标即代表跑的步数
            //所以下标*步长=运动长度，当然需要取整
            tr.addInIt(rp);

            //移动，注意是自身移动，不是创造新粒子
            //runDsSelf(rp);
            runDtSelf(rp);
        }

        //tr.printTest();

        //告知track，实际路程，用于设置detector的检测
        tr.setLength(rp.distance);
        //还要告知步长
        tr.setStepLength(STEP_DS);

        //七。仿真成功。希望能走到这一步
        InformationTextArea.getInstance().append(FormatPrint.StringsIntoPanel(
                "Simulation finished",
                "The actual running distance is " + tr.getLength()*SpecialNumber.M2MM + "mm",
                (outof==true)?"Particle ran out of magnetic field":"Particle stop at maximum distance"
        ));

        //八。画图
        PlotRealSpace.getInstance().fresh();

    }

    //以下是Beam部分，首先需要设定束流仿真环境beamRunEnvironment
    //然后不同的线程调用beamTask
    public static void beamTask(int threadID,int pidBegin, int pidEnd)
    {
        InformationTextArea.getInstance().append("Thread-"+threadID+" starts simulation of "+
                pidBegin+"~"+pidEnd+" particles\n");

        for(int i=pidBegin;i<pidEnd;i++)
            singleParticleRun(i);

        InformationTextArea.getInstance().append("Thread-"+threadID+" finished its task\n");
    }

    //跑单个粒子，和跑referred particle略有不同
    //pid绝对粒子编号
    public static void singleParticleRun(int pid)
    {
        int iDetect = 0;


        RunningParticle rp = Beam.getByPid(pid);//当前粒子
        RunningParticle prp;//前一时刻的粒子


        while((rp.distance+SpecialNumber.MINrealNonnegative)<MAXDISTANCE)
        {
            //更新前一时刻的粒子
            prp=new RunningParticle(rp);

            //移动，注意是自身移动，不是创造新粒子
            //runDsSelf(rp);
            runDtSelf(rp);

            //粒子询问自己是否在当前探测器的前面。如果返回false，粒子应开始询问下一个detector。
            //同时detector会自动因此记录需要记录的记录
            //粒子需要传来处理自己的线程编号，以满足快速并发
            //粒子需要传来上次位置，以满足detector的记录。

            //System.out.println("iDetect = " + iDetect);

            if(DetectorsSet.isHasNext(iDetect))
            {//2019年1月4日 23点32分。当前面没有了，当然不要问我是不是在你的前面
                //java.lang.ArrayIndexOutOfBoundsException: 5
                if(!DetectorsSet.getDetector(iDetect).amIFrontYou(DetectorWay.STEP,pid,rp,prp))
                {
                    iDetect++;
                }

            }


        }

        //似乎结束了
    }

    //对beam仿真环境的设定其实就是得到步长、最大长度等
    //还有查看beam类是否有粒子
    public static void beamRunEnvironment(double stepLength, double maxDistance,int totalParticleNum)
            throws Exception
    {
        //一。拿到步长，传参
        STEP_DS=stepLength;
        if(STEP_DS<=0)
            throw new Exception("Error:step length <= 0");

        //设置最大步長，查錯
        MAXDISTANCE=maxDistance;
        if(MAXDISTANCE<0)
            throw new Exception("Error:Maximum distance < 0");

        //二。查看磁场是否存在，若存在，拿到磁场信息
        if(ImportedMagnet.isEmpty())
            throw new Exception("Field data not found");

        ParticleRunCommander.DISX=ImportedMagnet.pointTriNumber.x;
        ParticleRunCommander.DISY=ImportedMagnet.pointTriNumber.y;
        ParticleRunCommander.DISZ=ImportedMagnet.pointTriNumber.z;

        ParticleRunCommander.GAPX=ImportedMagnet.gapTriNumber.x;
        ParticleRunCommander.GAPY=ImportedMagnet.gapTriNumber.y;
        ParticleRunCommander.GAPZ=ImportedMagnet.gapTriNumber.z;

        ParticleRunCommander.INIX=ImportedMagnet.positionVectorMin.x;
        ParticleRunCommander.INIY=ImportedMagnet.positionVectorMin.y;
        ParticleRunCommander.INIZ=ImportedMagnet.positionVectorMin.z;

        //三。查看参考粒子是否存在，拿到电荷信息
        if(!ReferredParticle.getInstance().isAlreadySet())
            throw new Exception("Referred particle not found");

        e=ReferredParticle.getInstance().chargeQuantity;

        //查看detectorsSet
        if(DetectorsSet.isEmpty())
            throw new Exception("Detectors unset");

        //查看beam是否存在
        if(Beam.isEmpty())
            throw new Exception("Beam is empty");

        //五。打印开始仿真的信息
        {
            InformationTextArea.getInstance().append(FormatPrint.StringsIntoPanel(
                    "Starting beam simulation",
                    "Step length="+stepLength*1000+"mm",
                    "Maximum distance="+maxDistance*1000+"mm",
                    "Use "+ SystemInfo.MAXTHREADS + " threads to simulate "+totalParticleNum+" particles"
            ));
        }

    }
}
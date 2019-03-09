package zrx.simulate.advancedDataContainer;

//import com.sun.prism.shader.DrawRoundRect_LinearGradient_PAD_Loader;
import zrx.gui.realPlot.DiretionXYZ1D;
import zrx.simulate.Geometry.Ellipse;
import zrx.simulate.Geometry.EllipseParameter;
import zrx.simulate.basicDataContainer.BeamParameterTwiss;
import zrx.simulate.basicDataContainer.ReferredParticle;
import zrx.simulate.basicDataContainer.RunningParticle;
import zrx.simulate.basicDataStructure.BiNumberDouble;
import zrx.simulate.basicDataStructure.PositionVector;
import zrx.simulate.basicDataStructure.TriNumberDouble;
import zrx.simulate.basicDataStructure.VelocityVector;
import zrx.simulate.tool.SpecialNumber;

import java.util.Random;

public class Beam {
    private Beam(){}

    public static RunningParticle[] rps;
    public static int pid;

    public static boolean isEmpty()
    {
        if(rps==null)
            return true;
        else
            return false;
    }

    //内部调用
    private static void initialize(int number)
    {
        rps = new RunningParticle[number];
        pid=0;
    }

    public static void clear()
    {
        rps=null;
        pid=0;
    }

    //内部调用
    private static void addParticle(RunningParticle runningParticle)
    {
        rps[pid] = new RunningParticle(runningParticle);
        pid++;
    }

    //面向外部的取粒子接口
    public static RunningParticle getByPid(int pid)
    {
        return rps[pid];
    }

    //beam生成
    //一维方向椭圆
    //都是默认粒子在XY平面上运动
    public static void makeBeamEllipseEdge1D(DiretionXYZ1D diretionXYZ1D, int number)
            throws Exception
    {
        if(!ReferredParticle.getInstance().isAlreadySet())
            throw new Exception("Referred particle not set");

        if(!BeamParameterTwiss.getInstance().isAlreadySet())
            throw new Exception("Beam parameter twiss not set");

        initialize(number);

        BiNumberDouble[] biNumberDoublesX = Ellipse.pointAtEllipseEdge(new Ellipse(
                //椭圆方程Ax^2+Bxy+Cy^2=D
                BeamParameterTwiss.getInstance().gammaX,
                BeamParameterTwiss.getInstance().alphaX*2,
                BeamParameterTwiss.getInstance().betaX,
                BeamParameterTwiss.getInstance().emitX
                //这里的单位是什么？毫米mm
        ),number);

        BiNumberDouble[] biNumberDoublesY = Ellipse.pointAtEllipseEdge(new Ellipse(
                //椭圆方程Ax^2+Bxy+Cy^2=D
                BeamParameterTwiss.getInstance().gammaY,
                BeamParameterTwiss.getInstance().alphaY*2,
                BeamParameterTwiss.getInstance().betaY,
                BeamParameterTwiss.getInstance().emitY
                //这里的单位是什么？毫米mm
        ),number);

        //PositionVector p, VelocityVector v, double rMass, double speed, double distance

        //搞到参考粒子的running particle
        RunningParticle referredRunningParticle = ReferredParticle.getInstance().makeRuningParticle();

        //得到水平方向单位矢量 垂直方向*速度，再归一化
        TriNumberDouble horizontalNomalVct = TriNumberDouble.crossProduct(
                referredRunningParticle.v,SpecialNumber.verticalNormalVtc);
        horizontalNomalVct.NormalizeSelf();

        switch (diretionXYZ1D)
        {
            case X:
                for(int i=0;i<number;i++)
                {
                    //旋转角
                    double w = biNumberDoublesX[i].y*SpecialNumber.MM2M;
                    double vz = referredRunningParticle.v.z* Math.cos(w)+
                            referredRunningParticle.v.x* Math.sin(w);
                    double vx = -referredRunningParticle.v.z* Math.sin(w)+
                            referredRunningParticle.v.x* Math.cos(w);


                    addParticle(new RunningParticle(
                            new PositionVector(TriNumberDouble.trinumberAddTrinumber(referredRunningParticle.p,
                                    TriNumberDouble.scalarMultipleTriNumberNew(
                                            biNumberDoublesX[i].x*SpecialNumber.MM2M, horizontalNomalVct))),
                            new VelocityVector(vx,referredRunningParticle.v.y,vz),
                            referredRunningParticle.rMass,
                            referredRunningParticle.speed,
                            referredRunningParticle.distance
                    ));
                }
                break;
            case Y:
                for(int i=0;i<number;i++)
                {
                    addParticle(new RunningParticle(
                            new PositionVector(referredRunningParticle.p.x,referredRunningParticle.p.y+
                                    biNumberDoublesY[i].x*SpecialNumber.MM2M, referredRunningParticle.p.z),
                            new VelocityVector(referredRunningParticle.v.x,
                                    referredRunningParticle.v.y+biNumberDoublesY[i].y*
                                            ReferredParticle.getInstance().kineticToSpeed()*SpecialNumber.MM2M,
                                    referredRunningParticle.v.z),
                            referredRunningParticle.rMass,
                            referredRunningParticle.speed,
                            referredRunningParticle.distance
                    ));
                }
                break;
            case Z:
                break;
        }
    }

    //2维相椭圆，XY方向
    public static void makeBeamEllipseEdge2D(int number)
        throws Exception
    {
        if(!ReferredParticle.getInstance().isAlreadySet())
            throw new Exception("Referred particle not set");

        if(!BeamParameterTwiss.getInstance().isAlreadySet())
            throw new Exception("Beam parameter twiss not set");


        initialize(number);

        BiNumberDouble[] biNumberDoublesX = Ellipse.pointAtEllipseEdge(new Ellipse(
                //椭圆方程Ax^2+Bxy+Cy^2=D
                BeamParameterTwiss.getInstance().gammaX,
                BeamParameterTwiss.getInstance().alphaX*2,
                BeamParameterTwiss.getInstance().betaX,
                BeamParameterTwiss.getInstance().emitX
                //这里的单位是什么？毫米mm
        ),number);

        BiNumberDouble[] biNumberDoublesY = Ellipse.pointAtEllipseEdge(new Ellipse(
                //椭圆方程Ax^2+Bxy+Cy^2=D
                BeamParameterTwiss.getInstance().gammaY,
                BeamParameterTwiss.getInstance().alphaY*2,
                BeamParameterTwiss.getInstance().betaY,
                BeamParameterTwiss.getInstance().emitY
                //这里的单位是什么？毫米mm
        ),number);

        //PositionVector p, VelocityVector v, double rMass, double speed, double distance

        //搞到参考粒子的running particle
        RunningParticle referredRunningParticle = ReferredParticle.getInstance().makeRuningParticle();

        //得到水平方向单位矢量 垂直方向*速度，再归一化
        TriNumberDouble horizontalNomalVct = TriNumberDouble.crossProduct(
                referredRunningParticle.v,SpecialNumber.verticalNormalVtc);
        horizontalNomalVct.NormalizeSelf();

        Random random = new Random();

        for(int i=0;i<number;i++)
        {

            int randX = random.nextInt(number);
            int randY = random.nextInt(number);

                //旋转角
                double w = biNumberDoublesX[randX].y*SpecialNumber.MM2M;
                double vz = referredRunningParticle.v.z* Math.cos(w)+
                        referredRunningParticle.v.x* Math.sin(w);
                double vx = -referredRunningParticle.v.z* Math.sin(w)+
                        referredRunningParticle.v.x* Math.cos(w);

                addParticle(new RunningParticle(


                        new PositionVector(

                                referredRunningParticle.p.x+biNumberDoublesX[randX].x*SpecialNumber.MM2M*horizontalNomalVct.x,
                                referredRunningParticle.p.y+ biNumberDoublesY[randY].x*SpecialNumber.MM2M,
                                referredRunningParticle.p.z+biNumberDoublesX[randX].x*SpecialNumber.MM2M*horizontalNomalVct.z),

                        new VelocityVector
                                (

                                        vx,
                                referredRunningParticle.v.y+biNumberDoublesY[randY].y*
                                        ReferredParticle.getInstance().kineticToSpeed()*SpecialNumber.MM2M,
                                        vz),

                        referredRunningParticle.rMass,
                        referredRunningParticle.speed,
                        referredRunningParticle.distance


                ));


        }

    }


    public static void makeBeamEllipseGauss2D(int number)
            throws Exception
    {
        if(!ReferredParticle.getInstance().isAlreadySet())
            throw new Exception("Referred particle not set");

        if(!BeamParameterTwiss.getInstance().isAlreadySet())
            throw new Exception("Beam parameter twiss not set");


        initialize(number);

        //两方向相椭圆
        final Ellipse ellipseX = new Ellipse(
                //椭圆方程Ax^2+Bxy+Cy^2=D
                BeamParameterTwiss.getInstance().gammaX,
                BeamParameterTwiss.getInstance().alphaX*2,
                BeamParameterTwiss.getInstance().betaX,
                BeamParameterTwiss.getInstance().emitX
                //这里的单位是什么？毫米mm
        );

        final Ellipse ellipseY = new Ellipse(
                //椭圆方程Ax^2+Bxy+Cy^2=D
                BeamParameterTwiss.getInstance().gammaY,
                BeamParameterTwiss.getInstance().alphaY*2,
                BeamParameterTwiss.getInstance().betaY,
                BeamParameterTwiss.getInstance().emitY
                //这里的单位是什么？毫米mm
        );

        EllipseParameter ellipseParameterX = Ellipse.getEllipseParameter(ellipseX);
        EllipseParameter ellipseParameterY = Ellipse.getEllipseParameter(ellipseY);

        //椭圆长轴倾角
        double rotateX = ellipseParameterX.angle;
        double rotateY = ellipseParameterY.angle;
        //System.out.println("rotateY = " + rotateY);
        //System.out.println("rotateX = " + rotateX);

        //长轴
        double longAxisX = ellipseParameterX.longAxis;
        double longAxisY = ellipseParameterY.longAxis;
        //System.out.println("longAxisY = " + longAxisY);
        //System.out.println("longAxisX = " + longAxisX);

        //短轴
        double shortAxisX = ellipseParameterX.shortAxis;
        double shortAxisY = ellipseParameterY.shortAxis;
        //System.out.println("shortAxisX = " + shortAxisX);
        //System.out.println("shortAxisY = " + shortAxisY);

        //全完了。2019年1月6日22点05分没问题
        //rotateY = 0.5692565888304709
        //rotateX = 0.5692565888304709
        //longAxisY = 23.500093142685454
        //longAxisX = 23.500093142685454
        //shortAxisX = 5.347370719199679
        //shortAxisY = 5.347370719199679


        //搞到参考粒子的running particle
        RunningParticle referredRunningParticle = ReferredParticle.getInstance().makeRuningParticle();

        //得到水平方向单位矢量 垂直方向*速度，再归一化
        TriNumberDouble horizontalNomalVct = TriNumberDouble.crossProduct(
                referredRunningParticle.v,SpecialNumber.verticalNormalVtc);
        horizontalNomalVct.NormalizeSelf();

        ////////////////////
        for(int i=0;i<number;i++)
        {
            //二维正态分布
            BiNumberDouble biNumberDoubleX = SpecialNumber.jointNormalDistribution(
                    0,
                    longAxisX/(SpecialNumber.KConfidence*2),
                    0,
                    shortAxisX/(SpecialNumber.KConfidence*2),
                    0
            );
            //旋转之
            biNumberDoubleX = BiNumberDouble.rotateNew(rotateX,biNumberDoubleX);

            //二维正态分布
            BiNumberDouble biNumberDoubleY = SpecialNumber.jointNormalDistribution(
                    0,
                    longAxisY/(SpecialNumber.KConfidence*2),
                    0,
                    shortAxisY/(SpecialNumber.KConfidence*2),
                    0
            );
            //旋转之
            biNumberDoubleY = BiNumberDouble.rotateNew(rotateY,biNumberDoubleY);

            //旋转角
            double w = biNumberDoubleX.y*SpecialNumber.MM2M;
            double vz = referredRunningParticle.v.z* Math.cos(w)+
                    referredRunningParticle.v.x* Math.sin(w);
            double vx = -referredRunningParticle.v.z* Math.sin(w)+
                    referredRunningParticle.v.x* Math.cos(w);

            addParticle(new RunningParticle(


                    new PositionVector(

                            referredRunningParticle.p.x+biNumberDoubleX.x*SpecialNumber.MM2M*horizontalNomalVct.x,
                            referredRunningParticle.p.y+ biNumberDoubleY.x*SpecialNumber.MM2M,
                            referredRunningParticle.p.z+biNumberDoubleX.x*SpecialNumber.MM2M*horizontalNomalVct.z),

                    new VelocityVector
                            (

                                    vx,
                                    referredRunningParticle.v.y+biNumberDoubleY.y*
                                            ReferredParticle.getInstance().kineticToSpeed()*SpecialNumber.MM2M,
                                    vz),

                    referredRunningParticle.rMass,
                    referredRunningParticle.speed,
                    referredRunningParticle.distance
            ));


        }


    }


    public static int size()
    {
        return rps.length;
    }



    public static void testPrint()
    {
        for(RunningParticle rp:rps)
        {
            System.out.println(rp);
        }
    }

    public static void testPrintBi()
    {
        BiNumberDouble[] biNumberDoubles = Ellipse.pointAtEllipseEdge(new Ellipse(
                //椭圆方程Ax^2+Bxy+Cy^2=D
                BeamParameterTwiss.getInstance().gammaX,
                BeamParameterTwiss.getInstance().alphaX*2,
                BeamParameterTwiss.getInstance().betaX,
                BeamParameterTwiss.getInstance().emitX
                //这里的单位是什么？毫米mm
        ),10);

        for(BiNumberDouble t:biNumberDoubles)
            t.print();
    }
}

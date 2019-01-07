package zrx.simulate.advancedDataContainer;

import zrx.gui.realPlot.DiretionXYZ1D;
import zrx.gui.realPlot.PlotWay;
import zrx.gui.realPlot.toBePlot.BinumbersToBePlot;
import zrx.gui.setParticle.ErrorDialog;
import zrx.simulate.Geometry.Face3D;
import zrx.simulate.basicDataContainer.RunningParticle;
import zrx.simulate.basicDataStructure.BiNumberDouble;
import zrx.simulate.basicDataStructure.TriNumberDouble;
import zrx.simulate.tool.ParticleRunCommander;
import zrx.simulate.tool.SpecialNumber;

import java.awt.*;

public class Detector {
    private TriNumberDouble[] detectorPartPosition=null;
    private TriNumberDouble[] detectorPartVelocity=null;
    private Face3D face3D;//面
    private double location;//位置 自然坐标
    private double time;
    private int id;//编号

    public void clear()
    {
        detectorPartPosition=null;
        detectorPartVelocity=null;
        face3D=null;
        location=0.0;
        time=0.0;
        id=0;
    }

    //这个类的实例应该是拿到任务的的线程建立的--不对！
    //首先设置detector的时候，就指定了数量、位置，这时这个实例就可以创建了，信息已经完备
    //但是任务数不知道，线程总是是知道的
    public Detector(Face3D face3D, double location,double time, int id) {

        //detectorPar的数目就是总线程数，至于里面开辟多大空间，那就看那个线程分到的粒子数
        this.face3D = face3D;
        this.location = location;
        this.time=time;
        this.id = id;
    }

    //再次分配空间
    public void allocateRoom(int totalPaticleNum)
    {
        detectorPartPosition = new TriNumberDouble[totalPaticleNum];
        detectorPartVelocity = new TriNumberDouble[totalPaticleNum];
    }

    //粒子询问自己是否在当前探测器的前面。如果返回false，粒子应开始询问下一个detector。同时detector会自动因此记录需要记录的记录
    //粒子需要传来处理自己的线程编号，以满足快速并发
    //粒子需要传来上次位置，以满足detector的记录。
    //新更新————2019年1月5日
    //探测器有不同的工作方法。暂时是 撞击面法 同时间法 同路程法
    public boolean amIFrontYou(DetectorWay detectorWay,int pid, RunningParticle current,RunningParticle previous)
    {
        switch (detectorWay)
        {
            case FACE:
                if(face3D.inFront(current.p))
                    return true;
                else
                {
                    detectorPartPosition[pid]=face3D.crossPoint(current.p,previous.p);

                    double dcurrent = face3D.pointAT(current.p);
                    double dprevious = face3D.pointAT(previous.p);
                    double k = Math.abs(dprevious)/(Math.abs(dprevious)/ Math.abs(dcurrent));

                    detectorPartVelocity[pid]=TriNumberDouble.trinumberAdds(
                            previous.v,
                            TriNumberDouble.scalarMultipleTriNumberNew(k,
                                    TriNumberDouble.trinumberSubtractTrinumber(current.v,previous.v)
                            )
                    );
                    return false;
                }
            case TIME:

                //System.out.println("-----------------");
                //System.out.println(time);
                //System.out.println(current.distance/current.speed);

                if(current.distance/current.speed<time)
                {
                    //System.out.println("true");
                    return true;
                }
                else
                {
                    System.out.println("-----------------");
                    System.out.println("false");
                    System.out.println("time"+time);
                    System.out.println("current.distance/current.speed"+current.distance/current.speed);
                    System.out.println("previous.distance/previous.speed"+previous.distance/previous.speed);

                    if(current.distance/current.speed==time)
                    {
                        //System.out.println("==");

                        detectorPartPosition[pid]=new TriNumberDouble(current.p);
                        detectorPartVelocity[pid]=new TriNumberDouble(current.v);
                        return false;
                    }

                    double dcurrent = current.distance/current.speed-time;
                    double dprevious = time-previous.distance/previous.speed;
                    double k = dprevious/(dcurrent+dprevious);

                    //System.out.println("k = " + k);
                    //System.out.println("dprevious = " + dprevious);
                    //System.out.println("dcurrent = " + dcurrent);

                    detectorPartPosition[pid]=TriNumberDouble.trinumberAdds(
                            previous.p,
                            TriNumberDouble.scalarMultipleTriNumberNew(k,
                                    TriNumberDouble.trinumberSubtractTrinumber(current.p,previous.p))
                    );

                    detectorPartVelocity[pid]=TriNumberDouble.trinumberAdds(
                            previous.v,
                            TriNumberDouble.scalarMultipleTriNumberNew(k,
                                    TriNumberDouble.trinumberSubtractTrinumber(current.v,previous.v))
                    );

                    return false;
                }
            case DISTANCE:
                if(current.distance<location)
                    return true;
                else
                {
                    if(current.distance==location)
                    {
                        detectorPartPosition[pid]=new TriNumberDouble(current.p);
                        detectorPartVelocity[pid]=new TriNumberDouble(current.v);
                        return false;
                    }

                    double dcurrent = current.distance-location;
                    double dprevious = location-current.distance;
                    double k = dprevious/(dcurrent+dprevious);

                    detectorPartPosition[pid]=TriNumberDouble.trinumberAdds(
                            previous.p,
                            TriNumberDouble.scalarMultipleTriNumberNew(k,
                                    TriNumberDouble.trinumberSubtractTrinumber(current.p,previous.p))
                    );

                    detectorPartVelocity[pid]=TriNumberDouble.trinumberAdds(
                            previous.v,
                            TriNumberDouble.scalarMultipleTriNumberNew(k,
                                    TriNumberDouble.trinumberSubtractTrinumber(current.v,previous.v))
                    );

                    return false;
                }
            case STEP:
                if(Math.round(current.distance/ ParticleRunCommander.STEP_DS)<Math.round(location/ParticleRunCommander.STEP_DS))
                    return true;
                else
                {
                    detectorPartPosition[pid]=new TriNumberDouble(current.p);
                    detectorPartVelocity[pid]=new TriNumberDouble(current.v);

                    return false;
                }
        }


        return false;
    }

    public BinumbersToBePlot testGetXYToBePlot()
    {
        BiNumberDouble[] biNumberDoubles = new BiNumberDouble[detectorPartPosition.length];

        int num = 0;
        for(int i=0;i<detectorPartPosition.length;i++)
        {
            if(detectorPartPosition[i]!=null)
            {
                biNumberDoubles[num] = new BiNumberDouble(
                        detectorPartPosition[i].x,
                        detectorPartPosition[i].y);
                num++;
            }
        }

        BiNumberDouble[] ans = new BiNumberDouble[num];

        for(int i=0;i<num;i++)
        {
            ans[i]=biNumberDoubles[i];
        }

        return new BinumbersToBePlot(ans,
                "Detecor-"+location*1000+"mm", Color.BLACK, PlotWay.Scatter);

    }

    public TriNumberDouble getPosition()
    {
        //反正不要求效率
        return ReferredParticleTrack.getInstance().getPositionByDistance(location);
    }

    public double getLocation() {
        return location;
    }

    //相空间~~相空间~~相空间~~相空间~~相空间~~
    public BiNumberDouble[] biNumberDoublesOfSpace(DiretionXYZ1D diretionXYZ1D)
    {
        //中心理想粒子
        TriNumberDouble centrePosition = ReferredParticleTrack.getInstance().getPositionByDistance(location);
        TriNumberDouble centreVelocity = ReferredParticleTrack.getInstance().getVelocityByDistance(location);

        //System.out.println("location = " + location);
        //System.out.println("centreVelocity = " + centreVelocity);
        //System.out.println("centrePosition = " + centrePosition);

        //System.out.println("centrePosition = " + centrePosition);
        //System.out.println("centreVelocity = " + centreVelocity);

        //记录偏差
        //deviatePosition记录 x 和 Y
        //deviateVelocity记录 x' 和 y'
        BiNumberDouble[] deviatePosition = new BiNumberDouble[detectorPartPosition.length];
        BiNumberDouble[] deviateVelocity = new BiNumberDouble[detectorPartPosition.length];

        if(false)
        {

            //由粒子速度方向 叉乘 垂直发现，得到水平方向矢量，并归一化
            //这里的水平方向，即相空间的X
            //这里的垂直发现，即相空间的Y
            TriNumberDouble horizontalVtc = TriNumberDouble.crossProduct(centreVelocity, SpecialNumber.verticalNormalVtc);
            horizontalVtc.NormalizeSelf();

            //System.out.println("horizontalVtc = " + horizontalVtc);

            //下面很美妙
            int num=0;
            for(int i=0;i<deviatePosition.length;i++)
            {
                if(detectorPartPosition[i]!=null)
                {
                    //统计非空的记录，因为有的粒子走的偏，可能没有记录到，那就是null
                    //这时连带地在deviatePosition和deviateVelocity中也可能有null

                    deviatePosition[i]=new BiNumberDouble(
                            TriNumberDouble.dotProduct(horizontalVtc,
                                    TriNumberDouble.trinumberSubtractTrinumber(detectorPartPosition[i],
                                            centrePosition))*SpecialNumber.M2MM,
                            TriNumberDouble.dotProduct(SpecialNumber.verticalNormalVtc,
                                    TriNumberDouble.trinumberSubtractTrinumber(detectorPartPosition[i],
                                            centrePosition))*SpecialNumber.M2MM
                    );

                    //System.out.println("deviatePosition = "+i+"||" + deviatePosition[i]);

                    deviateVelocity[i]=new BiNumberDouble(
                            TriNumberDouble.dotProduct(horizontalVtc,
                                    TriNumberDouble.trinumberSubtractTrinumber(detectorPartVelocity[i],
                                            centreVelocity))/
                                    centreVelocity.length()*SpecialNumber.M2MM

                            ,
                            TriNumberDouble.dotProduct(SpecialNumber.verticalNormalVtc,
                                    TriNumberDouble.trinumberSubtractTrinumber(detectorPartVelocity[i],
                                            centreVelocity))/
                                    centreVelocity.length()*SpecialNumber.M2MM
                    );

                    //System.out.println("deviateVelocity = " +i+"||" + deviateVelocity[i]);
                }
            }
        }


        if(true)
        {

            for(int i=0;i<deviatePosition.length;i++)
            {

                if(detectorPartPosition[i]!=null)
                {
                    //第一步 变换到原点位于ip.l，xyz和原坐标系相同方向的静止坐标系
                    TriNumberDouble dl = TriNumberDouble.trinumberSubtractTrinumber(detectorPartPosition[i],centrePosition);
                    TriNumberDouble dv = new TriNumberDouble(detectorPartVelocity[i]);

                    ////第二步 变换到原点位于ip.l，xyz和ip.v方向相同的静止坐标系，即参考粒子的纵向ss 横向中的水平方向hrz 垂直方向vtc 也就是自然坐标系
                    TriNumberDouble ss = TriNumberDouble.scalarMultipleTriNumberNew(
                            1/centreVelocity.length(),centreVelocity
                    );
                    TriNumberDouble hrz = TriNumberDouble.crossProduct(ss,SpecialNumber.verticalNormalVtc);
                    hrz.NormalizeSelf();

                    ////自然坐标系
                    TriNumberDouble nl = new TriNumberDouble(
                            TriNumberDouble.dotProduct(dl,hrz),
                            TriNumberDouble.dotProduct(dl,SpecialNumber.verticalNormalVtc),
                            TriNumberDouble.dotProduct(dl,ss)
                    );
                    TriNumberDouble nv = new TriNumberDouble(
                            TriNumberDouble.dotProduct(dv,hrz),
                            TriNumberDouble.dotProduct(dv,SpecialNumber.verticalNormalVtc),
                            TriNumberDouble.dotProduct(dv,ss)
                    );

                    //第三步 变换到原点位于ip.l，且以ip.v运动的坐标系 考虑相对论
                    double beta = centreVelocity.length()/SpecialNumber.LightSpeed;
                    double gamma = 1/ Math.sqrt(1-beta*beta);

                    TriNumberDouble rl = new TriNumberDouble(
                            nl.x,nl.y,gamma*nl.z
                    );
                    TriNumberDouble rv = new TriNumberDouble(
                            nv.x*((1 / gamma) / 1 - beta * nv.z / SpecialNumber.LightSpeed),
                            nv.y*((1 / gamma) / 1 - beta * nv.z / SpecialNumber.LightSpeed),
                            (nv.z - centreVelocity.length()) / (1 - beta * nv.z / SpecialNumber.LightSpeed)

                    );

                    deviatePosition[i] = new BiNumberDouble(rl.x*SpecialNumber.M2MM,rl.y*SpecialNumber.M2MM);
                    deviateVelocity[i] = new BiNumberDouble(rv.x/centreVelocity.length()*SpecialNumber.M2MM
                            ,rv.y/centreVelocity.length()*SpecialNumber.M2MM);
                }
            }
        }





        switch (diretionXYZ1D)
        {
            case X:
                BiNumberDouble[] phaseX = new BiNumberDouble[deviatePosition.length];
                for(int i=0;i<deviatePosition.length;i++)
                {
                    if(deviatePosition[i]!=null)
                    //再次查空，但这次非空总数一定是num.XXX
                    {
                        phaseX[i] = new BiNumberDouble(
                                deviatePosition[i].x,
                                deviateVelocity[i].x
                        );
                    }
                }
                return phaseX;
                //break;
            case Y:
                BiNumberDouble[] phaseY = new BiNumberDouble[deviatePosition.length];
                for(int i=0;i<deviatePosition.length;i++)
                {
                    if(deviatePosition[i]!=null)
                    //再次查空，但这次非空总数一定是num.XXX
                    {
                        phaseY[i] = new BiNumberDouble(
                                deviatePosition[i].y,
                                deviateVelocity[i].y
                        );
                    }
                }
                return phaseY;
                //break;
            case Z:
                ErrorDialog.getInstance().setErrorInformationToLabelAndVisible(
                        "In Detector at biNumberDoublesOfSpace. directionXYZ1D=Z"
                );
                return null;
                //break;
        }

        //走到这里，完了
        ErrorDialog.getInstance().setErrorInformationToLabelAndVisible(
                "In Detector at biNumberDoublesOfSpace. unknown error"
        );
        return null;
    }

    public boolean hasData()
    {
        for(TriNumberDouble t:detectorPartPosition)
        {
            if(t!=null)
                return true;
        }

        return false;
    }

    public String[] testGetPosition()
    {
        String[] strs = new String[detectorPartPosition.length];

        for(int i=0;i<detectorPartPosition.length;i++)
        {
            strs[i]=detectorPartPosition[i].toString();
        }

        return strs;
    }
}
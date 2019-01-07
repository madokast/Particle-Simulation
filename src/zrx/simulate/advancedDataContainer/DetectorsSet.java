package zrx.simulate.advancedDataContainer;

import zrx.gui.realPlot.DiretionXYZ1D;
import zrx.gui.realPlot.PlotWay;
import zrx.gui.realPlot.toBePlot.TrinumbersToBePlot;
import zrx.gui.menuBar.viewMenuItem.ComponentColorMenuItem.DetectorColor;
import zrx.simulate.Geometry.Face3D;
import zrx.simulate.basicDataContainer.ReferredParticle;
import zrx.simulate.basicDataStructure.BiNumberDouble;
import zrx.simulate.basicDataStructure.TriNumberDouble;
import zrx.simulate.tool.SpecialNumber;

public class DetectorsSet {//这里的set是集合的意思
    private DetectorsSet(){}

    public static Detector[] detectors;

    public static boolean isHasNext(int i)
    {
        if(i>=detectors.length)
            return false;
        else
            return true;
    }

    public static Detector getDetector(int i)
    {
        return detectors[i];
    }

    //接受的单位时m
    //主要这次没有给detector里面的二维向量分配空间
    //private TriNumberDouble[/*线程编号*/][/*该线程拿到的任务数*/] detectorPartVelocity=null;
    //所以当时后还要分配一次
    public static void makeDetectorsSet(double[] locations)
            throws Exception
    {
        if(ReferredParticleTrack.getInstance().isEmpty())
            throw new Exception("Referred particle track not found");

        clear();
        detectors = new Detector[locations.length];

        for(int i=0;i<locations.length;i++)
        {
            if(locations[i]<0||locations[i]>ReferredParticleTrack.getInstance().getLength())
                throw new Exception("cannot set detector at "+locations[i]* SpecialNumber.M2MM +"mm");


            detectors[i] = new Detector(
                    //getFace(TriNumberDouble position,TriNumberDouble velocity)
                    Face3D.getFace(ReferredParticleTrack.getInstance().getPositionByDistance(locations[i]),
                            ReferredParticleTrack.getInstance().getVelocityByDistance(locations[i])),
                    locations[i],
                    locations[i]/ ReferredParticle.getInstance().kineticToSpeed(),
                    i
            );

        }
    }

    //实空间绘图，绘制detector的位置
    public static TrinumbersToBePlot getDetectorsLocationToBePlot()
    {
        TriNumberDouble[] triNumberDoubles = new TriNumberDouble[detectors.length];
        for(int i=0;i<detectors.length;i++)
        {
            triNumberDoubles[i]=TriNumberDouble.mTommNew(getDetector(i).getPosition());
        }

        return new TrinumbersToBePlot(triNumberDoubles,"Detector",
                DetectorColor.getInstance().getCurrentColor(), PlotWay.Scatter);
    }

    public static boolean isEmpty()
    {
        if(detectors==null)
            return true;
        else
            return false;
    }

    public static int getNumber(){return detectors.length;}

    //相空间绘图
    public static BiNumberDouble[] biNumberDoublePOfPhaseOfDetect(int index, DiretionXYZ1D diretionXYZ1D)
    {
        return getDetector(index).biNumberDoublesOfSpace(diretionXYZ1D);
    }

    public static void clear()
    {
        detectors=null;
    }

    public static int getDetectorNum()
    {
        return detectors.length;
    }

    public static String[] testPrint(int i)
    {
        return DetectorsSet.getDetector(i).testGetPosition();
    }
}

package zrx.simulate.advancedDataContainer;

import zrx.gui.realPlot.PlotWay;
import zrx.gui.realPlot.toBePlot.TrinumbersToBePlot;
import zrx.gui.menuBar.viewMenuItem.ComponentColorMenuItem.ReferredParticleTrackColor;
import zrx.simulate.basicDataContainer.RunningParticle;
import zrx.simulate.basicDataStructure.TriNumberDouble;

public class ReferredParticleTrack {//单位m
    private static ReferredParticleTrack referredParticleTrack;
    public static ReferredParticleTrack getInstance()
    {
        if(referredParticleTrack==null)
            referredParticleTrack=new ReferredParticleTrack();

        return referredParticleTrack;
    }

    private TriNumberDouble[] track=null;
    private TriNumberDouble[] velocitys = null;
    private double stepLength=0.0;
    private int i=0;//用于加数据的指针
    private double length=0.0;//轨道长度，设置detector时需要检测

    //用数组大小来初始化。为什么知道数组大小？因为run的时候需要设定步长和最大仿真距离
    public void initialize(int size)
    {
        track = new TriNumberDouble[size];
        velocitys = new TriNumberDouble[size];
        i=0;
        stepLength=0.0;
        length=0.0;
    }

    //一个一个加入轨迹数据到其中，粒子指挥部调用
    public void addInIt(RunningParticle p)
    {
        track[i]=new TriNumberDouble(p.p);
        velocitys[i]=new TriNumberDouble(p.v);
        i++;
    }

    public void setLength(double length)
    {
        this.length=length;
    }

    public double getLength() {
        return length;
    }

    //画图，JfreePlotReal调用
    public TrinumbersToBePlot toBePlotTriNumberDoubles()
    {
        //System.out.println("i = " + i);

        TriNumberDouble[] ans = new TriNumberDouble[i];
        for(int j=0;j<ans.length;j++)
        {
            ans[j]=TriNumberDouble.mTommNew(track[j]);
            //System.out.println("ans = " + ans[j]);
        }

        return new TrinumbersToBePlot(ans,"Referred Particle Track",
                ReferredParticleTrackColor.getInstance().getCurrentColor(), PlotWay.Line);
    }

    //画图函数查看数据是否存在
    public boolean isEmpty()
    {
        if(track==null)
            return true;
        else
            return false;
    }

    //用于清空，粒子指挥部调用
    public void clear()
    {
        track=null;
        velocitys =null;
        i=0;
        stepLength=0.0;
        length=0.0;
    }

    public void setStepLength(double stepLength) {
        this.stepLength = stepLength;
    }

    public TriNumberDouble getPositionByDistance(double distance)
    {
        int steps = (int)(Math.round(distance / stepLength));
        return track[steps];
    }


    public TriNumberDouble getVelocityByDistance(double distance)
    {
        int steps = (int)(Math.round(distance / stepLength));
        return velocitys[steps];
    }

    public void printTest()
    {
        System.out.println("current point number i = " + i);
        for(int j=0;j<i;j++)
            System.out.println(track[j].toString()+ "j="+j);
    }

    //no constructor
    private ReferredParticleTrack() {}
}

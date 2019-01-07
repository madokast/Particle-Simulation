/*
* 不用泛型了--2019年1月2日
* 因为里面可能有Trinumber[]，也可能有Binumber[](大錯特錯)
* 現在改了——只能有 TrinumbersToBePlot 和 BiinumbersToBePlot
* 到时候用instanceof
* 这样一来也没必要单独的画磁场
* 但是既然已经是这样了，那就算了
* 以后再说
* */

package zrx.gui.realPlot;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import zrx.gui.previewPhasePlot.ChartCaption;
import zrx.gui.realPlot.toBePlot.BinumbersToBePlot;
import zrx.gui.realPlot.toBePlot.SToBePlot;
import zrx.gui.realPlot.toBePlot.TrinumbersToBePlot;
import zrx.gui.setParticle.ErrorDialog;
import zrx.simulate.advancedDataContainer.DetectorsSet;
import zrx.simulate.advancedDataContainer.ReferredParticleTrack;
import zrx.simulate.basicDataContainer.ImportedMagnet;
import zrx.simulate.basicDataContainer.ReferredParticle;
import zrx.simulate.basicDataStructure.BiNumberDouble;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;

public final class JfreePlotReal {
    //工具类
    private JfreePlotReal(){}

    //绘图队列
    private static ArrayDeque<SToBePlot> trinumbersOrBinumbersToBePlotQueue = new ArrayDeque<SToBePlot>();

    public static void clearPlotQueue()
    {
        trinumbersOrBinumbersToBePlotQueue.clear();
    }

    public static void autoAddQueue()
    {
        //先清空
        clearPlotQueue();

        //嘗試添加磁場
        if(!ImportedMagnet.isEmpty())
            addToBePlotToQueue(ImportedMagnet.importedMagnetToBinumberEdge());
        //磁場不存在，不予繪圖
        else
            return;

        //尝试添加参考粒子方向
        if(ReferredParticle.getInstance().isAlreadySet())
            addToBePlotToQueue(ReferredParticle.getInstance().arrowOfStartToBePlot());

        //尝试添加参考粒子运动轨迹
        if(!ReferredParticleTrack.getInstance().isEmpty())
            addToBePlotToQueue(ReferredParticleTrack.getInstance().toBePlotTriNumberDoubles());

        //尝试添加探测器detector
        if(!DetectorsSet.isEmpty())
            addToBePlotToQueue(DetectorsSet.getDetectorsLocationToBePlot());
    }

    public static void addToBePlotToQueue(SToBePlot sToBePlot)
    {
        trinumbersOrBinumbersToBePlotQueue.addLast(sToBePlot);
    }

    //如果吐出來的是Binumber[] 那就直接返回
    //如果是Trinumber[]，就調用投影函數，轉換爲Binumber[]然後返回
    //其他？報錯！
    private static BinumbersToBePlot pollOrConvertToBinumberFromQueue(DirectionXYZ2D directionXYZ2D)
    {
        Object o = trinumbersOrBinumbersToBePlotQueue.pollFirst();

        if(o instanceof BinumbersToBePlot)
            return (BinumbersToBePlot)o;
        else if(o instanceof TrinumbersToBePlot)
        {
            return TrinumbersToBePlot.trinumbersToBePlotToBi(((TrinumbersToBePlot)o));
        }
        else
        {
            ErrorDialog.getInstance().setErrorInformationToLabelAndVisible(
                    "error in private static Object pollOrConvertToBinumberFromQueue(DirectionXYZ2D directionXYZ2D)");

            return null;
        }
    }

    private static boolean QueueIsEmpty()
    {

        return trinumbersOrBinumbersToBePlotQueue.isEmpty();
    }

    private static int QueueCurrentLength()
    {
        //the number of elements in this deque
        return trinumbersOrBinumbersToBePlotQueue.size();
    }

    public static Image queuePlotAsImage(DirectionXYZ2D directionXYZ2D, boolean axisEqual)
            throws Exception
    {
        //自動添加繪圖列隊
        autoAddQueue();//miao!!

        //空的就調用實際空間的clear清圖
        if(QueueIsEmpty())
        {
            PlotRealSpace.getInstance().clear();
            throw new Exception("Magnetic field data not found");
        }

        int i=1;//索引
        BinumbersToBePlot binumbersToBePlot;
        XYSeries[] xySeriesArr = new XYSeries[QueueCurrentLength()];//序列，畫圖時需要把Binumber[]的數據導入其中
        XYLineAndShapeRenderer[] renderers = new XYLineAndShapeRenderer[QueueCurrentLength()];

        JFreeChart chart = drawMagNetAndGetChart(directionXYZ2D);//先画磁场

        while(!QueueIsEmpty())
        {
            //吐出來！
            binumbersToBePlot = pollOrConvertToBinumberFromQueue(directionXYZ2D);

            //畫圖序列
            xySeriesArr[i] = new XYSeries(binumbersToBePlot.getName(),false,true);
            for(BiNumberDouble t:binumbersToBePlot.getBiNumberDoubles())
                xySeriesArr[i].add(t.x,t.y);

            //把第一个加到最后一个点之后，实现图形封闭
            if(binumbersToBePlot.getPlotWay()==PlotWay.CloseLine)
                xySeriesArr[i].add(binumbersToBePlot.getBiNumberDoubles()[0].x,
                        binumbersToBePlot.getBiNumberDoubles()[0].y);

            //繪圖必須要的一步，把xySeries放進XYSeriesCollection
            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(xySeriesArr[i]);

            XYPlot xyPlot = chart.getXYPlot();
            xyPlot.setDataset(i,dataset);


            renderers[i] =  new XYLineAndShapeRenderer();

            //设置颜色
            renderers[i].setSeriesPaint(0, binumbersToBePlot.getColor());

            //{
            //    System.out.println("---------JfreePlotreal--------------");
            //    System.out.println(binumbersToBePlot);
            //    System.out.println("i = " + i);
            //}

            if(binumbersToBePlot.getPlotWay()== PlotWay.CloseLine||
                    binumbersToBePlot.getPlotWay()== PlotWay.Line)
            {
                renderers[i].setSeriesLinesVisible(0,true);//线条可见
                renderers[i].setSeriesShapesVisible(0,false);//数据点不可见
            }
            else if(binumbersToBePlot.getPlotWay()==PlotWay.Scatter)
            {
                renderers[i].setSeriesShape(0,new Rectangle(-5,-5,10,10),false);
                renderers[i].setSeriesLinesVisible(0,false);//线条不可见
                renderers[i].setSeriesShapesVisible(0,true);//数据点可见

                //System.out.println("dodod");
                //心头大问题解决
            }
            xyPlot.setRenderer(i, renderers[i]);

            i++;
        }

        chart.setBorderVisible(false);
        chart.setBackgroundPaint(Color.WHITE);
        chart.setBackgroundImageAlpha(0.0f);

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);//背景白色

        return chart.createBufferedImage(PlotRealSpace.getInstance().getWidth(),
                PlotRealSpace.getInstance().getHeight());//出图

    }

    private static JFreeChart drawMagNetAndGetChart(DirectionXYZ2D directionXYZ2D)
    {
        //先画磁场，因为第一个总是磁场，没有磁场没有一切

        ChartCaption chartCaption = new ChartCaption(
                "Magnetic Field-"+ directionXYZ2D.getFirst()+ directionXYZ2D.getSecond(),
                directionXYZ2D.getFirst()+"/mm",
                directionXYZ2D.getSecond()+"/mm"
        );//标题 X Y轴名称

            BinumbersToBePlot binumbersToBePlot = pollOrConvertToBinumberFromQueue(directionXYZ2D);

            //畫圖序列
            XYSeries xySeries = new XYSeries(binumbersToBePlot.getName(),false,true);
            for(BiNumberDouble t:binumbersToBePlot.getBiNumberDoubles())
                xySeries.add(t.x,t.y);

            //把第一个加到最后一个点之后，实现图形封闭
            if(binumbersToBePlot.getPlotWay()==PlotWay.CloseLine)
                xySeries.add(binumbersToBePlot.getBiNumberDoubles()[0].x,
                        binumbersToBePlot.getBiNumberDoubles()[0].y);

            //繪圖必須要的一步，把xySeries放進XYSeriesCollection
            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(xySeries);

            JFreeChart chart = ChartFactory.createXYLineChart(
                    chartCaption.title, // chart title
                    chartCaption.xAxisLable, // x axis label
                    chartCaption.yAxisLable, // y axis label
                    dataset, // data
                    PlotOrientation.VERTICAL,
                    true, // include legend
                    false, // tooltips
                    false // urls
            );


            float[] dash = {10f,10f};
            XYPlot xyPlot = chart.getXYPlot();
            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesStroke(0,new BasicStroke(//折现
                    2.0f,//线条宽度
                    BasicStroke.CAP_ROUND,//折线端点是圆弧的
                    BasicStroke.JOIN_ROUND,//折现转弯时，转角是圆弧的
                    10.0f,//
                    dash,//实线-空 循环
                    5.0f//循环开始的位置
            ));
            renderer.setSeriesShapesVisible(0,false);//数据点不可见

            xyPlot.setRenderer(0,renderer);


            //颜色设置
            chart.getXYPlot().getRenderer().setSeriesPaint(0,binumbersToBePlot.getColor());

            return chart;
    }
}

/*
*     public TrinumbersToBePlot(TriNumberDouble[] triNumberDoubles, String name, Color color, PlotWay plotWay) {
        this.triNumberDoubles = triNumberDoubles;
        this.name = name;
        this.color = color;
        this.plotWay = plotWay;
    }

    enum PlotWay{
        CloseLine,Line,Scatter;
    }


    */

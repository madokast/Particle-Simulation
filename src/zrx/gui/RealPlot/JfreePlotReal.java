package zrx.gui.RealPlot;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import zrx.gui.PhasePlot.ChartCaption;
import zrx.gui.setParticle.ErrorDialog;
import zrx.simulate.basicDataContainer.ImportedMagnet;
import zrx.simulate.basicDataStructure.BiNumberDouble;

import java.awt.*;
import java.util.ArrayDeque;

public final class JfreePlotReal {
    //工具类
    private JfreePlotReal(){}

    //绘图队列
    private static ArrayDeque<TrinumbersToBePlot> trinumbersToBePlotQueue;

    public static void clearPlotQueue()
    {
        if(trinumbersToBePlotQueue==null)
            trinumbersToBePlotQueue = new ArrayDeque<TrinumbersToBePlot>();

        trinumbersToBePlotQueue.clear();
    }

    public static void autoAddQueue()
    {

        if(trinumbersToBePlotQueue==null)
            trinumbersToBePlotQueue = new ArrayDeque<TrinumbersToBePlot>();

        //先清空
        trinumbersToBePlotQueue.clear();

        //!!!好东西
    }

    public static void addTrinumbersToBePlotToQueue(TrinumbersToBePlot trinumbersToBePlot)
    {
        if(trinumbersToBePlotQueue==null)
            trinumbersToBePlotQueue = new ArrayDeque<TrinumbersToBePlot>();

        trinumbersToBePlotQueue.addLast(trinumbersToBePlot);
    }

    private static TrinumbersToBePlot pollTrinumbersToBePlotFromQueue()
    {
        return (TrinumbersToBePlot)trinumbersToBePlotQueue.pollLast();
    }

    private static boolean QueueIsEmpty()
    {
        if(trinumbersToBePlotQueue==null)
            trinumbersToBePlotQueue = new ArrayDeque<TrinumbersToBePlot>();

        return trinumbersToBePlotQueue.isEmpty();
    }

    private static int QueueCurrentLength()
    {
        if(trinumbersToBePlotQueue==null)
            trinumbersToBePlotQueue = new ArrayDeque<TrinumbersToBePlot>();

        //the number of elements in this deque
        return trinumbersToBePlotQueue.size();
    }

    public static Image queuePlotAsImage(DirectionXYZ directionXYZ,boolean axisEqual)
    {
        autoAddQueue();//miao!!

        TrinumbersToBePlot trinumbersToBePlot;//从queue中提取到的trinumbersToBePlot
        BiNumberDouble[] biNumberDoubles;//trinumber在directionXYZ中变成binumber
        XYSeries[] seriesArr = new XYSeries[QueueCurrentLength()];//序列
        JFreeChart chart=null;//画图chart板子
        ChartCaption chartCaption = new ChartCaption(
                "Magnetic Field"+directionXYZ.getFirst()+directionXYZ.getSecond(),
                directionXYZ.getFirst()+"/mm",
                directionXYZ.getSecond()+"/mm"
        );//标题 X Y轴名称

        //先画磁场分布，永远先画磁场
        if(chart==null)
        {
            //磁场二维数据
            BiNumberDouble[] biNumberDoublesMAG= ImportedMagnet.importedMagnetToBinumberEdge(directionXYZ);
            XYSeries xySeriesMAG = new XYSeries(/*之后i=1*/0,false,true);
            for(BiNumberDouble t:biNumberDoublesMAG)
            {
                xySeriesMAG.add(t.x,t.y);
            }
            //把第一个加到最后一个点之后，实现图形封闭
            xySeriesMAG.add(biNumberDoublesMAG[0].x,biNumberDoublesMAG[0].y);

            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(xySeriesMAG);

            chart = ChartFactory.createXYLineChart(
                    chartCaption.title, // chart title
                    chartCaption.xAxisLable, // x axis label
                    chartCaption.yAxisLable, // y axis label
                    dataset, // data
                    PlotOrientation.VERTICAL,
                    false, // include legend
                    false, // tooltips
                    false // urls
            );

            Plot plot = chart.getPlot();
            plot.setBackgroundPaint(Color.white);
            ((XYPlot)plot).getRenderer().setBaseOutlineStroke(new BasicStroke(0.0F));//最小也很大诶

            if(axisEqual)
            {//坐标轴设置，横轴坐标比例尺一致
                //x轴和y轴
                //DomainAxis-x轴
                //RangeAxis-y轴
                ValueAxis axisX =((XYPlot) plot).getDomainAxis();
                ValueAxis axisY =((XYPlot) plot).getRangeAxis();

                double maxX = axisX.getUpperBound();
                double minX = axisX.getLowerBound();
                double maxY = axisY.getUpperBound();
                double minY = axisY.getLowerBound();

                axisX.setAutoRange(false);
                axisY.setAutoRange(false);

                axisX.setUpperBound(Math.max(maxX,maxY));
                axisY.setUpperBound(Math.max(maxY,maxY));
                axisX.setLowerBound(Math.min(minX,minY));
                axisY.setLowerBound(Math.min(minX,minY));
            }

            chart.setBorderVisible(false);
            chart.setBackgroundPaint(Color.WHITE);
            chart.setBackgroundImageAlpha(0.0f);

        }


        //画其他元素
        int i=1;//从1开始 因为0是磁场
        while(!QueueIsEmpty())
        {
            //XY序列
            seriesArr[i] = new XYSeries(/* KEY */i,false,true);
            //XYSeries(Comparable key, boolean autoSort, boolean allowDuplicateXValues)
            //XY序列中添加元素，远足从队列得到trinumbersToBePlot，然后用directionXYZ拿到biNumberDoubles
            //最后加入seriesArr[i]
            trinumbersToBePlot=pollTrinumbersToBePlotFromQueue();
            biNumberDoubles = TrinumbersToBePlot.trisToBinumbers(trinumbersToBePlot,directionXYZ);
            for (BiNumberDouble t:biNumberDoubles)
            {
                seriesArr[i].add(t.x, t.y);
            }

            //如果画闭合曲线
            if(trinumbersToBePlot.getPlotWay()==TrinumbersToBePlot.PlotWay.CloseLine)
            {
                //把第一个加到最后一个点之后，实现图形封闭
                seriesArr[i].add(biNumberDoubles[0].x,biNumberDoubles[0].y);
            }

            //数据集
            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(seriesArr[i]);

            XYPlot xyPlot = chart.getXYPlot();
            xyPlot.setDataset(i,dataset);
            //线or散点
            XYLineAndShapeRenderer render =  new XYLineAndShapeRenderer();
            render.setSeriesPaint(0, Color.black);
            if(trinumbersToBePlot.getPlotWay()==TrinumbersToBePlot.PlotWay.CloseLine||
                    trinumbersToBePlot.getPlotWay()==TrinumbersToBePlot.PlotWay.Line)
            {
                render.setSeriesLinesVisible(i,true);
                render.setSeriesShapesVisible(i,false);
            }
            xyPlot.setRenderer(i, render);

        }

        return chart.createBufferedImage(PlotRealSpace.getInstance().getWidth(),
                PlotRealSpace.getInstance().getHeight());//出图
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

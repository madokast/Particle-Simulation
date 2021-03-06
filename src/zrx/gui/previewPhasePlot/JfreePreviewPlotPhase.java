package zrx.gui.previewPhasePlot;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import zrx.gui.phasePlot.PhaseSpacePlot;
import zrx.gui.phasePlot.phasePlotDialog;
import zrx.simulate.basicDataStructure.BiNumberDouble;
import zrx.simulate.tool.SpecialNumber;

import java.awt.*;

public class JfreePreviewPlotPhase {
    //工具类
    private JfreePreviewPlotPhase(){}

    public static Image BiNumberPlotCloseChartAsImage(int width, int height, ChartCaption chartCaption, BiNumberDouble[] biNumberDoubles)
    {
        //哇哇哇
        //终于搞定了 关闭自动排序 允许x对应多个y
        //2018年12月30日 12点32分
        XYSeries series = new XYSeries("xySeries",false,true);

        for (BiNumberDouble t:biNumberDoubles)
        {
            series.add(t.x, t.y);
        }

        //把第一个加到最后一个点之后，实现图形封闭
        series.add(biNumberDoubles[0].x,biNumberDoubles[0].y);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                chartCaption.title, // chart title
                chartCaption.xAxisLable, // x axis label
                chartCaption.yAxisLable, // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                false, // include legend
                false, // tooltips
                false // urls
        );

        Plot plot = chart.getXYPlot();

        plot.setBackgroundPaint(Color.white);
        ((XYPlot) plot).getRenderer().setSeriesPaint(0, Color.black);

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

        return chart.createBufferedImage(width,height);
    }

    public static Image BiNumberPlotScatterChartAsImage(int width, int height, ChartCaption chartCaption, BiNumberDouble[] biNumberDoubles)
    {
        //哇哇哇
        //终于搞定了 关闭自动排序 允许x对应多个y
        //2018年12月30日 12点32分
        XYSeries series = new XYSeries("xySeries",false,true);

        for (BiNumberDouble t:biNumberDoubles)
        {
            if(t!=null)
                series.add(t.x, t.y);
        }


        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createScatterPlot(
                chartCaption.title, // chart title
                chartCaption.xAxisLable, // x axis label
                chartCaption.yAxisLable, // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                false, // include legend
                false, // tooltips
                false // urls
        );

        Plot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.white);

        XYPlot xyPlot = chart.getXYPlot();
        xyPlot.getRenderer().setSeriesPaint(0, Color.black);
        xyPlot.getRenderer().setSeriesShape(0,new Rectangle(-1,-1,1,1));

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

            double max = SpecialNumber.absMax(maxX,maxY,minX,minY);

            axisX.setAutoRange(false);
            axisY.setAutoRange(false);

            max*=phasePlotDialog.getInstance().getRateOfJfreeAxis();

            axisX.setUpperBound(max);
            axisY.setUpperBound(max);
            axisX.setLowerBound(-max);
            axisY.setLowerBound(-max);
        }

        chart.setBorderVisible(false);
        chart.setBackgroundPaint(Color.WHITE);
        chart.setBackgroundImageAlpha(0.0f);

        return chart.createBufferedImage(width,height);
    }

}

package zrx.gui.previewPhasePlot;

import zrx.gui.tool.GUItools;
import zrx.simulate.basicDataStructure.BiNumberDouble;
import zrx.simulate.Geometry.Ellipse;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PreviewPlotPhaseSpace extends Panel {
    //单一实例
    private static PreviewPlotPhaseSpace previewPlotPhaseSpace;
    public static PreviewPlotPhaseSpace getInstance()
    {
        if(previewPlotPhaseSpace ==null)
            previewPlotPhaseSpace =new PreviewPlotPhaseSpace();

        return previewPlotPhaseSpace;
    }

    //绘图大小
    private static final int Width = (int)((double)GUItools.getScreenWidth()*0.4);
    private static final int Height = Width;

    //画板
    private static MyCanvas myCanvas = new MyCanvas();

    //构造器
    private PreviewPlotPhaseSpace()
    {
        myCanvas.setBounds(0,0,Width,Height);
        this.add(myCanvas,BorderLayout.CENTER);
        this.setBounds(0,0,Width,Height);

        this.clear();
    }

    //画图方法
    public void drawEllipse(Ellipse ellipse, ChartCaption jfreeChartCaption)
    {
        BufferedImage bufferedImage = new BufferedImage(Width,Height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(pointAtEllipseEdgeImage(ellipse,100,jfreeChartCaption),0,0,null);

        myCanvas.getImageAndPrintIt(bufferedImage);
    }

    //内部方法
    private static Image pointAtEllipseEdgeImage(Ellipse ellipse, int num, ChartCaption jfreeChartCaption)
    {
        BiNumberDouble[] pointsAtEllipseEdge = Ellipse.pointAtEllipseEdge(ellipse,num);

        /*//m转为mm是错误的
        BiNumberDouble[] pointsToBeploted = new BiNumberDouble[num];
        for(int i=0;i<num;i++)
        {
            //pointsAtEllipseEdge[i].print();//test
            pointsToBeploted[i] = BiNumberDouble.mTOmm(pointsAtEllipseEdge[i]);
        }
        */

        return JfreePreviewPlotPhase.BiNumberPlotCloseChartAsImage(Width, Height,
                jfreeChartCaption,
                pointsAtEllipseEdge
        );
    }

    //清屏
    public void clear()
    {
        BufferedImage bufferedImage = new BufferedImage(Width,Height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0,bufferedImage.getWidth(),bufferedImage.getHeight());
        graphics.setColor(Color.ORANGE);
        GUItools.drawCenteredString(graphics,"Phase Ellipse Preview",
                new Rectangle(Width,Height),new Font(null,Font.PLAIN,28));

        myCanvas.getImageAndPrintIt(bufferedImage);
    }

}

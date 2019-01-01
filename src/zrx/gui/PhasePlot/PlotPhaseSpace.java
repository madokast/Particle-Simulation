package zrx.gui.PhasePlot;

import zrx.gui.tool.GUItools;
import zrx.simulate.basicDataStructure.BiNumberDouble;
import zrx.simulate.tool.Ellipse;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlotPhaseSpace extends Panel {
    private static PlotPhaseSpace plotPhaseSpace;
    public static PlotPhaseSpace getInstance()
    {
        if(plotPhaseSpace==null)
            plotPhaseSpace=new PlotPhaseSpace();

        return plotPhaseSpace;
    }

    private static final int Width = 600;
    private static final int Height = 600;

    private static MyCanvas myCanvas = new MyCanvas();
    private PlotPhaseSpace()
    {
        myCanvas.setBounds(0,0,Width,Height);
        this.add(myCanvas,BorderLayout.CENTER);
        this.setBounds(0,0,Width,Height);

        this.clear();
    }

    public void drawEllipse(Ellipse ellipse, ChartCaption jfreeChartCaption)
    {
        BufferedImage bufferedImage = new BufferedImage(Width,Height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(pointAtEllipseEdgeImage(ellipse,100,jfreeChartCaption),0,0,null);

        myCanvas.getImageAndPrintIt(bufferedImage);
    }

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

        return JfreePlotPhase.BinumberPlotCloseChartAsImage(Width, Height,
                jfreeChartCaption,
                pointsAtEllipseEdge
        );
    }

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

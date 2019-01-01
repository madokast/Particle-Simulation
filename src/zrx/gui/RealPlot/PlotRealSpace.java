package zrx.gui.RealPlot;

import zrx.MainProcess;
import zrx.gui.PhasePlot.MyCanvas;
import zrx.gui.menuBar.ViewMenu;
import zrx.gui.tool.GUItools;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlotRealSpace extends Panel {
    private static PlotRealSpace plotRealSpace;
    public static PlotRealSpace getInstance()
    {
        if(plotRealSpace==null)
            plotRealSpace=new PlotRealSpace();

        return plotRealSpace;
    }

    private static final int Width = 1500;
    private static final int Height = 700;

    private static MyCanvas myCanvas = new MyCanvas();


    public int getWidth() {
        return Width;
    }

    public int getHeight() {
        return Height;
    }

    private PlotRealSpace()
    {
        myCanvas.setBounds(0,0,Width,Height);
        this.add(myCanvas,BorderLayout.CENTER);
        this.setBounds(0,0,Width,Height);

        this.clear();
    }

    //要画图 调用这个。
    //不对！下面那个才是真理，最简单的接口，没有之一
    //这个画图是ViewMenu内部人员才调用的
    public void plotImage(DirectionXYZ directionXYZ,boolean axisEqual)
    {
        Image image = JfreePlotReal.queuePlotAsImage(directionXYZ,axisEqual);
        BufferedImage bufferedImage = new BufferedImage(Width,Height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(image,0,0,null);

        myCanvas.getImageAndPrintIt(bufferedImage);
    }

    //画图就是它！直接获得ViewMenu设定的状态。外部人员画图专属
    //2019年1月1日
    public void fresh()
    {
        plotImage(ViewMenu.getInstance().getDirectionXYZ(),ViewMenu.getInstance().isAxisEqual());
    }


    public void clear()
    {
        BufferedImage bufferedImage = new BufferedImage(Width,Height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();

        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0,0,bufferedImage.getWidth(),bufferedImage.getHeight());

        graphics.setColor(Color.ORANGE);
        GUItools.drawCenteredString(graphics, MainProcess.NAME,
                new Rectangle(Width,Height),new Font(null,Font.PLAIN,55));

        //System.out.println("clear");
        myCanvas.getImageAndPrintIt(bufferedImage);

    }

}

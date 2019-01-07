package zrx.gui.realPlot;

import zrx.MainProcess;
import zrx.gui.previewPhasePlot.MyCanvas;
import zrx.gui.menuBar.viewMenuItem.AxisEqualMenuItem;
import zrx.gui.menuBar.viewMenuItem.DirectionMenu;
import zrx.gui.setParticle.ErrorDialog;
import zrx.gui.tool.GUItools;
import zrx.simulate.basicDataContainer.ImportedMagnet;

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

    private static int Width = (int)((double)GUItools.getScreenWidth()*0.9);
    private static int Height = (int)((double)GUItools.getScreenHeight()*0.8);

    private static MyCanvas myCanvas = new MyCanvas();


    public int getWidth() {
        return Width;
    }

    public int getHeight() {
        return Height;
    }

    public static void resetWidthAndHeight(Rectangle r)
    {
        Width = (int)((double)r.width*0.9);
        Height = (int)((double)r.height*0.8);

        myCanvas.setBounds(0,0,Width,Height);
        PlotRealSpace.plotRealSpace.setBounds(0,0,Width,Height);

        plotRealSpace.fresh();
    }

    private PlotRealSpace()
    {
        myCanvas.setBounds(0,0,Width,Height);
        this.add(myCanvas,BorderLayout.CENTER);
        this.setBounds(0,0,Width,Height);

        this.clear();
    }

    //要画图 调用这个
    //不对！下面那个才是真理，最简单的接口，没有之一
    //这个画图是ViewMenu内部人员才调用的
    public void plotImage(DirectionXYZ2D directionXYZ2D, boolean axisEqual)
    {
        //如果没有磁场数据，不画图
        if(ImportedMagnet.isEmpty())
        {
            ErrorDialog.getInstance().setErrorInformationToLabelAndVisible("Magnetic field data not found");
            return;
        }

        try {
            Image image = JfreePlotReal.queuePlotAsImage(directionXYZ2D,axisEqual);
            BufferedImage bufferedImage = new BufferedImage(Width,Height,BufferedImage.TYPE_INT_RGB);
            Graphics graphics = bufferedImage.getGraphics();
            graphics.drawImage(image,0,0,null);

            myCanvas.getImageAndPrintIt(bufferedImage);
        }
        catch (Exception e) {
            ErrorDialog.getInstance().setErrorInformationToLabelAndVisible(e.getMessage());
        }

    }

    //画图就是它！直接获得ViewMenu设定的状态。外部人员画图专属
    //2019年1月1日
    public void fresh()
    {
        if(ImportedMagnet.isEmpty())
            clear();
        else
            plotImage(DirectionMenu.getInstance().getDirectionXYZ2D(), AxisEqualMenuItem.getInstance().isAxisEqualBooleam());
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

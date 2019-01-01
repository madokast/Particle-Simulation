package zrx.gui.Plot;

import zrx.MainProcess;
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
    private static final int Height = 720;

    private static MyCanvas myCanvas = new MyCanvas();

    private PlotRealSpace()
    {
        myCanvas.setBounds(0,0,Width,Height);
        this.add(myCanvas,BorderLayout.CENTER);
        this.setBounds(0,0,Width,Height);

        this.clear();
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

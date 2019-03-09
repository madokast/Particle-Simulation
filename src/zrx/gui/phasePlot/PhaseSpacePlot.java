package zrx.gui.phasePlot;

//import javafx.beans.binding.When;
import zrx.gui.previewPhasePlot.ChartCaption;
import zrx.gui.previewPhasePlot.JfreePreviewPlotPhase;
import zrx.gui.previewPhasePlot.MyCanvas;
import zrx.gui.realPlot.DiretionXYZ1D;
import zrx.gui.tool.GUItools;
import zrx.simulate.advancedDataContainer.DetectorsSet;
import zrx.simulate.basicDataStructure.BiNumberDouble;
import zrx.simulate.tool.SpecialNumber;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PhaseSpacePlot extends Panel {
    //构造器
    private static PhaseSpacePlot phaseSpacePlot;
    public static PhaseSpacePlot getInstance()
    {
        if(phaseSpacePlot==null)
            phaseSpacePlot=new PhaseSpacePlot();

        return phaseSpacePlot;
    }

    //绘图大小
    private static final int Width = (int)((double) GUItools.getScreenWidth()*0.4);
    private static final int Height = Width;

    //画板，左X，右Y
    private static MyCanvas myCanvasX = new MyCanvas();
    private static MyCanvas myCanvasY = new MyCanvas();

    //绘图

    //index是detector的编号
    public void drawPhasePlot(int index)
        throws Exception
    {
        if(DetectorsSet.isEmpty())
            throw new Exception("Detectors unset");

        if(index<0||index>=DetectorsSet.getNumber())
            throw new Exception("No detector of index-"+index);

        if(!DetectorsSet.getDetector(index).hasData())
            throw new Exception("Detavtor of index-"+index+" has no data");

        BufferedImage bufferedImageX = new BufferedImage(Width,Height,BufferedImage.TYPE_INT_RGB);
        Graphics graphicsX = bufferedImageX.getGraphics();
        graphicsX.drawImage(phaseImageX(index),0,0,null);
        myCanvasX.getImageAndPrintIt(bufferedImageX);

        BufferedImage bufferedImageY = new BufferedImage(Width,Height,BufferedImage.TYPE_INT_RGB);
        Graphics graphicsY = bufferedImageY.getGraphics();
        graphicsY.drawImage(phaseImageY(index),0,0,null);
        myCanvasY.getImageAndPrintIt(bufferedImageY);

    }


    private Image phaseImageX(int index)
    {
        BiNumberDouble[] biNumberDoubles = DetectorsSet.biNumberDoublePOfPhaseOfDetect(index, DiretionXYZ1D.X);

        return JfreePreviewPlotPhase.BiNumberPlotScatterChartAsImage(Width,Height,
                new ChartCaption(
                        "Detector-"+index+"  "+DetectorsSet.getDetector(index).getLocation()* SpecialNumber.M2MM+"mm",
                        "x(horizontal displacement)/mm",
                        "x\'(horizontal half angle of divergence)/mrad"
                ),
                biNumberDoubles
                );
    }

    private Image phaseImageY(int index)
    {
        BiNumberDouble[] biNumberDoubles = DetectorsSet.biNumberDoublePOfPhaseOfDetect(index, DiretionXYZ1D.Y);

        return JfreePreviewPlotPhase.BiNumberPlotScatterChartAsImage(Width,Height,
                new ChartCaption(
                        "Detector-"+index+"  "+DetectorsSet.getDetector(index).getLocation()* SpecialNumber.M2MM+"mm",
                        "y(horizontal displacement)/mm",
                        "y\'(horizontal half angle of divergence)/mrad"
                ),
                biNumberDoubles
        );
    }


    //清屏
    public void clear()
    {
        {//X图
            BufferedImage bufferedImageX = new BufferedImage(Width,Height,BufferedImage.TYPE_INT_RGB);
            Graphics graphicsX = bufferedImageX.getGraphics();

            graphicsX.setColor(Color.WHITE);
            graphicsX.fillRect(0,0,bufferedImageX.getWidth(),bufferedImageX.getHeight());
            graphicsX.setColor(Color.ORANGE);
            GUItools.drawCenteredString(graphicsX,"Phase Space-X",
                    new Rectangle(Width,Height),new Font(null,Font.PLAIN,28));

            myCanvasX.getImageAndPrintIt(bufferedImageX);
        }

        {//Y图
            BufferedImage bufferedImageY = new BufferedImage(Width,Height,BufferedImage.TYPE_INT_RGB);
            Graphics graphicsY = bufferedImageY.getGraphics();

            graphicsY.setColor(Color.WHITE);
            graphicsY.fillRect(0,0,bufferedImageY.getWidth(),bufferedImageY.getHeight());
            graphicsY.setColor(Color.ORANGE);
            GUItools.drawCenteredString(graphicsY,"Phase Ellipse Preview-Y",
                    new Rectangle(Width,Height),new Font(null,Font.PLAIN,28));

            myCanvasY.getImageAndPrintIt(bufferedImageY);
        }
    }

    //构造器
    private PhaseSpacePlot()
    {
        myCanvasX.setBounds(0,0,Width,Height);
        myCanvasY.setBounds(0,0,Width,Height);

        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));

        this.add(myCanvasX);
        this.add(myCanvasY);
    }



}

package zrx.gui.menuBar.viewMenuItem.ComponentColorMenuItem;

import zrx.gui.MainWindow;
import zrx.gui.realPlot.PlotRealSpace;

import javax.swing.*;
import java.awt.*;

public class ReferredParticleStartArrowColor extends Menu{
    private static ReferredParticleStartArrowColor referredParticleStartArrowColor;
    public static ReferredParticleStartArrowColor getInstance()
    {
        if(referredParticleStartArrowColor==null)
            referredParticleStartArrowColor=new ReferredParticleStartArrowColor();

        return referredParticleStartArrowColor;
    }


    private Color currentColor = Color.BLACK;

    private MenuItem blackMenuItem = new MenuItem("Black √");
    private MenuItem redMenuItem = new MenuItem("red");
    private MenuItem whiteMenuItem = new MenuItem("White");
    private MenuItem customizerMenuItem = new MenuItem("Customizer");
    {
        blackMenuItem.addActionListener(e->changeColor(Color.BLACK));
        redMenuItem.addActionListener(e->changeColor(Color.RED));
        whiteMenuItem.addActionListener(e->changeColor(Color.WHITE));
        customizerMenuItem.addActionListener(e->{
            //原来返回值就是选择的颜色 参数3只是窗口出现后的默认颜色
            currentColor = JColorChooser.showDialog(MainWindow.getInstance(),"Customize color",currentColor);
            //System.out.println("currentColor = " + currentColor.toString());
            resetLable(true);
            //fresh image
            //在绘图函数中，会自动调用颜色
            //因为颜色可以单独设置，若传参数，太不可能
            PlotRealSpace.getInstance().fresh();
        });
    }

    private ReferredParticleStartArrowColor()
    {
        super("Referred particle entering arrow");
        this.add(blackMenuItem);
        this.add(redMenuItem);
        this.add(whiteMenuItem);
        this.add(customizerMenuItem);

    }

    private void changeColor(Color color)
    {
        //fresh current color
        currentColor=color;

        resetLable(false);

        //fresh image
        //在绘图函数中，会自动调用颜色
        //因为颜色可以单独设置，若传参数，太不可能
        PlotRealSpace.getInstance().fresh();
    }

    private void resetLable(boolean isCustomize)
    {
        //initial
        blackMenuItem.setLabel("Black");
        redMenuItem.setLabel("Red");
        whiteMenuItem.setLabel("White");
        customizerMenuItem.setLabel("Customizer");

        //change label
        if(isCustomize)
            customizerMenuItem.setLabel("Customizer √");
        else if(currentColor==Color.BLACK)
            blackMenuItem.setLabel("Black √");
        else if(currentColor==Color.RED)
            redMenuItem.setLabel("Red √");
        else if(currentColor==Color.WHITE)
            whiteMenuItem.setLabel("White √");
    }

    //for plot function getting color
    public Color getCurrentColor() {
        return currentColor;
    }
}

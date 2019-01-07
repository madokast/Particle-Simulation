package zrx.gui.menuBar.viewMenuItem;

import zrx.gui.realPlot.DirectionXYZ2D;
import zrx.gui.realPlot.PlotRealSpace;
import zrx.gui.setParticle.ErrorDialog;
import zrx.simulate.basicDataContainer.ImportedMagnet;

import java.awt.*;

public class DirectionMenu extends Menu {
    private static DirectionMenu directionMenu;
    public static DirectionMenu getInstance()
    {
        if(directionMenu==null)
            directionMenu = new DirectionMenu();

        return directionMenu;
    }

    //default XY-direction
    private DirectionXYZ2D directionXYZ2D = DirectionXYZ2D.XY;

    public DirectionXYZ2D getDirectionXYZ2D() {
        return directionXYZ2D;
    }

    //观测方位切换 二级目录
    private MenuItem XYImportedMagnet = new MenuItem("XY √");
    private MenuItem XZImportedMagnet = new MenuItem("XZ");
    private MenuItem YXImportedMagnet = new MenuItem("YX");
    private MenuItem YZImportedMagnet = new MenuItem("YZ");
    private MenuItem ZXImportedMagnet = new MenuItem("ZX");
    private MenuItem ZYImportedMagnet = new MenuItem("ZY");
    {
        XYImportedMagnet.addActionListener(e->actionListenerFunc(DirectionXYZ2D.XY));
        XZImportedMagnet.addActionListener(e->actionListenerFunc(DirectionXYZ2D.XZ));
        YXImportedMagnet.addActionListener(e->actionListenerFunc(DirectionXYZ2D.YX));
        YZImportedMagnet.addActionListener(e->actionListenerFunc(DirectionXYZ2D.YZ));
        ZXImportedMagnet.addActionListener(e->actionListenerFunc(DirectionXYZ2D.ZX));
        ZYImportedMagnet.addActionListener(e->actionListenerFunc(DirectionXYZ2D.ZY));
    }

    //观测发现 一级目录
    private Menu direction = new Menu("Direction");
    {
        direction.add(XYImportedMagnet);
        direction.add(XZImportedMagnet);
        direction.add(YXImportedMagnet);
        direction.add(YZImportedMagnet);
        direction.add(ZXImportedMagnet);
        direction.add(ZYImportedMagnet);
    }

    private void actionListenerFunc(DirectionXYZ2D directionXYZ2D)
    {
        this.directionXYZ2D = directionXYZ2D;
        XYZImportedMagnetLableChange();

        if(ImportedMagnet.isEmpty())
        {
            ErrorDialog.getInstance().setErrorInformationToLabelAndVisible("Magnetic Field Data not found");
            return;
        }

        PlotRealSpace.getInstance().plotImage(directionXYZ2D,AxisEqualMenuItem.getInstance().isAxisEqualBooleam());
    }

    //标签改变
    private void XYZImportedMagnetLableChange()
    {
        //初始化
        XYImportedMagnet.setLabel("XY");
        XZImportedMagnet.setLabel("XY");
        YXImportedMagnet.setLabel("YX");
        YZImportedMagnet.setLabel("YZ");
        ZXImportedMagnet.setLabel("ZX");
        ZYImportedMagnet.setLabel("ZY");

        if(this.directionXYZ2D == DirectionXYZ2D.XY)
            XYImportedMagnet.setLabel("XY √");

        if(this.directionXYZ2D == DirectionXYZ2D.XZ)
            XZImportedMagnet.setLabel("XZ √");

        if(this.directionXYZ2D == DirectionXYZ2D.YX)
            YXImportedMagnet.setLabel("YX √");

        if(this.directionXYZ2D == DirectionXYZ2D.YZ)
            YZImportedMagnet.setLabel("YZ √");

        if(this.directionXYZ2D == DirectionXYZ2D.ZX)
            ZXImportedMagnet.setLabel("ZX √");

        if(this.directionXYZ2D == DirectionXYZ2D.ZY)
            ZYImportedMagnet.setLabel("ZY √");
    }

    private DirectionMenu()
    {
        super("Direction");
        this.add(XYImportedMagnet);
        this.add(XZImportedMagnet);
        this.add(YXImportedMagnet);
        this.add(YZImportedMagnet);
        this.add(ZXImportedMagnet);
        this.add(ZYImportedMagnet);
    }
}

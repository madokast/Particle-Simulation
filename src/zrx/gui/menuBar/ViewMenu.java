package zrx.gui.menuBar;

import zrx.gui.RealPlot.DirectionXYZ;
import zrx.gui.RealPlot.PlotRealSpace;
import zrx.gui.setParticle.ErrorDialog;
import zrx.simulate.basicDataContainer.ImportedMagnet;

import java.awt.*;

public class ViewMenu extends Menu {
    private static ViewMenu viewMenu;
    public static ViewMenu getInstance()
    {
        if(viewMenu==null)
            viewMenu=new ViewMenu();

        return viewMenu;
    }

    //观测方向
    private DirectionXYZ directionXYZ = DirectionXYZ.XY;

    //坐标轴相等与否切换
    private boolean axisEqual=true;
    private MenuItem axisEqualMenuItem = new MenuItem("Axis equal √");
    {
        axisEqualMenuItem.addActionListener(e->{
            if(axisEqual)
            {
                axisEqual=false;
                axisEqualMenuItem.setLabel("Axis equal ×");
            }

            else
            {
                axisEqual=true;
                axisEqualMenuItem.setLabel("Axis equal √");
            }

            PlotRealSpace.getInstance().plotImage(directionXYZ,axisEqual);
        });
    }

    //观测方位切换 二级目录
    private MenuItem XYImportedMagnet = new MenuItem("XY √");
    private MenuItem XZImportedMagnet = new MenuItem("XZ");
    private MenuItem YXImportedMagnet = new MenuItem("YX");
    private MenuItem YZImportedMagnet = new MenuItem("YZ");
    private MenuItem ZXImportedMagnet = new MenuItem("ZX");
    private MenuItem ZYImportedMagnet = new MenuItem("ZY");
    {
        XYImportedMagnet.addActionListener(e->actionListenerFunc(DirectionXYZ.XY));
        XZImportedMagnet.addActionListener(e->actionListenerFunc(DirectionXYZ.XZ));
        YXImportedMagnet.addActionListener(e->actionListenerFunc(DirectionXYZ.YX));
        YZImportedMagnet.addActionListener(e->actionListenerFunc(DirectionXYZ.YZ));
        ZXImportedMagnet.addActionListener(e->actionListenerFunc(DirectionXYZ.ZX));
        ZYImportedMagnet.addActionListener(e->actionListenerFunc(DirectionXYZ.ZY));
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


    //constructor
    private ViewMenu()
    {
        super("View");
        this.add(axisEqualMenuItem);
        this.add(direction);
    }

    private void actionListenerFunc(DirectionXYZ directionXYZ)
    {
        this.directionXYZ=directionXYZ;
        XYZImportedMagnetLableChange();

        if(ImportedMagnet.isEmpty())
        {
            ErrorDialog.getInstance().setErrorInformationToLabelAndVisible("Magnetic Field Data not found");
            return;
        }

        PlotRealSpace.getInstance().plotImage(directionXYZ,axisEqual);
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

        if(this.directionXYZ==DirectionXYZ.XY)
            XYImportedMagnet.setLabel("XY √");

        if(this.directionXYZ==DirectionXYZ.XZ)
            XZImportedMagnet.setLabel("XZ √");

        if(this.directionXYZ==DirectionXYZ.YX)
            YXImportedMagnet.setLabel("YX √");

        if(this.directionXYZ==DirectionXYZ.YZ)
            YZImportedMagnet.setLabel("YZ √");

        if(this.directionXYZ==DirectionXYZ.ZX)
            ZXImportedMagnet.setLabel("ZX √");

        if(this.directionXYZ==DirectionXYZ.ZY)
            ZYImportedMagnet.setLabel("ZY √");
    }


    public DirectionXYZ getDirectionXYZ() {
        return directionXYZ;
    }

    public boolean isAxisEqual() {
        return axisEqual;
    }
}

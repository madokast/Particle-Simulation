package zrx.gui.menuBar.viewMenuItem;

import zrx.gui.realPlot.PlotRealSpace;

import java.awt.*;

public class AxisEqualMenuItem extends MenuItem {
    private static AxisEqualMenuItem axisEqualMenuItem;

    public static AxisEqualMenuItem getInstance() {
        if(axisEqualMenuItem==null)
            axisEqualMenuItem = new AxisEqualMenuItem();

        return axisEqualMenuItem;
    }

    private boolean axisEqualBooleam=false;

    private AxisEqualMenuItem() {
        super("Axis equal ×");

        this.addActionListener(e->{
            if(axisEqualBooleam)
            {
                axisEqualBooleam=false;
                AxisEqualMenuItem.getInstance().setLabel("Axis equal ×");
            }

            else
            {
                axisEqualBooleam=true;
                AxisEqualMenuItem.getInstance().setLabel("Axis equal √");
            }

            PlotRealSpace.getInstance().plotImage(DirectionMenu.getInstance().getDirectionXYZ2D(),axisEqualBooleam);
        });
    }

    public boolean isAxisEqualBooleam() {
        return axisEqualBooleam;
    }
}

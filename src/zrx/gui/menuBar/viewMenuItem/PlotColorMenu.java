package zrx.gui.menuBar.viewMenuItem;

import zrx.gui.menuBar.viewMenuItem.ComponentColorMenuItem.DetectorColor;
import zrx.gui.menuBar.viewMenuItem.ComponentColorMenuItem.MagneticFieldColor;
import zrx.gui.menuBar.viewMenuItem.ComponentColorMenuItem.ReferredParticleStartArrowColor;
import zrx.gui.menuBar.viewMenuItem.ComponentColorMenuItem.ReferredParticleTrackColor;

import java.awt.*;

public class PlotColorMenu extends Menu {
    private static PlotColorMenu plotColorMenu;
    public static PlotColorMenu getInstance()
    {
        if(plotColorMenu==null)
            plotColorMenu = new PlotColorMenu();

        return plotColorMenu;
    }


    private PlotColorMenu()
    {
        super("Color set");
        this.add(MagneticFieldColor.getInstance());
        this.add(ReferredParticleStartArrowColor.getInstance());
        this.add(ReferredParticleTrackColor.getInstance());
        this.add(DetectorColor.getInstance());
    }
}

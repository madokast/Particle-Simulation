package zrx.gui.menuBar;

import zrx.gui.menuBar.viewMenuItem.AxisEqualMenuItem;
import zrx.gui.menuBar.viewMenuItem.DirectionMenu;
import zrx.gui.menuBar.viewMenuItem.FreshMenuItem;
import zrx.gui.menuBar.viewMenuItem.PlotColorMenu;
import zrx.gui.phasePlot.phaseSpaceOfDetectorMenuItem;

import java.awt.*;

public class ViewMenu extends Menu {
    private static ViewMenu viewMenu;
    public static ViewMenu getInstance()
    {
        if(viewMenu==null)
            viewMenu=new ViewMenu();

        return viewMenu;
    }


    //constructor
    private ViewMenu()
    {
        super("View");
        //this.add(AxisEqualMenuItem.getInstance());
        this.add(DirectionMenu.getInstance());
        this.add(PlotColorMenu.getInstance());
        this.add(new MenuItem("-"));
        this.add(FreshMenuItem.getInstance());
        this.add(new MenuItem("-"));
        this.add(phaseSpaceOfDetectorMenuItem.getInstance());
    }
}

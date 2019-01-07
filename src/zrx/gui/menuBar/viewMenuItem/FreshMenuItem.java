package zrx.gui.menuBar.viewMenuItem;

import zrx.gui.realPlot.PlotRealSpace;

import java.awt.*;

public class FreshMenuItem extends MenuItem {
    private static FreshMenuItem freshMenuItem;
    public static FreshMenuItem getInstance()
    {
        if(freshMenuItem==null)
            freshMenuItem = new FreshMenuItem();

        return freshMenuItem;
    }

    private FreshMenuItem()
    {
        super("Fresh");
        this.addActionListener(e-> PlotRealSpace.getInstance().fresh());
    }
}

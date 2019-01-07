package zrx.gui.menuBar;

import zrx.gui.testMethod.TestMenu;

import java.awt.*;

public class MyMenuBar extends MenuBar {
    //singleton
    private static MyMenuBar myMenuBar;
    public static MyMenuBar getInstance()
    {
        if(myMenuBar==null)
            myMenuBar = new MyMenuBar();

        return myMenuBar;
    }

    private MyMenuBar()
    {
        this.add(FileMenu.getInstance());
        this.add(ViewMenu.getInstance());
        this.add(ClearMenu.getInstance());
        this.add(TestMenu.getInstance());
    }


}

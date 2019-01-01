package zrx.gui.menuBar;

import java.awt.*;

public class FileMenu extends Menu {
    private static FileMenu fileMenu;
    public static FileMenu getInstance()
    {
        if(fileMenu==null)
            fileMenu = new FileMenu();

        return fileMenu;
    }

    //exitMenuItem
    private MenuItem exitMenuItem = new MenuItem("Exit");
    {
        exitMenuItem.addActionListener(e->{
            System.exit(0);
        });
    }

    //constructor
    private FileMenu()
    {
        super("File");
        this.add(exitMenuItem);
    }
}

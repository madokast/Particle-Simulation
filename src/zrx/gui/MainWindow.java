package zrx.gui;

import zrx.MainProcess;
import zrx.gui.RealPlot.PlotRealSpace;
import zrx.gui.menuBar.MyMenuBar;
import zrx.gui.tool.GUItools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends Frame {
    //singleton
    private static MainWindow mainWindow;
    public static MainWindow getInstance()
    {
        if(mainWindow==null)
            mainWindow = new MainWindow();

        return mainWindow;
    }

    private BoxLayout mainWindowBoxLayout;

    private MainWindow()
    {
        //title
        this.setTitle(MainProcess.NAME+"  "+MainProcess.VERSION);

        //MyMenuBar
        this.setMenuBar(MyMenuBar.getInstance());

        //BoxLayout
        mainWindowBoxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

        this.setLayout(mainWindowBoxLayout);

        this.add(ButtonsOnTop.getInstance());
        this.add(PlotRealSpace.getInstance());
        PlotRealSpace.getInstance().clear();


        this.pack();
        GUItools.frameCenter(this);

        //closing method
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        //visible
        this.setVisible(true);
    }

    private void fullScreen(Frame frame)
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        frame.setBounds(0,0,screenSize.width,screenSize.height);
    }


}

package zrx.gui;

import zrx.MainProcess;
import zrx.gui.realPlot.PlotRealSpace;
import zrx.gui.menuBar.MyMenuBar;
import zrx.gui.tool.GUItools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

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

        this.setIconImage(GUItools.getIcon());

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

        this.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if(e.getNewState()==Frame.MAXIMIZED_BOTH)
                {
                    //Rectangle r = MainWindow.getInstance().getBounds();

                    //PlotRealSpace.resetWidthAndHeight(r);
                }
            }
        });

        //visible
        //this.setVisible(true);//change at 2019年1月4日 08点44分
        //采用Splash，main函數中控制其可視
    }

    private void fullScreen(Frame frame)
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        frame.setBounds(0,0,screenSize.width,screenSize.height);
    }


}

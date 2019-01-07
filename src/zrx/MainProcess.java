package zrx;

import zrx.gui.MainWindow;
import zrx.gui.Preparation;
import zrx.gui.informationWindow.InformationTextArea;
import zrx.gui.informationWindow.InformationWindow;
import zrx.gui.splashScreen.Splash;
import zrx.gui.tool.GUItools;
import zrx.simulate.basicDataStructure.BiNumberDouble;
import zrx.simulate.tool.FormatPrint;
import zrx.simulate.tool.SystemInfo;

import java.awt.*;

public class MainProcess {

    public static final String NAME = "Particle Simulation";
    public static final String VERSION = "V1.0";

    public static void main(String[] args) {

        //快速展示splash
        Splash.exhibit();

        //準備工作
        try {
            Preparation.prepareAllWindows();
            Thread.currentThread().sleep(1000);
        }
        catch (Exception e){}

        //開啓mainwindow
        MainWindow.getInstance().setVisible(true);
        Splash.close();

        //InformationWindow.getInstance().setVisible(true);

        InformationTextArea.getInstance().append(FormatPrint.StringsIntoPanel(
                "欢迎使用"+NAME+" "+VERSION,
                "作者：赵润晓"
        ));
    }
}

package zrx;

import zrx.gui.MainWindow;
import zrx.gui.informationWindow.InformationTextArea;
import zrx.gui.informationWindow.InformationWindow;
import zrx.simulate.tool.FormatPrint;

public class MainProcess {

    public static final String NAME = "Particle Simulation";
    public static final String VERSION = "V-9.9";

    public static void main(String[] args) {
        MainWindow.getInstance();
        try {
            Thread.currentThread().sleep(500);
        }
        catch (InterruptedException ie)
        {
            System.out.println("すこしやすませて、ねぇ");
        }
        InformationWindow.getInstance().setVisible(true);

        InformationTextArea.getInstance().append(FormatPrint.StringsIntoPanel(
                "欢迎使用"+NAME+" "+VERSION,
                "作者：赵润晓"
        ));
    }
}

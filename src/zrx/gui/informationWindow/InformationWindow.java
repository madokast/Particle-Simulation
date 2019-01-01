package zrx.gui.informationWindow;

import zrx.gui.MainWindow;
import zrx.gui.tool.GUItools;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InformationWindow extends Dialog {
    private static InformationWindow informationWindow;
    public static InformationWindow getInstance()
    {
        if(informationWindow==null)
            informationWindow = new InformationWindow();

        return informationWindow;
    }

    private InformationWindow()
    {
        super(MainWindow.getInstance(),"Information",false);
        this.add(InformationTextArea.getInstance());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });
        this.pack();
        GUItools.dialogCenter(this);

        this.setVisible(true);
    }
}

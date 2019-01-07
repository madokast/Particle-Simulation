package zrx.gui.setDetectors;

import java.awt.*;

public class SetDetectorsButton extends Button {
    private static SetDetectorsButton setDetectorsButton;
    public static SetDetectorsButton getInstance()
    {
        if(setDetectorsButton ==null)
            setDetectorsButton = new SetDetectorsButton();

        return setDetectorsButton;
    }

    private SetDetectorsButton()
    {
        super("SetDetectors");
        this.addActionListener(e-> SetDetectorsDialog.getInstance().setVisible(true));
    }
}

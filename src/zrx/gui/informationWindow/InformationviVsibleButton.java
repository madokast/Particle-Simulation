package zrx.gui.informationWindow;

import java.awt.*;

public class InformationviVsibleButton extends Button {
    private static InformationviVsibleButton informationviVsibleButton;
    public static InformationviVsibleButton getInstance()
    {
        if(informationviVsibleButton==null)
            informationviVsibleButton = new InformationviVsibleButton();

        return informationviVsibleButton;
    }

    private InformationviVsibleButton()
    {
        super("InfoVisible");

        this.addActionListener(e->{
            if(InformationWindow.getInstance().isVisible())
                InformationWindow.getInstance().setVisible(false);
            else
                InformationWindow.getInstance().setVisible(true);
        });
    }
}

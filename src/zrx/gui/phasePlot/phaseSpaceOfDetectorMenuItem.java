package zrx.gui.phasePlot;

import java.awt.*;

public class phaseSpaceOfDetectorMenuItem extends MenuItem {
    private static phaseSpaceOfDetectorMenuItem phaseSpaceOfDetectorMenuItem;
    public static phaseSpaceOfDetectorMenuItem getInstance()
    {
        if(phaseSpaceOfDetectorMenuItem==null)
            phaseSpaceOfDetectorMenuItem=new phaseSpaceOfDetectorMenuItem();

        return phaseSpaceOfDetectorMenuItem;
    }

    private phaseSpaceOfDetectorMenuItem()
    {
        super("Detector data");
        this.addActionListener(e->{
            phasePlotDialog.getInstance().clear();
            phasePlotDialog.getInstance().setVisible(true);
        });
    }
}

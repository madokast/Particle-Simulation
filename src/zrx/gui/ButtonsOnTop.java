package zrx.gui;

import zrx.gui.importMagnet.ImportMagnetButton;
import zrx.gui.run.RunButton;
import zrx.gui.setParticle.SetParticleButton;
import zrx.gui.informationWindow.InformationviVsibleButton;
import zrx.gui.setDetectors.SetDetectorsButton;

import javax.swing.*;
import java.awt.*;

public class ButtonsOnTop extends Panel {
    private static ButtonsOnTop buttonsOnTop;
    public static ButtonsOnTop getInstance()
    {
        if(buttonsOnTop==null)
            buttonsOnTop = new ButtonsOnTop();

        return buttonsOnTop;
    }

    private Panel leftPanel = new Panel();
    private Panel rightPanel = new Panel();
    {
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    }

    private ButtonsOnTop()
    {
        leftPanel.add(ImportMagnetButton.getInstance());
        leftPanel.add(SetParticleButton.getInstance());
        leftPanel.add(SetDetectorsButton.getInstance());
        leftPanel.add(RunButton.getInstance());

        rightPanel.add(InformationviVsibleButton.getInstance());

        //prefect 2018-12-26-20:15
        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        this.add(leftPanel);
        this.add(rightPanel);
    }
}

package zrx.gui.button;

import java.awt.*;

public class RunButton extends Button {
    private static RunButton runButton;
    public static RunButton getInstance()
    {
        if(runButton==null)
            runButton = new RunButton();

        return runButton;
    }

    private RunButton()
    {
        super("Simulate");
    }
}

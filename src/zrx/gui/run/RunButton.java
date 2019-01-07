package zrx.gui.run;

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
        this.addActionListener(e->{
            RunDialog.getInstance().setVisible(true);
        });
    }
}

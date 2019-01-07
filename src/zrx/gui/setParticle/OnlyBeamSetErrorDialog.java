package zrx.gui.setParticle;

import zrx.gui.tool.GUItools;

import javax.swing.*;
import java.awt.*;

public class OnlyBeamSetErrorDialog extends Dialog {
    private static OnlyBeamSetErrorDialog onlyBeamSetErrorDialog;
    public static OnlyBeamSetErrorDialog getInstance()
    {
        if(onlyBeamSetErrorDialog==null)
            onlyBeamSetErrorDialog = new OnlyBeamSetErrorDialog();

        return onlyBeamSetErrorDialog;
    }

    private Button OKbutton = new Button("OK");
    {
        OKbutton.addActionListener(e->{
            onlyBeamSetErrorDialog.setVisible(false);
        });
    }

    private OnlyBeamSetErrorDialog()
    {
        super(ParticleBeamSetDialog.getInstance(),"Only beam set error",true);
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.add(new Label("Beam parameter set bases on referred particle set"));
        this.add(new Label("Can not set beam parameter in isolation"));
        this.add(OKbutton);

        this.setIconImage(GUItools.getErrorIcon());

        this.pack();
        GUItools.dialogCenter(this);
    }
}

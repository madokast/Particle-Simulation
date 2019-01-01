package zrx.gui.setParticle;

import zrx.gui.tool.GUItools;

import javax.swing.*;
import java.awt.*;

public class ErrorDialog extends Dialog{
    private static ErrorDialog syntacticErroInSetParticleDialog;
    public static ErrorDialog getInstance()
    {
        if(syntacticErroInSetParticleDialog==null)
            syntacticErroInSetParticleDialog = new ErrorDialog();

        return syntacticErroInSetParticleDialog;
    }

    private Label label = new Label("Error");

    private Button OKbutton = new Button("OK");
    {
        OKbutton.addActionListener(e->{
            ErrorDialog.getInstance().setVisible(false);
        });
    }


    private ErrorDialog()
    {
        super(ParticleBeamSetDialog.getInstance(),"Error",true);
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.add(label);
        this.add(OKbutton);

        this.pack();
        GUItools.dialogCenter(this);
    }

    public void setErrorInformationToLabelAndVisible(String string)
    {
        label.setText(string);
        this.pack();
        this.setVisible(true);
    }
}

package zrx.gui.setParticle;

import zrx.gui.realPlot.PlotRealSpace;
import zrx.gui.tool.GUItools;

import javax.swing.*;
import java.awt.*;

public class SuccessfulSetDialog extends Dialog {
    private static SuccessfulSetDialog successfulSetDialog;
    public static SuccessfulSetDialog getInstance()
    {
        if(successfulSetDialog==null)
            successfulSetDialog = new SuccessfulSetDialog();

        return successfulSetDialog;
    }

    private Label label = new Label("Successful Set");

    private Button OKbutton = new Button("OK");
    {
        OKbutton.addActionListener(e->{
            successfulSetDialog.getInstance().setVisible(false);
            PlotRealSpace.getInstance().fresh();
        });
    }

    private SuccessfulSetDialog()
    {
        super(ParticleBeamSetDialog.getInstance(),"Set finished",true);
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.add(label);
        this.add(OKbutton);

        this.setIconImage(GUItools.getIcon());

        this.pack();
        GUItools.dialogCenter(this);
    }

    public void setSuccessfulInformationToLabelAndVisible(String string)
    {
        label.setText(string);
        this.pack();
        this.setVisible(true);
    }
}

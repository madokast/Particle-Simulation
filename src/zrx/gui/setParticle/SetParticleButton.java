package zrx.gui.setParticle;

import zrx.gui.Plot.PlotPhaseSpace;

import java.awt.*;

public class SetParticleButton extends Button {
    private static SetParticleButton setParticleButton;
    public static SetParticleButton getInstance()
    {
        if(setParticleButton==null)
            setParticleButton = new SetParticleButton();

        return setParticleButton;
    }

    private SetParticleButton()
    {
        super("SetParticle");
        this.addActionListener(e->{
            PlotPhaseSpace.getInstance().clear();
            ParticleBeamSetDialog.getInstance().setVisible(true);
        });
    }
}

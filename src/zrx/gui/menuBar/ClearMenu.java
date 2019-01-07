package zrx.gui.menuBar;

import zrx.gui.realPlot.PlotRealSpace;
import zrx.simulate.advancedDataContainer.Beam;
import zrx.simulate.advancedDataContainer.DetectorsSet;
import zrx.simulate.advancedDataContainer.ReferredParticleTrack;
import zrx.simulate.basicDataContainer.BeamParameter;
import zrx.simulate.basicDataContainer.BeamParameterTwiss;
import zrx.simulate.basicDataContainer.ImportedMagnet;
import zrx.simulate.basicDataContainer.ReferredParticle;

import java.awt.*;

public class ClearMenu extends Menu {
    private static ClearMenu clearMenu;
    public static ClearMenu getInstance()
    {
        if(clearMenu==null)
           clearMenu=new ClearMenu();

        return clearMenu;
    }

    private MenuItem clearMagnet = new MenuItem("Magnetic field");
    private MenuItem clearParticleSet = new MenuItem("Particle&beam set");
    private MenuItem clearTrack = new MenuItem("Referred particle track");
    private MenuItem clearDetectors = new MenuItem("Detectors");
    {
        clearMagnet.addActionListener(e1->{
            ImportedMagnet.clear();
            PlotRealSpace.getInstance().fresh();
        });

        clearParticleSet.addActionListener(e2->{
            ReferredParticle.getInstance().clear();
            BeamParameterTwiss.getInstance().clear();
            Beam.clear();
            PlotRealSpace.getInstance().fresh();
        });

        clearTrack.addActionListener(e3-> {
            ReferredParticleTrack.getInstance().clear();
            PlotRealSpace.getInstance().fresh();
        });

        clearDetectors.addActionListener(e4->{
            DetectorsSet.clear();
            PlotRealSpace.getInstance().fresh();
        });
    }

    private ClearMenu()
    {
        super("clear");
        this.add(clearMagnet);
        this.add(clearParticleSet);
        this.add(clearTrack);
        this.add(clearDetectors);
    }
}

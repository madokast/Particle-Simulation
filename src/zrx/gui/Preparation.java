package zrx.gui;

import zrx.gui.importMagnet.MagnetDataNotEmptyErrorDialog;
import zrx.gui.informationWindow.InformationWindow;
import zrx.gui.run.RunDialog;
import zrx.gui.setParticle.ErrorDialog;
import zrx.gui.setParticle.OnlyBeamSetErrorDialog;
import zrx.gui.setParticle.ParticleBeamSetDialog;
import zrx.gui.setParticle.SuccessfulSetDialog;
import zrx.gui.setDetectors.SetDetectorsDialog;
import zrx.gui.testMethod.TestDialog;

public class Preparation {
    public static void prepareAllWindows()
    {
        MagnetDataNotEmptyErrorDialog.getInstance();

        InformationWindow.getInstance();

        RunDialog.getInstance();

        ErrorDialog.getInstance();

        OnlyBeamSetErrorDialog.getInstance();

        ParticleBeamSetDialog.getInstance();

        SuccessfulSetDialog.getInstance();

        SetDetectorsDialog.getInstance();

        TestDialog.getInstance();

        MainWindow.getInstance();
    }
}

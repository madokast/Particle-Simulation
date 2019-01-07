package zrx.gui.run;

import zrx.gui.MainWindow;
import zrx.gui.realPlot.DiretionXYZ1D;
import zrx.gui.informationWindow.InformationTextArea;
import zrx.gui.setParticle.ErrorDialog;
import zrx.gui.tool.GUItools;
import zrx.simulate.advancedDataContainer.Beam;
import zrx.simulate.advancedDataContainer.DetectorsSet;
import zrx.simulate.tool.ParticleRunCommander;
import zrx.simulate.tool.SystemInfo;
import zrx.simulate.tool.TaskAllocate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RunDialog extends Dialog {
    private static RunDialog runDialog;
    public static RunDialog getInstance()
    {
        if(runDialog==null)
            runDialog=new RunDialog();

        return runDialog;
    }

    //left panel for referred particle and right for beam
    private Panel leftPanel = new Panel();
    private Panel rightPanel = new Panel();

    private Panel upPanel = new Panel();

    private Panel downButtonPanel = new Panel();

    private Label stepLengthLabel = new Label("Step length(mm):");
    private Label maxDistanceLabel = new Label("Maximum distance(mm):");
    private Label particleNumberLabel = new Label("particle number:");
    private Label beamModeLabel = new Label("Beam mode:");

    private TextField stepLengthTextField = new TextField();
    private TextField maxDistanceTextField = new TextField();
    private TextField particleNumberTextField = new TextField();
    private Choice beamModeChoice = new Choice();

    private Button referredParticleRunButton = new Button("Referred particle");
    private Button beamRunButton = new Button("Beam");

    //constructor
    private RunDialog()
    {
        super(MainWindow.getInstance(),"Simulate",false);
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        upPanelSet();
        downButtonPanelSet();
        this.add(upPanel);
        this.add(Box.createVerticalStrut(10));
        this.add(downButtonPanel);


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                RunDialog.getInstance().setVisible(false);
            }
        });

        this.pack();
        GUItools.dialogCenter(this);
    }

    private void leftPanelSet()
    {
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));

        leftPanel.add(stepLengthLabel);
        leftPanel.add(stepLengthTextField);

        leftPanel.add(maxDistanceLabel);
        leftPanel.add(maxDistanceTextField);
    }

    private void rightPanelSet()
    {
        rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
        rightPanel.add(particleNumberLabel);
        rightPanel.add(particleNumberTextField);
        rightPanel.add(beamModeLabel);
        rightPanel.add(beamModeChoice);
        {
            beamModeChoice.add("Edge-X");
            beamModeChoice.add("Edge-Y");
            beamModeChoice.add("Edge-XY");
            beamModeChoice.add("Gauss-XY");
        }
    }

    private void upPanelSet()
    {
        leftPanelSet();
        rightPanelSet();

        upPanel.setLayout(new BoxLayout(upPanel,BoxLayout.X_AXIS));
        upPanel.add(Box.createHorizontalStrut(10));
        upPanel.add(leftPanel);
        upPanel.add(Box.createHorizontalStrut(20));
        upPanel.add(rightPanel);
        upPanel.add(Box.createHorizontalStrut(10));
    }

    private void downButtonPanelSet()
    {
        downButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        downButtonPanel.add(referredParticleRunButton);
        downButtonPanel.add(beamRunButton);

        referredParticleRunButton.addActionListener(e->{
            Runnable runnable = ()->{
                double stepLength=0.0;
                double maxDistancec=0.0;
                try {
                    stepLength = Double.parseDouble(stepLengthTextField.getText())/1000;
                    maxDistancec = Double.parseDouble(maxDistanceTextField.getText())/1000;
                }
                catch (Exception ee){
                    ErrorDialog.getInstance().setErrorInformationToLabelAndVisible(
                            "Check simulation parameter");

                    return;
                }

                try {
                    ParticleRunCommander.referredParticleRun(stepLength,maxDistancec);
                }
                catch (Exception ex){
                    //ex.printStackTrace();
                    InformationTextArea.getInstance().append(ex.getMessage()+"\n");
                    InformationTextArea.getInstance().append("Simulation failed\n");
                }
            };

            new Thread(runnable).start();
        });

        beamRunButton.addActionListener(e->{

            double stepLength=0.0;
            double maxDistancec=0.0;
            int particleNumber=0;
            DiretionXYZ1D diretionXYZ1D = DiretionXYZ1D.X;

            try {
                stepLength = Double.parseDouble(stepLengthTextField.getText())/1000;
                maxDistancec = Double.parseDouble(maxDistanceTextField.getText())/1000;
                particleNumber = Integer.parseInt(particleNumberTextField.getText());

                //让beam生成粒子
                if(beamModeChoice.getSelectedItem().equals("Edge-X"))
                    Beam.makeBeamEllipseEdge1D(DiretionXYZ1D.X,particleNumber);
                if(beamModeChoice.getSelectedItem().equals("Edge-Y"))
                    Beam.makeBeamEllipseEdge1D(DiretionXYZ1D.Y,particleNumber);
                if(beamModeChoice.getSelectedItem().equals("Edge-XY"))
                    Beam.makeBeamEllipseEdge2D(particleNumber);
                if(beamModeChoice.getSelectedItem().equals("Gauss-XY"))
                    Beam.makeBeamEllipseGauss2D(particleNumber);


                //给detector二次分配空间2019年1月4日 23点18分
                //2019年1月4日 23点54分
                for (int i = 0; i< DetectorsSet.getDetectorNum(); i++)
                {
                    DetectorsSet.getDetector(i).allocateRoom(Beam.size());
                }
                ///////////////////////

                //beamRunEnvironment(double stepLength, double maxDistance,int totalParticleNum)
                ParticleRunCommander.beamRunEnvironment(stepLength,maxDistancec,Beam.size());
            }
            catch (Exception ee)
            {
                ee.printStackTrace();

                ErrorDialog.getInstance().setErrorInformationToLabelAndVisible(
                        "Check simulation parameter");
                return;
            }

            try {
                TaskAllocate.taskAllocate(Beam.size());
            }
            catch (Exception e3)
            {
                e3.printStackTrace();

                InformationTextArea.getInstance().append(e3.getMessage()+"\n");
                InformationTextArea.getInstance().append("Simulation failed\n");
            }


        });
    }
}

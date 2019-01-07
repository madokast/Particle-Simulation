package zrx.gui.setDetectors;

import zrx.gui.MainWindow;
import zrx.gui.realPlot.PlotRealSpace;
import zrx.gui.setParticle.ErrorDialog;
import zrx.gui.tool.GUItools;
import zrx.simulate.advancedDataContainer.DetectorsSet;
import zrx.simulate.advancedDataContainer.ReferredParticleTrack;
import zrx.simulate.tool.SpecialNumber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SetDetectorsDialog extends Dialog {
    private static SetDetectorsDialog setDetectorsDialog;
    public static SetDetectorsDialog getInstance()
    {
        if(setDetectorsDialog ==null)
            setDetectorsDialog = new SetDetectorsDialog();

        return setDetectorsDialog;
    }
    private Label infoLabel = new Label("Set detector of beam \n" +
            "by specifying its location(mm) \n" +
            "along referred particle track");
    private TextArea textArea = new TextArea();

    private Label autoSetByStepLabel = new Label("Automatic set by interval");
    private TextField autoSetByStepTextField = new TextField();
    private Button autoSetByStepButton = new Button("Set");
    private Panel autoSetByStepPanel = new Panel();

    private Panel leftPanel = new Panel();
    private Panel rightPanel = new Panel();
    private Panel upPanel = new Panel();

    private void autoSetByStepPanelMake()
    {
        autoSetByStepPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        autoSetByStepPanel.add(autoSetByStepTextField);
        autoSetByStepPanel.add(autoSetByStepButton);

        autoSetByStepButton.addActionListener(e->{
            int step = 0;

            if(ReferredParticleTrack.getInstance().isEmpty())
            {
                ErrorDialog.getInstance().setErrorInformationToLabelAndVisible("No track data");
                return;
            }

            try {
                step = Integer.parseInt(autoSetByStepTextField.getText());
            }
            catch (Exception e2){
                ErrorDialog.getInstance().setErrorInformationToLabelAndVisible("syntactic error");
            }

            if(step <= 0){
                ErrorDialog.getInstance().setErrorInformationToLabelAndVisible("Interval cannot set as "+step);
                return;
            }

            int num = (int)(ReferredParticleTrack.getInstance().getLength()*SpecialNumber.M2MM/step);

            textArea.setText("");

            for(int i=0;i<num;i++)
            {
                textArea.append((i+1)*step+"\n");
            }

            textArea.replaceRange("",textArea.getText().length()-1,textArea.getText().length());

        });

    }

    private void UpPanelMake()
    {
        upPanel.setLayout(new BoxLayout(upPanel,BoxLayout.X_AXIS));

        leftPanelMake();
        rightPanelMake();

        upPanel.add(Box.createHorizontalStrut(10));
        upPanel.add(leftPanel);
        upPanel.add(Box.createHorizontalStrut(20));
        upPanel.add(rightPanel);
        upPanel.add(Box.createHorizontalStrut(10));
    }

    private void leftPanelMake()
    {
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));

        leftPanel.add(infoLabel);
        leftPanel.add(textArea);
        textArea.setText("500\n1000\n2000\n3000\n4000");
    }

    private void rightPanelMake()
    {
        autoSetByStepPanelMake();

        rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));

        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(autoSetByStepLabel);
        rightPanel.add(autoSetByStepPanel);
        rightPanel.add(Box.createVerticalStrut(20));

        autoSetByStepTextField.setText("50");
    }

    private Button OKButton = new Button("OK");
    {
        OKButton.addActionListener(e->{
            try {
                String[] strs = textArea.getText().split("\n");
                double[] locations = new double[strs.length];
                for(int i=0;i<strs.length;i++)
                    locations[i]=Double.parseDouble(strs[i])* SpecialNumber.MM2M;

                DetectorsSet.makeDetectorsSet(locations);

                PlotRealSpace.getInstance().fresh();
            }
            catch (Exception e1) {
                ErrorDialog.getInstance().setErrorInformationToLabelAndVisible(e1.getMessage());
            }
        });
    }


    //constructor
    private SetDetectorsDialog()
    {
        super(MainWindow.getInstance(),"Set Detectors",false);

        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        UpPanelMake();

        this.add(upPanel);
        this.add(OKButton);

        this.setIconImage(GUItools.getIcon());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SetDetectorsDialog.getInstance().setVisible(false);
            }
        });

        this.pack();
        GUItools.dialogCenter(this);
    }
}

//0.318309886183791=1/pi
//720
//1440
//2475
//3461
//4096
//4961
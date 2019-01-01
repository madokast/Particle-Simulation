package zrx.gui.setParticle;

import zrx.gui.MainWindow;
import zrx.gui.PhasePlot.ChartCaption;
import zrx.gui.PhasePlot.PlotPhaseSpace;
import zrx.gui.informationWindow.InformationTextArea;
import zrx.gui.tool.GUItools;
import zrx.simulate.basicDataContainer.BeamParameter;
import zrx.simulate.basicDataContainer.BeamParameterTwiss;
import zrx.simulate.basicDataContainer.BeamParameterTwissGauss;
import zrx.simulate.basicDataContainer.ReferredParticle;
import zrx.simulate.basicDataStructure.PositionVector;
import zrx.simulate.basicDataStructure.VectorDouble;
import zrx.simulate.tool.Ellipse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ParticleBeamSetDialog extends Dialog {
    //单一类
    private static ParticleBeamSetDialog particleBeamSetDialog;
    public static ParticleBeamSetDialog getInstance()
    {
        if(particleBeamSetDialog ==null)
            particleBeamSetDialog =new ParticleBeamSetDialog();

        return particleBeamSetDialog;
    }

    //整个的栏目
    private Panel mainPanel = new Panel();

    //左框 標簽、選項欄、文本區、確認欄、按鈕
    //主要目的是設定參考粒子的參數和束流參數
    private Panel leftPanel = new Panel();

    //確認按鈕 用於設置完畢
    //預覽按鈕 用於右邊的預覽圖顯示（只預覽束流相橢圓）
    //幫助按鈕 獲得幫助 幫助應該新開一個對話框
    private Button OKbutton = new Button("OK");
    private Button previewButton = new Button("Preview");
    private Button helpButton = new Button("Help");

    //兩個文本區
    private TextArea particleTextArea = new TextArea();
    private TextArea beamParamaterTextArea = new TextArea();

    //兩個下拉選項
    private Choice particleChoice = new Choice();
    private Choice beamParamaterChoice = new Choice();

    //兩個提示標簽
    private Label paticleLable = new Label("Set referred particle: ");
    private Label beamParamaterLable = new Label("Set distribution type");

    //兩個確認欄
    private Checkbox singleParticleOnlyCheckbox = new Checkbox("Set referred particle",true);
    private Checkbox beamParamaterCheckbox = new Checkbox("Set beam parameter",false);

    //枚舉類，用於相圖的XY方向
    private PhaseSpaceXYSwitch phaseSpaceXYSwitch=PhaseSpaceXYSwitch.PhaseSpaceX;

    private void setparticleChoice()
    {
        particleChoice.add("ParticleType...");
        particleChoice.add("Proton");
        particleChoice.add("Proton-DBA");
        particleChoice.addItemListener(e->{
            if(e.getItem().equals("Proton"))
            {
                particleTextArea.setText("");
                particleTextArea.append(protonSetPrompt);
            }
            if(e.getItem().equals("Proton-DBA"))
            {
                particleTextArea.setText("");
                particleTextArea.append(protonDBASetPrompt);
            }
        });
    }

    private void setBeamParamaterChoice()
    {
        beamParamaterChoice.add("distribution...");
        beamParamaterChoice.add("Twiss");
        beamParamaterChoice.add("TwissDBA");
        beamParamaterChoice.addItemListener(e->{
            if(e.getItem().equals("Twiss"))
            {
                beamParamaterTextArea.setText("");
                beamParamaterTextArea.append(BeamParamaterTwissPrompt);
            }
            if(e.getItem().equals("TwissDBA"))
            {
                beamParamaterTextArea.setText("");
                beamParamaterTextArea.append(BeamParamaterDBATwissPrompt);
            }
        });
    }

    //设定左边栏目
    private void setLeftPanel()
    {
        //Choice組件 用於參考粒子設定 選擇具體項，則對應的TextArea打印出提示符
        setparticleChoice();

        //Choice組件 用於束流參數設定 選擇具體項，則對應的TextArea打印出提示符
        setBeamParamaterChoice();

        //提示标签和下拉选项框
        Panel paticlePanel = new Panel();
        paticlePanel.setLayout(new BoxLayout(paticlePanel,BoxLayout.X_AXIS));
        paticlePanel.add(paticleLable);
        paticlePanel.add(particleChoice);

        //提示标签和下拉选项框
        Panel beamParamaterPanel = new Panel();
        beamParamaterPanel.setLayout(new BoxLayout(beamParamaterPanel,BoxLayout.X_AXIS));
        beamParamaterPanel.add(beamParamaterLable);
        beamParamaterPanel.add(beamParamaterChoice);

        //三个按钮
        Panel buttonPanle = new Panel();
        buttonPanle.setLayout(new FlowLayout(FlowLayout.CENTER));
        setButtonsAddActionListener();
        buttonPanle.add(OKbutton);
        buttonPanle.add(previewButton);
        buttonPanle.add(helpButton);

        //总添加
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
        leftPanel.add(Box.createVerticalStrut(50));

        leftPanel.add(paticlePanel);
        leftPanel.add(particleTextArea);
        leftPanel.add(Box.createVerticalStrut(50));

        leftPanel.add(beamParamaterPanel);
        leftPanel.add(beamParamaterTextArea);
        leftPanel.add(Box.createVerticalStrut(50));

        leftPanel.add(singleParticleOnlyCheckbox);
        leftPanel.add(beamParamaterCheckbox);
        leftPanel.add(buttonPanle);
    }

    private void setMainPanle()
    {
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
        mainPanel.add(Box.createHorizontalStrut(20));
        setLeftPanel();
        mainPanel.add(leftPanel);
        mainPanel.add(Box.createHorizontalStrut(19));
        mainPanel.add(PlotPhaseSpace.getInstance());
        mainPanel.add(Box.createHorizontalStrut(18));
    }

    //constructor
    private ParticleBeamSetDialog()
    {
        super(MainWindow.getInstance(),"Set particle parameter",false);

        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.add(Box.createVerticalStrut(10));

        setMainPanle();
        this.add(mainPanel);
        this.add(Box.createVerticalStrut(10));

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //相空間圖清空
                PlotPhaseSpace.getInstance().clear();
                //preview顯示 調回X
                phaseSpaceXYSwitch=PhaseSpaceXYSwitch.PhaseSpaceX;
                //關閉
                setVisible(false);
            }
        });

        this.pack();
        GUItools.dialogCenter(this);
    }

    private void setButtonsAddActionListener()
    {

        OKbutton.addActionListener(OKbuttonE->{
            if(singleParticleOnlyCheckbox.getState()==false&& beamParamaterCheckbox.getState()==false)
            {
                ParticleBeamSetDialog.getInstance().setVisible(false);
                InformationTextArea.getInstance().append("Set particle dialog: no settings completed\n");
            }
            if(singleParticleOnlyCheckbox.getState()==false&& beamParamaterCheckbox.getState()==true)
            {
                OnlyBeamSetErrorDialog.getInstance().setVisible(true);
            }
            if(singleParticleOnlyCheckbox.getState()==true&& beamParamaterCheckbox.getState()==false)
            {
                setSingleParticOnly();
            }
            if(singleParticleOnlyCheckbox.getState()==true&& beamParamaterCheckbox.getState()==true)
            {
                setBeamParamaterBasedOnSetSinglePartic();
            }
        });

        previewButton.addActionListener(previewButtonE->{

            if(BeamParameterTwiss.getInstance().isAlreadySet())
            {
                if(phaseSpaceXYSwitch==null)
                    phaseSpaceXYSwitch=PhaseSpaceXYSwitch.PhaseSpaceX;

                if(phaseSpaceXYSwitch==PhaseSpaceXYSwitch.PhaseSpaceX)
                {
                    phaseSpaceXYSwitch=PhaseSpaceXYSwitch.PhaseSpaceY;

                    PlotPhaseSpace.getInstance().drawEllipse(new Ellipse(BeamParameterTwiss.getInstance().gammaX,
                                    BeamParameterTwiss.getInstance().alphaX*2,
                                    BeamParameterTwiss.getInstance().betaX,
                                    BeamParameterTwiss.getInstance().emitX),
                            new ChartCaption("Phase Ellipse Preview-X",
                                    "x/mm",
                                    "x\'/mm*mrad")
                    );
                }
                else
                {
                    phaseSpaceXYSwitch=PhaseSpaceXYSwitch.PhaseSpaceX;

                    PlotPhaseSpace.getInstance().drawEllipse(new Ellipse(BeamParameterTwiss.getInstance().gammaY,
                                    BeamParameterTwiss.getInstance().alphaY*2,
                                    BeamParameterTwiss.getInstance().betaY,
                                    BeamParameterTwiss.getInstance().emitY),
                            new ChartCaption("Phase Ellipse Preview-Y",
                                    "y/mm",
                                    "y\'/mm*mrad")
                    );
                }

            }
            else if(BeamParameterTwissGauss.getInstance().isAlreadySet())
            {
                //
            }
            else
            {
                ErrorDialog.getInstance().setErrorInformationToLabelAndVisible("Beam parameter no found");
            }
        });
    }

    private void setSingleParticOnly()
    {
        ReferredParticle.getInstance().clear();

        try {
            setSinglePartic();

            //is OK test and print to info text
            if(ReferredParticle.getInstance().isAlreadySet())
            {
                ReferredParticle.getInstance().printToInformationTextArea();
                SuccessfulSetDialog.getInstance().
                        setSuccessfulInformationToLabelAndVisible("Set referred particle successfully");
            }
            else
            {
                throw new Exception("Set failed.you may need to check syntactic error");
            }
        }
        catch (Exception e)
        {
            //e.printStackTrace();
            ErrorDialog.getInstance().
                    setErrorInformationToLabelAndVisible(e.getMessage());
        }

    }

    private void setSinglePartic()
            throws Exception
    {
        //ReferredParticle.getInstance().clear();

        String[] strLine = particleTextArea.getText().split("\n");

        if(strLine[0].indexOf("proton")!=-1)
        {
            //System.out.println("strLine[0].indexOf(\"proton\")!=-1");

            try {
                setSingleProton(strLine);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new Exception("Syntactic Error in referred particale set");
            }
        }
        else
            throw new Exception("No match to particle type");

    }

    private void setSingleProton(String[] strLine)
            throws Exception
    {
        //ParticleType  StaticMass(Kg/MeV)  chargeQuantity
        ReferredParticle.getInstance().setParticleTypeStaticMassAndChargeQuantity("proton");

        //positionVector
        ReferredParticle.getInstance().positionVector = new PositionVector(
                Double.parseDouble(strLine[2].substring(strLine[2].indexOf("=")+1))/1000,
                Double.parseDouble(strLine[3].substring(strLine[3].indexOf("=")+1))/1000,
                Double.parseDouble(strLine[4].substring(strLine[4].indexOf("=")+1))/1000);//234

        //KineticEnergyMeV
        ReferredParticle.getInstance().KineticEnergyMeV =
                Double.parseDouble(strLine[1].substring(strLine[1].indexOf("=")+1));//1

        //VelocityVector
        ReferredParticle.getInstance().setVelocityVectorWithVectorDouble(
                new VectorDouble(
                        Double.parseDouble(strLine[6].substring(strLine[6].indexOf("=")+1)),
                        Double.parseDouble(strLine[7].substring(strLine[7].indexOf("=")+1)),
                        Double.parseDouble(strLine[8].substring(strLine[8].indexOf("=")+1))
                )
        );
    }

    private void setBeamParamaterBasedOnSetSinglePartic()
    {
        BeamParameter beamParameter;

        try {
            String[] strLine = beamParamaterTextArea.getText().split("\n");

            if(strLine[0].indexOf("Twiss")!=-1)
            {
                setSinglePartic();
                beamParameter = setBeamParamaterTwissAndReturn(strLine);
                beamParameter.printToInformationTextArea();
                SuccessfulSetDialog.getInstance().
                        setSuccessfulInformationToLabelAndVisible("Set beam paramater successfully");
            }
            else
                throw new Exception("No match to beam paramater");
        }
        catch (Exception e)
        {
            ErrorDialog.getInstance().
                    setErrorInformationToLabelAndVisible(e.getMessage());
        }
    }

    private BeamParameterTwiss setBeamParamaterTwissAndReturn(String[] strLine)
            throws Exception
    {
        BeamParameterTwiss.getInstance().clear();

        BeamParameterTwiss.getInstance().setBeamParameterTwiss(ReferredParticle.getInstance(),
                Double.parseDouble(strLine[1].substring(strLine[1].indexOf("=")+1))*Math.PI,
                Double.parseDouble(strLine[2].substring(strLine[2].indexOf("=")+1))*Math.PI,
                Double.parseDouble(strLine[3].substring(strLine[3].indexOf("=")+1)),
                Double.parseDouble(strLine[4].substring(strLine[4].indexOf("=")+1)),
                Double.parseDouble(strLine[5].substring(strLine[5].indexOf("=")+1)),
                Double.parseDouble(strLine[6].substring(strLine[6].indexOf("=")+1))
                );

        if(!BeamParameterTwiss.getInstance().isAlreadySet())
            throw new Exception("Syntactic Error in beam paramater set");

        return BeamParameterTwiss.getInstance();
    }

    private String protonSetPrompt = "Specify a proton in the following format:\n"+
            "Kinetic energy(MeV)=0.0\n"+
            "Absolute position x(mm)=0.0\n"+
            "Absolute position y(mm)=0.0\n"+
            "Absolute position z(mm)=0.0\n"+
            "Set Direction of movement, using a vector along it\n"+
            "Direction of movement x=0.0\n"+
            "Direction of movement y=0.0\n"+
            "Direction of movement z=0.0\n";

    private String protonDBASetPrompt = "Specify a proton in the following format:\n"+
            "Kinetic energy(MeV)=70.0\n"+
            "Absolute position x(mm)=4.540381057097854e+02\n"+
            "Absolute position y(mm)=0.0\n"+
            "Absolute position z(mm)=2.213582932388906e+03\n"+
            "Set Direction of movement, using a vector along it\n"+
            "Direction of movement x=1.0\n"+
            "Direction of movement y=0.0\n"+
            "Direction of movement z=-1.7320508075689\n";

    private String BeamParamaterTwissPrompt = "Specify beam paramater by the Twiss parameters:\n"+
            "emitX(PI*mm*mrad)=0.0\n"+
            "emitY(PI*mm*mrad)=0.0\n"+
            "betaX(m)=0.0\n"+
            "betaY(m)=0.0\n"+
            "alphaX=0.0\n"+
            "alphaY=0.0\n";

    private String BeamParamaterDBATwissPrompt = "Specify beam paramater by the Twiss parameters:\n"+
            "emitX(PI*mm*mrad)=10.0\n"+
            "emitY(PI*mm*mrad)=10.0\n"+
            "betaX(m)=1.43614\n"+
            "betaY(m)=1.43614\n"+
            "alphaX=-1.89096\n"+
            "alphaY=-1.89096\n";

    enum PhaseSpaceXYSwitch{
        PhaseSpaceX,PhaseSpaceY;
    }
}

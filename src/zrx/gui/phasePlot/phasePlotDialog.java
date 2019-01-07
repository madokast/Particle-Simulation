package zrx.gui.phasePlot;

import zrx.gui.MainWindow;
import zrx.gui.setParticle.ErrorDialog;
import zrx.gui.tool.GUItools;
import zrx.simulate.advancedDataContainer.DetectorsSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class phasePlotDialog extends Dialog {
    private static phasePlotDialog phasePlotDialog;
    public static phasePlotDialog getInstance()
    {
        if(phasePlotDialog==null)
            phasePlotDialog=new phasePlotDialog();

        return phasePlotDialog;
    }

    //detector的编号
    private int index=0;

    //上下panel
    private Panel downButtonsPanel = new Panel();

    //一些控制按钮
    private Button priorOneButton = new Button("prior");
    private Button nextOneButton = new Button("next");
    private Button clearButton = new Button("clear");
    private Button enlargeButton = new Button("enlarge");
    private Button narrowButton = new Button("narrow");

    //放大
    private double rateOfJfreeAxis = 1.0;

    public double getRateOfJfreeAxis() {
        return rateOfJfreeAxis;
    }

    //制作下栏按钮群
    private void makeDownButtonsPanel()
    {
        //流水型
        downButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        downButtonsPanel.add(priorOneButton);
        downButtonsPanel.add(nextOneButton);
        downButtonsPanel.add(clearButton);
        downButtonsPanel.add(enlargeButton);
        downButtonsPanel.add(narrowButton);

        priorOneButton.addActionListener(e1->{
            rateOfJfreeAxis = 1.0;

            try {
                if(DetectorsSet.isEmpty())
                {
                    throw new Exception("Detectors not set");
                }

                if(index==0)
                {
                    PhaseSpacePlot.getInstance().drawPhasePlot(index);
                    return;
                }
                    //throw new Exception("It is the first one!");

                index--;
                PhaseSpacePlot.getInstance().drawPhasePlot(index);
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
                ErrorDialog.getInstance().setErrorInformationToLabelAndVisible(e2.getMessage());
            }
        });

        nextOneButton.addActionListener(e3->{
            rateOfJfreeAxis = 1.0;

            try {

                if(DetectorsSet.isEmpty())
                {
                    throw new Exception("Detectors not set");
                }

                if(index== DetectorsSet.getNumber()-1)
                {
                    PhaseSpacePlot.getInstance().drawPhasePlot(index);
                    return;
                }
                    //throw new Exception("It is the last one!");

                index++;
                PhaseSpacePlot.getInstance().drawPhasePlot(index);
            }
            catch (Exception e4)
            {
                e4.printStackTrace();
                ErrorDialog.getInstance().setErrorInformationToLabelAndVisible(e4.getMessage());
            }
        });

        clearButton.addActionListener(e5->{

            index=0;
            PhaseSpacePlot.getInstance().clear();
        });


        enlargeButton.addActionListener(e6->{
            rateOfJfreeAxis*=0.9;
            try {
                PhaseSpacePlot.getInstance().drawPhasePlot(index);
            }
            catch (Exception e8)
            {
                e8.printStackTrace();
                ErrorDialog.getInstance().setErrorInformationToLabelAndVisible(e8.getMessage());
            }
        });

        narrowButton.addActionListener(e7->{
            rateOfJfreeAxis*=1.1;
            try {
                PhaseSpacePlot.getInstance().drawPhasePlot(index);
            }
            catch (Exception e9)
            {
                e9.printStackTrace();
                ErrorDialog.getInstance().setErrorInformationToLabelAndVisible(e9.getMessage());
            }
        });
    }

    public void clear()
    {
        index=0;
        PhaseSpacePlot.getInstance().clear();
    }

    private phasePlotDialog()
    {
        super(MainWindow.getInstance(),"Phase space",false);
        index=0;

        //加画图
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.add(PhaseSpacePlot.getInstance());
        PhaseSpacePlot.getInstance().clear();

        //加下栏按钮群
        makeDownButtonsPanel();
        this.add(downButtonsPanel);
        this.setIconImage(GUItools.getIcon());

        this.pack();
        GUItools.dialogCenter(this);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                zrx.gui.phasePlot.phasePlotDialog.getInstance().setVisible(false);
            }
        });
    }
}

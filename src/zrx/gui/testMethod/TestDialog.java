package zrx.gui.testMethod;

import zrx.gui.MainWindow;
import zrx.gui.tool.GUItools;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TestDialog extends Dialog {
    private static TestDialog testDialog;
    public static TestDialog getInstance()
    {
        if(testDialog==null)
            testDialog = new TestDialog();

        return testDialog;
    }

    private TextArea textArea = new TextArea(15,60);
    private Button OKbutton = new Button("Test");

    private TestDialog()
    {
        super(MainWindow.getInstance(),"Test",false);
        //this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.add(textArea);
        this.add(OKbutton,BorderLayout.SOUTH);

        this.setIconImage(GUItools.getIcon());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                TestDialog.getInstance().setVisible(false);
            }
        });

        this.pack();
        GUItools.dialogCenter(this);
    }

    public void beginTest(String str, TestLambda testLambda)
    {
        this.setVisible(true);

        textArea.setText(str);
        textArea.append("\n");

        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER)
                {
                    testLambda.test();
                }
            }
        });

        OKbutton.addActionListener(e->{
            testLambda.test();
        });
    }

    public String getLastLine()
    {
        return textArea.getText().split("\n")[textArea.getText().split("\n").length-1];
    }

    public void appendTextArea(String str)
    {
        textArea.append(str);
    }
}

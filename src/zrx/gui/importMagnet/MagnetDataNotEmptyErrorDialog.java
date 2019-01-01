package zrx.gui.importMagnet;

import zrx.gui.MainWindow;
import zrx.gui.informationWindow.InformationTextArea;
import zrx.gui.tool.GUItools;
import zrx.simulate.basicDataContainer.ImportedMagnet;

import javax.swing.*;
import java.awt.*;

public class MagnetDataNotEmptyErrorDialog extends Dialog {
    private static MagnetDataNotEmptyErrorDialog dataNotEmptyErrorDialog;
    public static MagnetDataNotEmptyErrorDialog getInstance()
    {
        if(dataNotEmptyErrorDialog ==null)
            dataNotEmptyErrorDialog = new MagnetDataNotEmptyErrorDialog();

        return dataNotEmptyErrorDialog;
    }

    private MagnetDataNotEmptyErrorDialog()
    {
        super(MainWindow.getInstance(),"Data nonempty",true);

        TextField textField = new TextField();
        textField.setText("Field Data is not empty.Do you want to delete the existing data?");
        textField.setEditable(false);

        Panel buttonsPanel = new Panel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel,BoxLayout.X_AXIS));
        Button okbutton = new Button("OK");
        Button nobutton = new Button("NO");
        buttonsPanel.add(okbutton);
        buttonsPanel.add(nobutton);

        okbutton.addActionListener(oke->{
            InformationTextArea.getInstance().append("Delete existing data\n");
            ImportedMagnet.clear();
            this.setVisible(false);
        });

        nobutton.addActionListener(noe->{
            this.setVisible(false);
        });

        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.add(textField);
        this.add(buttonsPanel);

        this.pack();
        GUItools.dialogCenter(this);
    }
}

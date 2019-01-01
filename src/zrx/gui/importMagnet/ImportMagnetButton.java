package zrx.gui.importMagnet;

import zrx.gui.MainWindow;
import zrx.gui.tool.GUItools;
import zrx.simulate.ImportMagnet;
import zrx.simulate.basicDataContainer.ImportedMagnet;

import java.awt.*;
import java.io.File;

public class ImportMagnetButton extends Button {
    private static ImportMagnetButton importMagnetButton;
    public static ImportMagnetButton getInstance()
    {
        if(importMagnetButton ==null)
            importMagnetButton = new ImportMagnetButton();

        return importMagnetButton;
    }

    private FileDialog fileDialog;

    private ImportMagnetButton()
    {
        super("ImportMagnet");

        this.addActionListener(e->{
            if(!ImportedMagnet.isEmpty())
            {
                DataNotEmptyErrorDialog.getInstance().setVisible(true);
            }
            else
            {
                //唯一一個new出來的dialog
                fileDialog = new FileDialog(MainWindow.getInstance(),"Open magnetic field data file",FileDialog.LOAD);
                GUItools.dialogCenter(fileDialog);
                fileDialog.setVisible(true);

                String filePath = fileDialog.getDirectory()+fileDialog.getFile();
                if(new File(filePath).exists())
                {
                    Runnable runnable = ()->
                            ImportMagnet.withDateFilePath(fileDialog.getDirectory()+fileDialog.getFile());

                    new Thread(runnable).start();
                }
            }

        });
    }
}
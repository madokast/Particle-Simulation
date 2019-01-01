package zrx.gui.informationWindow;

import java.awt.*;

public class InformationTextArea extends TextArea {
    private static InformationTextArea informationTextArea;
    public static InformationTextArea getInstance()
    {
        if(informationTextArea==null)
            informationTextArea = new InformationTextArea();

        return informationTextArea;
    }

    private InformationTextArea()
    {
        super(25,75);
        this.setEditable(false);
        this.setFont(new Font(null,Font.PLAIN,18));
    }

    @Override
    public void append(String str) {
        super.append(str);
    }
}

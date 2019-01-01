package zrx.simulate.tool;

public class FormatPrint {
    public static String StringsIntoPanel(String...strArr)
    {
        int Maxlength=0;
        for(String str:strArr)
        {
            if(Maxlength<str.length())
                Maxlength=str.length();
        }
        Maxlength+=8;
        
        StringBuilder panelWidth = new StringBuilder();
        for(int i=0;i<Maxlength;i++) { panelWidth.append("*"); }
        panelWidth.append("**\n");//the left and right * and newline

        StringBuilder panelString = new StringBuilder();
        panelString.append(panelWidth);
        for(int i=0;i<strArr.length;i++)
        {
            StringBuilder line = new StringBuilder();
            line.append("*    ");
            line.append(strArr[i]);
            line.append("\n");

            panelString.append(line);
        }
        panelString.append(panelWidth);

        panelString.append("\n");//换行

        return panelString.toString();
    }
}

package zrx.gui.RealPlot;

import zrx.gui.setParticle.ErrorDialog;

//繪圖方向
public enum DirectionXYZ {
    XY,XZ,YX,YZ,ZX,ZY;

    public String getFirst()
    {
        switch (this)
        {
            case XY: case XZ:
                return "X";

            case YX: case YZ:
                return "Y";

            case ZX: case ZY:
                return "Z";

        }

        ErrorDialog.getInstance().setErrorInformationToLabelAndVisible("Error in DirectionXYZ.getFirst");
        return "X";
    }

    public String getSecond()
    {
        switch (this)
        {
            case YX:case ZX:
                return "X";

            case XY:case ZY:
                return "Y";

            case YZ:case XZ:
                return "Z";

        }

        ErrorDialog.getInstance().setErrorInformationToLabelAndVisible("Error in DirectionXYZ.getSecond");
        return "X";
    }
}

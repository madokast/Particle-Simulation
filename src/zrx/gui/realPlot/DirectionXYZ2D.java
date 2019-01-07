package zrx.gui.realPlot;

import zrx.gui.setParticle.ErrorDialog;

//繪圖方向
public enum DirectionXYZ2D {
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

        ErrorDialog.getInstance().setErrorInformationToLabelAndVisible("Error in DirectionXYZ2D.getFirst");
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

        ErrorDialog.getInstance().setErrorInformationToLabelAndVisible("Error in DirectionXYZ2D.getSecond");
        return "X";
    }
}

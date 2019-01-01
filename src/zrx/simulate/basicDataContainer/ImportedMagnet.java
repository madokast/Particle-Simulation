package zrx.simulate.basicDataContainer;


import zrx.gui.RealPlot.DirectionXYZ;
import zrx.gui.RealPlot.PlotRealSpace;
import zrx.gui.informationWindow.InformationTextArea;
import zrx.simulate.basicDataStructure.*;
import zrx.simulate.tool.FormatPrint;


public class ImportedMagnet {
    public static PositionVector positionVectorMax;
    public static PositionVector positionVectorMin;
    public static TriNumberDouble gapTriNumber;
    public static TriNumberInteger pointTriNumber;
    public static MagneticVector[] dataArray;

    public static boolean isEmpty()
    {
        if(dataArray==null)
            return true;
        else
            return false;
    }

    public static void clear()
    {
        positionVectorMin=null;
        positionVectorMax=null;
        gapTriNumber=null;
        pointTriNumber =null;
        dataArray=null;

        PlotRealSpace.getInstance().clear();
    }

    public static void importedSuccessfullyInformation()
    {
        InformationTextArea.getInstance().append(
                FormatPrint.StringsIntoPanel(
                        "Import magnetic field data successfully",
                        "The total position number is "+ImportedMagnet.dataArray.length,
                        "The point number along X is "+ImportedMagnet.pointTriNumber.x,
                        "The point number along Y is "+ImportedMagnet.pointTriNumber.y,
                        "The point number along Z is "+ImportedMagnet.pointTriNumber.z,
                        "The MAX position is x= "+ImportedMagnet.positionVectorMax.x+"m    y="+
                                ImportedMagnet.positionVectorMax.y+"m    z="+ImportedMagnet.positionVectorMax.z+"m",
                        "The MIN position is x="+ImportedMagnet.positionVectorMin.x+"m    y="+
                                ImportedMagnet.positionVectorMin.y+"m    z="+ImportedMagnet.positionVectorMin.z+"m",
                        "And the step length is x="+ImportedMagnet.gapTriNumber.x+"m    y=="+
                                ImportedMagnet.gapTriNumber.y+"m    y="+ImportedMagnet.gapTriNumber.z+"m"
                )
        );
    }



    public static String stringPrintMagnetInNumber(int n)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("At the "+n+"-th point the magnetic field is\n");
        sb.append(dataArray[n].x+"  "+dataArray[n].y+"  "+dataArray[n].z+"\n");

        return sb.toString();
    }

    public static BiNumberDouble[] importedMagnetToBinumberEdge(DirectionXYZ plotRealSpaceDirectionXYZ)
    {
        BiNumberDouble[] biNumberDoubles = new BiNumberDouble[4];

        switch (plotRealSpaceDirectionXYZ)
        {
            case XY:
                biNumberDoubles[0] = new BiNumberDouble(ImportedMagnet.positionVectorMax.x,ImportedMagnet.positionVectorMax.y);
                biNumberDoubles[1] = new BiNumberDouble(ImportedMagnet.positionVectorMax.x,ImportedMagnet.positionVectorMin.y);
                biNumberDoubles[2] = new BiNumberDouble(ImportedMagnet.positionVectorMin.x,ImportedMagnet.positionVectorMin.y);
                biNumberDoubles[3] = new BiNumberDouble(ImportedMagnet.positionVectorMin.x,ImportedMagnet.positionVectorMax.y);
                break;
            case XZ:
                biNumberDoubles[0] = new BiNumberDouble(ImportedMagnet.positionVectorMax.x,ImportedMagnet.positionVectorMax.z);
                biNumberDoubles[1] = new BiNumberDouble(ImportedMagnet.positionVectorMax.x,ImportedMagnet.positionVectorMin.z);
                biNumberDoubles[2] = new BiNumberDouble(ImportedMagnet.positionVectorMin.x,ImportedMagnet.positionVectorMin.z);
                biNumberDoubles[3] = new BiNumberDouble(ImportedMagnet.positionVectorMin.x,ImportedMagnet.positionVectorMax.z);
                break;
            case YX:
                biNumberDoubles[0] = new BiNumberDouble(ImportedMagnet.positionVectorMax.y,ImportedMagnet.positionVectorMax.x);
                biNumberDoubles[1] = new BiNumberDouble(ImportedMagnet.positionVectorMax.y,ImportedMagnet.positionVectorMin.x);
                biNumberDoubles[2] = new BiNumberDouble(ImportedMagnet.positionVectorMin.y,ImportedMagnet.positionVectorMin.x);
                biNumberDoubles[3] = new BiNumberDouble(ImportedMagnet.positionVectorMin.y,ImportedMagnet.positionVectorMax.x);
                break;
            case YZ:
                biNumberDoubles[0] = new BiNumberDouble(ImportedMagnet.positionVectorMax.y,ImportedMagnet.positionVectorMax.z);
                biNumberDoubles[1] = new BiNumberDouble(ImportedMagnet.positionVectorMax.y,ImportedMagnet.positionVectorMin.z);
                biNumberDoubles[2] = new BiNumberDouble(ImportedMagnet.positionVectorMin.y,ImportedMagnet.positionVectorMin.z);
                biNumberDoubles[3] = new BiNumberDouble(ImportedMagnet.positionVectorMin.y,ImportedMagnet.positionVectorMax.z);
                break;
            case ZX:
                biNumberDoubles[0] = new BiNumberDouble(ImportedMagnet.positionVectorMax.z,ImportedMagnet.positionVectorMax.x);
                biNumberDoubles[1] = new BiNumberDouble(ImportedMagnet.positionVectorMax.z,ImportedMagnet.positionVectorMin.x);
                biNumberDoubles[2] = new BiNumberDouble(ImportedMagnet.positionVectorMin.z,ImportedMagnet.positionVectorMin.x);
                biNumberDoubles[3] = new BiNumberDouble(ImportedMagnet.positionVectorMin.z,ImportedMagnet.positionVectorMax.x);
                break;
            case ZY:
                biNumberDoubles[0] = new BiNumberDouble(ImportedMagnet.positionVectorMax.z,ImportedMagnet.positionVectorMax.y);
                biNumberDoubles[1] = new BiNumberDouble(ImportedMagnet.positionVectorMax.z,ImportedMagnet.positionVectorMin.y);
                biNumberDoubles[2] = new BiNumberDouble(ImportedMagnet.positionVectorMin.z,ImportedMagnet.positionVectorMin.y);
                biNumberDoubles[3] = new BiNumberDouble(ImportedMagnet.positionVectorMin.z,ImportedMagnet.positionVectorMax.y);
                break;
        }

        BiNumberDouble.ArrMtoMMSelf(biNumberDoubles);
        return biNumberDoubles;
    }

}

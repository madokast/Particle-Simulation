package zrx.simulate.basicDataContainer;

import zrx.simulate.basicDataStructure.*;


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
    }

    public static String stringPrintMagnetInNumber(int n)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("At the "+n+"-th point the magnetic field is\n");
        sb.append(dataArray[n].x+"  "+dataArray[n].y+"  "+dataArray[n].z+"\n");

        return sb.toString();
    }
}

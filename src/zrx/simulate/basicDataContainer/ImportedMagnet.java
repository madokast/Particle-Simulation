package zrx.simulate.basicDataContainer;


import zrx.gui.realPlot.toBePlot.BinumbersToBePlot;
import zrx.gui.realPlot.PlotRealSpace;
import zrx.gui.realPlot.PlotWay;
import zrx.gui.informationWindow.InformationTextArea;
import zrx.gui.menuBar.viewMenuItem.ComponentColorMenuItem.MagneticFieldColor;
import zrx.gui.menuBar.viewMenuItem.DirectionMenu;
import zrx.simulate.basicDataStructure.*;
import zrx.simulate.tool.FormatPrint;
import zrx.simulate.tool.SpecialNumber;
import zrx.simulate.tool.SystemInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class ImportedMagnet {
    public static PositionVector positionVectorMax;//磁场位置，XYZ的最大点
    public static PositionVector positionVectorMin;//磁场位置，XYZ最低点
    public static TriNumberDouble gapTriNumber;//磁场三方向，每一点的间距
    public static TriNumberInteger pointTriNumber;//磁场三方向点数

    private static boolean emptyBooleam=true;//是否为空

    //这家伙又吃内存速度又慢
    //删除，重写
    //public static MagneticVector[] dataArray;
    //XYZ三方向磁场分量，数组索引和MagneticVector[]一致，和C语言版本一致
    //使用简单命名，提高速度
    //重写后内存占用从1.1G降到400Mb
    public static double[] imX;//imported magnet X-direction
    public static double[] imY;//imported magnet Y-direction
    public static double[] imZ;//imported magnet Z-direction

    public static boolean isEmpty()
    {
        return emptyBooleam;
    }

    public static void clear()
    {
        positionVectorMin=null;
        positionVectorMax=null;
        gapTriNumber=null;
        pointTriNumber =null;
        //dataArray=null;
        imX=null;
        imY=null;
        imZ=null;

        emptyBooleam=true;

        PlotRealSpace.getInstance().clear();
    }

    public static void importedSuccessfullyInformation()
    {
        InformationTextArea.getInstance().append(
                FormatPrint.StringsIntoPanel(
                        "Import magnetic field data successfully in "+SystemInfo.getPeriodPerTwoCall(false),
                        "The total position number is "+ImportedMagnet.pointTriNumber.totalNumber(),
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
        sb.append(imX[n]+"  "+imY[n]+"  "+imZ[n]+"\n");

        return sb.toString();
    }

    //取向 顔色 全部來自Menu組件的getter 不要自己亂來
    public static BinumbersToBePlot importedMagnetToBinumberEdge()
    {
        BiNumberDouble[] biNumberDoubles = new BiNumberDouble[4];

        switch (DirectionMenu.getInstance().getDirectionXYZ2D())
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

        //1m=1000mm
        BiNumberDouble.ArrMtoMMSelf(biNumberDoubles);


        return new BinumbersToBePlot(biNumberDoubles,"Imported Magnetic Field",
                MagneticFieldColor.getInstance().getCurrentColor(), PlotWay.CloseLine);
    }

    public static void importWithDateFilePath(String filePath)
            throws Exception
    {
        SystemInfo.getPeriodPerTwoCall(true);

        clear();

        File dataFile = new File(filePath);

        try(FileReader fileReader= new FileReader(dataFile);
            BufferedReader br = new BufferedReader(fileReader))
        {
            InformationTextArea.getInstance().append("Import magnetic field data from " + filePath + "\n");

            String str;
            String[] strArr;
            int allPointNumber;

            //read the first line. it contains the data of point number along 3 axes
            //in my data file the first line is [ 901 13 224 2]. it is z,y,x axis point number respectively.
            //现在想来有点不对
            //这里写成ZYX三方向点数，令人生疑。为什么不是XYZ，而是ZYX，难道和内置的坐标系有关？奇了怪了
            //所以这里只计算总点数，不去用三方向点数
            //至于pointTriNumber怎么办。改成(MAX-MIN)/GAP+1，还能两次验证，我真是太聪明了
            try {
                strArr=br.readLine().split("\\s+");
                allPointNumber = Integer.parseInt(strArr[3])*Integer.parseInt(strArr[2])*Integer.parseInt(strArr[1]);
            }
            catch (Exception e)
            {
                throw new Exception("File format error: can not import it");
            }

            //calculate the total point number just using x*y*z and allocate storage for magnetic field data
            //allPointNumber = pointTriNumber.x* pointTriNumber.y* pointTriNumber.z;
            //dataArray = new MagneticVector[allPointNumber];//尸体

            //总点数还是认为是正确的
            imX = new double[allPointNumber];
            imY = new double[allPointNumber];
            imZ = new double[allPointNumber];

            //这个不保险
            //discard 7 lines to magnetic field data
            //for(int i=0;i<7;i++)
            //    br.readLine();

            //改成读到0
            //因为——
            // 301 13 181 2
            // 1 X [MM]
            // 2 Y [MM]
            // 3 Z [MM]
            // 4 BX [TESLA]
            // 5 BY [TESLA]
            // 6 BZ [TESLA]
            // 0
            //   435.000000000      ....
            //数据和表头间夹了一行" 0\t"
            while(true)
            {
                str = br.readLine();
                if(str.charAt(1)=='0')//第二个字符是空格，那就跳出循环
                    break;;
            }
            //跳出后，正好再读就是数据了

            //read magnetic field data
            int i=0;

            //for positionVectorMax, positionVectorMin and gapTriNumber
            PositionVector previousPosition = null;
            double xMAX=-SpecialNumber.MAXrealNonnegative,
                    yMAX=-SpecialNumber.MAXrealNonnegative,
                    zMAX=-SpecialNumber.MAXrealNonnegative,//智能地获得XYZ位置最大值
                    xMIN=SpecialNumber.MAXrealNonnegative,
                    yMIN=SpecialNumber.MAXrealNonnegative,
                    zMIN=SpecialNumber.MAXrealNonnegative,//智能地获得XYZ位置最小值
                    xGap=0,yGap=0,zGap=0;//智能地获得XYZ点间距

            //using process bar, dividing into 20 section
            final int completionSection = 20;
            int completionInt = 0;//0~20
            double completionDouble = completionInt/(double)completionSection;//0~1 +=0.05
            StringBuilder stringBuilder = new StringBuilder();//for writing process bar
            {//start process bar
                stringBuilder.append("Progress bar: ");
                stringBuilder.append("[");
                for(int ii=0;ii<completionSection;ii++){stringBuilder.append("+");}
                stringBuilder.append("]");

                InformationTextArea.getInstance().append(stringBuilder.toString());
            }

            while((str=br.readLine())!=null)
            {
                //split line intn ["",x,y,z,bx,by,bz] 123xyz 456bxyz
                strArr=str.split("\\s+");

                //get the MAX
                if(xMAX<Double.parseDouble(strArr[1])) {xMAX=Double.parseDouble(strArr[1]);}
                if(yMAX<Double.parseDouble(strArr[2])) {yMAX=Double.parseDouble(strArr[2]);}
                if(zMAX<Double.parseDouble(strArr[3])) {zMAX=Double.parseDouble(strArr[3]);}

                //get the MIN
                if(xMIN>Double.parseDouble(strArr[1])) {xMIN=Double.parseDouble(strArr[1]);}
                if(yMIN>Double.parseDouble(strArr[2])) {yMIN=Double.parseDouble(strArr[2]);}
                if(zMIN>Double.parseDouble(strArr[3])) {zMIN=Double.parseDouble(strArr[3]);}

                //get the gap
                if(previousPosition==null) {
                    previousPosition=new PositionVector(Double.parseDouble(strArr[1]),
                            Double.parseDouble(strArr[2]),Double.parseDouble(strArr[3]));
                }
                else {
                    if(xGap<SpecialNumber.MINrealNonnegative) {xGap=Math.abs(previousPosition.x-Double.parseDouble(strArr[1]));}
                    if(yGap<SpecialNumber.MINrealNonnegative) {yGap=Math.abs(previousPosition.y-Double.parseDouble(strArr[2]));}
                    if(zGap<SpecialNumber.MINrealNonnegative) {zGap=Math.abs(previousPosition.z-Double.parseDouble(strArr[3]));}

                    previousPosition.x=Double.parseDouble(strArr[1]);
                    previousPosition.y=Double.parseDouble(strArr[2]);
                    previousPosition.z=Double.parseDouble(strArr[3]);
                }

                //import data//尸体
                //dataArray[i] = new MagneticVector(Double.parseDouble(strArr[4]),
                //                Double.parseDouble(strArr[5]),Double.parseDouble(strArr[6]));

                //import data
                imX[i]=Double.parseDouble(strArr[4]);
                imY[i]=Double.parseDouble(strArr[5]);
                imZ[i]=Double.parseDouble(strArr[6]);


                //show process bar
                if(i==(int)(allPointNumber*completionDouble))
                {

                    int length = InformationTextArea.getInstance().getText().length();

                    stringBuilder = new StringBuilder();
                    stringBuilder.append("[");
                    for(int ii=0;ii<completionInt;ii++){stringBuilder.append("=");}
                    for(int ii=0;ii<20-completionInt;ii++){stringBuilder.append("+");}
                    stringBuilder.append("]");


                    //删除
                    InformationTextArea.getInstance().replaceRange("",length-stringBuilder.length(),length);
                    //添加
                    InformationTextArea.getInstance().append(stringBuilder.toString());

                    //InformationTextArea.getInstance().replaceRange(stringBuilder.toString(),end-stringBuilder.length(),end);

                    completionInt++;
                    completionDouble = completionInt/(double)completionSection;
                }

                i++;
            }

            {//finish Importing process bar
                int length = InformationTextArea.getInstance().getText().length();
                stringBuilder = new StringBuilder();

                stringBuilder.append("[");
                for(int ii=0;ii<completionSection;ii++){stringBuilder.append("=");}
                stringBuilder.append("]\n");//换行！
                //删除
                InformationTextArea.getInstance().replaceRange("",length-stringBuilder.length(),length);
                //添加
                InformationTextArea.getInstance().append(stringBuilder.toString());
            }

            //搜集到最大位置、最小位置、间距
            positionVectorMax = new PositionVector(xMAX/1000,yMAX/1000,zMAX/1000);
            positionVectorMin = new PositionVector(xMIN/1000,yMIN/1000,zMIN/1000);
            gapTriNumber = new TriNumberDouble(xGap/1000,yGap/1000,zGap/1000);

            //计算XYZ三方向点数
            pointTriNumber = new TriNumberInteger(
                    (int)((positionVectorMax.x-positionVectorMin.x)/gapTriNumber.x+
                            SpecialNumber.MINrealNonnegative)+1,
                    (int)((positionVectorMax.y-positionVectorMin.y)/gapTriNumber.y+
                            SpecialNumber.MINrealNonnegative)+1,
                    (int)((positionVectorMax.z-positionVectorMin.z)/gapTriNumber.z+
                            SpecialNumber.MINrealNonnegative)+1
            );
            //吐糟，我讨厌(int)0.1，我需要int(0.1)！！！C++还是不错啊

            //点数的双重验证
            if(pointTriNumber.totalNumber()!=allPointNumber)
                throw new Exception("File is corrupted"+" at [if(pointTriNumber.totalNumber()!=allPointNumber)]");

            //完成報告
            if(i== imX.length)
            {
                emptyBooleam=false;

                //打印完成信息
                importedSuccessfullyInformation();
                //实空间画图就靠它，一切自动完成，全部心血在此了
                PlotRealSpace.getInstance().fresh();
            }
            else
                throw new Exception("File is corrupted"+" at [if(!isEmpty()&&i== imX.length)]");
        }
    }
}

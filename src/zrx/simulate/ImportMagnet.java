package zrx.simulate;

import zrx.gui.RealPlot.DirectionXYZ;
import zrx.gui.RealPlot.PlotRealSpace;
import zrx.gui.informationWindow.InformationTextArea;
import zrx.simulate.basicDataContainer.ImportedMagnet;
import zrx.simulate.basicDataStructure.MagneticVector;
import zrx.simulate.basicDataStructure.PositionVector;
import zrx.simulate.basicDataStructure.TriNumberDouble;
import zrx.simulate.basicDataStructure.TriNumberInteger;
import zrx.simulate.tool.SpecialNumber;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ImportMagnet {
    public static void importWithDateFilePath(String filePath)
            throws Exception
    {

        File dataFile = new File(filePath);
        //System.out.println("filePath = " + filePath);
        //System.out.println("dataFile.exists() = " + dataFile.exists());

        try(FileReader fileReader= new FileReader(dataFile);
            BufferedReader br = new BufferedReader(fileReader))
        {
            InformationTextArea.getInstance().append("Import magnetic field data from " + filePath + "\n");

            String str;
            String[] strArr;
            int allPointNumber;

            //read the first line. it contains the data of point number along 3 axes
            //in my data file the first line is [ 901 13 224 2]. it is z,y,x axis point number respectively.
            try {
                strArr=br.readLine().split("\\s+");
                ImportedMagnet.pointTriNumber = new TriNumberInteger(Integer.parseInt(strArr[3]),
                        Integer.parseInt(strArr[2]),Integer.parseInt(strArr[1]));
            }
            catch (Exception e)
            {
                throw new Exception("File format error: can not import it");
            }

            //calculate the total point number just using x*y*z and allocate storage for magnetic field data
            allPointNumber = ImportedMagnet.pointTriNumber.x* ImportedMagnet.pointTriNumber.y* ImportedMagnet.pointTriNumber.z;
            ImportedMagnet.dataArray = new MagneticVector[allPointNumber];

            //discard 7 lines to magnetic field data
            for(int i=0;i<7;i++)
                br.readLine();

            //read magnetic field data
            int i=0;

            //for positionVectorMax, positionVectorMin and gapTriNumber
            PositionVector previousPosition = null;
            double xMAX=-SpecialNumber.MAXrealNonnegative,yMAX=-SpecialNumber.MAXrealNonnegative,zMAX=-SpecialNumber.MAXrealNonnegative,
                    xMIN=SpecialNumber.MAXrealNonnegative,yMIN=SpecialNumber.MAXrealNonnegative,zMIN=SpecialNumber.MAXrealNonnegative,
                    xGap=0,yGap=0,zGap=0;

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

                //import data
                ImportedMagnet.dataArray[i] = new MagneticVector(Double.parseDouble(strArr[4]),
                                Double.parseDouble(strArr[5]),Double.parseDouble(strArr[6]));


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

            ImportedMagnet.positionVectorMax = new PositionVector(xMAX/1000,yMAX/1000,zMAX/1000);
            ImportedMagnet.positionVectorMin = new PositionVector(xMIN/1000,yMIN/1000,zMIN/1000);
            ImportedMagnet.gapTriNumber = new TriNumberDouble(xGap/1000,yGap/1000,zGap/1000);

            //完成報告
            if(!ImportedMagnet.isEmpty()&&i== ImportedMagnet.dataArray.length)
            {
                ImportedMagnet.importedSuccessfullyInformation();
                //实空间画图就靠它，一切自动完成，全部心血在此了
                PlotRealSpace.getInstance().fresh();
            }
            else
                throw new Exception("File is corrupted");
        }
    }
}

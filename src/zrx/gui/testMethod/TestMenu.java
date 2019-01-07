package zrx.gui.testMethod;

import zrx.simulate.advancedDataContainer.Beam;
import zrx.simulate.advancedDataContainer.DetectorsSet;
import zrx.simulate.basicDataContainer.ImportedMagnet;
import zrx.simulate.tool.ParticleRunCommander;

import java.awt.*;

public class TestMenu extends Menu {
    private static TestMenu testMenu;
    public static TestMenu getInstance()
    {
        if(testMenu==null)
            testMenu = new TestMenu();

        return testMenu;
    }

    private Menu importedMagnetMenu = new Menu("Imported Magnet");
    private MenuItem lagrangeInterpolateMenuItem = new MenuItem("Lagrange Interpolate");
    private MenuItem getMagnetUsingNumber = new MenuItem("getMagnetUsingNumber");
    private MenuItem detectorXYZRealSpace = new MenuItem("detectorXYZRealSpace");
    private MenuItem beamPrint = new MenuItem("Beam starting print");
    {
        importedMagnetMenu.add(getMagnetUsingNumber);

        getMagnetUsingNumber.addActionListener(e->{
            TestDialog.getInstance().beginTest("get Magnet Using Number",()->{
                try {
                    String str = TestDialog.getInstance().getLastLine();
                    int n = Integer.parseInt(str);
                    TestDialog.getInstance().appendTextArea(ImportedMagnet.stringPrintMagnetInNumber(n));
                }
                catch (Exception except)
                {
                    except.printStackTrace();
                    TestDialog.getInstance().appendTextArea("error!\n");
                }
            });
        });


        lagrangeInterpolateMenuItem.addActionListener(e1->{
            TestDialog.getInstance().beginTest("enter 9 double number splitting by white space\n" +
                    "there is x x0 y0 x1 y1 x2 y2 x3 y3",()->{
                try {
                    String str = TestDialog.getInstance().getLastLine();
                    double[] doubles = new double[9];
                    for(int i=0;i<doubles.length;i++)
                    {
                        doubles[i]=Double.parseDouble(str.split(" ")[i]);
                        TestDialog.getInstance().appendTextArea(doubles[i]+"  ");
                    }

                    TestDialog.getInstance().appendTextArea("\n");


                    TestDialog.getInstance().appendTextArea(String.valueOf(
                            ParticleRunCommander.lagrangInterpole(
                                    doubles[0],doubles[1],doubles[2],doubles[3],doubles[4],doubles[5],doubles[6],doubles[7],doubles[8]
                            )
                    ));
                }
                catch (Exception except)
                {
                    except.printStackTrace();
                    TestDialog.getInstance().appendTextArea("error!\n");
                }
            });
        });

        detectorXYZRealSpace.addActionListener(e2->{
            TestDialog.getInstance().beginTest("entetr index of detectors:\n",()->{
                try {
                    int index = Integer.parseInt(TestDialog.getInstance().getLastLine());

                    for(String t: DetectorsSet.getDetector(index).testGetPosition())
                        TestDialog.getInstance().appendTextArea(t+"\n");
                }
                catch (Exception e3){
                    e3.printStackTrace();
                    TestDialog.getInstance().appendTextArea("error!\n");
                }

            });
        });

        beamPrint.addActionListener(e3->{
            TestDialog.getInstance().beginTest("Beam starting print:\n",()->{
                if(Beam.isEmpty())
                    TestDialog.getInstance().appendTextArea("beam not set.\n");
                else
                    for(int i=0;i<Beam.size();i++)
                    {
                        TestDialog.getInstance().appendTextArea(Beam.getByPid(i).toString()+"\n");
                    }
            });
        });
    }

    private TestMenu()
    {
        super("Test");
        this.add(importedMagnetMenu);
        this.add(lagrangeInterpolateMenuItem);
        this.add(detectorXYZRealSpace);
        this.add(beamPrint);
    }
}


//总debug，一定成功。2019年1月6日08点34分。

//磁场读入和C无异
//get Magnet Using Number
//0
//At the 0-th point the magnetic field is
//7.39287504878E-6  -3.82450553148E-5  -3.07970232578E-6
//9
//At the 9-th point the magnetic field is
//9.81390485512E-6  -3.11009970838E-5  -6.02569505545E-6
//7.392875e-06    -3.824506e-05   -3.079702e-06
//7.763155e-06    -3.781858e-05   -3.352569e-06
//8.007097e-06    -3.672305e-05   -3.578542e-06
//8.249418e-06    -3.601226e-05   -3.914702e-06
//8.491740e-06    -3.530147e-05   -4.250862e-06
//8.734061e-06    -3.459068e-05   -4.587022e-06
//8.976383e-06    -3.387989e-05   -4.923182e-06
//9.218704e-06    -3.316910e-05   -5.259341e-06
//9.497634e-06    -3.224423e-05   -5.626638e-06
//9.813905e-06    -3.110100e-05   -6.025695e-06

//参考粒子————没问题
//c语言：0	4.540381e+02	0.000000e+00	2.213583e+03
// 5.487747e+07	0.000000e+00	-9.505057e+07
// 1.797408e-27	1.602177e-19	7.000000e+01	1.097549e+08
//java
//0	4.540381e+02	0.000000e+00	2.213583e+03
// 5.487747e+10	0.000000e+00	-9.505057e+10
// 0.000000e+00	0.000000e+00	7.000000e+01


//朗格朗日插值法没有问题
//c：printf("%e", lagrange_interpolation(3, 1, 2, 2, 3, 4, 4, 5, 6));
//3.333333e+00
//java
//0.3333333


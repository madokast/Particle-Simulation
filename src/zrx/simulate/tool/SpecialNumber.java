package zrx.simulate.tool;

import zrx.simulate.basicDataStructure.BiNumberDouble;
import zrx.simulate.basicDataStructure.Matrix22;
import zrx.simulate.basicDataStructure.TriNumberDouble;

import java.util.Random;


public class SpecialNumber {
    private SpecialNumber(){}
    public static final double MINrealNonnegative = 1.0e-10;
    public static final double MAXrealNonnegative = 1.0e+50;

    public static final double ProtonchargeQuantity=1.6021766208e-19;
    public static final double ProtonstaticMassKg =1.672621898e-27;
    public static final double ProtonstaticMassMeV=938.2720813;

    public static final double LightSpeed = 299792458.0;

    public static final double MM2M = 0.001;//mm to m
    public static final double M2MM = 1000;//m to mm

    public static final TriNumberDouble verticalNormalVtc = new TriNumberDouble(0.0,1.0,0.0);

    //置信椭圆k参数 由方程 1-exp(-k*k/2)=0.954 解得，0.954为2σ置信区间置信度
    public static final double KConfidence = 2.481578;

    private static Random random = new Random();

    //以下两个旧代码，本用于自己的绘图
    //不轮子jfree啊
    public static double integerNumberBigger(double x)
    {
        double t=1.0;
        double answer=0;

        if(x==0)
        {
            answer=1.0;
        }

        while(Math.abs(x)>10.0)
        {
            x/=10.0;
            t*=10.0;
        }

        while(Math.abs(x)<1.0)
        {
            x*=10.0;
            t/=10.0;
        }

        if(x>0)
        {
            int xINT = (int)(x+SpecialNumber.MINrealNonnegative)+1;
            answer=(double)xINT*t;
        }
        if(x<0)
        {
            int xINT = (int)(x+SpecialNumber.MINrealNonnegative);
            answer=(double)xINT*t;
        }

        return answer;

    }

    public static double integerNumberSmaller(double x)
    {
        double t=1.0;
        double answer=0;

        if(x==0)
        {
            answer=-1.0;
        }

        while(Math.abs(x)>10.0)
        {
            x/=10.0;
            t*=10.0;
        }

        while(Math.abs(x)<1.0)
        {
            x*=10.0;
            t/=10.0;
        }

        if(x>0)
        {
            int xINT = (int)(x+SpecialNumber.MINrealNonnegative);
            answer=(double)xINT*t;
        }
        if(x<0)
        {
            int xINT = (int)(x-SpecialNumber.MINrealNonnegative)-1;
            answer=(double)xINT*t;
        }

        return answer;
    }

    public static double absMax(double...doubles)
    {
        double max=-SpecialNumber.MAXrealNonnegative;

        for(double t:doubles)
        {
            if(max< Math.abs(t))
                max = Math.abs(t);
        }

        return max;

    }


    ////二维正态分布 均值1 标准差1 均值2 标准差2 协方差
    public static BiNumberDouble jointNormalDistribution(double mu1, double sigama1, double mu2, double sigama2, double cov)
    {
        //Returns the next pseudorandom, Gaussian ("normally") distributed
        //{@code double} value with mean {@code 0.0} and standard
        //deviation {@code 1.0} from this random number generator's sequence.
        double ges_x1 = random.nextGaussian();
        double ges_x2 = random.nextGaussian();

        return new BiNumberDouble(
                mu1 + sigama1 * ges_x1,
                mu2 + (sigama2*sigama2*ges_x1 + Math.sqrt(sigama1*sigama2*(sigama1*sigama2 - cov))*ges_x2) / sigama1
        );
    }
}

/*
**integerNumberBigger and integerNumberSmaller
        {//test
            System.out.println(SpecialNumber.integerNumberBigger(-0.08));
            System.out.println(SpecialNumber.integerNumberBigger(-12.3));
            System.out.println(SpecialNumber.integerNumberBigger(45.85));
            System.out.println(SpecialNumber.integerNumberBigger(0.05));
            //-0.07
            //-10.0
            //50.0
            //0.06

            System.out.println(SpecialNumber.integerNumberSmaller(0.08));
            System.out.println(SpecialNumber.integerNumberSmaller(2250));
            System.out.println(SpecialNumber.integerNumberSmaller(450));
            System.out.println(SpecialNumber.integerNumberSmaller(-0.05));
            //0.08
            //2000.0
            //400.0
            //-0.06
        }
*/
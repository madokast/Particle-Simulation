package zrx.simulate.tool;

import java.lang.module.FindException;

public class SpecialNumber {
    public static final double MINrealNonnegative = 1.0e-10;
    public static final double MAXrealNonnegative = 1.0e+50;

    public static final double ProtonchargeQuantity=1.6021766208e-19;
    public static final double ProtonstaticMassKg =1.672621898e-27;
    public static final double ProtonstaticMassMeV=938.2720813;

    public static final double LightSpeed = 299792458.0;

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
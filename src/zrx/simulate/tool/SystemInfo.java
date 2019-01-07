package zrx.simulate.tool;


public class SystemInfo {
    //in my computer it is 4*2+1=9
    public static final int MAXTHREADS = Runtime.getRuntime().availableProcessors() *2 +1;

    private static long firstCallTime;//millisecond
    private static long secondCallTime;//millisecond

    static
    {
        firstCallTime=0;
        secondCallTime=0;
    }

    public static String getPeriodPerTwoCall(boolean isFirstTime)
    {
        if(isFirstTime)
        {
            firstCallTime=System.currentTimeMillis();

            return null;
        }
        else
        {
            secondCallTime=System.currentTimeMillis();

            long millisecondALL = secondCallTime-firstCallTime;

            double secondsALL = ((double) millisecondALL)/1000.0;

            int milliseconds = (int)(millisecondALL-((int)secondsALL)*1000);

            int minutes = (int)(secondsALL/60.0);

            int seconds = (int)(
                    secondsALL-(double) (minutes*60)
            );

            return ((minutes==0)?(""):(minutes+"m"))
                    +
                    seconds
                    +
                    "s"
                    +
                    milliseconds+"ms";
        }

    }
}

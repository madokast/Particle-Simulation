package zrx.simulate.tool;

import zrx.gui.informationWindow.InformationTextArea;
import zrx.simulate.advancedDataContainer.DetectorsSet;
import zrx.simulate.basicDataStructure.BiNumberInterger;

public class TaskAllocate {
    public static int threadID;

    public static void taskAllocate(int particleNumber)
    {
        int MAXTHREADS = SystemInfo.MAXTHREADS;

        //分配任务
        final BiNumberInterger[] range = TaskAllocate.taskDivide(particleNumber);
        BeamRunnable[] beamRunnables = new BeamRunnable[MAXTHREADS];

        //多线程
        Thread[] threads = new Thread[MAXTHREADS];
        for(threadID=0;threadID<MAXTHREADS;threadID++)
        {
            beamRunnables[threadID]=new BeamRunnable(threadID,range[threadID].x,range[threadID].y);
            threads[threadID]=new Thread(beamRunnables[threadID],String.valueOf(beamRunnables[threadID].getId()));
        }

        //主线程
        Thread beamMainThread = new Thread((Runnable)()->{
            //我的宝贝
            SystemInfo.getPeriodPerTwoCall(true);

            for(Thread t:threads)
            {
                t.start();
            }

            for(Thread t:threads)
            {
                try {
                    t.join();
                }catch (Exception e){/*swallow*/}
            }

            InformationTextArea.getInstance().append(FormatPrint.StringsIntoPanel(
                    "Beam simulation finished in "+SystemInfo.getPeriodPerTwoCall(false)
            ));
        });

        beamMainThread.start();
    }

    public static BiNumberInterger[] taskDivide(int particleNumber)
    {
        int MAXTHREADS = SystemInfo.MAXTHREADS;
        int mainNum = particleNumber/MAXTHREADS;
        int mod = particleNumber%MAXTHREADS;

        int[] task = new int[MAXTHREADS];
        for(int i=0;i<MAXTHREADS;i++)
        {
            if(mod>0) {
                task[i]=mainNum+1;
            }
            else {
                task[i]=mainNum;
            }

            mod--;
        }


        BiNumberInterger[] range = new BiNumberInterger[MAXTHREADS];
        for(int i=0;i<MAXTHREADS;i++)
        {
            range[i]=new BiNumberInterger(
                    (i==0)?0:range[i-1].y,
                    task[i]+((i==0)?0:range[i-1].y)
            );
        }

        return range;
    }
}

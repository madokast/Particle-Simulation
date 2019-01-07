package zrx.gui.splashScreen;

import zrx.gui.MainWindow;
import zrx.gui.previewPhasePlot.MyCanvas;
import zrx.gui.tool.GUItools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Splash extends Frame {
    private static Splash splash;
    public static Splash getInstance()
    {
        if(splash==null)
            splash = new Splash();

        return splash;
    }

    private int width = GUItools.getScreenHeight()/2;
    private int height = GUItools.getScreenHeight()/3;

    private Image splashImage;

    private MyCanvas myCanvas = new MyCanvas();

    public static void exhibitMS(int ms)
    {
        new Thread((Runnable)(()->{
            Splash.getInstance().setVisible(true);

            try {
                Thread.currentThread().sleep(ms);
            }
            catch (Exception e){}

            close();
        })).start();
    }

    public static void exhibit()
    {
        new Thread((Runnable)(()->{
            Splash.getInstance().setVisible(true);
        })).start();
    }

    public static void close()
    {
        Splash.getInstance().setVisible(false);
    }



    //constructor
    private Splash()
    {
        try {
            splashImage = ImageIO.read(new File("image/splash.jpg"));

            BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            Graphics graphics = bufferedImage.getGraphics();
            graphics.drawImage(splashImage.getScaledInstance(width,height,Image.SCALE_SMOOTH),0,0,null);
            graphics.dispose();

            myCanvas.setBounds(0,0,width,height);
            myCanvas.getImageAndPrintIt(bufferedImage);
        }catch (Exception e){e.printStackTrace();}

        this.add(myCanvas,BorderLayout.CENTER);

        this.setUndecorated(true);
        //this.setBackground(Color.blue);

        this.setAlwaysOnTop(true);
        this.setIconImage(GUItools.getIcon());

        //美妙的參數化編程
        this.pack();
        GUItools.frameCenter(this);
    }
}

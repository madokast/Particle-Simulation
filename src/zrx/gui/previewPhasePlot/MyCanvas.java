/*此中都是我的胡说八道
* */


package zrx.gui.previewPhasePlot;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MyCanvas extends Canvas {
    //用於接收外來圖片(已經在外面畫好了)
    private BufferedImage ImageToBePaint;
    //用于缓冲绘图
    private BufferedImage offScreenImage;

    //對外的接口，用於得到外界的圖片，在此中調用repaint()方法
    public void getImageAndPrintIt(BufferedImage bufferedImage)
    {
        //拿到要畫的圖片
        this.ImageToBePaint = bufferedImage;
        //畫圖順序：repaint()->update()->paint()
        super.repaint();
    }

    @Override
    //重写update方法，该方法原本用于清屏+调用paint()
    public void update(Graphics g) {
        if(offScreenImage==null)
            offScreenImage = new BufferedImage(ImageToBePaint.getWidth(),
                    ImageToBePaint.getHeight(),ImageToBePaint.getType());

        Graphics graphics = offScreenImage.getGraphics();
        this.paint(graphics);//传缓存图的画笔给paint

        //一次性画到屏幕上
        g.drawImage(offScreenImage,0,0,null);
    }

    //update调用，传来的是缓冲图的画笔
    @Override
    public void paint(Graphics g) {
        g.drawImage(ImageToBePaint,0,0,null);
    }

    @Override
    @Deprecated
    //最后。不要用repaint()，應該使用getImageAndPrintIt(BufferedImage bufferedImage)
    public void repaint() { super.repaint(); }
}

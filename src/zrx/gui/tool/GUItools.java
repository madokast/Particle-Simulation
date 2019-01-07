package zrx.gui.tool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public final class GUItools {
    private GUItools(){}
    //工具类

    public static Image getIcon()
    {
        try {
            return ImageIO.read(new File("image/icon.png"));
        }
        catch (Exception e){e.printStackTrace();}

        return null;
    }

    public static Image getErrorIcon()
    {
        try {
            return ImageIO.read(new File("image/errorIcon.png"));
        }
        catch (Exception e){e.printStackTrace();}

        return null;
    }

    public static void dialogCenter(Dialog dialog)
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        dialog.setBounds(screenSize.width/2-dialog.getWidth()/2,
                screenSize.height/2-dialog.getHeight()/2,
                dialog.getWidth(),dialog.getHeight());
    }

    public static void frameCenter(Frame frame)
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        frame.setBounds(screenSize.width/2-frame.getWidth()/2,
                screenSize.height/2-frame.getHeight()/2,
                frame.getWidth(),frame.getHeight());
    }

    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param g The Graphics instance.
     * @param text The String to draw.
     * @param rect The Rectangle to center the text in.
     */
    public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    public static void NewWindowAndSeeWhatIsIt(Panel panel)
    {
        Frame frame = new Frame("TEST NewWindowAndSeeWhatIsIt");
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.setVisible(false);
            }
        });
    }

    public static int getScreenWidth()
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        return screenSize.width;
    }

    public static int getScreenHeight()
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        return screenSize.height;
    }

    public static void sleepMS(int ms)
    {
        try {
            Thread.currentThread().sleep(ms);
        }
        catch (Exception e){}
    }
}

package zrx.gui.realPlot.toBePlot;

import zrx.gui.realPlot.DirectionXYZ2D;
import zrx.gui.realPlot.PlotWay;
import zrx.gui.menuBar.viewMenuItem.DirectionMenu;
import zrx.simulate.basicDataStructure.BiNumberDouble;
import zrx.simulate.basicDataStructure.TriNumberDouble;

import java.awt.*;

public class TrinumbersToBePlot extends SToBePlot {
    private TriNumberDouble[] triNumberDoubles;
    private String name;
    private Color color;
    private PlotWay plotWay;

    public TrinumbersToBePlot(TriNumberDouble[] triNumberDoubles, String name, Color color, PlotWay plotWay) {
        this.triNumberDoubles = triNumberDoubles;
        this.name = name;
        this.color = color;
        this.plotWay = plotWay;
    }

    public static BinumbersToBePlot trinumbersToBePlotToBi(TrinumbersToBePlot t)
    {
        BiNumberDouble[] biNumberDoubles = new BiNumberDouble[t.triNumberDoubles.length];
        for(int i=0;i<t.triNumberDoubles.length;i++)
            biNumberDoubles[i]=t.triNumberDoubles[i].projection(DirectionMenu.getInstance().getDirectionXYZ2D());

        {
            //System.out.println("--------TrinumbersToBePlot------------");
            //System.out.println("t = " + t);
        }

        return new BinumbersToBePlot(biNumberDoubles,t.name,t.color,t.plotWay);
    }

    public static BiNumberDouble[] trisToBinumbers(TrinumbersToBePlot trinumbersToBePlot, DirectionXYZ2D directionXYZ2D)
    {
        //这代码，令人窒息
        //2019年1月1日
        BiNumberDouble[] biNumberDoubles = new BiNumberDouble[trinumbersToBePlot.triNumberDoubles.length];

        switch (directionXYZ2D)
        {
            case XY:
                for(int i=0;i<trinumbersToBePlot.triNumberDoubles.length;i++)
                {
                    biNumberDoubles[i]=new BiNumberDouble(trinumbersToBePlot.triNumberDoubles[i].x,
                            trinumbersToBePlot.triNumberDoubles[i].y);
                }
                break;
            case XZ:
                for(int i=0;i<trinumbersToBePlot.triNumberDoubles.length;i++)
                {
                    biNumberDoubles[i]=new BiNumberDouble(trinumbersToBePlot.triNumberDoubles[i].x,
                            trinumbersToBePlot.triNumberDoubles[i].z);
                }
                break;
            case YX:
                for(int i=0;i<trinumbersToBePlot.triNumberDoubles.length;i++)
                {
                    biNumberDoubles[i] = new BiNumberDouble(trinumbersToBePlot.triNumberDoubles[i].y,
                            trinumbersToBePlot.triNumberDoubles[i].x);
                }
                break;
            case YZ:
                for(int i=0;i<trinumbersToBePlot.triNumberDoubles.length;i++)
                {
                    biNumberDoubles[i] = new BiNumberDouble(trinumbersToBePlot.triNumberDoubles[i].y,
                            trinumbersToBePlot.triNumberDoubles[i].z);
                }
                break;
            case ZX:
                for(int i=0;i<trinumbersToBePlot.triNumberDoubles.length;i++)
                {
                    biNumberDoubles[i] = new BiNumberDouble(trinumbersToBePlot.triNumberDoubles[i].z,
                            trinumbersToBePlot.triNumberDoubles[i].x);
                }
                break;
            case ZY:
                for(int i=0;i<trinumbersToBePlot.triNumberDoubles.length;i++)
                {
                    biNumberDoubles[i] = new BiNumberDouble(trinumbersToBePlot.triNumberDoubles[i].z,
                            trinumbersToBePlot.triNumberDoubles[i].y);
                }
                break;
        }

        return biNumberDoubles;
    }

    @Override
    public String toString() {
        return "TrinumbersToBePlot{" +
                "name='" + name + '\'' +
                ", color=" + color +
                ", plotWay=" + plotWay +
                '}';
    }

    public TriNumberDouble[] getTriNumberDoubles() {
        return triNumberDoubles;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public PlotWay getPlotWay() {
        return plotWay;
    }

}

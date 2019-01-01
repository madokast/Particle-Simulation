package zrx.gui.RealPlot;

import zrx.simulate.basicDataContainer.ImportedMagnet;
import zrx.simulate.basicDataStructure.BiNumberDouble;
import zrx.simulate.basicDataStructure.TriNumberDouble;

import java.awt.*;

import static zrx.gui.RealPlot.DirectionXYZ.YZ;

public class TrinumbersToBePlot {
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

    public static BiNumberDouble[] trisToBinumbers(TrinumbersToBePlot trinumbersToBePlot,DirectionXYZ directionXYZ)
    {
        //这代码，令人窒息
        //2019年1月1日
        BiNumberDouble[] biNumberDoubles = new BiNumberDouble[trinumbersToBePlot.triNumberDoubles.length];

        switch (directionXYZ)
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

    enum PlotWay{
        CloseLine,Line,Scatter;
    }
}

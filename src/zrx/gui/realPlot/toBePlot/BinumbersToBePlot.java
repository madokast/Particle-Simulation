package zrx.gui.realPlot.toBePlot;

import zrx.gui.realPlot.PlotWay;
import zrx.simulate.basicDataStructure.BiNumberDouble;

import java.awt.*;

public class BinumbersToBePlot extends SToBePlot {
    private BiNumberDouble[] biNumberDoubles;
    private String name;
    private Color color;
    private PlotWay plotWay;

    public BinumbersToBePlot(BiNumberDouble[] biNumberDoubles, String name, Color color, PlotWay plotWay) {
        this.biNumberDoubles = biNumberDoubles;
        this.name = name;
        this.color = color;
        this.plotWay = plotWay;
    }

    @Override
    public String toString() {
        return "BinumbersToBePlot{" +
                ", name='" + name  +
                ", color=" + color +
                ", plotWay=" + plotWay +
                '}';
    }

    public BiNumberDouble[] getBiNumberDoubles() {
        return biNumberDoubles;
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

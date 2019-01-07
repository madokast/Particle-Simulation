package zrx.simulate.basicDataContainer;

import zrx.gui.realPlot.toBePlot.BinumbersToBePlot;
import zrx.gui.realPlot.PlotWay;
import zrx.gui.realPlot.tool.PlotShape;
import zrx.gui.menuBar.viewMenuItem.ComponentColorMenuItem.ReferredParticleStartArrowColor;
import zrx.gui.menuBar.viewMenuItem.DirectionMenu;
import zrx.simulate.basicDataStructure.*;
import zrx.simulate.tool.FormatPrint;
import zrx.gui.informationWindow.InformationTextArea;
import zrx.simulate.tool.SpecialNumber;

public class ReferredParticle {
    private static ReferredParticle referredParticle;
    public static ReferredParticle getInstance()
    {
        if(referredParticle==null)
            referredParticle = new ReferredParticle();

        return referredParticle;
    }

    public String particleType=null;
    public double staticMassKg=-1;
    public double staticMassMeV=-1;
    public double chargeQuantity=-1;

    public PositionVector positionVector=null;
    public VelocityVector velocityVector=null;
    public double KineticEnergyMeV=-1;

    private boolean alreadySetBoolean;

    //constructor
    private ReferredParticle()
    {
        this.clear();
    }

    //由粒子類型設置基本參數
    public void setParticleTypeStaticMassAndChargeQuantity(String particleType)
            throws Exception
    {
        if(particleType.equals("proton"))
        {
            this.particleType = "proton";
            this.chargeQuantity = SpecialNumber.ProtonchargeQuantity;
            this.staticMassKg = SpecialNumber.ProtonstaticMassKg;
            this.staticMassMeV = SpecialNumber.ProtonstaticMassMeV;
        }
        else
            throw new Exception("no match paticle type");
    }

    //是否設置成功
    public boolean isAlreadySet()
    {
        //public String particleType=null;
        //    public double staticMassKg=-1;
        //    public double staticMassMeV=-1;
        //    public double chargeQuantity=-1;
        //
        //    public PositionVector p=null;
        //    public VelocityVector velocityVector=null;
        //    public double KineticEnergyMeV=-1;
        //
        //    private boolean alreadySetBoolean;

        if(alreadySetBoolean)
            return alreadySetBoolean;
        else
        {
            if(particleType==null||positionVector==null||velocityVector==null) {
                alreadySetBoolean = false;
                return alreadySetBoolean;
            }
            else
            {
                alreadySetBoolean = true;
                return alreadySetBoolean;
            }
        }
    }

    //清空
    public void clear()
    {
        this.particleType=null;

        positionVector=null;
        velocityVector=null;

        alreadySetBoolean=false;
    }

    //動能到速度
    public double kineticToSpeed()
    {
        return SpecialNumber.LightSpeed*Math.sqrt(
                1-Math.pow(
                        staticMassMeV/(staticMassMeV+KineticEnergyMeV),
                        2)
        );
        //src in C
        //return v_c * sqrt(
        // 1 - (proton_static_energy_MeV / (proton_static_energy_MeV + K))*
        // (proton_static_energy_MeV / (proton_static_energy_MeV + K)));
    }

    //設置速度
    public void setVelocityVectorWithVectorDouble(VectorDouble v)
    {
        v.NormalizeSelf();
        double speed = this.kineticToSpeed();

        velocityVector = new VelocityVector(speed*v.x,speed*v.y,speed*v.z);
    }

    //打印信息到info
    public void printToInformationTextArea()
    {
        InformationTextArea.getInstance().append(FormatPrint.StringsIntoPanel(
                "Referred particle set successfully",
                "Particle type："+particleType,
                "Kinetic EnergyMeV: "+KineticEnergyMeV+" MeV",
                "position-X: "+positionVector.x+" m",
                "position-Y: "+positionVector.y+" m",
                "position-Z: "+positionVector.z+" m",
                "speed: "+kineticToSpeed()+" m/s",
                "velocity-X："+velocityVector.x+" m/s",
                "velocity-Y："+velocityVector.y+" m/s",
                "velocity-Z："+velocityVector.z+" m/s"
        ));
    }

    //和磁場一樣 顔色 方向都跟Menu中的來
    public BinumbersToBePlot arrowOfStartToBePlot()
    {
        BiNumberDouble[] biNumberDoubles = PlotShape.arrowOfBinumber(
                DirectionMenu.getInstance().getDirectionXYZ2D(),
                getStartPoint(),
                getStartDirection(),
                50.0
        );

        return new BinumbersToBePlot(biNumberDoubles,"referred particle",
                ReferredParticleStartArrowColor.getInstance().getCurrentColor(), PlotWay.Line);
    }

    //主要是用于画图，一个起点和方向的箭头
    public TriNumberDouble getStartPoint()
    {
        return TriNumberDouble.mTommNew(positionVector);
    }

    ////主要是用于画图，一个起点和方向的箭头
    public TriNumberDouble getStartDirection()
    {
        return TriNumberDouble.mTommNew(velocityVector);
    }

    public double getRunningMass()
    {
        //t.nt.m = proton_m / sqrt(1.0 - t.speed*t.speed / (v_c*v_c));

        return staticMassKg/ Math.sqrt(1.0-
                kineticToSpeed()*kineticToSpeed()
                /
                (SpecialNumber.LightSpeed*SpecialNumber.LightSpeed)
        );
    }

    public RunningParticle makeRuningParticle()
    {
        //省去isAlreadySet檢測

        //public RunningParticle(PositionVector p, VelocityVector v, double rMass, double speed, double distance)

        return new RunningParticle(
                new PositionVector(positionVector),
                new VelocityVector(velocityVector),//不能传引用啊
                getRunningMass(),
                kineticToSpeed(),
                0.0);
    }
}

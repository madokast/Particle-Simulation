package zrx.simulate.basicDataContainer;

import zrx.simulate.tool.FormatPrint;
import zrx.gui.informationWindow.InformationTextArea;
import zrx.simulate.basicDataStructure.PositionVector;
import zrx.simulate.basicDataStructure.VectorDouble;
import zrx.simulate.basicDataStructure.VelocityVector;
import zrx.simulate.tool.SpecialNumber;

import java.util.concurrent.TimeUnit;

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

    public boolean isAlreadySet()
    {
        //public String particleType=null;
        //    public double staticMassKg=-1;
        //    public double staticMassMeV=-1;
        //    public double chargeQuantity=-1;
        //
        //    public PositionVector positionVector=null;
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

    public void clear()
    {
        this.particleType=null;

        positionVector=null;
        velocityVector=null;

        alreadySetBoolean=false;
    }

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

    public void setVelocityVectorWithVectorDouble(VectorDouble v)
    {
        v.NormalizeSelf();
        double speed = this.kineticToSpeed();

        velocityVector = new VelocityVector(speed*v.x,speed*v.y,speed*v.z);
    }

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
}

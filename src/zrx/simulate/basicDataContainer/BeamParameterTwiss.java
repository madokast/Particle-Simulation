package zrx.simulate.basicDataContainer;

import zrx.gui.informationWindow.InformationTextArea;
import zrx.simulate.tool.FormatPrint;

public class BeamParameterTwiss extends BeamParameter {
    private static BeamParameterTwiss beamParameterTwiss;
    public static BeamParameterTwiss getInstance()
    {
        if(beamParameterTwiss==null)
            beamParameterTwiss = new BeamParameterTwiss();

        return beamParameterTwiss;
    }

    public ReferredParticle referredParticle;
    public double emitX;
    public double emitY;
    public double betaX;
    public double betaY;
    public double alphaX;
    public double alphaY;
    public double gammaX;
    public double gammaY;
    //super class has
    //public String beamParameterType;
    //public boolean alreadySetBooleam;

    //构造器
    private BeamParameterTwiss()
    {
        beamParameterType="Twiss";
        alreadySetBooleam=false;
    }

    public void setBeamParameterTwiss(ReferredParticle referredParticle,
                                             double emitX,double emitY,
                                             double betaX,double betaY,
                                             double alphaX,double alphaY)
    {
        this.referredParticle=referredParticle;
        this.emitX=emitX;
        this.emitY=emitY;
        this.betaX=betaX;
        this.betaY=betaY;
        this.alphaX=alphaX;
        this.alphaY=alphaY;

        this.gammaX=(1.0+alphaX*alphaX)/betaX;
        this.gammaY=(1.0+alphaY*alphaY)/betaY;

        this.alreadySetBooleam=true;
    }

    @Override
    public void clear()
    {
        this.referredParticle=null;

        alreadySetBooleam=false;
    }

    @Override
    public void printToInformationTextArea()
    {
        InformationTextArea.getInstance().append(FormatPrint.StringsIntoPanel(
                "Beam parameter twiss set successfully",
                "emitX=: "+emitX+" mm*mrad",
                "emitY=: "+emitY+" mm*mrad",
                "betaX=: "+betaX+" m",
                "betaY=: "+betaY+" m",
                "alphaX=: "+alphaX+" m",
                "alphaY=: "+alphaY+" m",
                "And it set based on the following referred particle",
                "Particle type："+referredParticle.particleType,
                "Kinetic EnergyMeV: "+referredParticle.KineticEnergyMeV+" MeV",
                "position-X: "+referredParticle.positionVector.x+" m",
                "position-Y: "+referredParticle.positionVector.y+" m",
                "position-Z: "+referredParticle.positionVector.z+" m",
                "speed: "+referredParticle.kineticToSpeed()+" m/s",
                "velocity-X："+referredParticle.velocityVector.x+" m/s",
                "velocity-Y："+referredParticle.velocityVector.y+" m/s",
                "velocity-Z："+referredParticle.velocityVector.z+" m/s"
        ));
    }

    @Override
    public boolean isAlreadySet() {
        return alreadySetBooleam;
    }
}

//"Specify beam paramater by the Twiss parameters:\n"+
//            "emitX(mm*mrad)=0.0"+
//            "emitY(mm*mrad)=0.0"+
//            "betaX(m)=0.0"+
//            "betaY(m)=0.0"+
//            "alphaX=0.0"+
//            "alphaY=0.0"

//"Referred particle set successfully",
//                "Particle type："+particleType,
//                "Kinetic EnergyMeV: "+KineticEnergyMeV+" MeV",
//                "position-X: "+positionVector.x+" m",
//                "position-Y: "+positionVector.y+" m",
//                "position-Z: "+positionVector.z+" m",
//                "speed: "+kineticToSpeed()+" m/s",
//                "velocity-X："+velocityVector.x+" m/s",
//                "velocity-Y："+velocityVector.y+" m/s",
//                "velocity-Z："+velocityVector.z+" m/s"
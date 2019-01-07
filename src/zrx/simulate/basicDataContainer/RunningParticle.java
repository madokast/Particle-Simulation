/*
* 我覺得有必要的量就這麽多
* 動質量  電荷量  位置  速度  運動長度
* 其他的 步長  編號  時間  能量  都放在粒子運動指揮所裏面就行了吧
* 能量、時間都沒必要
* 粒子束可以另開一個類
* 裏面再寫粒子類型之類的
* */

package zrx.simulate.basicDataContainer;

import zrx.simulate.basicDataStructure.PositionVector;
import zrx.simulate.basicDataStructure.VelocityVector;

public class RunningParticle{
    public PositionVector p;//位置
    public VelocityVector v;//速度

    //public static String particleType;
    //public static double staticMass;

    public double rMass;//running mass
    //public static double e;//chargeQuantity

    public double speed;//速率
    //public double KineticEnergyMeV;


    //public static double stepLength;

    //public int number;
    //public double time;
    public double distance;//運動距離


    public RunningParticle(PositionVector p, VelocityVector v, double rMass, double speed, double distance) {
        this.p = p;
        this.v = v;
        this.rMass = rMass;
        this.speed = speed;
        this.distance = distance;
    }


    @Override
    public String toString() {
        return "RunningParticle{" +
                "p=" + p.x + "  " + p.y + "  "+ p.z + "  " +
                ", v=" + v.x + "  " + v.y + "  "+ v.z + "  " +
                ", distance=" + distance +
                '}';
    }

    public RunningParticle(RunningParticle rp)
    {
        this.p = rp.p;
        this.v = rp.v;
        this.rMass = rp.rMass;
        this.speed = rp.speed;
        this.distance = rp.distance;
    }
}
package zrx.simulate.tool;

//啊啊啊啊
//public class BeamRunnable extends Runnable {
public class BeamRunnable implements Runnable {

    private int id;
    private int particleBegin;
    private int particleEnd;

    public BeamRunnable(int id, int particleBegin, int particleEnd) {
        this.id = id;
        this.particleBegin = particleBegin;
        this.particleEnd = particleEnd;
    }


    public int getId() {
        return id;
    }

    @Override
    public void run() {
        ParticleRunCommander.beamTask(id,particleBegin,particleEnd);
    }
}

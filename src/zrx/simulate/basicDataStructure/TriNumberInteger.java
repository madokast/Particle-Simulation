package zrx.simulate.basicDataStructure;

public class TriNumberInteger {
    public int x;
    public int y;
    public int z;

    public TriNumberInteger(){}

    public TriNumberInteger(int x,int y,int z)
    {
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public int totalNumber()
    {
        return this.x*this.y*this.z;
    }

    public void print()
    {
        System.out.printf("%d\t%d\t%d\n",x,y,z);
    }
}

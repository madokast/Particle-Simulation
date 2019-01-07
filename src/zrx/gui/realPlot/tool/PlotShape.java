package zrx.gui.realPlot.tool;

import zrx.gui.realPlot.DirectionXYZ2D;
import zrx.simulate.basicDataStructure.BiNumberDouble;
import zrx.simulate.basicDataStructure.TriNumberDouble;

public final class PlotShape {
    private PlotShape(){}

    //箭头左右支相对主体的旋转角
    private static final double ARROW_ROTATE = Math.PI/8;
    //箭头左右支相对主体的长度比
    private static final double ARROW_RATIO = 1.0/6.0;

    //画箭头
    public static BiNumberDouble[] arrowOfBinumber(final DirectionXYZ2D directionXYZ2D, //箭头在哪个子空间投影
                                                   final TriNumberDouble startPoint3D, //箭头起点，是一个三维矢量
                                                   final TriNumberDouble direction, //箭头方向，是一个三维矢量，这里接受的是速度
                                                   final double length)//箭头长度--主要用于调整实际画图中长度
    {
        //画箭头6个点
        //1起点-2顶点-3左支-4顶点-5右支-6顶点
        BiNumberDouble[] biNumberDoubles = new BiNumberDouble[6];

        //起点
        final BiNumberDouble startPoint2D = startPoint3D.projection(directionXYZ2D);

        //顶点
        final BiNumberDouble endPoint2D = lineSegment(directionXYZ2D,startPoint3D,direction,length)[1];

        //左右支点相对于顶点的方向 direction投影，反转，左右旋转
        final BiNumberDouble rightDirection = BiNumberDouble.rotateNew(ARROW_ROTATE,//旋转
                    BiNumberDouble.reverseNew(//反转
                            direction.projection(directionXYZ2D)//原方向的投影
                    ));
        final BiNumberDouble leftDirection = BiNumberDouble.rotateNew(-ARROW_ROTATE,//旋转
                BiNumberDouble.reverseNew(//反转
                        direction.projection(directionXYZ2D)//原方向的投影
                ));
        //右支点 rightDirection归一化，乘length*ARROW_RATIO，加上endPoint2D
        final BiNumberDouble rightPoint2D = BiNumberDouble.binumberAddBinumberNew(endPoint2D,//加上起点
                BiNumberDouble.scalarMultipleNew(length*ARROW_RATIO,//乘长度
                        BiNumberDouble.normalizeNew(rightDirection))//归一化
                );
        //左支点 同上
        final BiNumberDouble leftPoint2D = BiNumberDouble.binumberAddBinumberNew(endPoint2D,
                BiNumberDouble.scalarMultipleNew(length*ARROW_RATIO,
                        BiNumberDouble.normalizeNew(leftDirection))
        );

        /*               ----以上搞定----               */
        //0起点-1顶点-2左支-3顶点-4右支-5顶点
        biNumberDoubles[0]=startPoint2D;
        biNumberDoubles[1]=endPoint2D;
        biNumberDoubles[2]=leftPoint2D;
        biNumberDoubles[3]=endPoint2D;
        biNumberDoubles[4]=rightPoint2D;
        biNumberDoubles[5]=endPoint2D;

        return biNumberDoubles;
    }

    //画线段
    public static BiNumberDouble[] lineSegment(final DirectionXYZ2D directionXYZ2D,
                                               final TriNumberDouble startPoint3D,
                                               final TriNumberDouble endPoint3D)
    {
        BiNumberDouble[] biNumberDoubles = new  BiNumberDouble[2];

        biNumberDoubles[0] = startPoint3D.projection(directionXYZ2D);
        biNumberDoubles[1] = endPoint3D.projection(directionXYZ2D);

        return biNumberDoubles;
    }

    //画线段
    public static BiNumberDouble[] lineSegment(final DirectionXYZ2D directionXYZ2D,
                                               final TriNumberDouble startPoint3D,
                                               final TriNumberDouble direction3D,
                                               final double length)
    {
        return lineSegment(
                directionXYZ2D,
                startPoint3D,
                TriNumberDouble.trinumberAddTrinumber(startPoint3D,TriNumberDouble.scalarMultipleTriNumberNew(length,
                        TriNumberDouble.normalizeTrinumberNew(direction3D)))
        );
    }
}

//class zrx.simulate.basicDataStructure.TriNumberDouble
//cannot be cast to class zrx.simulate.basicDataStructure.VectorDouble
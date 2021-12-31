package moe.skneko.upv.dim.tangram.model.pieces.kinds;

import android.graphics.Point;

import moe.skneko.upv.dim.tangram.drawing.ParallelogramPathFactory;
import moe.skneko.upv.dim.tangram.drawing.PathFactory;
import moe.skneko.upv.dim.tangram.drawing.TrianglePathFactory;

public class BasicShapes {
    public static final PathFactory RectangleTriangleShape =
            new TrianglePathFactory(
                    new Point(0, 25),
                    new Point(25, 0),
                    new Point(50, 25));

    public static final PathFactory SquareShape =
            new ParallelogramPathFactory(
                    new Point(0, 25),
                    new Point(25, 0),
                    new Point(50, 25),
                    new Point(25, 50));

    public static final PathFactory RhomboidShape =
            new ParallelogramPathFactory(
                    new Point(0, 25),
                    new Point(25, 0),
                    new Point(75, 0),
                    new Point(50, 25));
}

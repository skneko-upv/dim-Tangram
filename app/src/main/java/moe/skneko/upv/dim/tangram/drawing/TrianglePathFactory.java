package moe.skneko.upv.dim.tangram.drawing;

import android.graphics.Path;
import android.graphics.Point;

public class TrianglePathFactory implements PathFactory {
    private final Point a, b, c;

    public TrianglePathFactory(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public Path buildPath() {
        Path path = new Path();
        path.moveTo(a.x, a.y);
        path.lineTo(b.x, b.y);
        path.lineTo(c.x, c.y);
        path.close();

        return path;
    }
}

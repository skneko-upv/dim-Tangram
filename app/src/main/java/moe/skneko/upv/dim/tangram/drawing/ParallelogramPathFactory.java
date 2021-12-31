package moe.skneko.upv.dim.tangram.drawing;

import android.graphics.Path;
import android.graphics.Point;

public class ParallelogramPathFactory implements PathFactory {
    private final Point a, b, c, d;

    public ParallelogramPathFactory(Point a, Point b, Point c, Point d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public Path buildPath() {
        Path path = new Path();
        path.moveTo(a.x, a.y);
        path.lineTo(b.x, b.y);
        path.lineTo(d.x, d.y);
        path.moveTo(c.x, c.y);
        path.lineTo(b.x, b.y);
        path.lineTo(d.x, d.y);
        path.moveTo(a.x, a.y);
        path.close();

        return path;
    }
}

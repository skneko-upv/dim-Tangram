package moe.skneko.upv.dim.tangram.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import moe.skneko.upv.dim.tangram.drawing.PathDrawable;

public class SolutionOutline extends PathDrawable {
    private final Paint paint = new Paint();

    public SolutionOutline(Path path, int x, int y) {
        this(path, x, y, 1, 0);
    }

    public SolutionOutline(Path path, int x, int y, float scale, float rotationDegrees) {
        super(path, x, y, scale, rotationDegrees);

        paint.setColor(Color.argb(60, 255, 255, 255));
        paint.setShadowLayer(3, 3, 3, Color.BLACK);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas, paint);
    }
}

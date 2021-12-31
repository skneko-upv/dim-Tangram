package moe.skneko.upv.dim.tangram.drawing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import java.util.Objects;

public class PathDrawable {
    private final Path path;
    private final Matrix matrix;
    private final RectF bounds;

    private float scale;
    private float degrees;

    public PathDrawable(Path path, int x, int y) {
        this(path, x, y, 1, 0);
    }

    public PathDrawable(Path path, int x, int y, float scale, float rotationDegrees) {
        this.path = path;

        degrees = 0;

        matrix = new Matrix();
        bounds = new RectF();

        scale(scale, false);
        rotate(rotationDegrees, false);
        move(x, y);
    }

    public Path getPath() {
        return path;
    }

    public int getX() {     // TODO: float?
        return Math.round(bounds.left);
    }

    public int getY() {
        return Math.round(bounds.top);
    }

    public float getRotation() {
        return degrees;
    }

    public float getScale() {
        return scale;
    }

    public RectF getBounds() {
        return bounds;
    }

    public void rotate(float degrees) {
        rotate(degrees, true);
    }

    public void rotate(float degrees, boolean pivotAtCenter) {
        float pivotX = pivotAtCenter ? bounds.centerX() : bounds.left;
        float pivotY = pivotAtCenter ? bounds.centerY() : bounds.top;
        matrix.postRotate(degrees, pivotX, pivotY);
        path.transform(matrix);
        if (!pivotAtCenter) {
            matrix.reset();
            path.computeBounds(bounds, true);
            matrix.postTranslate(Math.abs(pivotX - getX()), Math.abs(pivotY - getY()));
            path.transform(matrix);
        }

        matrix.reset();
        path.computeBounds(bounds, true);
        this.degrees += degrees;
        this.degrees %= 360;
    }

    public void scale(float scale) {
        scale(scale, true);
    }

    public void scale(float scale, boolean pivotAtCenter) {
        float nativeScale = (float) Math.sqrt(DrawingConstants.BaseScale * scale);
        float pivotX = pivotAtCenter ? bounds.centerX() : bounds.left;
        float pivotY = pivotAtCenter ? bounds.centerY() : bounds.top;
        matrix.postScale(nativeScale, nativeScale, pivotX, pivotY);
        path.transform(matrix);

        matrix.reset();
        path.computeBounds(bounds, true);
        this.scale += scale;
    }

    public void move(float x, float y) {
        matrix.postTranslate(x, y);
        path.transform(matrix);

        matrix.reset();
        path.computeBounds(bounds, true);
    }

    public void flip() {
        matrix.postRotate(-degrees, bounds.centerX(), bounds.centerY());
        matrix.postScale(-1, 1, bounds.centerX(), bounds.centerY());
        matrix.postRotate(degrees, bounds.centerX(), bounds.centerY());
        path.transform(matrix);

        matrix.reset();
        path.computeBounds(bounds, true);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawPath(path, paint);

        if (Objects.equals(System.getProperty("debugDrawing", "false"), "true")) {
            Paint.Style prevStyle = paint.getStyle();
            int prevColor = paint.getColor();

            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.LTGRAY);
            canvas.drawRect(getBounds(), paint);
            canvas.drawCircle(getX(), getY(), 4, paint);
            float centerX = (getBounds().left + getBounds().right) / 2;
            float centerY = (getBounds().top + getBounds().bottom) / 2;
            canvas.drawCircle(centerX, centerY, 4, paint);

            paint.setStyle(prevStyle);
            paint.setColor(prevColor);
        }
    }
}

package moe.skneko.upv.dim.tangram.drawing;

import android.graphics.Point;

public class PointTranslator {
    private final int baseX;
    private final int baseY;

    public PointTranslator(int baseX, int baseY) {
        this.baseX = baseX;
        this.baseY = baseY;
    }

    public Point translate(Point point, int scale, float rotationDegrees) {
        double adjustment = Math.sqrt(DrawingConstants.BaseScale * scale) * Math.cos(Math.toRadians(rotationDegrees % 90));
        int newX = (int) Math.round(baseX + point.x * adjustment);
        int newY = (int) Math.round(baseY + point.y * adjustment);

        return new Point(newX, newY);
    }
}

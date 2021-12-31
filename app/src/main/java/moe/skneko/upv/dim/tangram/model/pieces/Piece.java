package moe.skneko.upv.dim.tangram.model.pieces;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import moe.skneko.upv.dim.tangram.drawing.PathDrawable;

public class Piece extends PathDrawable {
    private final PieceKind kind;

    private final Paint paint = new Paint();

    public Piece(PieceKind kind, int x, int y, int color) {
        this(kind, x, y, color, 0);
    }

    public Piece(PieceKind kind, int x, int y, int color, float rotationDegrees) {
        super(kind.getPathFactory().buildPath(), x, y, kind.getScale(), rotationDegrees);
        this.kind = kind;

        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setShadowLayer(8, 3, 3, Color.BLACK);
    }

    public PieceKind getKind() {
        return kind;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas, paint);
    }
}
package moe.skneko.upv.dim.tangram;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import moe.skneko.upv.dim.tangram.drawing.GrailPathFactory;
import moe.skneko.upv.dim.tangram.interaction.TouchSurfaceRegion;
import moe.skneko.upv.dim.tangram.model.GameConfiguration;
import moe.skneko.upv.dim.tangram.model.SolutionOutline;
import moe.skneko.upv.dim.tangram.model.conventions.StandardGameConfiguration;
import moe.skneko.upv.dim.tangram.model.pieces.Piece;
import moe.skneko.upv.dim.tangram.model.pieces.PieceSet;

public class GameAreaView extends View {
    private final GameConfiguration<? extends PieceSet> configuration = new StandardGameConfiguration();
    private final SolutionOutline outline = new SolutionOutline(new GrailPathFactory().buildPath(), 500, 500);

    private CollisionMap collisionMap;

    private final List<TouchSurfaceRegion> regions = new CopyOnWriteArrayList<>();

    public GameAreaView(Context context) {
        super(context);
    }

    public GameAreaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GameAreaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        outline.draw(canvas);

        for (Piece piece : configuration.getPieces()) {
            piece.draw(canvas);
        }

        for (TouchSurfaceRegion region : regions) {
            region.draw(canvas);
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (changed) {
            collisionMap = new CollisionMap(getWidth(), getHeight());
            for (Piece piece : configuration.getPieces()) {
                collisionMap.trackPiece(piece);
            }
            collisionMap.recalculate();

            regions.clear();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                removeExpiredRegions();
                TouchSurfaceRegion region = getTouchedRegion(event.getX(pointerIndex), event.getY(pointerIndex));
                if (region != null) {
                    region.onTouchEvent(event);
                } else {
                    TouchSurfaceRegion newRegion = new TouchSurfaceRegion(
                            event.getX(pointerIndex),
                            event.getY(pointerIndex),
                            pointerId,
                            getContext(),
                            collisionMap);
                    regions.add(newRegion);
                    newRegion.onTouchEvent(event);
                }

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                for (TouchSurfaceRegion region : regions) {
                    region.onTouchEvent(event);
                }

                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: {
                for (TouchSurfaceRegion region : regions) {
                    if (region.getTrackedPointers().contains(pointerId)) {
                        region.onTouchEvent(event);
                    }
                }

                break;
            }
        }

        collisionMap.recalculate();
        this.invalidate();
        return true;
    }

    private TouchSurfaceRegion getTouchedRegion(float x, float y) {
        for (int i = regions.size() - 1; i >= 0; i--) {
            TouchSurfaceRegion region = regions.get(i);
            if (region.isInside(x, y)) {
                return region;
            }
        }

        return null;
    }

    private void removeExpiredRegions() {
        long currentTime = System.currentTimeMillis();

        for (TouchSurfaceRegion region : regions) {
            if (region.getExpiryTime() < currentTime) {
                regions.remove(region);
            }
        }
    }
}

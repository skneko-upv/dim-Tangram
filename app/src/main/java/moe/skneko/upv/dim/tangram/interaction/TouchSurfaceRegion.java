package moe.skneko.upv.dim.tangram.interaction;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import moe.skneko.upv.dim.tangram.CollisionMap;
import moe.skneko.upv.dim.tangram.interaction.gestures.GestureListener;
import moe.skneko.upv.dim.tangram.interaction.gestures.RotationGestureListener;
import moe.skneko.upv.dim.tangram.interaction.gestures.detectors.RotationGestureDetector;
import moe.skneko.upv.dim.tangram.model.pieces.Piece;

public class TouchSurfaceRegion {
    public static final float DEFAULT_RADIUS = 450f /* px */;
    public static final int DEFAULT_DURATION = 1000 /* ms */;

    private float cx, cy;
    private final float radius;

    private final int anchorPointerId;
    private boolean anchorStillDown;

    private final int duration;
    private long expiryTime;

    private final Set<Integer> trackedPointers = new HashSet<>();

    private Paint paint;

    private final CollisionMap collisionMap;

    private final GestureDetector gestureDetector;
    private final GestureListener gestureListener;
    private final RotationGestureDetector rotationGestureDetector;
    private final RotationGestureListener rotationGestureListener;

    private Piece selectedPiece;
    private float prevMoveX, prevMoveY;
    private int selectedPiecePointerId;

    public TouchSurfaceRegion(float cx, float cy, int anchorPointerId, Context context, CollisionMap collisionMap) {
        this(cx, cy, DEFAULT_RADIUS, DEFAULT_DURATION, anchorPointerId, context, collisionMap);
    }

    public TouchSurfaceRegion(float cx, float cy, float radius, int duration, int anchorPointerId, Context context, CollisionMap collisionMap) {
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
        this.anchorPointerId = anchorPointerId;
        anchorStillDown = true;

        this.duration = duration;
        this.expiryTime = System.currentTimeMillis() + duration;

        if (Objects.equals(System.getProperty("debugDrawing", "false"), "true")) {
            paint = new Paint();
            Random random = new Random();
            paint.setColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            paint.setStyle(Paint.Style.STROKE);
            paint.setTextSize(100);
        }

        gestureListener = new GestureListener(collisionMap);
        gestureDetector = new GestureDetector(context, gestureListener);
        rotationGestureListener = new RotationGestureListener();
        rotationGestureDetector = new RotationGestureDetector(rotationGestureListener);

        this.collisionMap = collisionMap;
        rotationGestureListener.setCollisionMap(collisionMap);
    }

    public void onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);

        expiryTime = System.currentTimeMillis() + duration;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                if (trackedPointers.isEmpty()) {
                    event.setAction(MotionEvent.ACTION_DOWN);
                }
                trackedPointers.add(pointerId);

                if (selectedPiece == null) {
                    selectedPiece = collisionMap.getPieceAt(
                            Math.round(event.getX(pointerIndex)),
                            Math.round(event.getY(pointerIndex)));
                    selectedPiecePointerId = pointerId;
                    prevMoveX = event.getX(pointerIndex);
                    prevMoveY = event.getY(pointerIndex);
                    rotationGestureListener.setSelectedPiece(selectedPiece);
                }

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (anchorStillDown) {
                    int anchorIndex = event.findPointerIndex(anchorPointerId);
                    if (anchorIndex >= 0) {
                        cx = event.getX(anchorIndex);
                        cy = event.getY(anchorIndex);
                    } else {
                        anchorStillDown = false;
                    }
                }

                if (selectedPiece != null) {
                    int selectedPiecePointerIndex = event.findPointerIndex(selectedPiecePointerId);
                    if (selectedPiecePointerIndex >= 0) {
                        float dx = event.getX(selectedPiecePointerIndex) - prevMoveX;
                        float dy = event.getY(selectedPiecePointerIndex) - prevMoveY;
                        prevMoveX = event.getX(selectedPiecePointerIndex);
                        prevMoveY = event.getY(selectedPiecePointerIndex);
                        selectedPiece.move(dx, dy);
                    } else {
                        selectedPiece = null;
                    }
                }

                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: {
                if (pointerId == anchorPointerId) {
                    anchorStillDown = false;
                }
                trackedPointers.remove(pointerId);
                if (trackedPointers.isEmpty()) {
                    event.setAction(MotionEvent.ACTION_UP);
                }

                if (pointerId == selectedPiecePointerId) {
                    selectedPiece = null;
                }
                rotationGestureListener.setSelectedPiece(null);

                break;
            }
        }

        rotationGestureListener.enableSnapping(!gestureListener.hasLongPressed());

        gestureDetector.onTouchEvent(event);
        rotationGestureDetector.onTouchEvent(event);
    }

    public Set<Integer> getTrackedPointers() {
        return trackedPointers;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public boolean isInside(float x, float y) {
        float dx = Math.abs(cx - x);
        float dy = Math.abs(cy - y);
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        return distance <= radius;
    }

    public void draw(Canvas canvas) {
        if (Objects.equals(System.getProperty("debugDrawing", "false"), "true")) {
            canvas.drawCircle(cx, cy, radius, paint);
            if (anchorStillDown) {
                canvas.drawCircle(cx, cy, 10, paint);
            }

            StringBuilder builder = new StringBuilder();
            for (Integer i : trackedPointers) {
                builder.append(i);
                builder.append(' ');
            }
            canvas.drawText(builder.toString(), cx , cy + 150, paint);
        }
    }
}

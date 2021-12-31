package moe.skneko.upv.dim.tangram.interaction.gestures.detectors;

import android.view.MotionEvent;

public class RotationGestureDetector {
    private static final int INVALID_POINTER_ID = -1;
    private float fX, fY, sX, sY;
    private float focusX, focusY;
    private int pointerId1, pointerId2;
    private float angle;

    private boolean ongoing;

    private final OnRotationGestureListener listener;

    public float getAngle() {
        return angle;
    }

    public float getFocusX() {
        return focusX;
    }

    public float getFocusY() {
        return focusY;
    }

    public RotationGestureDetector(OnRotationGestureListener listener) {
        this.listener = listener;
        pointerId1 = INVALID_POINTER_ID;
        pointerId2 = INVALID_POINTER_ID;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                if (pointerId1 == INVALID_POINTER_ID) {
                    pointerId1 = event.getPointerId(event.getActionIndex());
                } else {
                    pointerId2 = event.getPointerId(event.getActionIndex());
                    sX = event.getX(event.findPointerIndex(pointerId1));
                    sY = event.getY(event.findPointerIndex(pointerId1));
                    fX = event.getX(event.findPointerIndex(pointerId2));
                    fY = event.getY(event.findPointerIndex(pointerId2));

                    focusX = (sX + fX) / 2;
                    focusY = (sY + fY) / 2;

                    listener.onBeginRotation(this);
                    ongoing = true;
                }

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int pointerIndex1 = event.findPointerIndex(pointerId1);
                int pointerIndex2 = event.findPointerIndex(pointerId2);
                if (ongoing && pointerIndex1 != INVALID_POINTER_ID && pointerIndex2 != INVALID_POINTER_ID) {
                    float nfX, nfY, nsX, nsY;
                    nsX = event.getX(pointerIndex1);
                    nsY = event.getY(pointerIndex1);
                    nfX = event.getX(pointerIndex2);
                    nfY = event.getY(pointerIndex2);

                    angle = angleBetweenLines(fX, fY, sX, sY, nfX, nfY, nsX, nsY);

                    if (listener != null) {
                        listener.onRotation(this);
                    }
                } else {
                    finalizeAction();
                }

                break;
            }
            case MotionEvent.ACTION_UP: {
                pointerId1 = INVALID_POINTER_ID;
                finalizeAction();

                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                pointerId2 = INVALID_POINTER_ID;
                finalizeAction();

                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                pointerId1 = INVALID_POINTER_ID;
                pointerId2 = INVALID_POINTER_ID;
                if (listener != null) {
                    listener.onRotation(this);
                }
                finalizeAction();

                break;
            }
        }
        return true;
    }

    private void finalizeAction() {
        if (ongoing && listener != null) {
            listener.onEndRotation(this);
        }

        ongoing = false;
    }

    private float angleBetweenLines(float fX, float fY, float sX, float sY, float nfX, float nfY, float nsX, float nsY) {
        float angle1 = (float) Math.atan2( (fY - sY), (fX - sX) );
        float angle2 = (float) Math.atan2( (nfY - nsY), (nfX - nsX) );

        float angle = ((float)Math.toDegrees(angle1 - angle2)) % 360;
        if (angle < -180.f) angle += 360.0f;
        if (angle > 180.f) angle -= 360.0f;
        return angle;
    }

    public interface OnRotationGestureListener {
        default void onBeginRotation(RotationGestureDetector rotationDetector) {}
        default void onEndRotation(RotationGestureDetector rotationDetector) {}
        void onRotation(RotationGestureDetector rotationDetector);
    }
}
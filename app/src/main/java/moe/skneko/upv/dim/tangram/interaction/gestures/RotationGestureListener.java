package moe.skneko.upv.dim.tangram.interaction.gestures;

import moe.skneko.upv.dim.tangram.CollisionMap;
import moe.skneko.upv.dim.tangram.interaction.gestures.detectors.RotationGestureDetector;
import moe.skneko.upv.dim.tangram.model.pieces.Piece;

public class RotationGestureListener implements RotationGestureDetector.OnRotationGestureListener {
    public static final float ROTATION_FACTOR = 2f;
    public static final float ROTATION_SNAPPING_INTERVAL = 45f;

    private CollisionMap collisionMap = null;

    private boolean useSnapping = true;

    private Piece selectedPiece = null;
    private float referenceRotation;
    private float currentRotation;
    private float lastDetectorAngle;

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public void setCollisionMap(CollisionMap collisionMap) {
        this.collisionMap = collisionMap;
    }

    public boolean isUsingSnapping() {
        return useSnapping;
    }

    public void enableSnapping(boolean useSnapping) {
        this.useSnapping = useSnapping;
    }

    @Override
    public void onRotation(RotationGestureDetector rotationDetector) {
        if (selectedPiece != null) {
            float detectorAngleDelta = lastDetectorAngle - rotationDetector.getAngle();
            lastDetectorAngle = rotationDetector.getAngle();

            currentRotation += detectorAngleDelta * ROTATION_FACTOR;

            if (useSnapping) {
                float rotationDelta = currentRotation - referenceRotation;
                if (rotationDelta > ROTATION_SNAPPING_INTERVAL) {
                    selectedPiece.rotate(ROTATION_SNAPPING_INTERVAL);
                    referenceRotation += ROTATION_SNAPPING_INTERVAL;
                } else if (rotationDelta < -ROTATION_SNAPPING_INTERVAL) {
                    selectedPiece.rotate(-ROTATION_SNAPPING_INTERVAL);
                    referenceRotation -= ROTATION_SNAPPING_INTERVAL;
                }
            } else {
                selectedPiece.rotate(detectorAngleDelta);
            }
        }
    }

    @Override
    public void onBeginRotation(RotationGestureDetector rotationDetector) {
        if (collisionMap != null && selectedPiece == null) {
            int focusX = Math.round(rotationDetector.getFocusX());
            int focusY = Math.round(rotationDetector.getFocusY());
            selectedPiece = collisionMap.getPieceAt(focusX, focusY);
        }

        if (selectedPiece != null) {
            lastDetectorAngle = 0;
            referenceRotation = selectedPiece.getRotation();
            currentRotation = selectedPiece.getRotation();
        }
    }
}

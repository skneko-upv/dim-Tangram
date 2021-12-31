package moe.skneko.upv.dim.tangram.interaction.gestures;

import android.view.GestureDetector;
import android.view.MotionEvent;

import moe.skneko.upv.dim.tangram.CollisionMap;
import moe.skneko.upv.dim.tangram.model.pieces.Piece;

public class GestureListener extends GestureDetector.SimpleOnGestureListener {
    private final CollisionMap collisionMap;

    private boolean hasLongPressed;

    public GestureListener(CollisionMap collisionMap) {
        this.collisionMap = collisionMap;
    }

    public boolean hasLongPressed() {
        return hasLongPressed;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        hasLongPressed = false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Piece touchedPiece = collisionMap.getPieceAt(
                Math.round(e.getX()),
                Math.round(e.getY()));
        if (touchedPiece != null && touchedPiece.getKind().allowsFlip()) {
            touchedPiece.flip();
        }

        return super.onDoubleTap(e);
    }

    @Override
    public void onLongPress(MotionEvent e) {
        hasLongPressed = true;
    }
}

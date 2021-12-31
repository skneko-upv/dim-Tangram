package moe.skneko.upv.dim.tangram;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import java.util.HashMap;
import java.util.Map;

import moe.skneko.upv.dim.tangram.model.pieces.Piece;

public class CollisionMap {
    private Bitmap lookupBitmap;
    private final Paint paint;

    private final int width;
    private final int height;

    private final Map<Integer, Piece> pieces = new HashMap<>();
    private int lastIndex = 0;

    public CollisionMap(int width, int height) {
        this.width = width;
        this.height = height;

        paint = new Paint();
        paint.setAntiAlias(false);
        paint.setStyle((Paint.Style.FILL));

        // avoid alpha blending for overlapping pieces
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }

    public void trackPiece(Piece piece) {
        lastIndex += 1;     // first index needs to be 1
        pieces.put(lastIndex, piece);
    }

    public Piece getPieceAt(int x, int y) {
        int pathIdx = 255 - (lookupBitmap.getPixel(x, y) >>> 24);
        if (pieces.containsKey(pathIdx)) {
            return pieces.get(pathIdx);
        } else {
            return null;
        }
    }

    public void recalculate() {
        lookupBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
        lookupBitmap.eraseColor(0x00000000);

        Canvas collisionCanvas = new Canvas(lookupBitmap);

        for (Map.Entry<Integer, Piece> entry : pieces.entrySet()) {
            int pieceIdx = entry.getKey();
            Path path = entry.getValue().getPath();

            paint.setColor((255 - pieceIdx) << 24);
            collisionCanvas.drawPath(path, paint);
        }
    }
}

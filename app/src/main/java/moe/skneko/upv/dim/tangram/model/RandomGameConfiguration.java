package moe.skneko.upv.dim.tangram.model;

import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

import moe.skneko.upv.dim.tangram.model.GameConfiguration;
import moe.skneko.upv.dim.tangram.model.pieces.Piece;
import moe.skneko.upv.dim.tangram.model.pieces.PieceKind;
import moe.skneko.upv.dim.tangram.model.pieces.PieceSet;

public class RandomGameConfiguration implements GameConfiguration<PieceSet> {
    private final PieceSet pieceSet;
    private final List<Piece> pieces;

    private final Random rng = new Random();

    public RandomGameConfiguration(PieceSet pieceSet, Rect bounds) {
        this.pieceSet = pieceSet;
        pieces = new ArrayList<>(pieceSet.getTotalPieceCount());

        for (Map.Entry<PieceKind, Integer> entry : pieceSet.getPieceCountPerKind().entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                pieces.add(generatePiece(entry.getKey(), bounds));
            }
        }
    }

    @Override
    public PieceSet getPieceSet() {
        return pieceSet;
    }

    @Override
    public Collection<Piece> getPieces() {
        return pieces;
    }

    private Piece generatePiece(PieceKind kind, Rect bounds) {
        int x = randomBetween(bounds.left, bounds.right);
        int y = randomBetween(bounds.top, bounds.bottom);
        int color = Color.rgb(rng.nextInt(255), rng.nextInt(255), rng.nextInt(255));
        float rotation = rng.nextFloat() * 360;
        return new Piece(kind, x, y, color, rotation);
    }

    private int randomBetween(int min, int max) {
        return min + rng.nextInt(max - min + 1);
    }
}

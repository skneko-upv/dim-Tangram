package moe.skneko.upv.dim.tangram.model.conventions;

import android.graphics.Point;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import moe.skneko.upv.dim.tangram.drawing.PastelColor;
import moe.skneko.upv.dim.tangram.model.GameConfiguration;
import moe.skneko.upv.dim.tangram.model.pieces.Piece;
import moe.skneko.upv.dim.tangram.model.pieces.PieceKind;
import moe.skneko.upv.dim.tangram.drawing.PointTranslator;

public class StandardGameConfiguration implements GameConfiguration<StandardPieceSet> {

    private final StandardPieceSet pieceSet;
    private final List<Piece> pieces;

    private final PointTranslator pointTranslator = new PointTranslator(325, 325);

    public StandardGameConfiguration() {
        pieceSet = new StandardPieceSet();

        pieces = Arrays.asList(
                makePiece(StandardPieceSet.LargeTriangle, 0, 0, PastelColor.GREEN, 180),
                makePiece(StandardPieceSet.LargeTriangle, 0, 0, PastelColor.CYAN, 90),
                makePiece(StandardPieceSet.Square, 50, 25, PastelColor.VIOLET, 0),
                makePiece(StandardPieceSet.SmallTriangle, 25, 50, PastelColor.YELLOW, 0),
                makePiece(StandardPieceSet.Rhomboid, 0, 75, PastelColor.RED, 0),
                makePiece(StandardPieceSet.SmallTriangle, 75, 0, PastelColor.PINK, 270),
                makePiece(StandardPieceSet.MediumTriangle, 50, 50, PastelColor.ORANGE, 135));
    }

    @Override
    public StandardPieceSet getPieceSet() {
        return pieceSet;
    }

    @Override
    public Collection<Piece> getPieces() {
        return pieces;
    }

    private Piece makePiece(PieceKind kind, int x, int y, int color, int rotationDegrees) {
        Point coords = pointTranslator.translate(new Point(x, y), kind.getScale(), rotationDegrees);

        return new Piece(kind, coords.x, coords.y, color, rotationDegrees);
    }
}

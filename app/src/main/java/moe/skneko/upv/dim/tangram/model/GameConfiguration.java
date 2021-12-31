package moe.skneko.upv.dim.tangram.model;

import java.util.Collection;

import moe.skneko.upv.dim.tangram.model.pieces.Piece;
import moe.skneko.upv.dim.tangram.model.pieces.PieceSet;

public interface GameConfiguration<S extends PieceSet> {
    S getPieceSet();
    Collection<Piece> getPieces();
}

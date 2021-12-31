package moe.skneko.upv.dim.tangram.model.pieces;

import java.util.Map;
import java.util.Set;

public interface PieceSet {
    Map<PieceKind, Integer> getPieceCountPerKind();

    default Set<PieceKind> getKinds() {
        return getPieceCountPerKind().keySet();
    }

    default int getTotalPieceCount() {
        int total = 0;

        for (Integer countOfOneShape : getPieceCountPerKind().values()) {
            if (countOfOneShape != null) {
                total += countOfOneShape;
            }
        }

        return total;
    }
}

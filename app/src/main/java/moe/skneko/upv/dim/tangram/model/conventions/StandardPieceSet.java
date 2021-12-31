package moe.skneko.upv.dim.tangram.model.conventions;

import java.util.HashMap;
import java.util.Map;

import moe.skneko.upv.dim.tangram.model.pieces.PieceKind;
import moe.skneko.upv.dim.tangram.model.pieces.PieceSet;
import moe.skneko.upv.dim.tangram.model.pieces.kinds.BasicShapes;

public class StandardPieceSet implements PieceSet {

    public static final PieceKind LargeTriangle = new PieceKind(BasicShapes.RectangleTriangleShape, 4);
    public static final PieceKind MediumTriangle = new PieceKind(BasicShapes.RectangleTriangleShape, 2);
    public static final PieceKind SmallTriangle = new PieceKind(BasicShapes.RectangleTriangleShape, 1);
    public static final PieceKind Square = new PieceKind(BasicShapes.SquareShape, 1);
    public static final PieceKind Rhomboid = new PieceKind(BasicShapes.RhomboidShape, 1, true);

    private final Map<PieceKind, Integer> unitsPerKind = new HashMap<>();

    public StandardPieceSet() {
        unitsPerKind.put(LargeTriangle, 2);
        unitsPerKind.put(MediumTriangle, 1);
        unitsPerKind.put(SmallTriangle, 2);
        unitsPerKind.put(Square, 1);
        unitsPerKind.put(Rhomboid, 1);
    }

    @Override
    public Map<PieceKind, Integer> getPieceCountPerKind() {
        return unitsPerKind;
    }
}

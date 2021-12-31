package moe.skneko.upv.dim.tangram.model.pieces;

import moe.skneko.upv.dim.tangram.drawing.PathFactory;

public class PieceKind {
    private final PathFactory pathFactory;
    private final int scale;
    private final boolean allowsFlip;

    public PieceKind(PathFactory pathFactory, int scale) {
        this(pathFactory, scale, false);
    }

    public PieceKind(PathFactory pathFactory, int scale, boolean allowsFlip) {
        this.pathFactory = pathFactory;
        this.scale = scale;
        this.allowsFlip = allowsFlip;
    }

    public PathFactory getPathFactory() {
        return pathFactory;
    }

    public int getScale() {
        return scale;
    }

    public boolean allowsFlip() {
        return allowsFlip;
    }
}

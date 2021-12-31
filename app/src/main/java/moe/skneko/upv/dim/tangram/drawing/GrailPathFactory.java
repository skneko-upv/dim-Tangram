package moe.skneko.upv.dim.tangram.drawing;

import android.graphics.Path;

public class GrailPathFactory implements PathFactory {
    public static final float BASE = 50 * (float) Math.sqrt(2);

    @Override
    public Path buildPath() {
        Path path = new Path();
        /*
        Para el profesor de DIM:
        si siente curiosidad sobre esto, comuníquemelo y le enviaré una captura de una interesante
        página de OneNote con un boceto explicativo con los cálculos con los que se ha obtenido la
        figura de la copa.
         */
        path.moveTo(0, 0);
        path.lineTo(2 * BASE, 0);
        path.lineTo(BASE, BASE);
        path.moveTo(BASE - 50, BASE + 50);
        path.lineTo(BASE, BASE);
        path.lineTo(BASE + 50, BASE + 50);
        path.moveTo(BASE + 50, BASE);
        path.lineTo(BASE, BASE - 50);
        path.lineTo(BASE, BASE + 50);
        path.moveTo(BASE - 50, BASE);
        path.lineTo(BASE, BASE - 50);
        path.lineTo(BASE, BASE + 50);
        path.moveTo(BASE + 25, BASE - 25);
        path.lineTo(BASE, BASE);
        path.lineTo(BASE, BASE - 50);
        path.moveTo(BASE + 25, BASE + 25);
        path.lineTo(BASE, BASE);
        path.lineTo(BASE, BASE + 50);
        path.moveTo(0, 0);
        path.close();

        return path;
    }
}

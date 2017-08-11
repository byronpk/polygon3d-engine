import java.awt.Color;
import java.awt.Polygon;
import java.awt.Graphics2D;

public class Polygon2DObject {
    Color c;
    Polygon p;

    public Polygon2DObject (double[] x, double[] y, Color c) {
        p = new Polygon();
        for (int i = 0; i  < x.length; i++) {
            p.addPoint((int)x[i], (int)y[i]);
        }
        this.c = c;
    }

    public void drawPolygon(Graphics2D g) {
        g.setColor(c);
        g.fillPolygon(p);
        g.setColor(Color.BLACK);
        g.drawPolygon(p);
    }
}
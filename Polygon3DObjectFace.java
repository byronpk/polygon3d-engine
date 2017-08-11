import java.awt.Color;
import java.awt.Polygon;

public class Polygon3DObjectFace {
    Color c;
    double[] x, y, z, viewPoint, viewDirection;
    Polygon2DObject surface;
    double distanceFromViewPoint = 0;

    public Polygon3DObjectFace (double[] x, double[] y, double[] z, double[] vp, double[] vd, Color c) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.viewPoint = vp;
        this.viewDirection = vd;
        this.c = c;

        surface = create3DPolygonMesh();
    }

    private Polygon2DObject create3DPolygonMesh() {
        double[] polygonX = new double[x.length];
        double[] polygonY = new double[x.length];
        double[] temp;
        double total = 0;
        
        for (int i = 0; i < x.length; i ++) {
            temp = recalculatePolygonVectors(viewPoint, viewDirection, x[i], y[i], z[i]);
            polygonX[i] = temp[0];
            polygonY[i] = temp[1];
        }

        for (int i = 0; i < x.length; i++) {
            total += Math.sqrt(
                Math.pow((viewPoint[0] - x[i]), 2) +
                Math.pow((viewPoint[1] - y[i]), 2) + 
                Math.pow((viewPoint[2] - z[i]), 2)
            );
        }

        distanceFromViewPoint = total / x.length;
        return new Polygon2DObject(polygonX, polygonY, c);
    }

    public Polygon2DObject getPolygon2DSurface() {
        return surface;
    }

    public double getDistanceFromViewport() {
        return distanceFromViewPoint;
    }

    public void updatePolygonSurface(double[] vp, double[] vd) {
        this.viewPoint = vp;
        this.viewDirection = vd;
        surface = create3DPolygonMesh();
    }

    private double[] recalculatePolygonVectors(double[] viewPoint, double viewDirection[], double x, double y, double z) {
        double outputX = 0;
        double outputY = 0;

        Vector3D viewVector = new Vector3D (viewDirection[0] - viewPoint[0], viewDirection[1] - viewPoint[1], viewDirection[2] - viewPoint[2]);
        Vector3D directionalVector = new Vector3D (1, 1, 1);
        Vector3D planeVector1 = viewVector.crossProduct(directionalVector);
        Vector3D planeVector2 = viewVector.crossProduct(planeVector1);

        Vector3D viewToVector = new Vector3D (x - viewPoint[0], y - viewPoint[1], z - viewPoint[2]);

        double t = (viewVector.x * viewDirection[0] + viewVector.y * viewDirection[1] + viewVector.z * viewDirection[2] 
                 - (viewVector.x * viewPoint[0] + viewVector.y * viewPoint[1] + viewVector.z * viewPoint[2]))
                 / (viewVector.x * viewToVector.x + viewVector.y * viewToVector.y + viewVector.z * viewToVector.z);

        x = viewPoint[0] + viewToVector.x * t;
        y = viewPoint[1] + viewToVector.y * t;
        z = viewPoint[2] + viewToVector.z * t;

        if (t >= 0) {
            outputX = 320 + 50 * ((planeVector2.x * x) + (planeVector2.y * y) + (planeVector2.z * z));
            outputY = 240 + 50 * ((planeVector1.x * x) + (planeVector1.y * y) + (planeVector1.z * z));
        }

        return new double[] { outputX, outputY };
    }
}

class Vector3D {
    double x = 0, y = 0, z = 0;
    public Vector3D (double x, double y, double z) {
        double length = Math.sqrt((x * x) + (y * y) + (z * z));
        if (length > 0) {
            this.x = x / length;
            this.y = y / length; 
            this.z = z / length;
        }
    }

    Vector3D crossProduct(Vector3D v) {
        Vector3D product = new Vector3D(
            (y * v.z) - (z * v.y),
            (z * v.x) - (x * v.z),
            (x * v.y) - (y * v.x)
        );
        return product;
    }
}

class Polygon3DObject {
    private int surfaceCount = 0;
    private Polygon3DObjectFace[] surfaces = new Polygon3DObjectFace[16];
    private int drawingOrder[] = new int[16];
    public Polygon3DObject(Polygon3DObjectFace[] faces) {
        surfaces = faces;
        for(int i = 0; i < 16; i++) {
            drawingOrder[i] = i;
        }
    }

    public void addSurface(Polygon3DObjectFace p3d) {
        surfaces[surfaceCount] = p3d;
        surfaceCount++;
    }

    public void updatePolygon(double[] vp, double[] vd) {
        for(int i = 0; i < surfaces.length; i++) {
            surfaces[i].updatePolygonSurface(vp, vd);
        }
        updateSurfaceDrawingOrder();
    }

    public void updateSurfaceDrawingOrder() {
        double[] k = new double[surfaces.length];
        drawingOrder = new int[surfaces.length];
        for(int i = 0; i < surfaces.length; i++) {
            k[i] = surfaces[i].getDistanceFromViewport();
            drawingOrder[i] = i;
        }

        double temp;
        int tempr;

        for (int a = 0; a < k.length - 1; a++) {
            for (int b = 0; b < k.length - 1; b++) {
                if(k[b] < k[b + 1]) {
                    temp = k[b];
                    tempr = drawingOrder[b];

                    drawingOrder[b] = drawingOrder[b + 1];
                    k[b] = k[b + 1];

                    drawingOrder[b + 1] = tempr;
                    k[b + 1] = temp;
                }
            }
        }
    }

    public Polygon2DObject[] getDrawableSurfaces() {
        Polygon2DObject[] temp = new Polygon2DObject[surfaces.length];
        for(int i = 0; i < surfaces.length; i++) {
            temp[i] = surfaces[drawingOrder[i]].getPolygon2DSurface();
        }

        return temp;
    }
}
import javax.swing.JFrame;
import java.awt.Color;
public class entryPoint implements Runnable {
    public void run() {

    }

    public static void main (String args[]) {
        JFrame f = new JFrame("Graphics 3D Test");
        displayPanel contentPane = new displayPanel();
        double[] camera = contentPane.getCamera();
        double[] viewingDirection = contentPane.getViewDirection();

        Polygon3DObjectFace face1 = new Polygon3DObjectFace(new double[] {0, 2, 2, 0}, new double[] {0, 0, 2, 2}, new double[] {0, 0, 0, 0}, camera, viewingDirection, Color.RED);
        Polygon3DObjectFace face2 = new Polygon3DObjectFace(new double[] {0, 2, 2, 0}, new double[] {0, 0, 2, 2}, new double[] {2, 2, 2, 2}, camera, viewingDirection, Color.GREEN);
        Polygon3DObjectFace face3 = new Polygon3DObjectFace(new double[] {0, 2, 2, 0}, new double[] {0, 0, 0, 0}, new double[] {0, 0, 2, 2}, camera, viewingDirection, Color.BLUE);
        Polygon3DObjectFace face4 = new Polygon3DObjectFace(new double[] {0, 2, 2, 0}, new double[] {2, 2, 2, 2}, new double[] {0, 0, 2, 2}, camera, viewingDirection, Color.YELLOW);
        Polygon3DObjectFace face5 = new Polygon3DObjectFace(new double[] {0, 0, 0, 0}, new double[] {0, 2, 2, 0}, new double[] {0, 0, 2, 2}, camera, viewingDirection, Color.CYAN);
        Polygon3DObjectFace face6 = new Polygon3DObjectFace(new double[] {2, 2, 2, 2}, new double[] {0, 2, 2, 0}, new double[] {0, 0, 2, 2}, camera, viewingDirection, Color.MAGENTA);

        Polygon3DObject obj3d = new Polygon3DObject(new Polygon3DObjectFace[] { face1, face2, face3, face4, face5, face6 });
        contentPane.addPolygon3D(obj3d);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(640, 480);
        f.setContentPane(contentPane);
        f.setVisible(true);
    }
}
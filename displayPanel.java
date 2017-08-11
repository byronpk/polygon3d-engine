import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class displayPanel extends JPanel implements KeyListener {
    private double[] camera = new double[] { 10, 10, 10 };
    private double[] viewingDirection = new double[] { 5, 0, 0 };
    private double sleepTime = 1000 / 30, lastRefresh = 0;
    private int polygonCount = 0;
    private Polygon3DObject[] polygon3ds = new Polygon3DObject[16];

    public double[] getCamera() {
        return camera;
    }
    
    public double[] getViewDirection() {
        return viewingDirection;
    }

    public displayPanel() {
        addKeyListener(this);
        setFocusable(true);
    }

    public void paintComponent(Graphics g2) {
        Graphics2D g = (Graphics2D) g2;
        g.clearRect(0, 0, 2000, 2000);

        for(int i = 0; i < polygonCount; i++) {
            polygon3ds[i].updatePolygon(camera, viewingDirection);
            Polygon2DObject[] drawables = polygon3ds[i].getDrawableSurfaces();
            for(int j = 0; j < drawables.length; j++) {
                drawables[j].drawPolygon(g);
            }
        }
        
        sleepAndRefresh();
    }

    public void addPolygon3D(Polygon3DObject p3d) {
        polygon3ds[polygonCount] = p3d;
        polygonCount++;
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            camera[0]--;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            camera[0]++;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            camera[1]--;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            camera[1]++;
        }
        if(e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            camera[2]--;
        }
        if(e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            camera[2]++;
        }
    }

    public void keyReleased(KeyEvent e) {
        
    }

    public void keyTyped(KeyEvent e) {
        
    }

    public void sleepAndRefresh() {
        while (true) {
            if((System.currentTimeMillis() - lastRefresh) > sleepTime) {
                lastRefresh = System.currentTimeMillis();
                repaint();
                break;
            } else {
                try {
                    long sleep = (long)(sleepTime - (System.currentTimeMillis() - lastRefresh));
                    if(sleep <= 0) {
                        sleep = 1;
                    } 
                    Thread.sleep(sleep);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
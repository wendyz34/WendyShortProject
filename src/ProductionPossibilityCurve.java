import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.event.MouseEvent;

public class ProductionPossibilityCurve extends JPanel{

    private List<Point> points;
    private String xAxisLabel;
    private String yAxisLabel;
    private static int con;
    private static int cap;
    private boolean ovalClicked;

    public ProductionPossibilityCurve(String xAxisLabel, String yAxisLabel) {
        points = new ArrayList<>();
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
    }
    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
        // Set up the coordinate system
        int width = getWidth();
        int height = getHeight();
        int xCenter = width/2;
        int yCenter = height/2;
        int thickness = 7;
         ((Graphics2D) g).setStroke(new BasicStroke(thickness));
        //Draw the vertical line
        g.setColor(Color.DARK_GRAY);
        g.drawLine(width / 2 ,0, width/2 , height / 2);
        // Draw the horizontal line
        g.drawLine(width/2, height/2, width,height/2);
        // Draw the x and y axis labels
        g.drawString(xAxisLabel, 250, 300);
        g.drawString(yAxisLabel, 100, 100);

        // Plot the user-provided points
        g.setColor(Color.RED);
        for (Point point : points) {
            int x = xCenter + point.x;
            int y = yCenter - point.y;
            ((Graphics2D) g).setStroke(new BasicStroke(thickness));
           g.fillOval(x - 2, y - 2, 10, 10);
        }

        // Draw the slope
        g.setColor(Color.DARK_GRAY);
        ((Graphics2D) g).setStroke(new BasicStroke(4));
        g.drawLine(width, con/2, cap/2,0);
        //g.drawArc(0,con/2,500,500,0,90);
        g.drawArc(cap/2,0,400,400,0,90);
       // g.drawArc();



    }
    public void mousePressed(MouseEvent e){

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Production Possibility Curve");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 550);
        Scanner scanner = new Scanner(System.in);
        //label axis
        //coordinates only show within 250?
        String xAxisLabel= JOptionPane.showInputDialog("Label x:");
        String yAxisLabel= JOptionPane.showInputDialog("Label y:");
        ProductionPossibilityCurve graphPanel = new ProductionPossibilityCurve(xAxisLabel, yAxisLabel);
        //get slope
        //max production only show within 500
        String slope = JOptionPane.showInputDialog("During a normal economic time what's the max production of consumer goods?");
         con = Integer.parseInt(slope);
        String s = JOptionPane.showInputDialog("Max production of consumer goods?");
         cap = Integer.parseInt(s);
        //get coordinates
        while (true) {
            String input = JOptionPane.showInputDialog("Enter coordinates (x, y) separated by spaces (or press enter to finish): ");
            if (input.isEmpty()) {
                break;
            }

            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("Invalid input! Please enter coordinates in the format 'x y'.");
                continue;
            }
            try {
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                graphPanel.addPoint(x, y);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter integers for the coordinates.");
                continue;
            }
        }

        frame.add(graphPanel);

        frame.setVisible(true);
        //scanner.close();
    }
}


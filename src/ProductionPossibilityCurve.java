import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductionPossibilityCurve extends JPanel {

    private List<Point> points;
    private String xAxisLabel;
    private String yAxisLabel;
    private static int con;
    private static int cap;

    public ProductionPossibilityCurve(String xAxisLabel, String yAxisLabel) {
        points = new ArrayList<>();
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
    }

    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        // Set up the coordinate system
        int width = getWidth();
        int height = getHeight();
        int xCenter = width / 2;
        int yCenter = height / 2;

        int thickness = 7;
         ((Graphics2D) g).setStroke(new BasicStroke(thickness));
        //Draw the vertical line
        g.setColor(Color.darkGray);
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
        //int y2 = (int) ((width/2)-(slopeRatio * (350-width/2)));
        g.drawLine(width/2,cap/2,con/2, 0);
        //g.drawArc(width/2,height/2,300,300,0,90);
        //g.drawLine(width,height/2,width/2,0);



    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Production Possibility Curve");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        Scanner scanner = new Scanner(System.in);
        //label axis
        String xAxisLabel= JOptionPane.showInputDialog("Label x:");
        String yAxisLabel= JOptionPane.showInputDialog("Label y:");
        ProductionPossibilityCurve graphPanel = new ProductionPossibilityCurve(xAxisLabel, yAxisLabel);
        //get slope
        String slope = JOptionPane.showInputDialog("During a normal economic time what's the max production of consumer goods?");
         con = Integer.parseInt(slope);
        String s = JOptionPane.showInputDialog("Max production of consumer goods?");
         cap = Integer.parseInt(s);
        //get coordinates
        while (true) {
            System.out.print("Enter coordinates (x, y) separated by spaces (or press enter to finish): ");
            String input = scanner.nextLine();
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


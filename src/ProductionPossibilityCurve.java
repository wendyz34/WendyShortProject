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
    private double slopeRatio;

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
        System.out.println(width + " " + height);
        int xCenter = width / 2;
        int yCenter = height / 2;
        // ((Graphics2D) g).setStroke(new BasicStroke(width));
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
            g.fillOval(x - 2, y - 2, 4, 4);
        }

        // Draw the slope
        int y2 = (int) (slopeRatio * width);
        g.drawLine(0, height, width, y2);
       /* if (points.size() >= 2) {
            g.setColor(Color.DARK_GRAY);
            Point p1 = points.get(0);
            Point p2 = points.get(1);
            double slope = (double) (p2.y - p1.y) / (p2.x - p1.x);
            double angle = Math.atan(slope);

            int radius = Math.min(width, height) / 4;
            int arcX = xCenter - radius;
            int arcY = yCenter - radius;
            int arcWidth = radius * 2;
            int arcHeight = radius * 2;

            g.drawArc(arcX, arcY, arcWidth, arcHeight, 0, (int) Math.toDegrees(angle));
        }*/

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Production Possibility Curve");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        Scanner scanner = new Scanner(System.in);
        //GraphPlotter graphPanel = new GraphPlotter();

        System.out.print("Enter x-axis label: ");
        String xAxisLabel = scanner.nextLine();

        System.out.print("Enter y-axis label: ");
        String yAxisLabel = scanner.nextLine();

       /* System.out.println("What is the ratio between consumer goods and capital goods(type y/x)");
        double ratio = Double.parseDouble(scanner.nextLine());*/
        //graphPanel.setSlopeRatio(ratio);


        ProductionPossibilityCurve graphPanel = new ProductionPossibilityCurve(xAxisLabel, yAxisLabel);

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


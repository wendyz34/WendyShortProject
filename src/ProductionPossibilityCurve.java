import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

    public ProductionPossibilityCurve(String xAxisLabel, String yAxisLabel) {
        points = new ArrayList<>();
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point clickedPoint = getClickedPoint(e.getX(), e.getY());
                if (clickedPoint != null) {
                    handlePointClick(clickedPoint);
                }
            }
        });
    }
    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
    }
    private Point getClickedPoint(int x, int y) {
        int width = getWidth();
        int height = getHeight();
        int xCenter = width / 2;
        int yCenter = height / 2;

        for (Point point : points) {
            int pointX = xCenter + point.x;
            int pointY = yCenter - point.y;

            if (Math.abs(pointX - x) <= 5 && Math.abs(pointY - y) <= 5) {
                return point;
            }
        }

        return null;
    }

    private void handlePointClick(Point clickedPoint) {
        int x = clickedPoint.x;
        int y = clickedPoint.y;
        double ratio = (double)y/x;
        String message = " ";

        /*if (Math.abs((double) y / x - ratio) < 0.001) {
            message = "Coordinate on the PPC.";
        } else if ((double) y / x < ratio) {
            message = "Area probably in a recession.";
        } else if ((double) y / x > ratio) {
            message = "Production more than usual. Technological advancement.";
        }*/

        JOptionPane.showMessageDialog(this, "Clicked point: (" + x + ", " + y + ") " + message);
    }


        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(Color.darkGray);
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);

            int width = getWidth();
            int height = getHeight();
            int xCenter = width / 2;
            int yCenter = height / 2;

            // Draw the vertical line
            g.setColor(Color.DARK_GRAY);
            g.drawLine(xCenter, 0, xCenter, height / 2);

            // Draw the horizontal line
            g.drawLine(xCenter, height / 2, width, height / 2);

            // Draw the x and y axis labels
            g.drawString(xAxisLabel, width - 70, height / 2 - 10);
            g.drawString(yAxisLabel, xCenter + 10, 20);

            // Plot the user-provided points
            g.setColor(Color.RED);
            for (Point point : points) {
                int x = xCenter + point.x;
                int y = yCenter - point.y;
                g.fillOval(x - 2, y - 2, 10, 10);
            }

            // Draw the arc
            int arcStartX = xCenter - cap;
            int arcStartY = yCenter - con;
            int arcWidth = cap * 2;
            int arcHeight = con * 2;

            g.drawArc(arcStartX, arcStartY, arcWidth, arcHeight, 0, 90);
        }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Production Possibility Curve");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550,550);
        Scanner scanner = new Scanner(System.in);
        //Label axis
        //coordinates only show within 250?
        String xAxisLabel= JOptionPane.showInputDialog("Label x:");
        String yAxisLabel= JOptionPane.showInputDialog("Label y:");
        ProductionPossibilityCurve graphPanel = new ProductionPossibilityCurve(xAxisLabel, yAxisLabel);
        //Get slope
        //max production only show within 500
        String slope = JOptionPane.showInputDialog("During a normal economic time what's the max production of consumer goods?");
        con = Integer.parseInt(slope);
        String s = JOptionPane.showInputDialog("Max production of consumer goods?");
        cap = Integer.parseInt(s);
        //get coordinates
        while (true) {
            /*System.out.println("Enter coordinates (x, y) separated by spaces (or press enter to finish): ");
            String input = scanner.nextLine();*/
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

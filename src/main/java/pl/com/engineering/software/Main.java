package pl.com.engineering.software;

import com.jsevy.jdxf.DXFDocument;
import com.jsevy.jdxf.DXFGraphics;
import pl.com.engineering.software.domain.ObstaclesCoordinations;
import pl.com.engineering.software.domain.Span;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DXFDocument dxfDocument = new DXFDocument("First dxf output");

        List<Span> spansToDraw = SpanDataProvider.provideData();

        prepareDrawing(dxfDocument, spansToDraw);
        generateDxfFile(dxfDocument);
    }

    private static void generateDxfFile(DXFDocument dxfDocument) {
        String stringOutput = dxfDocument.toDXFString();
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("E:/dxfOutput1.dxf");
            fileWriter.write(stringOutput);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void prepareDrawing(DXFDocument dxfDocument, List<Span> spansToDraw) {
        DXFGraphics graphics = dxfDocument.getGraphics();
        graphics.setColor(Color.WHITE);

        Double currentXcoordination = 0.0;

        for (int i = 0; i < spansToDraw.size(); i++) {
            drawSpanPylons(graphics, currentXcoordination, spansToDraw.get(i), i);
            drawSpanGround(graphics, currentXcoordination, spansToDraw.get(i));
            drawSpanWire(graphics, currentXcoordination, spansToDraw.get(i));

            currentXcoordination += spansToDraw.get(i).getNextPylonDistance();
        }


//        graphics.setColor(Color.RED);
//        graphics.setStroke(new BasicStroke(3));
//
//        graphics.drawLine(0, 0, 1000, 500);
//        graphics.drawRect(1000, 500, 150, 150);
//        graphics.drawRoundRect(20, 200, 130, 100, 20,
//                10);
//        graphics.drawOval(200, 800, 200, 400);
//        graphics.drawArc(100, 1900, 400, 200, 60, 150);
//
//        graphics.setColor(Color.BLUE);
//        graphics.fillRect(100, 100, 100, 50);
//        int[] xPoints = {200, 300, 250};
//        int[] yPoints = {200, 250, 300};
//        graphics.fillPolygon(xPoints, yPoints, xPoints.length);
//
//        graphics.setFont(new Font("TimesRoman",Font.BOLD, 18));
//        graphics.drawString("Some 38-point monospaced blue text at position 480, 400", 480, 400);
//
//        graphics.shear(0.1f, 0.2f);
//        graphics.drawRect(100, 100, 200, 200);
    }

    private static void drawSpanWire(DXFGraphics graphics, Double currentXcoordination, Span span) {
            double[] x = new double[3];
            x[0] = currentXcoordination;
            x[1] = currentXcoordination + span.getX1();
            x[2] = currentXcoordination + span.getNextPylonDistance();

            double[] y = new double[3];
            y[0] = -(span.getFirstPylonCoordination() + span.getFirstPylonHeight());
            y[1] = -(span.getFirstPylonHeight() - 100);
            y[2] = -(span.getSecondPylonCoordination() + span.getSecondPylonHeight());
            graphics.drawPolyline(x,y,3);
    }

    private static void drawSpanPylons(DXFGraphics graphics, Double currentXcoordination, Span span, int i) {
        Double theLowestPylonPoint = span.getFirstPylonCoordination() - span.getFirstPylonDepth();
        Double totalPylonHeight = span.getFirstPylonHeight();

        if (i == 0) {
            graphics.drawLine(
                    currentXcoordination,-theLowestPylonPoint,
                    currentXcoordination,-(totalPylonHeight + span.getFirstPylonCoordination()));
        }
        graphics.drawLine(0,-span.getFirstPylonCoordination(),250, -span.getFirstPylonCoordination());
        graphics.drawLine(0,-theLowestPylonPoint,250, -theLowestPylonPoint);


        theLowestPylonPoint = span.getSecondPylonCoordination() - span.getSecondPylonDepth();
        totalPylonHeight = span.getSecondPylonHeight();
        graphics.drawLine(
                currentXcoordination + span.getNextPylonDistance(),-theLowestPylonPoint,
                currentXcoordination + span.getNextPylonDistance(),-(totalPylonHeight + span.getSecondPylonCoordination()));

        graphics.drawLine(250,-span.getSecondPylonCoordination(),1000, -span.getSecondPylonCoordination());
        graphics.drawLine(250,-theLowestPylonPoint,1000, -theLowestPylonPoint);
    }

    private static void drawSpanGround(DXFGraphics graphics, Double currentXcoordination, Span span) {
        Double previousXCoordination = span.getObstaclesCoordinations().get(0).getX();
        Double previousYCoordination = span.getObstaclesCoordinations().get(0).getY();

        for (int j = 0; j < span.getObstaclesCoordinations().size();j++) {

            ObstaclesCoordinations coordinations = span.getObstaclesCoordinations().get(j);

            if (j == 0) {
                graphics.drawLine(
                        currentXcoordination, -span.getFirstPylonCoordination(),
                        currentXcoordination + coordinations.getX(), -coordinations.getY());
            } else if (j < span.getObstaclesCoordinations().size() - 1){
                graphics.drawLine(
                        currentXcoordination + previousXCoordination, -previousYCoordination,
                        currentXcoordination + coordinations.getX(), -coordinations.getY());
                previousXCoordination = coordinations.getX();
                previousYCoordination = coordinations.getY();
            } else {
                graphics.drawLine(
                        currentXcoordination + previousXCoordination, -previousYCoordination,
                        currentXcoordination + coordinations.getX(), -coordinations.getY());

                graphics.drawLine(
                        currentXcoordination + coordinations.getX(), -coordinations.getY(),
                        currentXcoordination + span.getNextPylonDistance(), -span.getSecondPylonCoordination());
            }
        }
    }
}

package pl.com.engineering.software;

import com.jsevy.jdxf.DXFDocument;
import com.jsevy.jdxf.DXFGraphics;
import pl.com.engineering.software.domain.LinearFunction;
import pl.com.engineering.software.domain.PointCoordinations;
import pl.com.engineering.software.domain.Span;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int DEGREES_180 = 180;
    private static final int DEGREES_90 = 90;

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
    }

    private static void drawSpanWire(DXFGraphics graphics, Double currentXcoordination, Span span) {
        PointCoordinations firstPointCoordinations = new PointCoordinations();
        firstPointCoordinations.setX(currentXcoordination);
        firstPointCoordinations.setY(-(span.getFirstPylonCoordination() + span.getFirstPylonHeight()));

        PointCoordinations middlePointCoordinations = new PointCoordinations();
        middlePointCoordinations.setX(currentXcoordination + span.getX1());
        middlePointCoordinations.setY(-(span.getFirstPylonHeight() - 50));

        PointCoordinations lastPointCoordinations = new PointCoordinations();
        lastPointCoordinations.setX(currentXcoordination + span.getNextPylonDistance());
        lastPointCoordinations.setY(-(span.getSecondPylonCoordination() + span.getSecondPylonHeight()));

        PointCoordinations circleCoordinations = findCircleCoordination(
                firstPointCoordinations.getX(),
                firstPointCoordinations.getY(),
                middlePointCoordinations.getX(),
                middlePointCoordinations.getY(),
                lastPointCoordinations.getX(),
                lastPointCoordinations.getY());

//        graphics.drawLine(firstPointCoordinations.getX(), firstPointCoordinations.getY(), circleCoordinations.getX(), circleCoordinations.getY());
//        graphics.drawLine(middlePointCoordinations.getX(), middlePointCoordinations.getY(), circleCoordinations.getX(), circleCoordinations.getY());
//        graphics.drawLine(lastPointCoordinations.getX(), lastPointCoordinations.getY(), circleCoordinations.getX(), circleCoordinations.getY());

        double arcRadius = findArcRadius(circleCoordinations, firstPointCoordinations.getX(), firstPointCoordinations.getY());

        LinearFunction firstPointLinearFunction = findLinearFunction(firstPointCoordinations, circleCoordinations);
        LinearFunction lastPointLinearFunction = findLinearFunction(lastPointCoordinations, circleCoordinations);

        double[] edgeAngles = findEdgeAngles(firstPointLinearFunction, lastPointLinearFunction);

        List<PointCoordinations> pointCoordinations = generateArcPoints(firstPointCoordinations, lastPointCoordinations, circleCoordinations, arcRadius, edgeAngles);

        double[] xPoints = new double[pointCoordinations.size()];
        double[] yPoints = new double[pointCoordinations.size()];
        for(int i = 0; i < pointCoordinations.size(); i++) {
            xPoints[i] = pointCoordinations.get(i).getX();
            yPoints[i] = pointCoordinations.get(i).getY();
        }
        graphics.drawPolyline(xPoints, yPoints, pointCoordinations.size());
    }

    private static LinearFunction findLinearFunction(PointCoordinations pointCoordinations, PointCoordinations circleCoordination) {
        LinearFunction linearFunction = new LinearFunction();
        linearFunction.setSlope((circleCoordination.getY()-pointCoordinations.getY())/(circleCoordination.getX()-pointCoordinations.getX()));
        linearFunction.setIntercept(pointCoordinations.getY()-linearFunction.getSlope()*pointCoordinations.getX());
        return linearFunction;
    }

    private static double[] findEdgeAngles(LinearFunction firstPointLinearFunction, LinearFunction lastPointLinearFunction) {
        double[] edgeAngles = new double[2];
        edgeAngles[0] = Math.atan(Math.abs(-lastPointLinearFunction.getSlope()))*DEGREES_180/Math.PI;
        edgeAngles[1] = - Math.atan(Math.abs(-firstPointLinearFunction.getSlope()))*DEGREES_180/Math.PI + DEGREES_180;
        return edgeAngles;
    }

    private static List<PointCoordinations> generateArcPoints(PointCoordinations firstPointCoordinations, PointCoordinations lastPointCoordinations, PointCoordinations circleCoordinations, double arcRadius, double[] edgeAngles) {
        List<PointCoordinations> outputCoordinations = new ArrayList<PointCoordinations>();
        outputCoordinations.add(lastPointCoordinations);

        for (double i = edgeAngles[0] + 1; i < edgeAngles[1]; i++) {
            double x = arcRadius * Math.cos(i * Math.PI / 180) + circleCoordinations.getX();
            double y = arcRadius * Math.sin(i * Math.PI / 180) + circleCoordinations.getY();
            outputCoordinations.add(new PointCoordinations(x,y));
        }

        outputCoordinations.add(firstPointCoordinations);
        return outputCoordinations;
    }

    private static PointCoordinations findCircleCoordination(double x1, double y1, double x2, double y2, double x3, double y3) {
        PointCoordinations circleCoordinations = new PointCoordinations();

        double mid_12_x = (x1 + x2)/2;
        double mid_12_y = (y1 + y2)/2;

        double mid_23_x = (x2 + x3)/2;
        double mid_23_y = (y2 + y3)/2;

        double slope_12 = (y2-y1)/(x2-x1);
        double slope_23 = (y3-y2)/(x3-x2);

        double slope_perp_12 = -Math.pow(slope_12, -1);
        double slope_perp_23 = -Math.pow(slope_23, -1);

        //Linear function
        //firstFunctionSlope, firstFunctionIntercept
        double firstFunctionSlope = slope_perp_12;
        double firstFunctionIntercept = mid_12_y - slope_perp_12*mid_12_x;

        //Linear function
        //secondFunctionSlope, secondFunctionIntercept
        double secondFunctionSlope = slope_perp_23;
        double secondFunctionIntercept = mid_23_y - slope_perp_23*mid_23_x;

        circleCoordinations.setX((secondFunctionIntercept-firstFunctionIntercept)/(firstFunctionSlope-secondFunctionSlope));
        circleCoordinations.setY(firstFunctionSlope*circleCoordinations.getX()+firstFunctionIntercept);
        return circleCoordinations;
    }

    private static double findArcRadius(PointCoordinations circleCoordinations, double x, double y) {
        return Math.sqrt(Math.pow(x-circleCoordinations.getX(), 2) + Math.pow(y-circleCoordinations.getY(), 2));
    }

    private static void drawSpanPylons(DXFGraphics graphics, Double currentXcoordination, Span span, int i) {
        Double theLowestPylonPoint = span.getFirstPylonCoordination() - span.getFirstPylonDepth();
        Double totalPylonHeight = span.getFirstPylonHeight();

        if (i == 0) {
            graphics.drawLine(
                    currentXcoordination,-theLowestPylonPoint,
                    currentXcoordination,-(totalPylonHeight + span.getFirstPylonCoordination()));
        }

        theLowestPylonPoint = span.getSecondPylonCoordination() - span.getSecondPylonDepth();
        totalPylonHeight = span.getSecondPylonHeight();
        graphics.drawLine(
                currentXcoordination + span.getNextPylonDistance(),-theLowestPylonPoint,
                currentXcoordination + span.getNextPylonDistance(),-(totalPylonHeight + span.getSecondPylonCoordination()));
    }

    private static void drawSpanGround(DXFGraphics graphics, Double currentXcoordination, Span span) {
        Double previousXCoordination = span.getObstaclesCoordinations().get(0).getX();
        Double previousYCoordination = span.getObstaclesCoordinations().get(0).getY();

        for (int j = 0; j < span.getObstaclesCoordinations().size();j++) {

            PointCoordinations coordinations = span.getObstaclesCoordinations().get(j);

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

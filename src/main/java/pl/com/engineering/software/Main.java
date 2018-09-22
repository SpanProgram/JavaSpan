package pl.com.engineering.software;

import com.jsevy.jdxf.DXFDocument;
import com.jsevy.jdxf.DXFGraphics;
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

            if (i == 0) {
                //first pylon
                graphics.drawLine(
                        currentXcoordination,spansToDraw.get(i).getFirstPylonDepth(),
                        currentXcoordination,-spansToDraw.get(i).getFirstPylonHeight());
            }

            //second pylon
            graphics.drawLine(
                    currentXcoordination + spansToDraw.get(i).getNextPylonDistance(),spansToDraw.get(i).getSecondPylonDepth(),
                    currentXcoordination + spansToDraw.get(i).getNextPylonDistance(),-spansToDraw.get(i).getSecondPylonHeight());

            //ground
            graphics.drawLine(
                    currentXcoordination, 0,
                    currentXcoordination + spansToDraw.get(i).getNextPylonDistance(), 0);

            //arc
            double[] x = new double[3];
            x[0] = currentXcoordination;
            x[1] = currentXcoordination + spansToDraw.get(i).getX1();
            x[2] = currentXcoordination + spansToDraw.get(i).getNextPylonDistance();

            double[] y = new double[3];
            y[0] = -spansToDraw.get(i).getFirstPylonHeight();
            y[1] = -(spansToDraw.get(i).getFirstPylonHeight() - 100);
            y[2] = -spansToDraw.get(i).getSecondPylonHeight();
            graphics.drawPolyline(x,y,3);

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
}

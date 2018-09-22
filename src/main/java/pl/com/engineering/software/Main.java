package pl.com.engineering.software;

import com.jsevy.jdxf.DXFDocument;
import com.jsevy.jdxf.DXFGraphics;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        DXFDocument dxfDocument = new DXFDocument("First dxf output");

        prepareDrawing(dxfDocument);
        generateDxfFile(dxfDocument);
    }

    private static void generateDxfFile(DXFDocument dxfDocument) {
        String stringOutput = dxfDocument.toDXFString();
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("E:/dxfOutput.dxf");
            fileWriter.write(stringOutput);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void prepareDrawing(DXFDocument dxfDocument) {
        DXFGraphics graphics = dxfDocument.getGraphics();

        graphics.setColor(Color.RED);
        graphics.setStroke(new BasicStroke(3));

        graphics.drawLine(0, 0, 1000, 500);
        graphics.drawRect(1000, 500, 150, 150);
        graphics.drawRoundRect(20, 200, 130, 100, 20,
                10);
        graphics.drawOval(200, 800, 200, 400);
        graphics.drawArc(100, 1900, 400, 200, 60, 150);

        graphics.setColor(Color.BLUE);
        graphics.fillRect(100, 100, 100, 50);
        int[] xPoints = {200, 300, 250};
        int[] yPoints = {200, 250, 300};
        graphics.fillPolygon(xPoints, yPoints, xPoints.length);

        graphics.setFont(new Font("TimesRoman",Font.BOLD, 18));
        graphics.drawString("Some 38-point monospaced blue text at position 480, 400", 480, 400);

        graphics.shear(0.1f, 0.2f);
        graphics.drawRect(100, 100, 200, 200);
    }
}

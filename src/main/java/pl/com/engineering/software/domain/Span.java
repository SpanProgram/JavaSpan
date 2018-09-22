package pl.com.engineering.software.domain;

public class Span {

    private Double firstPylonHeight;
    private Double firstPylonDepth;

    private Double secondPylonHeight;
    private Double secondPylonDepth;

    private Double nextPylonDistance;

    private Double x1;

    public Span() {
    }

    public Span(Double firstPylonHeight, Double firstPylonDepth, Double secondPylonHeight, Double secondPylonDepth, Double nextPylonDistance, Double x1) {
        this.firstPylonHeight = firstPylonHeight;
        this.firstPylonDepth = firstPylonDepth;
        this.secondPylonHeight = secondPylonHeight;
        this.secondPylonDepth = secondPylonDepth;
        this.nextPylonDistance = nextPylonDistance;
        this.x1 = x1;
    }

    public Double getFirstPylonHeight() {
        return firstPylonHeight;
    }

    public void setFirstPylonHeight(Double firstPylonHeight) {
        this.firstPylonHeight = firstPylonHeight;
    }

    public Double getFirstPylonDepth() {
        return firstPylonDepth;
    }

    public void setFirstPylonDepth(Double firstPylonDepth) {
        this.firstPylonDepth = firstPylonDepth;
    }

    public Double getSecondPylonHeight() {
        return secondPylonHeight;
    }

    public void setSecondPylonHeight(Double secondPylonHeight) {
        this.secondPylonHeight = secondPylonHeight;
    }

    public Double getSecondPylonDepth() {
        return secondPylonDepth;
    }

    public void setSecondPylonDepth(Double secondPylonDepth) {
        this.secondPylonDepth = secondPylonDepth;
    }

    public Double getNextPylonDistance() {
        return nextPylonDistance;
    }

    public void setNextPylonDistance(Double nextPylonDistance) {
        this.nextPylonDistance = nextPylonDistance;
    }

    public Double getX1() {
        return x1;
    }

    public void setX1(Double x1) {
        this.x1 = x1;
    }
}

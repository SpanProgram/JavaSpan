package pl.com.engineering.software.domain;

public class ObstaclesCoordinations {

    private Double X;
    private Double Y;

    public ObstaclesCoordinations(Double x, Double y) {
        X = x;
        Y = y;
    }

    public Double getX() {
        return X;
    }

    public void setX(Double x) {
        X = x;
    }

    public Double getY() {
        return Y;
    }

    public void setY(Double y) {
        Y = y;
    }
}

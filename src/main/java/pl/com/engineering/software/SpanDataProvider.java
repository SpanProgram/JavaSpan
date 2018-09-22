package pl.com.engineering.software;

import pl.com.engineering.software.domain.ObstaclesCoordinations;
import pl.com.engineering.software.domain.Span;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpanDataProvider {

    public static List<Span> provideData() {

        Span firstSpan = new Span();
        firstSpan.setFirstPylonHeight(320d);
        firstSpan.setFirstPylonDepth(110d);
        firstSpan.setSecondPylonHeight(430d);
        firstSpan.setSecondPylonDepth(170d);
        firstSpan.setNextPylonDistance(530d);
        firstSpan.setX1(210d);
        firstSpan.setFirstPylonCoordination(0d);
        firstSpan.setSecondPylonCoordination(45d);
        List<ObstaclesCoordinations> firstObstaclesCoordination = new ArrayList<ObstaclesCoordinations>();
        firstObstaclesCoordination.add(new ObstaclesCoordinations(120d, -23d));
        firstObstaclesCoordination.add(new ObstaclesCoordinations(324d, 33d));
        firstObstaclesCoordination.add(new ObstaclesCoordinations(500d, 15d));
        firstSpan.setObstaclesCoordinations(firstObstaclesCoordination);

        Span secondSpan = new Span();
        secondSpan.setFirstPylonHeight(430d);
        secondSpan.setFirstPylonDepth(170d);
        secondSpan.setSecondPylonHeight(610d);
        secondSpan.setSecondPylonDepth(190d);
        secondSpan.setNextPylonDistance(720d);
        secondSpan.setX1(210d);
        secondSpan.setFirstPylonCoordination(45d);
        secondSpan.setSecondPylonCoordination(20d);
        List<ObstaclesCoordinations> secondObstaclesCoordination = new ArrayList<ObstaclesCoordinations>();
        secondObstaclesCoordination.add(new ObstaclesCoordinations(120d, 0d));
        secondObstaclesCoordination.add(new ObstaclesCoordinations(385d, 21d));
        secondObstaclesCoordination.add(new ObstaclesCoordinations(611d, 0d));
        secondSpan.setObstaclesCoordinations(secondObstaclesCoordination);

        List<Span> list = new ArrayList<Span>();
        list.add(firstSpan);
        list.add(secondSpan);
        return list;
    }
}

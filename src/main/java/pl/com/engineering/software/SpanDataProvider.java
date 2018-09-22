package pl.com.engineering.software;

import pl.com.engineering.software.domain.Span;

import java.util.ArrayList;
import java.util.List;

public class SpanDataProvider {

    public static List<Span> provideData() {

        Span firstSpan = new Span();
        firstSpan.setFirstPylonHeight(320d);
        firstSpan.setFirstPylonDepth(110d);
        firstSpan.setSecondPylonHeight(430d);
        firstSpan.setSecondPylonDepth(170d);
        firstSpan.setNextPylonDistance(530d);
        firstSpan.setX1(210d);

        Span secondSpan = new Span();
        secondSpan.setFirstPylonHeight(430d);
        secondSpan.setFirstPylonDepth(170d);
        secondSpan.setSecondPylonHeight(610d);
        secondSpan.setSecondPylonDepth(190d);
        secondSpan.setNextPylonDistance(720d);
        secondSpan.setX1(210d);

        List<Span> list = new ArrayList<Span>();
        list.add(firstSpan);
        list.add(secondSpan);
        return list;
    }
}

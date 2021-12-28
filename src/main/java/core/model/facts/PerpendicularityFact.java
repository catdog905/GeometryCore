package core.model.facts;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import core.model.facts.objects.GeometryObject;
import core.model.facts.objects.LineSegment;

public class PerpendicularityFact extends Fact{
    private LinkedList<LineSegment> objects;

    public PerpendicularityFact(LinkedList<LineSegment> objects) {
        this.objects = objects;
    }

    public PerpendicularityFact(LineSegment a, LineSegment lineSegment) {
        this(new LinkedList<>(Arrays.asList(a, lineSegment)));
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(objects);
    }

    @Override
    public Fact createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new PerpendicularityFact(objects.stream().map(x -> (LineSegment) correspondence.get(x))
                .collect(Collectors.toCollection(LinkedList::new)));
    }
}
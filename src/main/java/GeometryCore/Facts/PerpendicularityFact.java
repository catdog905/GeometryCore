package GeometryCore.Facts;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import GeometryCore.GeometryObjects.GeometryObject;
import GeometryCore.GeometryObjects.LineSegment;

public class PerpendicularityFact extends Fact{
    private HashSet<LineSegment> objects;

    public PerpendicularityFact(HashSet<LineSegment> objects) {
        this.objects = objects;
    }

    public PerpendicularityFact(LineSegment a, LineSegment lineSegment) {
        this(new HashSet<>(Arrays.asList(a, lineSegment)));
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(objects);
    }

    @Override
    public Fact createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new PerpendicularityFact(objects);
    }
}

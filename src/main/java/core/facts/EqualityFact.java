package core.facts;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import core.objects.GeometryObject;

public class EqualityFact extends Fact {
    public GeometryObject left, right;

    public EqualityFact(GeometryObject left, GeometryObject right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(Arrays.asList(left, right));
    }

    @Override
    public Fact createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new EqualityFact(correspondence.get(left), correspondence.get(right));
    }
}

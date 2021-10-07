package core.facts;

import java.util.LinkedList;
import java.util.Map;

import core.objects.GeometryObject;

public class DebugEquivalenceFact extends Fact {
    @Override
    public LinkedList<? extends GeometryObject> getAllSubObjects() {
        return new LinkedList<>();
    }

    @Override
    public Fact createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return null;
    }
}

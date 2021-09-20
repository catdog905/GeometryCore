package core.facts;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import core.objects.GeometryObject;

public class ExistFact extends Fact{
    public GeometryObject object;

    public ExistFact(GeometryObject object) {
        this.object = object;
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(Arrays.asList(object));
    }

    @Override
    public Fact createNewSimilarCorrespondenceObject(Map<GeometryObject, GeometryObject> correspondence) {
        GeometryObject newObj = object.createNewSimilarCorrespondenceObject(correspondence);
        correspondence.put(object, newObj);
        return new ExistFact(newObj);
    }
}

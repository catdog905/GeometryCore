package GeometryCore.Facts;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import GeometryCore.GeometryObjects.GeometryObject;
import GeometryCore.GeometryObjects.Triangle;

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
    public Fact createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new ExistFact(new Triangle(((Triangle)object).lineSegments).createNewSimilarObject(correspondence)); //TODO: fix hack
    }
}

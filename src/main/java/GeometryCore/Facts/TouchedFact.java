package GeometryCore.Facts;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import GeometryCore.GeometryObjects.GeometryObject;
import GeometryCore.GeometryObjects.Vertex;

public class TouchedFact extends Fact{
    private HashSet<GeometryObject> objects;
    private Vertex touchPlace;

    public TouchedFact(HashSet<GeometryObject> objects, Vertex touchPlace) {
        this.objects = objects;
        this.touchPlace = touchPlace;
    }

    public TouchedFact(Vertex touchPlace, GeometryObject... objects) {
        this(new HashSet<>(Arrays.asList(objects)), touchPlace);
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        LinkedList<GeometryObject> list = new LinkedList<>(objects);
        list.add(touchPlace);
        return list;
    }

    @Override
    public Fact createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new TouchedFact(objects, touchPlace);
    }
}

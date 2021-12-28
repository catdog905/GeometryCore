package core.model.facts;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import core.model.facts.objects.GeometryObject;
import core.model.facts.objects.Vertex;

public class TouchedFact extends Fact{
    private LinkedList<GeometryObject> objects;
    private Vertex touchPlace;

    public TouchedFact(LinkedList<GeometryObject> objects, Vertex touchPlace) {
        this.objects = objects;
        this.touchPlace = touchPlace;
    }

    public TouchedFact(Vertex touchPlace, GeometryObject... objects) {
        this(new LinkedList<>(Arrays.asList(objects)), touchPlace);
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        LinkedList<GeometryObject> list = new LinkedList<>(objects);
        list.add(touchPlace);
        return list;
    }

    @Override
    public Fact createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        LinkedList<GeometryObject> list = new LinkedList<>();
        for (GeometryObject obj : objects)
            list.add(correspondence.get(obj));
        return new TouchedFact(list, (Vertex)correspondence.get(touchPlace));
    }
}
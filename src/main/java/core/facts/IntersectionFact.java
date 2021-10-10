package core.facts;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import core.objects.GeometryObject;
import core.objects.Vertex;

public class IntersectionFact extends Fact {
    private LinkedList<GeometryObject> intersectionObjects;
    private Vertex intersectVertex;

    public IntersectionFact(LinkedList<GeometryObject> intersectionObjects, Vertex intersectVertex) {
        this.intersectionObjects = intersectionObjects;
        this.intersectVertex = intersectVertex;
    }

    public IntersectionFact(Vertex intersectVertex, GeometryObject... intersectionObjects) {
        this(new LinkedList(Arrays.asList(intersectionObjects)), intersectVertex);
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(intersectionObjects);
    }

    @Override
    public Fact createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new IntersectionFact(intersectionObjects.stream().map(correspondence::get)
                .collect(Collectors.toCollection(LinkedList::new)),
                (Vertex) correspondence.get(intersectVertex));
    }
}

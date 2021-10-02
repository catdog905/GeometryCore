package core.objects;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import core.objects.expression.Monomial;
import core.objects.expression.MonomialEnveloper;

public class LineSegment extends GeometryObject {
    public HashSet<Vertex> vertexes = new HashSet<>();
    public LineSegment(HashSet<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public LineSegment(Vertex first, Vertex second) {
        this(new HashSet<>(Arrays.asList(first, second)));
    }

    public LineSegment() {
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        HashSet<Vertex> newObjects = new HashSet<>();
        for (Vertex obj : vertexes) {
            newObjects.add((Vertex) correspondence.get(obj));
        }
        return new LineSegment(newObjects);
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(vertexes);
    }

    @Override
    public Monomial getMonomial() {
        return Monomial.buildOf(this, LineSegment.class, MonomialEnveloper.class);
    }
}

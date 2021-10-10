package core.objects;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import core.objects.expression.Monomial;
import core.objects.expression.MonomialEnveloper;

public class Circle extends GeometryObject{
    private Vertex center;

    public Circle(Vertex center) {
        this.center = center;
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        if (center != null) {
            return new LinkedList<>(Arrays.asList(center));
        }
        return new LinkedList<>();
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new Circle((Vertex)correspondence.get(center));
    }

    @Override
    public Monomial getMonomial() {
        return Monomial.buildOf(this, Circle.class, MonomialEnveloper.class);
    }
}

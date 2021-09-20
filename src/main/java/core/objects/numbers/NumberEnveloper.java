package core.objects.numbers;

import java.util.LinkedList;
import java.util.Map;

import core.objects.GeometryObject;
import core.objects.Monomial;

public class NumberEnveloper extends Monomial {
    public Number number;

    protected NumberEnveloper(Number number) {
        super();
        this.number = number;
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>();
    }

    @Override
    public GeometryObject createNewSimilarCorrespondenceObject(Map<GeometryObject, GeometryObject> correspondence) {
        return this;
    }
}

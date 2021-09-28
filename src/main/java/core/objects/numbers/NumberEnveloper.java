package core.objects.numbers;

import java.util.LinkedList;
import java.util.Map;

import core.objects.GeometryObject;
import core.objects.expression.Monomial;

public class NumberEnveloper extends Monomial {
    public Number number;

    public NumberEnveloper(Number number) {
        super();
        this.number = number;
    }

    @Override
    public LinkedList<Monomial> getAllSubObjects() {
        return new LinkedList<>();
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new NumberEnveloper(number);
    }
}

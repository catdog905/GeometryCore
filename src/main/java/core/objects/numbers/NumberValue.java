package core.objects.numbers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import core.objects.GeometryObject;
import core.objects.Monomial;

public class NumberValue extends Monomial {
    private NumberEnveloper numberEnveloper;
    private GeometryObject object;

    public NumberValue(GeometryObject object, NumberEnveloper numberEnveloper) {
        super();
        this.numberEnveloper = numberEnveloper;
        this.object = object;
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        LinkedList<GeometryObject> temp = new LinkedList<>(Arrays.asList(object));
        if (numberEnveloper != null)
            temp.add(numberEnveloper);
        return temp;
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new NumberValue(correspondence.get(object), numberEnveloper);
    }
}
package GeometryCore.GeometryObjects;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class NumberValue extends Monomial {
    private NumberEnveloper numberEnveloper;
    private GeometryObject object;

    public NumberValue(GeometryObject object, NumberEnveloper numberEnveloper) {
        super();
        this.numberEnveloper = numberEnveloper;
        this.object = object;
    }

    public NumberValue(GeometryObject obj) {
        this(obj, null);
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        LinkedList<GeometryObject> temp = new LinkedList<>(Arrays.asList(object));
        if (numberEnveloper != null)
            temp.add(numberEnveloper);
        return temp;
    }

    @Override
    public NumberValue createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new NumberValue(correspondence.get(object), numberEnveloper);
    }

    @Override
    public boolean equals(Object o) {
        return object.equals(o);
    }
}

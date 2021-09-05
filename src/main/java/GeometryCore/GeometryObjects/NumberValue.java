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

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(Arrays.asList(numberEnveloper, object));
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new NumberValue(correspondence.get(object), numberEnveloper);
    }
}

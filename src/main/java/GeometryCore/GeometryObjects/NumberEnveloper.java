package GeometryCore.GeometryObjects;

import java.util.LinkedList;
import java.util.Map;

public class NumberEnveloper extends Monomial {
    public Number number;

    protected NumberEnveloper(Number number) {
        super();
        this.number = number;
    }

    @Override
    public LinkedList<NumberEnveloper> getAllSubObjects() {
        return new LinkedList<>();
    }

    @Override
    public NumberEnveloper createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new NumberEnveloper(number);
    }
}

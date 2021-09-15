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
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>();
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return this;
    }
}

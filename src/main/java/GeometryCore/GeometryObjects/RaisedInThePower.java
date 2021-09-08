package GeometryCore.GeometryObjects;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class RaisedInThePower extends Monomial {
    public Monomial power;

    public RaisedInThePower(LinkedList<Monomial> objects, Monomial power) {
        super(objects);
        this.power = power;
    }

    public RaisedInThePower(Monomial object, Monomial power) {
        super(new LinkedList<>(Arrays.asList(object)));
        this.power = power;
    }

    @Override
    public LinkedList<Monomial> getAllSubObjects() {
        return (LinkedList<Monomial>) super.getAllSubObjects();
    }

    @Override
    public RaisedInThePower createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new RaisedInThePower((LinkedList<Monomial>) super.getAllSubObjects(), (Monomial) correspondence.get(power));
    }
}

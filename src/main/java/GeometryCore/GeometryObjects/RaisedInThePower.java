package GeometryCore.GeometryObjects;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class RaisedInThePower extends Monomial {
    private Monomial power;

    public RaisedInThePower(LinkedList<Monomial> subObjects, Monomial power) {
        super(subObjects);
        this.power = power;
    }

    public RaisedInThePower(NumberValue value, GeometryNumber number) {
        this(new LinkedList<>(Arrays.asList(value)), number);
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        LinkedList<GeometryObject> newList = new LinkedList<>(super.getAllSubObjects());
        newList.add(power);
        return newList;
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        LinkedList<Monomial> newObjects = new LinkedList<>();
        for (GeometryObject obj : super.getAllSubObjects()) {
            newObjects.add((Monomial) correspondence.get(obj));
        }
        return new RaisedInThePower(newObjects, (Monomial) correspondence.get(power));
    }
}

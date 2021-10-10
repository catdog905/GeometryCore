package core.objects.expression;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import core.objects.GeometryObject;

public class RaisedInThePower extends Monomial implements Substitutable {
    private Monomial power;

    public RaisedInThePower(LinkedList<Monomial> subObjects, Monomial power) {
        super(subObjects);
        this.power = power;
    }

    public RaisedInThePower(Monomial value, Monomial power) {
        this(new LinkedList<>(Arrays.asList(value)), power);
    }

    @Override
    public LinkedList<Monomial> getAllSubObjects() {
        LinkedList<Monomial> newList = new LinkedList<>(super.getAllSubObjects());
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

    @Override
    public Monomial substitute(HashMap<Monomial, Monomial> substituteTable) {
        if (substituteTable.containsKey(this)) {
            return substituteTable.get(this);
        }
        LinkedList<? extends Monomial> subObjects = getAllSubObjects();
        int iterationsToDo = subObjects.size() - 1;
        var newSubObjects = new LinkedList<Monomial>();
        Monomial newPower = subObjects.getLast();
        if (newPower != null)
            newPower = newPower.substitute(substituteTable);
        for (var subObject : subObjects) {
            if (iterationsToDo == 0)
                break;
            newSubObjects.add(subObject.substitute(substituteTable));
            iterationsToDo--;

        }
        return new RaisedInThePower(newSubObjects, newPower);
    }
}
package core.objects.expression;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import core.objects.GeometryObject;

public class RaisedInThePower extends Monomial implements Substitutable {
    public Monomial power;
    public Monomial base;

    public RaisedInThePower(Monomial base, Monomial power) {
        super(base, power);
        this.power = power;
        this.base = base;
    }

    @Override
    public LinkedList<Monomial> getAllSubObjects() {
        LinkedList<Monomial> newList = new LinkedList<>(super.getAllSubObjects());
        return newList;
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new RaisedInThePower((Monomial) correspondence.get(base),
                (Monomial) correspondence.get(power));
    }

    @Override
    public Monomial substitute(HashMap<Monomial, Monomial> substituteTable) {
        return new RaisedInThePower(base.substitute(substituteTable),
                power.substitute(substituteTable));
    }
}
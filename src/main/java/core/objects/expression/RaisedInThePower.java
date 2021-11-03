package core.objects.expression;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import core.objects.GeometryObject;

public class RaisedInThePower extends Monomial{
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

    @Override
    public Monomial expandAllBrackets() {
        Monomial newBase = base.expandAllBrackets();
        Monomial newPower = power.expandAllBrackets();
        if (newBase.getAllSubObjects().size() == 0 || newBase instanceof Polynomial)
            return new RaisedInThePower(newBase, newPower);

        LinkedList<Monomial> subObjects = new LinkedList<>();
        for (Monomial subObj : newBase.getAllSubObjects())
            subObjects.add(new RaisedInThePower(subObj, newPower));
        return new Monomial(subObjects);
    }

    @Override
    public Monomial addSimilarTerms() {
        return new RaisedInThePower(base.addSimilarTerms(),power.addSimilarTerms());
    }
}
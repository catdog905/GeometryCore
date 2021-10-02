package core.objects.expression;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import core.objects.GeometryObject;

public class Polynomial extends Monomial{
    public Polynomial(LinkedList<Monomial> subObjects) {
        super(subObjects);
        if (subObjects.size() < 2){
            throw new RuntimeException("Illegal constructor for polynomial");
        }
    }

    public Polynomial(Monomial... subObjects) {
        this(new LinkedList<>(Arrays.asList(subObjects)));
    }

    @Override
    public LinkedList<Monomial> getAllSubObjects() {
        return new LinkedList<>(super.getAllSubObjects());
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        LinkedList<Monomial> newObjects = new LinkedList<>();
        for (GeometryObject obj : super.getAllSubObjects()) {
            newObjects.add((Monomial) correspondence.get(obj));
        }
        return new Polynomial(newObjects);
    }

    @Override
    public Monomial substitute(HashMap<Monomial, Monomial> substituteTable) {
        if (substituteTable.containsKey(this)) {
            return substituteTable.get(this);
        }
        LinkedList<Monomial> newPolynomialContents = new LinkedList<>();
        for (GeometryObject term : getAllSubObjects()) {
            Monomial monomialTerm = (Monomial) term;
            newPolynomialContents.add(monomialTerm.substitute(substituteTable));
        }
        return new Polynomial(newPolynomialContents);
    }
}
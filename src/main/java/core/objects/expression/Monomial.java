package core.objects.expression;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import core.objects.GeometryObject;

public class Monomial extends GeometryObject implements Substitutable {
    LinkedList<Monomial> subObjects;


    // Does NOT include values, only includes class names and parental relationships [the structure]
    // This function is made for unit testing purposes, it's not recommended to use it anywhere else
    public String getUniqueStructureString(){
        String structureString = "";
        structureString += getClass().toString();
        structureString += "[";
        for (GeometryObject term: getAllSubObjects()) {
            if (term instanceof Monomial)
                structureString += ((Monomial)term).getUniqueStructureString();
            else
                structureString += term.getClass().toString();
        }
        structureString += "]";
        return structureString;
    }

    public Monomial(LinkedList<Monomial> subObjects) {
        this.subObjects = subObjects;
    }

    public Monomial(Monomial... subObjects) {
        this.subObjects = new LinkedList<>(Arrays.asList(subObjects));
    }

    public Monomial() {
        subObjects = new LinkedList<>();
    }

    public Monomial(Monomial factor1, Monomial factor2) {
        subObjects = new LinkedList<>(Arrays.asList(factor1, factor2));
    }

    @Override
    public LinkedList<Monomial> getAllSubObjects() {
        return new LinkedList<>(subObjects);
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        LinkedList<Monomial> newObjects = new LinkedList<>();
        for (Monomial obj : subObjects) {
            newObjects.add((Monomial) correspondence.get(obj));
        }
        return new Monomial(newObjects);
    }

    @Override
    public Monomial substitute(HashMap<Monomial, Monomial> substituteTable) {
        if (substituteTable.containsKey(this)) {
            return substituteTable.get(this);
        }
        LinkedList<Monomial> newMonomialContents = new LinkedList<>();
        for (GeometryObject term : getAllSubObjects()) {
            Monomial monomialTerm = (Monomial) term;
            newMonomialContents.add(monomialTerm.substitute(substituteTable));
        }
        return new Monomial(newMonomialContents);
    }
}
package core.objects;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class Monomial extends GeometryObject {
    private LinkedList<Monomial> subObjects;


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
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(subObjects);
    }

    @Override
    public GeometryObject createNewSimilarCorrespondenceObject(Map<GeometryObject, GeometryObject> correspondence) {
        LinkedList<Monomial> newObjects = new LinkedList<>();
        for (Monomial obj : subObjects) {
            newObjects.add((Monomial) correspondence.get(obj));
        }
        return new Monomial(newObjects);
    }
}
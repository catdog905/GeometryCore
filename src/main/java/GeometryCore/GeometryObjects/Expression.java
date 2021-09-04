package GeometryCore.GeometryObjects;

import java.util.LinkedList;
import java.util.Map;

public class Expression extends GeometryObject{
    public LinkedList<Term> terms;

    public Expression(LinkedList<Term> terms) {
        this.terms = terms;
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(terms);
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        LinkedList<Term> newObjects = new LinkedList<>();
        for (Term obj : terms) {
            newObjects.add((Term) correspondence.get(obj));
        }
        return new Expression(newObjects);
    }
}

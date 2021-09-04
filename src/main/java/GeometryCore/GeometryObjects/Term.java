package GeometryCore.GeometryObjects;

import java.util.LinkedList;
import java.util.Map;

public class Term extends GeometryObject {
    LinkedList<Factor> factors;

    public Term(LinkedList<Factor> factors) {
        this.factors = factors;
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(factors);
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        LinkedList<Factor> newObjects = new LinkedList<>();
        for (Factor obj : factors) {
            newObjects.add((Factor) correspondence.get(obj));
        }
        return new Term(newObjects);
    }
}

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class EqualityFact extends GeometryFact {
    public HashSet<GeometryObject> geometryObjects;

    public EqualityFact(HashSet<GeometryObject> geometryObjects) {
        this.geometryObjects = geometryObjects;
    }

    @Override
    public boolean checkFact(GeometryFact geometryFact) {
        for (GeometryObject object: ((EqualityFact)geometryFact).geometryObjects) {
            if (!geometryObjects.contains(object) && !(object.isAnyObject()))
                return false;
        }
        return true;
    }

    @Override
    public void fillInUnknowns(GeometryFact ruleFact) {
        ((EqualityFact) ruleFact).geometryObjects = this.geometryObjects;
    }

    @Override
    public HashMap<GeometryObject, GeometryObject> getMatchingObjects(GeometryFact templateFact, GeometryFact modelFact) {
        HashMap<GeometryObject, GeometryObject> hashMap = new HashMap<>();
        Iterator<GeometryObject> itTemplate = ((EqualityFact)templateFact).geometryObjects.iterator();
        Iterator<GeometryObject> itModel = ((EqualityFact)templateFact).geometryObjects.iterator();
        while(itTemplate.hasNext()){
            System.out.println(itTemplate.next());
            hashMap.put(itTemplate.next(), itModel.next());
        }
        return hashMap;
    }

    @Override
    public LinkedList<GeometryObject> getSubObjects() {
        return new LinkedList<>(geometryObjects);
    }

    @Override
    public void putSubObjects(LinkedList<GeometryObject> subObjects) {
        geometryObjects.clear();
        for (GeometryObject obj: subObjects) {
            geometryObjects.add(obj);
        }
    }
}

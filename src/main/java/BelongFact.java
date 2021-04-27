import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class BelongFact extends GeometryFact {
    public GeometryObject object, subject;

    public BelongFact(GeometryObject object, GeometryObject subject) {
        this.object = object;
        this.subject = subject;
    }

    @Override
    public boolean checkFact(GeometryFact geometryFact) {
        return (((BelongFact)geometryFact).object == this.object || ((BelongFact)geometryFact).object.isAnyObject())
                && (((BelongFact)geometryFact).subject == this.subject || ((BelongFact)geometryFact).subject.isAnyObject());
    }

    @Override
    public void fillInUnknowns(GeometryFact ruleFact) {
        ((BelongFact) ruleFact).object = this.object;
        ((BelongFact) ruleFact).subject = this.subject;
    }

    @Override
    public HashMap<GeometryObject, GeometryObject> getMatchingObjects(GeometryFact templateFact, GeometryFact modelFact) {
        HashMap<GeometryObject, GeometryObject> hashMap = new HashMap<>();
        hashMap.put(((BelongFact)templateFact).object, ((BelongFact)modelFact).object);
        hashMap.put(((BelongFact)templateFact).subject, ((BelongFact)modelFact).subject);
        //hashMap.putAll();
        return hashMap;
    }

    @Override
    public LinkedList<GeometryObject> getSubObjects() {
        return new LinkedList<>(Arrays.asList(object, subject));
    }

    @Override
    public void putSubObjects(LinkedList<GeometryObject> subObjects) {
        object = subObjects.getFirst();
        subject = subObjects.getLast();
    }
}

package core.model.facts;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import core.model.facts.objects.GeometryObject;
/*
object \in subject
 */

public class BelongFact extends Fact {
    final public GeometryObject object, subject;

    public BelongFact(GeometryObject object, GeometryObject subject) {
        this.object = object;
        this.subject = subject;
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(Arrays.asList(object, subject));
    }

    @Override
    public Fact createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new BelongFact(correspondence.get(object),
                correspondence.get(subject));
    }
}

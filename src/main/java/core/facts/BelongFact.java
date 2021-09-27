package core.facts;
/*
object \in subject
 */


import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import core.objects.GeometryObject;

public class BelongFact extends Fact {
    public GeometryObject object, subject;

    public BelongFact(GeometryObject object, GeometryObject subject) {
        this.object = object;
        this.subject = subject;
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(Arrays.asList(object, subject));
    }

    @Override
    public Fact createNewSimilarCorrespondenceObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new BelongFact(correspondence.get(object),
                correspondence.get(subject));
    }
}

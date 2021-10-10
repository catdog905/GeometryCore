package core.facts;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import core.objects.GeometryObject;

public class InscribedFact extends Fact {
    private GeometryObject object, inscribedIn;

    public InscribedFact(GeometryObject object, GeometryObject inscribedIn) {
        this.object = object;
        this.inscribedIn = inscribedIn;
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(Arrays.asList(object, inscribedIn));
    }

    @Override
    public Fact createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new InscribedFact(correspondence.get(object), correspondence.get(inscribedIn));
    }
}

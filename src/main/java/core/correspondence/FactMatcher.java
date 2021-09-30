package core.correspondence;

import java.util.LinkedList;

import core.facts.Fact;
import core.objects.GeometryObject;
import core.objects.expression.GeometryNumber;

public class FactMatcher {
    private Fact ruleFact;
    private CorrespondenceWithoutNullElements correspondence;

    public FactMatcher(Fact ruleFact, CorrespondenceWithoutNullElements correspondence) {
        this.ruleFact = ruleFact;
        this.correspondence = correspondence;
    }

    public Boolean checkMatch(Fact curFact) {
        if (!curFact.getClass().equals(ruleFact.getClass()))
            return false;
        LinkedList<? extends GeometryObject> curFactSubObjects = curFact.getAllSubObjects();
        for (GeometryObject obj : ruleFact.getAllSubObjects()){
            GeometryObject match = null;
            if (obj instanceof GeometryNumber)
                if (curFactSubObjects.contains(obj))
                    continue;
                else
                    return false;
            if (correspondence.containsKey(obj)
                    && !curFactSubObjects.contains(correspondence.get(obj)))
                return false;
        }
        return true;
    }
}

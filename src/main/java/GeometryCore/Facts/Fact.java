package GeometryCore.Facts;
import GeometryCore.SubObjectsEditor;
import GeometryCore.GeometryObjects.GeometryObject;

public abstract class Fact implements SubObjectsEditor<GeometryObject, Fact> {
    @Override
    public boolean equals(Object o) {
        if (o == null )
            return false;

        //Same link
        if (this == o)
            return true;

        if (!o.getClass().equals(getClass()))
            return false;

        Fact fact = (Fact) o;
        var theirSubObjects = fact.getAllSubObjects();
        var ourSubObjects = getAllSubObjects();
        return theirSubObjects.size()==ourSubObjects.size()&&theirSubObjects.containsAll(ourSubObjects);
    }

}

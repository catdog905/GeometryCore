package GeometryCore.Facts;
import GeometryCore.SubObjectsEditor;
import GeometryCore.GeometryObjects.GeometryObject;

public abstract class Fact implements SubObjectsEditor<GeometryObject, Fact> {
    public boolean isTheSameFact(Fact fact){
     //   if (!fact.getClass().equals(getClass()))
     //       return false;
        return getAllSubObjects().containsAll(fact.getAllSubObjects());
    }

}

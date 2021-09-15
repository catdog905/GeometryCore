package GeometryCore.Facts;
import java.util.stream.Collectors;

import GeometryCore.SubObjectsEditor;
import GeometryCore.GeometryObjects.GeometryObject;

public abstract class Fact implements SubObjectsEditor<GeometryObject, Fact> {
    public boolean isTheSameFact(Fact fact) {
        return getClass() == fact.getClass() && getAllSubObjects().stream()
                    .map(x -> x.getAllSubBasicObjects())
                    .collect(Collectors.toList())
                .containsAll(fact.getAllSubObjects().stream()
                    .map(x -> x.getAllSubBasicObjects())
                    .collect(Collectors.toList()));
    }
}

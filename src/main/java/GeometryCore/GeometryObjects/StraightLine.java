package GeometryCore.GeometryObjects;

import java.util.LinkedList;
import java.util.Map;

public class StraightLine extends GeometryObject {
    public StraightLine() {
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new StraightLine();
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>();
    }
}

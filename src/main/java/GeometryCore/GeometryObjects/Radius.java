package GeometryCore.GeometryObjects;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class Radius extends GeometryObject {
    private Circle circle;

    public Radius(Circle circle) {
        this.circle = circle;
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        return new LinkedList<>(Arrays.asList(circle));
    }

    @Override
    public GeometryObject createNewSimilarCorrespondenceObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new Radius((Circle)correspondence.get(circle));
    }
}

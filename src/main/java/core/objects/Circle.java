package core.objects;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class Circle extends GeometryObject{
    private Vertex center;

    public Circle(Vertex center) {
        this.center = center;
    }

    @Override
    public LinkedList<GeometryObject> getAllSubObjects() {
        if (center != null) {
            return new LinkedList<>(Arrays.asList(center));
        }
        return new LinkedList<>();
    }

    @Override
    public GeometryObject createNewSimilarCorrespondenceObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new Circle((Vertex)correspondence.get(center));
    }
}

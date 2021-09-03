package GeometryCore;

import java.util.LinkedList;
import java.util.Map;

import GeometryCore.GeometryObjects.GeometryObject;

public interface SubObjectsEditor<T, E> {
    LinkedList<T> getAllSubObjects();
    E createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence);
}
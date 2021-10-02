package core;

import java.util.LinkedList;
import java.util.Map;

import core.objects.GeometryObject;

public interface SubObjectsEditor<T, E> {
    LinkedList<? extends T> getAllSubObjects();
    E createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence);
}
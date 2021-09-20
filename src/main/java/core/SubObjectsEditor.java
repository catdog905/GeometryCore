package core;

import java.util.LinkedList;
import java.util.Map;

import core.objects.GeometryObject;

public interface SubObjectsEditor<T, E> {
    LinkedList<T> getAllSubObjects();
    E createNewSimilarCorrespondenceObject(Map<GeometryObject, GeometryObject> correspondence);
}
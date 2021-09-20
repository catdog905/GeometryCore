package core;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import core.objects.GeometryObject;

public class CorrespondenceNotNullDecorator extends HashMap<GeometryObject, GeometryObject> {
    public CorrespondenceNotNullDecorator(Map<GeometryObject, GeometryObject> correspondence) {
        super(correspondence);
    }

    public CorrespondenceNotNullDecorator() {
        super();
    }

    public CorrespondenceNotNullDecorator makeFull() {
        HashSet<GeometryObject> allSubKeyObjects = new HashSet<>();
        allSubKeyObjects.addAll(keySet());
        for (GeometryObject obj : keySet()) {
            allSubKeyObjects.addAll(obj.getAllSubObjects());
        }
        LinkedList<GeometryObject> allSubKeyObjectList = new LinkedList<>(allSubKeyObjects).stream()
                .sorted(Comparator.comparingInt(x -> {
                    int counter = 0;
                    for (GeometryObject obj : keySet()) {
                        if (obj.getAllSubObjects().contains(x))
                            counter++;
                    }
                    return counter;
                })).collect(Collectors.toCollection(LinkedList::new));
        Collections.reverse(allSubKeyObjectList);
        CorrespondenceNotNullDecorator correspondence = new CorrespondenceNotNullDecorator();
        HashSet<GeometryObject> intersectionSet = new HashSet<>();
        for (GeometryObject obj : values()) {
            intersectionSet.addAll(obj.getAllSubObjects());
        }
        intersectionSet.addAll(values());
        if (allSubKeyObjectList.size() != intersectionSet.size())
            return null;
        for (GeometryObject obj : allSubKeyObjectList) {
            LinkedList<GeometryObject> intersectionList = new LinkedList<>(intersectionSet);
            for (Entry<GeometryObject, GeometryObject> entry : entrySet()) {
                if (entry.getKey().getAllSubObjects().contains(obj)) {
                    for (int i = 0; i < intersectionList.size(); i++) {
                        if (!entry.getValue().getAllSubObjects().contains(intersectionList.get(i))) {
                            intersectionList.remove(i);
                            i--;
                        }
                    }
                }
            }
            for (int i = 0; i < intersectionList.size(); i++) {
                if (intersectionList.get(i).getClass() == obj.getClass()
                    && !correspondence.containsValue(intersectionList.get(i))) {
                    correspondence.put(obj, intersectionList.get(i));
                    break;
                }

            }

        }
        return correspondence;
    }

    private HashMap<GeometryObject, GeometryObject> getAllCorrespondenceOfSubObjects(GeometryObject obj1, GeometryObject obj2){
        if (obj1.getAllSubObjects().size() == 0) {
            HashMap<GeometryObject, GeometryObject> map = new HashMap<>();
            map.put(obj1, obj2);
            return map;
        }
        HashMap<GeometryObject, GeometryObject> temp = new HashMap<>();
        for (int i = 0; i < obj1.getAllSubObjects().size(); i++) {
            HashMap<GeometryObject, GeometryObject> map = getAllCorrespondenceOfSubObjects(
                    obj1.getAllSubObjects().get(i), obj2.getAllSubObjects().get(i));
            for (Entry<GeometryObject, GeometryObject> entry : map.entrySet()){
                temp.put(entry.getKey(), entry.getValue());
            }
        }
        temp.put(obj1, obj2);
        return temp;
    }

    private LinkedList<CorrespondencePair> makeFull(GeometryObject key, GeometryObject value) {
        LinkedList<CorrespondencePair> temp = new LinkedList<>();
        temp.add(new CorrespondencePair(key, value));
        for (int i = 0; i < key.getAllSubObjects().size(); i++) {
            for (int j = 0; j < value.getAllSubObjects().size(); j++)
                temp.addAll(makeFull(key.getAllSubObjects().get(i), value.getAllSubObjects().get(j)));
        }
        return temp;
    }

    @Override
    public GeometryObject get(Object key) {
        GeometryObject result = super.get(key);
        if (result == null) {
            GeometryObject curObj = ((GeometryObject)key).createNewSimilarCorrespondenceObject(this);
            super.put((GeometryObject) key, curObj);
            return curObj;
        }
        return result;
    }

    public class CorrespondencePair {
        public GeometryObject key, value;

        public CorrespondencePair(GeometryObject key, GeometryObject value) {
            this.key = key;
            this.value = value;
        }
    }
}
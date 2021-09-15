package GeometryCore;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import GeometryCore.GeometryObjects.CorrespondencePair;
import GeometryCore.GeometryObjects.GeometryObject;

public class CorrespondenceNotNullDecorator extends HashMap<GeometryObject, GeometryObject> {
    public CorrespondenceNotNullDecorator(Map<GeometryObject, GeometryObject> correspondence) {
        super(correspondence);
    }

    public CorrespondenceNotNullDecorator() {
        super();
    }

    public CorrespondenceNotNullDecorator makeFull() {
        HashSet<GeometryObject> allSubKeyObjects = new HashSet<>();
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
        for (Entry<GeometryObject, GeometryObject> entry : entrySet()){
            correspondence.put(entry.getKey(), entry.getValue());
        }
        HashSet<GeometryObject> intersectionSet = new HashSet<>();
        for (GeometryObject obj : values()) {
            intersectionSet.addAll(obj.getAllSubObjects());
        }
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
                if (!correspondence.containsValue(intersectionList.get(i))) {
                    correspondence.put(obj, intersectionList.get(i));
                    break;
                }

            }

        }
        return correspondence;

        //LinkedList<CorrespondencePair> allCorrespondence = new LinkedList<>();
        //for(Entry<GeometryObject, GeometryObject> elem : super.entrySet()) {
        //    allCorrespondence.addAll(makeFull(elem.getKey(), elem.getValue()));
        //}
        //HashMap<GeometryObject, LinkedList<GeometryObject>> fullCorrespondence = new HashMap<>();
        //for (CorrespondencePair pair : allCorrespondence) {
        //    LinkedList<GeometryObject> existElement = fullCorrespondence.get(pair.key);
        //    if (existElement == null) {
        //        LinkedList<GeometryObject> tempList = new LinkedList<>();
        //        tempList.add(pair.value);
        //        fullCorrespondence.put(pair.key, tempList);
        //    } else {
        //        if (!existElement.contains(pair.value))
        //            existElement.add(pair.value);
        //    }
        //}
        //LinkedList<Entry<GeometryObject, LinkedList<GeometryObject>>> sortedFullCorr =
        //    fullCorrespondence.entrySet().stream().sorted(
        //            Comparator.comparingInt(x -> x.getKey().getAllSubObjects().size()))
        //            .collect(Collectors.toCollection(LinkedList::new));
        ////Collections.reverse(sortedFullCorr);
        //CorrespondenceNotNullDecorator correspondence = new CorrespondenceNotNullDecorator();
        //for (int i = 0; i < sortedFullCorr.size(); i++) {
        //    HashMap<GeometryObject, GeometryObject> curCorrespondence = getAllCorrespondenceOfSubObjects(
        //            sortedFullCorr.get(i).getKey(), sortedFullCorr.get(i).getValue().get(0));
//
        //    for (int j = i + 1; j < sortedFullCorr.size(); j++) {
        //        if (curCorrespondence.containsKey(sortedFullCorr.get(j).getKey())) {
        //            sortedFullCorr.remove(j);
        //            j--;
        //            continue;
        //        }
        //        for (GeometryObject value : curCorrespondence.values())
        //            sortedFullCorr.get(j).getValue().remove(value);
        //        if (sortedFullCorr.get(j).getValue().size() == 0)
        //            return null;
        //    }
        //    for (Entry<GeometryObject, GeometryObject> entry : curCorrespondence.entrySet()){
        //        if (!correspondence.containsKey(entry.getKey()))
        //            correspondence.put(entry.getKey(), entry.getValue());
        //    }
        //}
        //return correspondence;
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
            //GeometryObject curObj = ((GeometryObject)(((GeometryObject) key).clone())).createNewSimilarObject(this);
            GeometryObject curObj = ((GeometryObject)key).createNewSimilarObject(this);
            super.put((GeometryObject) key, curObj);
            return curObj;
        }
        return result;
    }


}
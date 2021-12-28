package core.correspondence;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import core.model.facts.objects.GeometryObject;

public class FullCorrespondence extends HashMap<GeometryObject, GeometryObject> {

    public FullCorrespondence(Map<? extends GeometryObject, ? extends GeometryObject> m) {
        super(makeFull(m));
    }

    public static Map<GeometryObject, GeometryObject> makeFull(Map<? extends GeometryObject, ? extends GeometryObject> m) {
        LinkedList<Pair> allCorrespondence = new LinkedList<>();
        for(Entry<? extends GeometryObject, ? extends GeometryObject> elem : m.entrySet()) {
            allCorrespondence.addAll(makeFull(elem.getKey(), elem.getValue()));
        }
        HashMap<GeometryObject, LinkedList<GeometryObject>> fullCorrespondence = new HashMap<>();
        for (Pair pair : allCorrespondence) {
            if (!fullCorrespondence.containsKey(pair.key)) {
                LinkedList<GeometryObject> tempList = new LinkedList<>();
                tempList.add(pair.value);
                fullCorrespondence.put(pair.key, tempList);
            } else {
                fullCorrespondence.get(pair.key).add(pair.value);
            }
        }
        LinkedList<Entry<GeometryObject, LinkedList<GeometryObject>>> sortedFullCorr =
                fullCorrespondence.entrySet().stream().sorted(Comparator.comparingInt(x -> x.getValue().size()))
                        .collect(Collectors.toCollection(LinkedList::new));
        Collections.reverse(sortedFullCorr);
        CorrespondenceWithoutNullElements correspondence = new CorrespondenceWithoutNullElements();
        for (int i = 0; i < sortedFullCorr.size(); i++) {
            HashMap<GeometryObject, Integer> count = new HashMap<>();
            for (GeometryObject predict : sortedFullCorr.get(i).getValue()) {
                if (count.containsKey(predict))
                    count.put(predict, count.get(predict) + 1);
                else
                    count.put(predict, 1);
            }
            GeometryObject correspondenceObj = count.entrySet().stream()
                        .max(Comparator.comparing(x -> x.getValue())).get().getKey();
            for (int j = i + 1; j < sortedFullCorr.size(); j++)
                sortedFullCorr.get(j).getValue().remove(correspondenceObj);
            correspondence.put(sortedFullCorr.get(i).getKey(), correspondenceObj);
        }
        return correspondence;
    }

    private static LinkedList<Pair> makeFull(GeometryObject key, GeometryObject value) {
        LinkedList<Pair> temp = new LinkedList<>();
        temp.add(new Pair(key, value));
        for (int i = 0; i < key.getAllSubObjects().size(); i++) {
            for (int j = 0; j < value.getAllSubObjects().size(); j++)
                temp.addAll(makeFull(key.getAllSubObjects().get(i), value.getAllSubObjects().get(j)));
        }
        return temp;
    }
}

package core.correspondence;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import core.objects.GeometryObject;
import core.objects.Vertex;

public class CorrespondenceNotNullDecorator extends HashMap<GeometryObject, GeometryObject> {
    public CorrespondenceNotNullDecorator(Map<GeometryObject, GeometryObject> correspondence) {
        super(correspondence);
    }

    public CorrespondenceNotNullDecorator() {
        super();
    }

    public CorrespondenceNotNullDecorator makeFull() {
        LinkedList<CorrespondencePair> allCorrespondence = new LinkedList<>();
        for(Entry<GeometryObject, GeometryObject> elem : super.entrySet()) {
            allCorrespondence.addAll(makeFull(elem.getKey(), elem.getValue()));
        }
        HashMap<GeometryObject, LinkedList<GeometryObject>> fullCorrespondence = new HashMap<>();
        for (CorrespondencePair pair : allCorrespondence) {
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
        CorrespondenceNotNullDecorator correspondence = new CorrespondenceNotNullDecorator();
        for (int i = 0; i < sortedFullCorr.size(); i++) {
            HashMap<GeometryObject, Integer> count = new HashMap<>();
            for (GeometryObject predict : sortedFullCorr.get(i).getValue()) {
                if (count.containsKey(predict))
                    count.put(predict, count.get(predict) + 1);
                else
                    count.put(predict, 1);
            }
            GeometryObject correspondenceObj = new Vertex();
            try {
                correspondenceObj = count.entrySet().stream()
                        .max(Comparator.comparing(x -> x.getValue())).get().getKey();
            }catch(Exception e)
            {
                return null;
            }
            for (int j = i + 1; j < sortedFullCorr.size(); j++)
                sortedFullCorr.get(j).getValue().remove(correspondenceObj);
            correspondence.put(sortedFullCorr.get(i).getKey(), correspondenceObj);
        }
        return correspondence;
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
            try {
                return ((GeometryObject)(((GeometryObject) key).clone())).createNewSimilarObject(this);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
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
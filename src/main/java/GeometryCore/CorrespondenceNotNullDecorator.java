package GeometryCore;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import GeometryCore.GeometryObjects.GeometryObject;

public class CorrespondenceNotNullDecorator extends HashMap<GeometryObject, GeometryObject> {
    public CorrespondenceNotNullDecorator(Map<GeometryObject, GeometryObject> correspondence) {
        super(correspondence);
    }

    public CorrespondenceNotNullDecorator() {
        super();
    }

    public CorrespondenceNotNullDecorator makeFull() {
        return makeFull(new LinkedList<>(super.keySet()),
                new LinkedList<>(super.values()));
    }

    private CorrespondenceNotNullDecorator makeFull(LinkedList<GeometryObject> keys,
                                                    LinkedList<GeometryObject> values) {
        CorrespondenceNotNullDecorator temp = new CorrespondenceNotNullDecorator();
        for (int i = 0; i < keys.size(); i++) {
            temp.put(keys.get(i), values.get(i));
            temp.putAll(makeFull(keys.get(i).getAllSubObjects(), values.get(i).getAllSubObjects()));
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


}
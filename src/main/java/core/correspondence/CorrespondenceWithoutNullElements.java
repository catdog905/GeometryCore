package core.correspondence;

import java.util.HashMap;
import java.util.Map;

import core.model.facts.objects.GeometryObject;

public class CorrespondenceWithoutNullElements extends HashMap<GeometryObject, GeometryObject> {
    public CorrespondenceWithoutNullElements(Map<GeometryObject, GeometryObject> correspondence) {
        super(correspondence);
    }

    public CorrespondenceWithoutNullElements() {
        super();
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
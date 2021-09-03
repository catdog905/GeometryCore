package GeometryCore;

import java.util.HashMap;
import java.util.Map;

import GeometryCore.GeometryObjects.GeometryObject;

public class CorrespondenceDecorator extends HashMap<GeometryObject, GeometryObject> {
    public CorrespondenceDecorator(Map<GeometryObject, GeometryObject> correspondence) {
        super(correspondence);
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
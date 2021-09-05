package GeometryCore.GeometryObjects;

import java.util.LinkedList;

import GeometryCore.SubObjectsEditor;

public abstract class GeometryObject implements SubObjectsEditor<GeometryObject, GeometryObject>, Cloneable {
    @Override
    public boolean equals(Object o) {

        //Same link
        if (this == o)
            return true;

        if (!(o.getClass().equals(this.getClass())))
            return false;

        LinkedList<GeometryObject> ourSubObjects = this.getAllSubObjects();
        LinkedList<GeometryObject> theirSubObjects=((GeometryObject)o).getAllSubObjects();


        if (ourSubObjects.size() == 0 || ourSubObjects.size() != theirSubObjects.size()){
            return false;
        }
        return theirSubObjects.containsAll(ourSubObjects);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

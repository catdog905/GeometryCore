package GeometryCore.GeometryObjects;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import GeometryCore.SubObjectsEditor;

public abstract class GeometryObject implements SubObjectsEditor<GeometryObject, GeometryObject>, Cloneable {
    public boolean isEquivalentTo(GeometryObject object){
        if (!getClass().equals(object.getClass()))
            return false;
        LinkedList<GeometryObject> ourSubObjects = getAllSubObjects(), theirSubObjects = object.getAllSubObjects();
        if (ourSubObjects.size() != theirSubObjects.size())
            return false;
        for (Iterator<GeometryObject> ourObjectIterator = ourSubObjects.iterator(), theirObjectIterator = theirSubObjects.iterator(); theirObjectIterator.hasNext(); ) {
            var ourSubject = ourObjectIterator.next();
            var theirSubject = theirObjectIterator.next();
            if (!ourSubject.isEquivalentTo(theirSubject))
                return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null)
            return false;

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

    public LinkedList<GeometryObject> getAllSubBasicObjects() {
        if (getAllSubObjects().size() == 0)
            return new LinkedList<>(Arrays.asList(this));
        LinkedList<GeometryObject> list = new LinkedList<>();
        for (GeometryObject curObj : getAllSubObjects())
            list.addAll(curObj.getAllSubBasicObjects());
        return list;
    }

    public LinkedList<GeometryObject> getAllSubObjectsInTree() {
        if (getAllSubObjects().size() == 0)
            return new LinkedList<>(Arrays.asList(this));
        LinkedList<GeometryObject> list = new LinkedList<>();
        for (GeometryObject curObj : getAllSubObjects())
            list.addAll(curObj.getAllSubBasicObjects());
        list.add(this);
        return list;
    }
}

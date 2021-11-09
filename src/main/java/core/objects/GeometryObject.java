package core.objects;

import java.util.Iterator;
import java.util.LinkedList;

import core.SubObjectsEditor;

public abstract class GeometryObject implements SubObjectsEditor<GeometryObject, GeometryObject>,
        AbleToBeMonomial, Cloneable {
    public boolean isEquivalentTo(GeometryObject object){
        if (!getClass().equals(object.getClass()))
            return false;
        LinkedList<? extends GeometryObject> ourSubObjects = getAllSubObjects(),
                theirSubObjects = object.getAllSubObjects();
        if (ourSubObjects.size() != theirSubObjects.size())
            return false;
        for (Iterator<? extends GeometryObject> ourObjectIterator = ourSubObjects.iterator(),
             theirObjectIterator = theirSubObjects.iterator(); theirObjectIterator.hasNext(); ) {
            var ourSubject = ourObjectIterator.next();
            var theirSubject = theirObjectIterator.next();
            try {
                if (!ourSubject.isEquivalentTo(theirSubject))
                    return false;
            } catch(Exception e) {
                e.printStackTrace();
            }
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

        LinkedList<? extends GeometryObject> ourSubObjects = this.getAllSubObjects();/*.stream()
                .filter(x -> !(x == GeometryNumber.get(1)))
                .collect(Collectors.toCollection(LinkedList::new));*/
        LinkedList<? extends GeometryObject> theirSubObjects=((GeometryObject)o).getAllSubObjects();/*
                .stream().filter(x -> !(x == GeometryNumber.get(1)))
                .collect(Collectors.toCollection(LinkedList::new));*/


        if (ourSubObjects.size() == 0 || ourSubObjects.size() != theirSubObjects.size()){
            return false;
        }
        return theirSubObjects.containsAll(ourSubObjects);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    //For Debug
    private String name;
    public GeometryObject setDebugName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        if (name != null)
            return name;
        return super.toString();
    }

    @Override
    public int hashCode() {
        if (getAllSubObjects().size() == 0)
            return super.hashCode();
        return getAllSubObjects().stream().map(GeometryObject::hashCode)
                .reduce(0, Integer::sum);
    }
}

package core.model.facts.objects;

import java.util.LinkedList;
import java.util.Map;

import core.model.facts.objects.expression.AbleToBeMonomial;

public abstract class GeometryObject implements AbleToBeMonomial, Cloneable {

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

    public abstract LinkedList<? extends GeometryObject> getAllSubObjects();
    public abstract GeometryObject createNewSimilarObject(
            Map<GeometryObject, GeometryObject> correspondence);
}

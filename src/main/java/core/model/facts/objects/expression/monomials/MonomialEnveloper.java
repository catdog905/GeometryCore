package core.model.facts.objects.expression.monomials;

import java.util.LinkedList;
import java.util.Map;

import core.model.facts.objects.GeometryObject;

public class MonomialEnveloper<T> extends Monomial {
    private T toEnvelop;

    MonomialEnveloper(T toEnvelop) {
        this.toEnvelop = toEnvelop;
    }

    static <T> MonomialEnveloper returnNewObject(T obj){
        return new MonomialEnveloper(obj);
    }

    @Override
    public LinkedList<Monomial> getAllSubObjects() {
        return new LinkedList<>();
    }

    @Override
    public GeometryObject createNewSimilarObject(Map<GeometryObject, GeometryObject> correspondence) {
        return new MonomialEnveloper(toEnvelop);
    }

    public T toEnvelop() {
        return toEnvelop;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }
}

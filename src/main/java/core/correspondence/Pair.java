package core.correspondence;

import core.model.facts.objects.GeometryObject;

public class Pair {
    public GeometryObject key, value;

    public Pair(GeometryObject key, GeometryObject value) {
        this.key = key;
        this.value = value;
    }
}

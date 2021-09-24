package GeometryCore;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class CollectionComparator {
    public <V,U extends Collection<V>> boolean compare(BiFunction<V,V,Boolean> comparator, U collection1, U collection2)
    {
        return compare(comparator,collection1,collection2,(a)->false);
    }
    public <V,U extends Collection<V>> boolean compare(BiFunction<V,V,Boolean> comparator, U collection1, U collection2 , Function<V,Boolean> toSkip){return true;}
}

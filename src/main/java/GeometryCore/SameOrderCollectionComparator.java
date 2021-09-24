package GeometryCore;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SameOrderCollectionComparator extends CollectionComparator{
    @Override
    public <V,U extends Collection<V>> boolean compare(BiFunction<V,V,Boolean> comparator, U collection1, U collection2, Function<V,Boolean> toSkip ){
        if (collection1.size()!=collection2.size())
            return false;
        var it1 = collection1.iterator();
        var it2 = collection2.iterator();
        while (it1.hasNext()){
            V el1 = it1.next(),el2 = it2.next();
            if (toSkip.apply(el1) || toSkip.apply(el2))
                continue;
            if (!comparator.apply(el1,el2))
                return false;
        }
        return true;
    }
}

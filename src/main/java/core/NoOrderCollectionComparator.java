package core;

import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.function.BiFunction;
import java.util.function.Function;

public class NoOrderCollectionComparator extends CollectionComparator {
    @Override
    public <V,U extends Collection<V>> boolean compare(BiFunction<V,V,Boolean> comparator, U collection1, U collection2, Function<V,Boolean> toSkip ){
        if (collection1.size()!=collection2.size())
            return false;
        LinkedList<V> collection2WithRemovals = new LinkedList<>(collection2);
        for (V el1:collection1) {
            if (toSkip.apply(el1))
                continue;
            ListIterator<V> it2 = collection2WithRemovals.listIterator();
            boolean equivalenceFound = false;
            while (it2.hasNext()){
                V el2 = it2.next();
                if (toSkip.apply(el2))
                    continue;
                if (comparator.apply(el1,el2)){
                    equivalenceFound = true;
                    break;
                }
            }
            if (!equivalenceFound)
                return false;
            it2.remove();
        }
        return true;
    }
}

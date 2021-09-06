package GeometryCore;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import GeometryCore.Facts.Fact;
import GeometryCore.GeometryObjects.GeometryObject;

public class FactOptimizer {
    public static void deleteRepeatingFacts(Model model){
        HashSet<Fact> newFacts = new HashSet<>(model.facts);
        for (Fact fact: model.facts) {
            if (!newFacts.contains(fact))
                continue;

            List<Fact> allSimilarFacts =  newFacts.stream().filter
                    (x -> x!=fact&&
                    x.getClass().equals(fact.getClass())&&
                    fact.isTheSameFact(x))
                        .collect(Collectors.toList());

            newFacts.removeAll(allSimilarFacts);
        }
        model.facts=newFacts;
    }
}

package GeometryCore;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import GeometryCore.Facts.Fact;

public class FactOptimizer {
    public static void deleteRepeatingFacts(Model model){
        HashSet<Fact> newFacts = new HashSet<>(model.getFacts());
        for (Fact fact: model.getFacts()) {
            if (!newFacts.contains(fact))
                continue;

            List<Fact> allSimilarFacts =  newFacts.stream().filter
                    (x -> x!=fact&&
                    x.equals(fact))
                        .collect(Collectors.toList());

            newFacts.removeAll(allSimilarFacts);
        }
        model.setFacts(newFacts);
    }
}

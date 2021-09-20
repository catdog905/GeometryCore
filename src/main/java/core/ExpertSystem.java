package core;

import core.rules.storage.RuleStorage;

public class ExpertSystem {
    public static void ForwardPass(Model model) {
        int lastFactsAmount = 0;
        while (lastFactsAmount != model.getFacts().size()) {
            lastFactsAmount = model.getFacts().size();
            for (Rule rule : RuleStorage.getInstance().rules) {
                rule.applyToModel(model);
                FactOptimizer.deleteRepeatingFacts(model);
            }
        }
    }
}
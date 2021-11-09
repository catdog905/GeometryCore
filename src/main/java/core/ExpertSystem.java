package core;

import core.model.Model;
import core.rule.Rule;
import core.rules.storage.RuleStorage;

public class ExpertSystem {
    public static Model ForwardPass(Model model) {
        int lastFactsAmount = 0;
        while (lastFactsAmount != model.facts().size()) {
            lastFactsAmount = model.facts().size();
            for (Rule rule : RuleStorage.getInstance().rules) {
                model = rule.applyToModel(model);
                model.deleteRepeatingFacts();
            }
        }
        return model;
    }
}
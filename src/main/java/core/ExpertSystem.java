package core;

import core.model.Model;
import core.rules.storage.RuleStorage;

public class ExpertSystem {
    public static Model ForwardPass(Model model) {
        Model curModel = new Model(model);
        //int lastFactsAmount = 0;
        //while (lastFactsAmount != model.facts.size()) {
            for (Rule rule : RuleStorage.getInstance().rules) {
                rule.applyToModel(model);
                curModel = model.deleteRepeatingFacts();
            }
        //}
        return curModel;
    }
}
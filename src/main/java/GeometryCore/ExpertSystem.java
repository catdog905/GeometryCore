package GeometryCore;

public class ExpertSystem {
    public static void ForwardPass(Model model) {
        int lastFactsAmount = 0;
        while (lastFactsAmount != model.facts.size()) {
            lastFactsAmount = model.facts.size();
            for (Rule rule : RuleStorage.getInstance().rules) {
                rule.applyToModel(model);
                FactOptimizer.deleteRepeatingFacts(model);
            }
        }
    }
}
package core.objects.benchmark;

import java.util.HashMap;

public interface Benchmarkable {
    default void startExecution(String criterion_string){
        var criterion = getTheCriterion(criterion_string);
        criterion.startTimer();
    }
    default BenchmarkCriterion getTheCriterion(String criterion_string){
        if (!benchmarkCriterions.containsKey(criterion_string)){
            benchmarkCriterions.put(criterion_string, new BenchmarkCriterion());
        }
        return benchmarkCriterions.get(criterion_string);
    }
    HashMap<String, BenchmarkCriterion> benchmarkCriterions = new HashMap<>();
    default void resetBenchmark(){
        benchmarkCriterions.clear();
    }
    default void endExecution(String criterion_string){
        var criterion = getTheCriterion(criterion_string);
        criterion.endTimer();

    }

    default void increaseCriterion(String criterion_string){
        increaseCriterion(criterion_string,1);

    }
    default void decreaseCriterion(String criterion_string){
        decreaseCriterion(criterion_string,1);
    }
    default void increaseCriterion(String criterion_string,int times){
        var criterion = getTheCriterion(criterion_string);
        criterion.increase(times );

    }
    default void decreaseCriterion(String criterion_string,int times){
        var criterion = getTheCriterion(criterion_string);
        criterion.decrease(times);
    }
    public default void outputBenchmarks(){
        for (String bench:benchmarkCriterions.keySet()) {
            System.out.println(bench + ": "+ benchmarkCriterions.get(bench).output());
        }
    }
}

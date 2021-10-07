package core.objects.benchmark;

import java.util.Date;
import java.util.LinkedList;

public class BenchmarkCriterion {
    public void startTimer(){
        curTimer = new Date().getTime();
    }
    public void endTimer(){
        timesTimer++;
        curTimer =new Date().getTime()-curTimer;
        timerSum+=curTimer;
        timers.add(curTimer);
    }
    int value = 0;
    long curTimer = 0;
    public void increase(){
        value++;
    }
    public void increase(int add){
        value+=add;
    }
    public void decrease(){
        value--;
    }
    public void decrease(int sub){
        value-=sub;
    }
    LinkedList<Long> timers =  new LinkedList<>();
    long timerSum=0;
    int timesTimer = 0;
    public String output(){
        String ans;
        if (timesTimer != 0){
            ans = "Average - " + Long.toString(timerSum/timesTimer) + "ms;\n\ttimes - " + Integer.toString(timesTimer) + ';';
        }
        else{
            ans = Integer.toString(value) + ';';
        }



        return ans;
    }
}

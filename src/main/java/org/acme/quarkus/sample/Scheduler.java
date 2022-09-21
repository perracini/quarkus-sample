package org.acme.quarkus.sample;

import java.util.concurrent.atomic.AtomicInteger;
import javax.enterprise.context.ApplicationScoped;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;

@ApplicationScoped              
public class Scheduler {

    private AtomicInteger counter = new AtomicInteger();

    public int get() {  
        return counter.get();
    }

    @Scheduled(every="25s")     
    void increment() {
        counter.incrementAndGet(); 
        System.out.println("25 seconds");
    }

    @Scheduled(cron="0 15 10 * * ?") 
    void cronJob(ScheduledExecution execution) {
        counter.incrementAndGet();
        System.out.println(execution.getScheduledFireTime());
    }

    @Scheduled(cron = "{cron.expr}") 
    void cronJobWithExpressionInConfig() {
       counter.incrementAndGet();
       System.out.println("Cron expression configured in application.properties");
    }
}

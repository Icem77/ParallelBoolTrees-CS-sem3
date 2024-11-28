package cp2024.solution.tasks;

import java.util.concurrent.ExecutorService;

public abstract class PrioritezedTask implements Runnable {
    protected ExecutorService executor;

    public PrioritezedTask(ExecutorService executor) {
        this.executor = executor;
    }
}

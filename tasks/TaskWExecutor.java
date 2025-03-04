package cp2024.solution.tasks;

import java.util.concurrent.ExecutorService;

public abstract class TaskWExecutor implements Runnable {
    protected ExecutorService executor;

    public TaskWExecutor(ExecutorService executor) {
        this.executor = executor;
    }
}

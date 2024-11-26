package cp2024.solution.tasks;

import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class PrioritezedTask implements Runnable {
    protected Integer depth;
    protected ThreadPoolExecutor executor;

    public PrioritezedTask(ThreadPoolExecutor executor, Integer depth) {
        this.executor = executor;
        this.depth = depth;
    }

    public Integer getDepth() {
        return depth;
    }
}

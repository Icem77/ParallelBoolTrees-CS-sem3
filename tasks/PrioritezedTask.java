package cp2024.solution.tasks;

import java.util.concurrent.ThreadPoolExecutor;

public abstract class PrioritezedTask implements Runnable, Comparable<PrioritezedTask>{
    protected Integer depth;
    protected ThreadPoolExecutor executor;

    public PrioritezedTask(ThreadPoolExecutor executor, Integer depth) {
        this.depth = depth;
    }

    @Override
    public int compareTo(PrioritezedTask arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    public Integer getDepth() {
        return depth;
    }
}

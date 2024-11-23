package cp2024.solution.tasks;

import java.util.concurrent.ThreadPoolExecutor;

public abstract class PrioritezedTask implements Runnable, Comparable<PrioritezedTask> {
    protected Integer depth;
    protected ThreadPoolExecutor executor;

    public PrioritezedTask(ThreadPoolExecutor executor, Integer depth) {
        this.depth = depth;
    }

    @Override
    public int compareTo(PrioritezedTask task) {
        if (this.depth == task.getDepth()) {
            if (this instanceof SqueezeLeaf) {
                return 1;
            }

            if (this instanceof CancelDown) {
                if (task instanceof SqueezeLeaf) {
                    return -1;
                } else {
                    return 1;
                }
            }

            if (this instanceof PushToParent) {
                if (task instanceof CancelDown || task instanceof SqueezeLeaf) {
                    return -1;
                } else {
                    return 1;
                }
            }

            return -1;
        } else {
            return (-1) * Integer.compare(this.depth, task.depth);
        }
    }

    public Integer getDepth() {
        return depth;
    }
}

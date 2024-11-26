package cp2024.solution.tasks;

import java.util.concurrent.ThreadPoolExecutor;

import cp2024.solution.nodes.Node;

public class CancelDown extends PrioritezedTask {
    private Node nodeToCancel;

    public CancelDown(ThreadPoolExecutor executor, Node nodeToCancel, Integer depth) {
        super(executor, depth);
        this.nodeToCancel = nodeToCancel;
    }

    @Override
    public void run() {
        this.nodeToCancel.lock.lock();
        if (this.nodeToCancel.isCanceled() == false) {
            this.nodeToCancel.cancel(executor, depth);
        }
        this.nodeToCancel.lock.unlock();
    }
}

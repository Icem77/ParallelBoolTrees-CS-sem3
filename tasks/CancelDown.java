package cp2024.solution.tasks;

import java.util.concurrent.ExecutorService;

import cp2024.solution.nodes.Node;

public class CancelDown extends PrioritezedTask {
    private Node nodeToCancel;

    public CancelDown(ExecutorService executor, Node nodeToCancel) {
        super(executor);
        this.nodeToCancel = nodeToCancel;
    }

    @Override
    public void run() {
        this.nodeToCancel.lock.lock();
        if (this.nodeToCancel.isCanceled() == false) {
            this.nodeToCancel.cancel(executor);
        }
        this.nodeToCancel.lock.unlock();
    }
}

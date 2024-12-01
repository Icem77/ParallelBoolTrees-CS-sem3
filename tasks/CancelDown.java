package cp2024.solution.tasks;

import java.util.concurrent.ExecutorService;

import cp2024.solution.nodes.Node;

public class CancelDown extends Task {
    private Node nodeToCancel;

    public CancelDown(ExecutorService executor, Node nodeToCancel) {
        super(executor);
        this.nodeToCancel = nodeToCancel;
    }

    @Override
    public void run() {
        synchronized (nodeToCancel) {
            if (nodeToCancel.isCanceled() == false) {
                nodeToCancel.cancel(executor);
            }
        }
    }
}

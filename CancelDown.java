package cp2024.solution;

import java.util.concurrent.ExecutorService;

public class CancelDown extends TaskWExecutor {
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

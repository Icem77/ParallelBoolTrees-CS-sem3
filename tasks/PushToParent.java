package cp2024.solution.tasks;

import java.util.concurrent.ExecutorService;

import cp2024.solution.nodes.Node;

public class PushToParent extends PrioritezedTask {
    private Node parentNode;
    private Boolean valueToPush;

    public PushToParent(ExecutorService executor, Node parentNode, Boolean valueToPush) {
        super(executor);
        this.parentNode = parentNode;
        this.valueToPush = valueToPush;
    }

    @Override
    public void run() {
        synchronized (this.parentNode) {
            if (this.parentNode.isCanceled() == false) {
                this.parentNode.takeSubresult(executor, valueToPush);
                this.parentNode.check(executor);
            }
        }
    }
}
